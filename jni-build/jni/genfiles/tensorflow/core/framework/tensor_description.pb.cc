// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tensorflow/core/framework/tensor_description.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "tensorflow/core/framework/tensor_description.pb.h"

#include <algorithm>

#include <google/protobuf/stubs/common.h>
#include <google/protobuf/stubs/port.h>
#include <google/protobuf/stubs/once.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/wire_format_lite_inl.h>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/generated_message_reflection.h>
#include <google/protobuf/reflection_ops.h>
#include <google/protobuf/wire_format.h>
// @@protoc_insertion_point(includes)

namespace tensorflow {

namespace {

const ::google::protobuf::Descriptor* TensorDescription_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  TensorDescription_reflection_ = NULL;

}  // namespace


void protobuf_AssignDesc_tensorflow_2fcore_2fframework_2ftensor_5fdescription_2eproto() {
  protobuf_AddDesc_tensorflow_2fcore_2fframework_2ftensor_5fdescription_2eproto();
  const ::google::protobuf::FileDescriptor* file =
    ::google::protobuf::DescriptorPool::generated_pool()->FindFileByName(
      "tensorflow/core/framework/tensor_description.proto");
  GOOGLE_CHECK(file != NULL);
  TensorDescription_descriptor_ = file->message_type(0);
  static const int TensorDescription_offsets_[3] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TensorDescription, dtype_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TensorDescription, shape_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TensorDescription, allocation_description_),
  };
  TensorDescription_reflection_ =
    ::google::protobuf::internal::GeneratedMessageReflection::NewGeneratedMessageReflection(
      TensorDescription_descriptor_,
      TensorDescription::default_instance_,
      TensorDescription_offsets_,
      -1,
      -1,
      -1,
      sizeof(TensorDescription),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TensorDescription, _internal_metadata_),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TensorDescription, _is_default_instance_));
}

namespace {

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AssignDescriptors_once_);
inline void protobuf_AssignDescriptorsOnce() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AssignDescriptors_once_,
                 &protobuf_AssignDesc_tensorflow_2fcore_2fframework_2ftensor_5fdescription_2eproto);
}

void protobuf_RegisterTypes(const ::std::string&) {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
      TensorDescription_descriptor_, &TensorDescription::default_instance());
}

}  // namespace

void protobuf_ShutdownFile_tensorflow_2fcore_2fframework_2ftensor_5fdescription_2eproto() {
  delete TensorDescription::default_instance_;
  delete TensorDescription_reflection_;
}

void protobuf_AddDesc_tensorflow_2fcore_2fframework_2ftensor_5fdescription_2eproto() {
  static bool already_here = false;
  if (already_here) return;
  already_here = true;
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::tensorflow::protobuf_AddDesc_tensorflow_2fcore_2fframework_2ftypes_2eproto();
  ::tensorflow::protobuf_AddDesc_tensorflow_2fcore_2fframework_2ftensor_5fshape_2eproto();
  ::tensorflow::protobuf_AddDesc_tensorflow_2fcore_2fframework_2fallocation_5fdescription_2eproto();
  ::google::protobuf::DescriptorPool::InternalAddGeneratedFile(
    "\n2tensorflow/core/framework/tensor_descr"
    "iption.proto\022\ntensorflow\032%tensorflow/cor"
    "e/framework/types.proto\032,tensorflow/core"
    "/framework/tensor_shape.proto\0326tensorflo"
    "w/core/framework/allocation_description."
    "proto\"\250\001\n\021TensorDescription\022#\n\005dtype\030\001 \001"
    "(\0162\024.tensorflow.DataType\022+\n\005shape\030\002 \001(\0132"
    "\034.tensorflow.TensorShapeProto\022A\n\026allocat"
    "ion_description\030\004 \001(\0132!.tensorflow.Alloc"
    "ationDescriptionB5\n\030org.tensorflow.frame"
    "workB\027TensorDescriptionProtosP\001b\006proto3", 439);
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedFile(
    "tensorflow/core/framework/tensor_description.proto", &protobuf_RegisterTypes);
  TensorDescription::default_instance_ = new TensorDescription();
  TensorDescription::default_instance_->InitAsDefaultInstance();
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_tensorflow_2fcore_2fframework_2ftensor_5fdescription_2eproto);
}

// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_tensorflow_2fcore_2fframework_2ftensor_5fdescription_2eproto {
  StaticDescriptorInitializer_tensorflow_2fcore_2fframework_2ftensor_5fdescription_2eproto() {
    protobuf_AddDesc_tensorflow_2fcore_2fframework_2ftensor_5fdescription_2eproto();
  }
} static_descriptor_initializer_tensorflow_2fcore_2fframework_2ftensor_5fdescription_2eproto_;

namespace {

static void MergeFromFail(int line) GOOGLE_ATTRIBUTE_COLD;
static void MergeFromFail(int line) {
  GOOGLE_CHECK(false) << __FILE__ << ":" << line;
}

}  // namespace


// ===================================================================

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const int TensorDescription::kDtypeFieldNumber;
const int TensorDescription::kShapeFieldNumber;
const int TensorDescription::kAllocationDescriptionFieldNumber;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

TensorDescription::TensorDescription()
  : ::google::protobuf::Message(), _internal_metadata_(NULL) {
  SharedCtor();
  // @@protoc_insertion_point(constructor:tensorflow.TensorDescription)
}

void TensorDescription::InitAsDefaultInstance() {
  _is_default_instance_ = true;
  shape_ = const_cast< ::tensorflow::TensorShapeProto*>(&::tensorflow::TensorShapeProto::default_instance());
  allocation_description_ = const_cast< ::tensorflow::AllocationDescription*>(&::tensorflow::AllocationDescription::default_instance());
}

TensorDescription::TensorDescription(const TensorDescription& from)
  : ::google::protobuf::Message(),
    _internal_metadata_(NULL) {
  SharedCtor();
  MergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:tensorflow.TensorDescription)
}

void TensorDescription::SharedCtor() {
    _is_default_instance_ = false;
  _cached_size_ = 0;
  dtype_ = 0;
  shape_ = NULL;
  allocation_description_ = NULL;
}

TensorDescription::~TensorDescription() {
  // @@protoc_insertion_point(destructor:tensorflow.TensorDescription)
  SharedDtor();
}

void TensorDescription::SharedDtor() {
  if (this != default_instance_) {
    delete shape_;
    delete allocation_description_;
  }
}

void TensorDescription::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* TensorDescription::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return TensorDescription_descriptor_;
}

const TensorDescription& TensorDescription::default_instance() {
  if (default_instance_ == NULL) protobuf_AddDesc_tensorflow_2fcore_2fframework_2ftensor_5fdescription_2eproto();
  return *default_instance_;
}

TensorDescription* TensorDescription::default_instance_ = NULL;

TensorDescription* TensorDescription::New(::google::protobuf::Arena* arena) const {
  TensorDescription* n = new TensorDescription;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void TensorDescription::Clear() {
  dtype_ = 0;
  if (GetArenaNoVirtual() == NULL && shape_ != NULL) delete shape_;
  shape_ = NULL;
  if (GetArenaNoVirtual() == NULL && allocation_description_ != NULL) delete allocation_description_;
  allocation_description_ = NULL;
}

bool TensorDescription::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  // @@protoc_insertion_point(parse_start:tensorflow.TensorDescription)
  for (;;) {
    ::std::pair< ::google::protobuf::uint32, bool> p = input->ReadTagWithCutoff(127);
    tag = p.first;
    if (!p.second) goto handle_unusual;
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // optional .tensorflow.DataType dtype = 1;
      case 1: {
        if (tag == 8) {
          int value;
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   int, ::google::protobuf::internal::WireFormatLite::TYPE_ENUM>(
                 input, &value)));
          set_dtype(static_cast< ::tensorflow::DataType >(value));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(18)) goto parse_shape;
        break;
      }

      // optional .tensorflow.TensorShapeProto shape = 2;
      case 2: {
        if (tag == 18) {
         parse_shape:
          DO_(::google::protobuf::internal::WireFormatLite::ReadMessageNoVirtual(
               input, mutable_shape()));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(34)) goto parse_allocation_description;
        break;
      }

      // optional .tensorflow.AllocationDescription allocation_description = 4;
      case 4: {
        if (tag == 34) {
         parse_allocation_description:
          DO_(::google::protobuf::internal::WireFormatLite::ReadMessageNoVirtual(
               input, mutable_allocation_description()));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectAtEnd()) goto success;
        break;
      }

      default: {
      handle_unusual:
        if (tag == 0 ||
            ::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
          goto success;
        }
        DO_(::google::protobuf::internal::WireFormatLite::SkipField(input, tag));
        break;
      }
    }
  }
