ifeq ($(strip $(BUILD_WITH_GST)),true)

LOCAL_PATH:= $(call my-dir)
LIBMAD_LOCAL_PATH:=$(LOCAL_PATH)/libmad

include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
	$(LIBMAD_LOCAL_PATH)/version.c \
	$(LIBMAD_LOCAL_PATH)/fixed.c \
	$(LIBMAD_LOCAL_PATH)/bit.c \
	$(LIBMAD_LOCAL_PATH)/timer.c \
	$(LIBMAD_LOCAL_PATH)/stream.c \
	$(LIBMAD_LOCAL_PATH)/frame.c  \
	$(LIBMAD_LOCAL_PATH)/synth.c \
	$(LIBMAD_LOCAL_PATH)/decoder.c \
	$(LIBMAD_LOCAL_PATH)/layer12.c \
	$(LIBMAD_LOCAL_PATH)/layer3.c \
	$(LIBMAD_LOCAL_PATH)/huffman.c

LOCAL_SHARED_LIBRARIES := 

LOCAL_MODULE:= libmad

LOCAL_C_INCLUDES := \
    $(LIBMAD_LOCAL_PATH)/android 

LOCAL_CFLAGS := \
    -DHAVE_CONFIG_H \
    -DFPM_DEFAULT

include $(BUILD_STATIC_LIBRARY)

endif
