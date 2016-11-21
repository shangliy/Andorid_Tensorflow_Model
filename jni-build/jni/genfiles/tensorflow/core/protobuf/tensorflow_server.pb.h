// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tensorflow/core/protobuf/tensorflow_server.proto

#ifndef PROTOBUF_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto__INCLUDED
#define PROTOBUF_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 3000000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 3000000 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/arena.h>
#include <google/protobuf/arenastring.h>
#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/metadata.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/extension_set.h>
#include <google/protobuf/map.h>
#include <google/protobuf/map_field_inl.h>
#include <google/protobuf/unknown_field_set.h>
#include "tensorflow/core/protobuf/config.pb.h"
// @@protoc_insertion_point(includes)

namespace tensorflow {

// Internal implementation detail -- do not call these.
void protobuf_AddDesc_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto();
void protobuf_AssignDesc_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto();
void protobuf_ShutdownFile_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto();

class ClusterDef;
class JobDef;
class ServerDef;

// ===================================================================

class JobDef : public ::google::protobuf::Message {
 public:
  JobDef();
  virtual ~JobDef();

  JobDef(const JobDef& from);

  inline JobDef& operator=(const JobDef& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const JobDef& default_instance();

  void Swap(JobDef* other);

  // implements Message ----------------------------------------------

  inline JobDef* New() const { return New(NULL); }

  JobDef* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const JobDef& from);
  void MergeFrom(const JobDef& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  void InternalSwap(JobDef* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return _internal_metadata_.arena();
  }
  inline void* MaybeArenaPtr() const {
    return _internal_metadata_.raw_arena_ptr();
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------


  // accessors -------------------------------------------------------

  // optional string name = 1;
  void clear_name();
  static const int kNameFieldNumber = 1;
  const ::std::string& name() const;
  void set_name(const ::std::string& value);
  void set_name(const char* value);
  void set_name(const char* value, size_t size);
  ::std::string* mutable_name();
  ::std::string* release_name();
  void set_allocated_name(::std::string* name);

  // map<int32, string> tasks = 2;
  int tasks_size() const;
  void clear_tasks();
  static const int kTasksFieldNumber = 2;
  const ::google::protobuf::Map< ::google::protobuf::int32, ::std::string >&
      tasks() const;
  ::google::protobuf::Map< ::google::protobuf::int32, ::std::string >*
      mutable_tasks();

  // @@protoc_insertion_point(class_scope:tensorflow.JobDef)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  bool _is_default_instance_;
  ::google::protobuf::internal::ArenaStringPtr name_;
  typedef ::google::protobuf::internal::MapEntryLite<
      ::google::protobuf::int32, ::std::string,
      ::google::protobuf::internal::WireFormatLite::TYPE_INT32,
      ::google::protobuf::internal::WireFormatLite::TYPE_STRING,
      0 >
      JobDef_TasksEntry;
  ::google::protobuf::internal::MapField<
      ::google::protobuf::int32, ::std::string,
      ::google::protobuf::internal::WireFormatLite::TYPE_INT32,
      ::google::protobuf::internal::WireFormatLite::TYPE_STRING,
      0 > tasks_;
  mutable int _cached_size_;
  friend void  protobuf_AddDesc_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto();
  friend void protobuf_AssignDesc_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto();
  friend void protobuf_ShutdownFile_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto();

  void InitAsDefaultInstance();
  static JobDef* default_instance_;
};
// -------------------------------------------------------------------

class ClusterDef : public ::google::protobuf::Message {
 public:
  ClusterDef();
  virtual ~ClusterDef();

  ClusterDef(const ClusterDef& from);

  inline ClusterDef& operator=(const ClusterDef& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const ClusterDef& default_instance();

  void Swap(ClusterDef* other);

  // implements Message ----------------------------------------------

  inline ClusterDef* New() const { return New(NULL); }

  ClusterDef* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const ClusterDef& from);
  void MergeFrom(const ClusterDef& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  void InternalSwap(ClusterDef* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return _internal_metadata_.arena();
  }
  inline void* MaybeArenaPtr() const {
    return _internal_metadata_.raw_arena_ptr();
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // repeated .tensorflow.JobDef job = 1;
  int job_size() const;
  void clear_job();
  static const int kJobFieldNumber = 1;
  const ::tensorflow::JobDef& job(int index) const;
  ::tensorflow::JobDef* mutable_job(int index);
  ::tensorflow::JobDef* add_job();
  ::google::protobuf::RepeatedPtrField< ::tensorflow::JobDef >*
      mutable_job();
  const ::google::protobuf::RepeatedPtrField< ::tensorflow::JobDef >&
      job() const;

  // @@protoc_insertion_point(class_scope:tensorflow.ClusterDef)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  bool _is_default_instance_;
  ::google::protobuf::RepeatedPtrField< ::tensorflow::JobDef > job_;
  mutable int _cached_size_;
  friend void  protobuf_AddDesc_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto();
  friend void protobuf_AssignDesc_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto();
  friend void protobuf_ShutdownFile_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto();

  void InitAsDefaultInstance();
  static ClusterDef* default_instance_;
};
// -------------------------------------------------------------------

class ServerDef : public ::google::protobuf::Message {
 public:
  ServerDef();
  virtual ~ServerDef();

  ServerDef(const ServerDef& from);

  inline ServerDef& operator=(const ServerDef& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const ServerDef& default_instance();

  void Swap(ServerDef* other);

  // implements Message ----------------------------------------------

  inline ServerDef* New() const { return New(NULL); }

  ServerDef* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const ServerDef& from);
  void MergeFrom(const ServerDef& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  void InternalSwap(ServerDef* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return _internal_metadata_.arena();
  }
  inline void* MaybeArenaPtr() const {
    return _internal_metadata_.raw_arena_ptr();
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // optional .tensorflow.ClusterDef cluster = 1;
  bool has_cluster() const;
  void clear_cluster();
  static const int kClusterFieldNumber = 1;
  const ::tensorflow::ClusterDef& cluster() const;
  ::tensorflow::ClusterDef* mutable_cluster();
  ::tensorflow::ClusterDef* release_cluster();
  void set_allocated_cluster(::tensorflow::ClusterDef* cluster);

  // optional string job_name = 2;
  void clear_job_name();
  static const int kJobNameFieldNumber = 2;
  const ::std::string& job_name() const;
  void set_job_name(const ::std::string& value);
  void set_job_name(const char* value);
  void set_job_name(const char* value, size_t size);
  ::std::string* mutable_job_name();
  ::std::string* release_job_name();
  void set_allocated_job_name(::std::string* job_name);

  // optional int32 task_index = 3;
  void clear_task_index();
  static const int kTaskIndexFieldNumber = 3;
  ::google::protobuf::int32 task_index() const;
  void set_task_index(::google::protobuf::int32 value);

  // optional .tensorflow.ConfigProto default_session_config = 4;
  bool has_default_session_config() const;
  void clear_default_session_config();
  static const int kDefaultSessionConfigFieldNumber = 4;
  const ::tensorflow::ConfigProto& default_session_config() const;
  ::tensorflow::ConfigProto* mutable_default_session_config();
  ::tensorflow::ConfigProto* release_default_session_config();
  void set_allocated_default_session_config(::tensorflow::ConfigProto* default_session_config);

  // optional string protocol = 5;
  void clear_protocol();
  static const int kProtocolFieldNumber = 5;
  const ::std::string& protocol() const;
  void set_protocol(const ::std::string& value);
  void set_protocol(const char* value);
  void set_protocol(const char* value, size_t size);
  ::std::string* mutable_protocol();
  ::std::string* release_protocol();
  void set_allocated_protocol(::std::string* protocol);

  // @@protoc_insertion_point(class_scope:tensorflow.ServerDef)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  bool _is_default_instance_;
  ::tensorflow::ClusterDef* cluster_;
  ::google::protobuf::internal::ArenaStringPtr job_name_;
  ::tensorflow::ConfigProto* default_session_config_;
  ::google::protobuf::internal::ArenaStringPtr protocol_;
  ::google::protobuf::int32 task_index_;
  mutable int _cached_size_;
  friend void  protobuf_AddDesc_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto();
  friend void protobuf_AssignDesc_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto();
  friend void protobuf_ShutdownFile_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto();

  void InitAsDefaultInstance();
  static ServerDef* default_instance_;
};
// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// JobDef

// optional string name = 1;
inline void JobDef::clear_name() {
  name_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& JobDef::name() const {
  // @@protoc_insertion_point(field_get:tensorflow.JobDef.name)
  return name_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void JobDef::set_name(const ::std::string& value) {
  
  name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:tensorflow.JobDef.name)
}
inline void JobDef::set_name(const char* value) {
  
  name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:tensorflow.JobDef.name)
}
inline void JobDef::set_name(const char* value, size_t size) {
  
  name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:tensorflow.JobDef.name)
}
inline ::std::string* JobDef::mutable_name() {
  
  // @@protoc_insertion_point(field_mutable:tensorflow.JobDef.name)
  return name_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* JobDef::release_name() {
  
  return name_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void JobDef::set_allocated_name(::std::string* name) {
  if (name != NULL) {
    
  } else {
    
  }
  name_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), name);
  // @@protoc_insertion_point(field_set_allocated:tensorflow.JobDef.name)
}

// map<int32, string> tasks = 2;
inline int JobDef::tasks_size() const {
  return tasks_.size();
}
inline void JobDef::clear_tasks() {
  tasks_.Clear();
}
inline const ::google::protobuf::Map< ::google::protobuf::int32, ::std::string >&
JobDef::tasks() const {
  // @@protoc_insertion_point(field_map:tensorflow.JobDef.tasks)
  return tasks_.GetMap();
}
inline ::google::protobuf::Map< ::google::protobuf::int32, ::std::string >*
JobDef::mutable_tasks() {
  // @@protoc_insertion_point(field_mutable_map:tensorflow.JobDef.tasks)
  return tasks_.MutableMap();
}

// -------------------------------------------------------------------

// ClusterDef

// repeated .tensorflow.JobDef job = 1;
inline int ClusterDef::job_size() const {
  return job_.size();
}
inline void ClusterDef::clear_job() {
  job_.Clear();
}
inline const ::tensorflow::JobDef& ClusterDef::job(int index) const {
  // @@protoc_insertion_point(field_get:tensorflow.ClusterDef.job)
  return job_.Get(index);
}
inline ::tensorflow::JobDef* ClusterDef::mutable_job(int index) {
  // @@protoc_insertion_point(field_mutable:tensorflow.ClusterDef.job)
  return job_.Mutable(index);
}
inline ::tensorflow::JobDef* ClusterDef::add_job() {
  // @@protoc_insertion_point(field_add:tensorflow.ClusterDef.job)
  return job_.Add();
}
inline ::google::protobuf::RepeatedPtrField< ::tensorflow::JobDef >*
ClusterDef::mutable_job() {
  // @@protoc_insertion_point(field_mutable_list:tensorflow.ClusterDef.job)
  return &job_;
}
inline const ::google::protobuf::RepeatedPtrField< ::tensorflow::JobDef >&
ClusterDef::job() const {
  // @@protoc_insertion_point(field_list:tensorflow.ClusterDef.job)
  return job_;
}

// -------------------------------------------------------------------

// ServerDef

// optional .tensorflow.ClusterDef cluster = 1;
inline bool ServerDef::has_cluster() const {
  return !_is_default_instance_ && cluster_ != NULL;
}
inline void ServerDef::clear_cluster() {
  if (GetArenaNoVirtual() == NULL && cluster_ != NULL) delete cluster_;
  cluster_ = NULL;
}
inline const ::tensorflow::ClusterDef& ServerDef::cluster() const {
  // @@protoc_insertion_point(field_get:tensorflow.ServerDef.cluster)
  return cluster_ != NULL ? *cluster_ : *default_instance_->cluster_;
}
inline ::tensorflow::ClusterDef* ServerDef::mutable_cluster() {
  
  if (cluster_ == NULL) {
    cluster_ = new ::tensorflow::ClusterDef;
  }
  // @@protoc_insertion_point(field_mutable:tensorflow.ServerDef.cluster)
  return cluster_;
}
inline ::tensorflow::ClusterDef* ServerDef::release_cluster() {
  
  ::tensorflow::ClusterDef* temp = cluster_;
  cluster_ = NULL;
  return temp;
}
inline void ServerDef::set_allocated_cluster(::tensorflow::ClusterDef* cluster) {
  delete cluster_;
  cluster_ = cluster;
  if (cluster) {
    
  } else {
    
  }
  // @@protoc_insertion_point(field_set_allocated:tensorflow.ServerDef.cluster)
}

// optional string job_name = 2;
inline void ServerDef::clear_job_name() {
  job_name_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& ServerDef::job_name() const {
  // @@protoc_insertion_point(field_get:tensorflow.ServerDef.job_name)
  return job_name_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void ServerDef::set_job_name(const ::std::string& value) {
  
  job_name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:tensorflow.ServerDef.job_name)
}
inline void ServerDef::set_job_name(const char* value) {
  
  job_name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:tensorflow.ServerDef.job_name)
}
inline void ServerDef::set_job_name(const char* value, size_t size) {
  
  job_name_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:tensorflow.ServerDef.job_name)
}
inline ::std::string* ServerDef::mutable_job_name() {
  
  // @@protoc_insertion_point(field_mutable:tensorflow.ServerDef.job_name)
  return job_name_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* ServerDef::release_job_name() {
  
  return job_name_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void ServerDef::set_allocated_job_name(::std::string* job_name) {
  if (job_name != NULL) {
    
  } else {
    
  }
  job_name_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), job_name);
  // @@protoc_insertion_point(field_set_allocated:tensorflow.ServerDef.job_name)
}

// optional int32 task_index = 3;
inline void ServerDef::clear_task_index() {
  task_index_ = 0;
}
inline ::google::protobuf::int32 ServerDef::task_index() const {
  // @@protoc_insertion_point(field_get:tensorflow.ServerDef.task_index)
  return task_index_;
}
inline void ServerDef::set_task_index(::google::protobuf::int32 value) {
  
  task_index_ = value;
  // @@protoc_insertion_point(field_set:tensorflow.ServerDef.task_index)
}

// optional .tensorflow.ConfigProto default_session_config = 4;
inline bool ServerDef::has_default_session_config() const {
  return !_is_default_instance_ && default_session_config_ != NULL;
}
inline void ServerDef::clear_default_session_config() {
  if (GetArenaNoVirtual() == NULL && default_session_config_ != NULL) delete default_session_config_;
  default_session_config_ = NULL;
}
inline const ::tensorflow::ConfigProto& ServerDef::default_session_config() const {
  // @@protoc_insertion_point(field_get:tensorflow.ServerDef.default_session_config)
  return default_session_config_ != NULL ? *default_session_config_ : *default_instance_->default_session_config_;
}
inline ::tensorflow::ConfigProto* ServerDef::mutable_default_session_config() {
  
  if (default_session_config_ == NULL) {
    default_session_config_ = new ::tensorflow::ConfigProto;
  }
  // @@protoc_insertion_point(field_mutable:tensorflow.ServerDef.default_session_config)
  return default_session_config_;
}
inline ::tensorflow::ConfigProto* ServerDef::release_default_session_config() {
  
  ::tensorflow::ConfigProto* temp = default_session_config_;
  default_session_config_ = NULL;
  return temp;
}
inline void ServerDef::set_allocated_default_session_config(::tensorflow::ConfigProto* default_session_config) {
  delete default_session_config_;
  default_session_config_ = default_session_config;
  if (default_session_config) {
    
  } else {
    
  }
  // @@protoc_insertion_point(field_set_allocated:tensorflow.ServerDef.default_session_config)
}

// optional string protocol = 5;
inline void ServerDef::clear_protocol() {
  protocol_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& ServerDef::protocol() const {
  // @@protoc_insertion_point(field_get:tensorflow.ServerDef.protocol)
  return protocol_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void ServerDef::set_protocol(const ::std::string& value) {
  
  protocol_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:tensorflow.ServerDef.protocol)
}
inline void ServerDef::set_protocol(const char* value) {
  
  protocol_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:tensorflow.ServerDef.protocol)
}
inline void ServerDef::set_protocol(const char* value, size_t size) {
  
  protocol_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:tensorflow.ServerDef.protocol)
}
inline ::std::string* ServerDef::mutable_protocol() {
  
  // @@protoc_insertion_point(field_mutable:tensorflow.ServerDef.protocol)
  return protocol_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* ServerDef::release_protocol() {
  
  return protocol_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void ServerDef::set_allocated_protocol(::std::string* protocol) {
  if (protocol != NULL) {
    
  } else {
    
  }
  protocol_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), protocol);
  // @@protoc_insertion_point(field_set_allocated:tensorflow.ServerDef.protocol)
}

#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS
// -------------------------------------------------------------------

// -------------------------------------------------------------------


// @@protoc_insertion_point(namespace_scope)

}  // namespace tensorflow

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_tensorflow_2fcore_2fprotobuf_2ftensorflow_5fserver_2eproto__INCLUDED
