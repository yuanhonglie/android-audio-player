
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_ARM_MODE := arm

LOCAL_MODULE := soundtouch

LOCAL_SRC_FILES := \
	AAFilter.cpp \
	BPMDetect.cpp \
	cpu_detect_x86.cpp \
	FIFOSampleBuffer.cpp \
	FIRFilter.cpp \
	mmx_optimized.cpp \
	PeakFinder.cpp \
	RateTransposer.cpp \
	SoundTouch.cpp \
	sse_optimized.cpp \
	TDStretch.cpp


include $(BUILD_STATIC_LIBRARY)
