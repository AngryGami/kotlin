/*
 * Copyright 2010-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

#include "ExtraObjectData.hpp"

#include "ObjectOps.hpp"
#include "PointerBits.h"
#include "Weak.h"
#include "ExtraObjectDataFactory.hpp"

#ifdef KONAN_OBJC_INTEROP
#include "ObjCMMAPI.h"
#endif

using namespace kotlin;

// static
mm::ExtraObjectData& mm::ExtraObjectData::Install(ObjHeader* object) noexcept {
    // TODO: Consider extracting initialization scheme with speculative load.
    // `object->typeInfoOrMeta_` is assigned at most once. If we read some old value (i.e. not a meta object),
    // we will fail at CAS below. If we read the new value, we will immediately return it.
    TypeInfo* typeInfo = object->typeInfoOrMetaAcquire();

    if (auto* metaObject = ObjHeader::AsMetaObject(typeInfo)) {
        return mm::ExtraObjectData::FromMetaObjHeader(metaObject);
    }

    RuntimeCheck(!hasPointerBits(typeInfo, OBJECT_TAG_MASK), "Object must not be tagged");

    auto *threadData = mm::ThreadRegistry::Instance().CurrentThreadData();
    auto& data = mm::ExtraObjectDataFactory::Instance().CreateExtraObjectDataForObject(threadData, object, typeInfo);

    if (!compareExchange(object->typeInfoOrMeta_, typeInfo, reinterpret_cast<TypeInfo*>(&data))) {
        // Somebody else created `mm::ExtraObjectData` for this object
        mm::ExtraObjectDataFactory::Instance().DestroyExtraObjectData(threadData, data);
        return *reinterpret_cast<mm::ExtraObjectData*>(typeInfo);
    }

    return data;
}

void mm::ExtraObjectData::Uninstall() noexcept {
    auto *object = GetBaseObject();
    atomicSetRelease(const_cast<const TypeInfo**>(&object->typeInfoOrMeta_), typeInfo_);
    RuntimeAssert(!object->has_meta_object(), "Object has metaobject after removing metaobject");

#ifdef KONAN_OBJC_INTEROP
    Kotlin_ObjCExport_releaseAssociatedObject(associatedObject_);
    associatedObject_ = nullptr;
#endif
}

void mm::ExtraObjectData::DetachAssociatedObject() noexcept {
#ifdef KONAN_OBJC_INTEROP
    Kotlin_ObjCExport_detachAssociatedObject(associatedObject_);
#endif
}

bool mm::ExtraObjectData::HasAssociatedObject() noexcept {
#ifdef KONAN_OBJC_INTEROP
    return associatedObject_ != nullptr;
#else
    return false;
#endif
}


void mm::ExtraObjectData::ClearWeakReferenceCounter() noexcept {
    if (!HasWeakReferenceCounter()) return;

    auto *object = GetBaseObject();
    WeakReferenceCounterClear(GetWeakReferenceCounter());
    // Not using `mm::SetHeapRef here`, because this code is called during sweep phase by the GC thread,
    // and so cannot affect marking.
    // TODO: Asserts on the above?
    weakReferenceCounterOrBaseObject_ = object;
}

mm::ExtraObjectData::~ExtraObjectData() {
    RuntimeAssert(!HasWeakReferenceCounter(), "Object must have cleared weak references");

#ifdef KONAN_OBJC_INTEROP
    RuntimeAssert(associatedObject_ == nullptr, "Object must have cleared associated object");
#endif
}

void mm::ExtraObjectData::mark() noexcept {
    // TODO: If ExtraObjectData is a proper object, it can be a weak counter itself,
    //       and the following are unnecessary.
    if (HasWeakReferenceCounter()) {
        WeakReferenceCounterMark(GetWeakReferenceCounter());
    }
#ifdef KONAN_OBJC_INTEROP
    Kotlin_ObjCExport_markAssociatedObject(associatedObject_);
#endif
}

void mm::ExtraObjectData::resetMark() noexcept {
    // TODO: If ExtraObjectData is a proper object, it can be a weak counter itself,
    //       and the following are unnecessary.
    if (HasWeakReferenceCounter()) {
        WeakReferenceCounterResetMark(GetWeakReferenceCounter());
    }
#ifdef KONAN_OBJC_INTEROP
    Kotlin_ObjCExport_resetMarkAssociatedObject(associatedObject_);
#endif
}