success:
  // @@protoc_insertion_point(parse_success:tensorflow.TensorDescription)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:tensorflow.TensorDescription)
  return false;
#undef DO_
}

void TensorDescription::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:tensorflow.TensorDescription)
  // optional .tensorflow.DataType dtype = 1;
  if (this->dtype() != 0) {
    ::google::protobuf::internal::WireFormatLite::WriteEnum(
      1, this->dtype(), output);
  }

  // optional .tensorflow.TensorShapeProto shape = 2;
  if (this->has_shape()) {
    ::google::protobuf::internal::WireFormatLite::WriteMessageMaybeToArray(
      2, *this->shape_, output);
  }

  // optional .tensorflow.AllocationDescription allocation_description = 4;
  if (this->has_allocation_description()) {
    ::google::protobuf::internal::WireFormatLite::WriteMessageMaybeToArray(
      4, *this->allocation_description_, output);
  }

  // @@protoc_insertion_point(serialize_end:tensorflow.TensorDescription)
}

::google::protobuf::uint8* TensorDescription::SerializeWithCachedSizesToArray(
    ::google::protobuf::uint8* target) const {
  // @@protoc_insertion_point(serialize_to_array_start:tensorflow.TensorDescription)
  // optional .tensorflow.DataType dtype = 1;
  if (this->dtype() != 0) {
    target = ::google::protobuf::internal::WireFormatLite::WriteEnumToArray(
      1, this->dtype(), target);
  }

  // optional .tensorflow.TensorShapeProto shape = 2;
  if (this->has_shape()) {
    target = ::google::protobuf::internal::WireFormatLite::
      WriteMessageNoVirtualToArray(
        2, *this->shape_, target);
  }

  // optional .tensorflow.AllocationDescription allocation_description = 4;
  if (this->has_allocation_description()) {
    target = ::google::protobuf::internal::WireFormatLite::
      WriteMessageNoVirtualToArray(
        4, *this->allocation_description_, target);
  }

  // @@protoc_insertion_point(serialize_to_array_end:tensorflow.TensorDescription)
  return target;
}

