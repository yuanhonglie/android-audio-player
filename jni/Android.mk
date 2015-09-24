LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
	libmad/version.c \
	libmad/fixed.c \
	libmad/bit.c \
	libmad/timer.c \
	libmad/stream.c \
	libmad/frame.c  \
	libmad/synth.c \
	libmad/decoder.c \
	libmad/layer12.c \
	libmad/layer3.c \
	libmad/huffman.c \
	soundtouch/AAFilter.cpp \
	soundtouch/BPMDetect.cpp \
	soundtouch/cpu_detect_x86.cpp \
	soundtouch/FIFOSampleBuffer.cpp \
	soundtouch/FIRFilter.cpp \
	soundtouch/mmx_optimized.cpp \
	soundtouch/PeakFinder.cpp \
	soundtouch/RateTransposer.cpp \
	soundtouch/SoundTouch.cpp \
	soundtouch/sse_optimized.cpp \
	soundtouch/TDStretch.cpp \
	MP3Decoder.cpp
	
LOCAL_ARM_MODE := arm
LOCAL_MODULE:= decoder

LOCAL_C_INCLUDES := \
    $(LOCAL_PATH)/libmad/android \
    $(LOCAL_PATH)/libmad \
    $(LOCAL_PATH)/soundtouch

LOCAL_CFLAGS := \
    -DHAVE_CONFIG_H \
    -DFPM_DEFAULT

LOCAL_LDLIBS :=-llog

include $(BUILD_SHARED_LIBRARY)