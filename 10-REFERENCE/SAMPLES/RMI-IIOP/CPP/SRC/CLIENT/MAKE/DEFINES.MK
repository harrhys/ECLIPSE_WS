#  Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
#  
#  This software is the proprietary information of Sun Microsystems, Inc.  
#  Use is subject to license terms.

# This file determines the hardware and OS architecture we're running and the
# build variant that we want, then it includes the other files.

# First we collect some information from UNAME.  To allow speedier makefile
# execution, these can be set in the environment.

ifndef UNAME_REPORTS
UNAME_REPORTS:=$(shell uname)
endif # UNAME_REPORTS

ifndef UNAME_OS_RELEASE
UNAME_OS_RELEASE := $(shell uname -r)
endif #UNAME_OS_RELEASE

ifndef UNAME_OS_ARCH
UNAME_OS_ARCH:=$(subst /,_,$(shell uname -s))
endif #UNAME_OS_ARCH

OS_ARCH=$(UNAME_OS_ARCH)
OS_RELEASE=$(UNAME_OS_RELEASE)
OS_CPU=

# This is your chance to lie to the makefile.  Change OS_ARCH and OS_RELEASE
# as needed for various exceptional cases here.

####################
#### WINDOWS NT ####
####################

# There are many uname's for NT.  We need to get a single OS_CONFIG from them:
# We're going to use "WINNT" as the NT OS_ARCH string...  so if you find
# another, put in the aliasing here.  i.e.
ifeq ($(findstring CYGWIN_NT, $(OS_ARCH)), CYGWIN_NT)
OS_ARCH = WINNT
endif
ifeq ($(findstring CYGWIN32_NT, $(OS_ARCH)), CYGWIN32_NT)
OS_ARCH = WINNT
endif
ifeq ($(OS_ARCH),Windows_NT)
OS_ARCH = WINNT
endif

# Force OS release to 4.0 for now so it finds the components for win2k
ifeq ($(OS_ARCH),WINNT)
OS_RELEASE = 4.0
endif

ifeq ($(OS_ARCH),WINNT)
BUILD_ARCH=X86
BUILD_OS=NT
BUILD_VER=4.0
endif

#############
#### AIX ####
#############

ifeq ($(OS_ARCH),AIX)
UNAME_OS_RELEASE := $(shell uname -v).$(shell uname -r)
OS_RELEASE=$(UNAME_OS_RELEASE)

BUILD_ARCH=POWER
BUILD_OS=$(OS_ARCH)
BUILD_VER=$(OS_RELEASE)
endif

##############
#### IRIX ####
#########*####

# Force the IRIX64 machines to use IRIX.
ifeq ($(OS_ARCH),IRIX64)
OS_ARCH = IRIX
endif

ifeq ($(OS_ARCH), IRIX)
BUILD_ARCH=MIPS
BUILD_OS=IRIX
BUILD_VER=6.5
endif

###############
#### SunOS ####
##########*####

ifeq ($(OS_ARCH),SunOS)
BUILD_ARCH=SPARC
BUILD_OS=SOLARIS
# the following is a workaround for SunOS -> Solaris version translation
# only works for Solaris 2.5 and 2.6
ifeq ($(OS_RELEASE),5.6)
BUILD_VER=2.6
endif
ifeq ($(OS_RELEASE),5.7)
BUILD_VER=2.7
endif
ifeq ($(OS_RELEASE),5.8)
BUILD_VER=2.8
endif
ifeq ($(shell arch), i86pc)
BUILD_ARCH=i86pc
BUILD_VER=2.8
OS_CPU=i86pc
OS_RELEASE=5.8
endif
endif

##############
#### OSF1 ####
#########*####

ifeq ($(OS_ARCH),OSF1)
BUILD_ARCH=ALPHA
BUILD_OS=$(OS_ARCH)
BUILD_VER=$(OS_RELEASE)
endif

###############
#### Linux ####
##########*####

ifeq ($(OS_ARCH), Linux)
ARCH_REPORTS=$(shell arch)
ifeq ($(ARCH_REPORTS), i686)
BUILD_ARCH=x86
else
BUILD_ARCH=UNKNOWN
endif
BUILD_OS=Linux

UNAME_MAJOR_VER=$(word 1, $(subst ., ,$(UNAME_OS_RELEASE)))
UNAME_MINOR_VER=$(word 2, $(subst ., ,$(UNAME_OS_RELEASE)))
OS_RELEASE=$(UNAME_MAJOR_VER).$(UNAME_MINOR_VER)
BUILD_VER=$(OS_RELEASE)
endif

###############
#### HP-UX ####
##########*####

ifeq ($(OS_ARCH), HP-UX)
BUILD_ARCH=HPPA
BUILD_OS=HP-UX
BUILD_VER=B.11.00
endif

ifndef OSVERSION
OSVERSION = $(subst .,0,$(UNAME_OS_RELEASE))
endif #OSVERSION

###############
## OS_CONFIG ##
###############
ifdef OS_CPU
OS_CONFIG = $(OS_ARCH)$(OS_RELEASE)_$(OS_CPU)
else
OS_CONFIG = $(OS_ARCH)$(OS_RELEASE)
endif

PLATFORM=${BUILD_ARCH}_${BUILD_OS}_${BUILD_VER}

###################
## BUILD VARIANT ##
###################

ifndef BUILD_VARIANT
BUILD_VARIANT=DEBUG
endif # BUILD_VARIANT

ifeq ($(BUILD_VARIANT),DEBUG)
DEBUG=1
DEBUG_BUILD=1
endif

ifeq ($(BUILD_VARIANT),OPTIMIZED)
OPTIMIZED_BUILD=1
NPROBE=1
endif

ifeq ($(BUILD_VARIANT),RELEASE)
OPTIMIZED_BUILD=1
NPROBE=1
endif

# ADD NEW BUILD_VARIANT TAGS HERE

ifdef OPTIMIZED_BUILD
OBJDIR_TAG = _OPT
else # OPTIMIZED_BUILD
ifdef DEBUG_BUILD
OBJDIR_TAG = _DBG
else # DEBUG_BUILD
OBJDIR_TAG = _UNKNOWN
endif # DEBUG_BUILD
endif # OPTIMIZED_BUILD

# if we're building java or cross compiling, we don't build the local platform.
# HOST_OBJDIR will point to the  local platform while OBJDIR will point
# to the target platform

HOST_OBJDIR:=$(OS_CONFIG)$(OBJDIR_TAG).OBJ

ifndef BUILD_JAVA
OBJDIR_NAME:=$(HOST_OBJDIR)
OBJDIR:=$(OBJDIR_NAME)
endif

#USE_JDK=1

include ${BUILD_ROOT}/make/defines_COMMON.mk
include ${BUILD_ROOT}/make/defines_${PLATFORM}.mk
