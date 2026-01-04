#  Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
#  
#  This software is the proprietary information of Sun Microsystems, Inc.  
#  Use is subject to license terms.


###############
#### TOOLS ####
###############

# Versions
PERL_VER	=v5

ifdef DEBUG_BUILD
DEFINES+=-DDEBUG -D_DEBUG
VERGEN_FLAGS+=-debug
endif

# DEBUG/OPTIMIZE SETTINGS.  Override as needed in the platform definitions
CC_DEBUG=
C_DEBUG=
LD_DEBUG=

# PROFILE SETTINGS. Override as needed in the platform definitions
CC_PROFILE=
LD_PROFILE=

# BROWSE SETTINGS. Override as needed in the platform definitions
CC_BROWSE=
C_BROWSE=
LD_BROWSE=

# PURIFY SETTINGS. Override as needed.  Define PURIFY to enable
CC_PURIFY=
PRELINK=

# QUANTIFY SETTINGS. Override as needed in the platform definitions.
CC_QUANTIFY=

ifndef USE_AUTO_VERSION_INSERTION
SKIP_AUTO_VERSION_INSERTION=1
endif

ifndef SKIP_AUTO_VERSION_INSERTION
ifndef SKIP_VERSION_REGISTRY_LIB
SYSTEM_LIB+=nscpVer
SYSTEM_INC+=-I$(NSPR_INCLUDE)
DEFINES+=-DINCLUDE_AUTO_VERSION_REGISTRATION
endif
endif

CC_INCL		= $(LOCAL_INC) $(PROJECT_INC) $(SUBSYS_INC) \
		  $(SYSTEM_INC) $(PLATFORM_INC) $(INCLUDES) $(LATE_INCLUDES)

CC_DEFS		= $(LOCAL_DEF) $(PROJECT_DEF) $(SUBSYS_DEF) \
		  $(SYSTEM_DEF) $(PLATFORM_DEF) $(DEFINES)

CC_OPTS		= $(LOCAL_CC_OPTS) $(PROJECT_CC_OPTS) $(SUBSYS_CC_OPTS) \
		  $(SYSTEM_CC_OPTS) $(PLATFORM_CC_OPTS)

C_OPTS          = $(LOCAL_C_OPTS) $(PROJECT_C_OPTS) $(SUBSYS_C_OPTS) \
                  $(SYSTEM_C_OPTS) $(PLATFORM_C_OPTS)

LD_LIBDIRS_RAW  = $(LOCAL_LIBDIRS) $(PROJECT_LIBDIRS) $(SUBSYS_LIBDIRS) \
                  $(SYSTEM_LIBDIRS) $(PLATFORM_LIBDIRS) $(LIBDIRS) \
		  $(LATE_LIBDIRS)

ifeq ($(OS_ARCH),WINNT) 
LD_LIBDIRS      = $(addprefix /LIBPATH:, $(LD_LIBDIRS_RAW))
else
LD_LIBDIRS      = $(addprefix -L, $(LD_LIBDIRS_RAW))
endif

LD_LIBS_RAW	= $(LOCAL_LIB) $(PROJECT_LIB) $(SUBSYS_LIB) \
		  $(LIBS) $(SYSTEM_LIB) $(PLATFORM_LIB)
ifeq ($(OS_ARCH),WINNT)
LD_LIBS      	= $(addsuffix .lib, $(LD_LIBS_RAW))
else
LD_LIBS        	= $(addprefix -l, $(LD_LIBS_RAW))
endif

ifneq ($(OS_ARCH),WINNT)
LD_RPATHS      	= $(addprefix $(RPATH_PREFIX), $(LD_RPATH))
endif

LD_OPTS		= $(LOCAL_LD_OPTS) $(PROJECT_LD_OPTS) $(SUBSYS_LD_OPTS) \
		  $(SYSTEM_LD_OPTS) $(PLATFORM_LD_OPTS)

LD_FLAGS	= $(LD_PREFLAGS) $(LD_OPTS) $(LD_LIBDIRS) \
		  $(LD_DEBUG) $(LD_PROFILE) $(LD_BROWSE) $(LD_POSTFLAGS)

CC_FLAGS	= $(CC_PREFLAGS) $(CC_OPTS) $(CC_DEFS) $(CC_INCL) \
		  $(CC_DEBUG) $(CC_PROFILE) $(CC_BROWSE) $(CC_POSTFLAGS)

C++FLAGS	= $(C++PREFLAGS) $(CC_OPTS) $(CC_DEFS) $(CC_INCL) \
		  $(CC_DEBUG) $(CC_PROFILE) $(CC_BROWSE) $(C++POSTFLAGS)

C_FLAGS         = $(C_PREFLAGS) $(C_OPTS) $(CC_DEFS) $(CC_INCL) \
                  $(C_DEBUG) $(C_PROFILE) $(C_BROWSE) $(C_POSTFLAGS)

NOSUCHFILE=/thisfilemustnotexist