int TensorDescription::ByteSize() const {
  int total_size = 0;

  // optional .tensorflow.DataType dtype = 1;
  if (this->dtype() != 0) {
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::EnumSize(this->dtype());
  }

  // optional .tensorflow.TensorShapeProto shape = 2;
  if (this->has_shape()) {
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::MessageSizeNoVirtual(
        *this->shape_);
  }

  // optional .tensorflow.AllocationDescription allocation_description = 4;
  if (this->has_allocation_description()) {
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::MessageSizeNoVirtual(
        *this->allocation_description_);
  }

  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = total_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void TensorDescription::MergeFrom(const ::google::protobuf::Message& from) {
  if (GOOGLE_PREDICT_FALSE(&from == this)) MergeFromFail(__LINE__);
  const TensorDescription* source = 
      ::google::protobuf::internal::DynamicCastToGenerated<const TensorDescription>(
          &from);
  if (source == NULL) {
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
    MergeFrom(*source);
  }
}

void TensorDescription::MergeFrom(const TensorDescription& from) {
  if (GOOGLE_PREDICT_FALSE(&from == this)) MergeFromFail(__LINE__);
  if (from.dtype() != 0) {
    set_dtype(from.dtype());
  }
  if (from.has_shape()) {
    mutable_shape()->::tensorflow::TensorShapeProto::MergeFrom(from.shape());
  }
  if (from.has_allocation_description()) {
    mutable_allocation_description()->::tensorflow::AllocationDescription::MergeFrom(from.allocation_description());
  }
}

void TensorDescription::CopyFrom(const ::google::protobuf::Message& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void TensorDescription::CopyFrom(const TensorDescription& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool TensorDescription::IsInitialized() const {

  return true;
}

void TensorDescription::Swap(TensorDescription* other) {
  if (other == this) return;
  InternalSwap(other);
}
void TensorDescription::InternalSwap(TensorDescription* other) {
  std::swap(dtype_, other->dtype_);
  std::swap(shape_, other->shape_);
  std::swap(allocation_description_, other->allocation_description_);
  _internal_metadata_.Swap(&other->_internal_metadata_);
  std::swap(_cached_size_, other->_cached_size_);
}

::google::protobuf::Metadata TensorDescription::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = TensorDescription_descriptor_;
  metadata.reflection = TensorDescription_reflection_;
  return metadata;
}

#if PROTOBUF_INLINE_NOT_IN_HEADERS
// TensorDescription

// optional .tensorflow.DataType dtype = 1;
void TensorDescription::clear_dtype() {
  dtype_ = 0;
}
 ::tensorflow::DataType TensorDescription::dtype() const {
  // @@protoc_insertion_point(field_get:tensorflow.TensorDescription.dtype)
  return static_cast< ::tensorflow::DataType >(dtype_);
}
 void TensorDescription::set_dtype(::tensorflow::DataType value) {
  
  dtype_ = value;
  // @@protoc_insertion_point(field_set:tensorflow.TensorDescription.dtype)
}

// optional .tensorflow.TensorShapeProto shape = 2;
bool TensorDescription::has_shape() const {
  return !_is_default_instance_ && shape_ != NULL;
}
void TensorDescription::clear_shape() {
  if (GetArenaNoVirtual() == NULL && shape_ != NULL) delete shape_;
  shape_ = NULL;
}
const ::tensorflow::TensorShapeProto& TensorDescription::shape() const {
  // @@protoc_insertion_point(field_get:tensorflow.TensorDescription.shape)
  return shape_ != NULL ? *shape_ : *default_instance_->shape_;
}
::tensorflow::TensorShapeProto* TensorDescription::mutable_shape() {
  
  if (shape_ == NULL) {
    shape_ = new ::tensorflow::TensorShapeProto;
  }
  // @@protoc_insertion_point(field_mutable:tensorflow.TensorDescription.shape)
  return shape_;
}
::tensorflow::TensorShapeProto* TensorDescription::release_shape() {
  
  ::tensorflow::TensorShapeProto* temp = shape_;
  shape_ = NULL;
  return temp;
}
void TensorDescription::set_allocated_shape(::tensorflow::TensorShapeProto* shape) {
  delete shape_;
  shape_ = shape;
  if (shape) {
    
  } else {
    
  }
  // @@protoc_insertion_point(field_set_allocated:tensorflow.TensorDescription.shape)
}

// optional .tensorflow.AllocationDescription allocation_description = 4;
bool TensorDescription::has_allocation_description() const {
  return !_is_default_instance_ && allocation_description_ != NULL;
}
void TensorDescription::clear_allocation_description() {
  if (GetArenaNoVirtual() == NULL && allocation_description_ != NULL) delete allocation_description_;
  allocation_description_ = NULL;
}
const ::tensorflow::AllocationDescription& TensorDescription::allocation_description() const {
  // @@protoc_insertion_point(field_get:tensorflow.TensorDescription.allocation_description)
  return allocation_description_ != NULL ? *allocation_description_ : *default_instance_->allocation_description_;
}
::tensorflow::AllocationDescription* TensorDescription::mutable_allocation_description() {
  
  if (allocation_description_ == NULL) {
    allocation_description_ = new ::tensorflow::AllocationDescription;
  }
  // @@protoc_insertion_point(field_mutable:tensorflow.TensorDescription.allocation_description)
  return allocation_description_;
}
::tensorflow::AllocationDescription* TensorDescription::release_allocation_description() {
  
  ::tensorflow::AllocationDescription* temp = allocation_description_;
  allocation_description_ = NULL;
  return temp;
}
void TensorDescription::set_allocated_allocation_description(::tensorflow::AllocationDescription* allocation_description) {
  delete allocation_description_;
  allocation_description_ = allocation_description;
  if (allocation_description) {
    
  } else {
    
  }
  // @@protoc_insertion_point(field_set_allocated:tensorflow.TensorDescription.allocation_description)
}

#endif  // PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace tensorflow

// @@protoc_insertion_point(global_scope)
