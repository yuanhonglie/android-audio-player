LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
	version.c \
	fixed.c \
	bit.c \
	timer.c \
	stream.c \
	frame.c  \
	synth.c \
	decoder.c \
	layer12.c \
	layer3.c \
	huffman.c \
	NativeMP3Decoder.cpp
	
LOCAL_ARM_MODE := arm

LOCAL_MODULE:= mad

LOCAL_C_INCLUDES := \
    $(LOCAL_PATH)/android 

LOCAL_CFLAGS := \
    -DHAVE_CONFIG_H \
    -DFPM_DEFAULT

LOCAL_LDLIBS :=-llog

include $(BUILD_SHARED_LIBRARY)