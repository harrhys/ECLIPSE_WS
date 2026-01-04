#  Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
#  
#  This software is the proprietary information of Sun Microsystems, Inc.  
#  Use is subject to license terms.

include $(BUILD_ROOT)/make/defines_UNIX.mk
LOCAL_COPY=0

# Compiler version
SUNWSPRO_VER = 6.1
SUNWSPRO_DIR_42 = /tools/ns/workshop
#SUNWSPRO_DIR_61 = /usr/suntools/internal/SUNWspro
SUNWSPRO_DIR_61 = /usr/dist/share/forte_dev/SUNWspro
SUNWSPRO_DIR = $(SUNWSPRO_DIR_61)
SYSTEM_LIBDIRS += $(SUNWSPRO_DIR_61)/lib $(SUNWSPRO_DIR_42)/lib

# -compat=4/-compat=5 selection
PLATFORM_CC_OPTS = -compat=$(SUNWSPRO_CC_COMPAT)
ifeq ($(SUNWSPRO_CC_COMPAT),5)
# We want the 6.1 C++ libraries, but we pull in 4.2 for any old so's
SUNWSPRO_CC_LIB = Crun Cstd C
FORTE6 = forte6
else
# We want the 4.2 C++ library
SUNWSPRO_CC_LIB = C
FORTE6 =
endif

# Tool locations
C++C		=$(PRE_CC) $(SUNWSPRO_DIR)/bin/CC
CC		=$(PRE_CC) $(SUNWSPRO_DIR)/bin/CC
C               =$(PRE_C)  $(SUNWSPRO_DIR)/bin/cc
AR              =/usr/ccs/bin/ar
RANLIB          =/usr/ccs/bin/ranlib
YACC            =/usr/ccs/bin/yacc
LD		=$(PRE_LD) /usr/ccs/bin/ld
PROFILER	=$(SUNWSPRO_DIR)/bin/profile
FTP		=/usr/bin/ftp
PERL		=/tools/ns/bin/perl5.004
LINT		=$(SUNWSPRO_DIR)/bin/lint
STRIP		=/usr/ccs/bin/strip -x

# what is NLIST?
NLIST            = elf

# Verity defines
VERITY_ARCH	=_ssol26

BASEFLAGS = -xtarget=ultra 

ifdef DEBUG_BUILD
CC_DEBUG	= -g -xs $(BASEFLAGS)
C_DEBUG         = -g $(BASEFLAGS)
else
CC_DEBUG        = -dalign -xO4 $(BASEFLAGS)
C_DEBUG         = $(CC_DEBUG)
endif

# Platform specific build options
LD_DYNAMIC	= -G
ARFLAGS		= -r
PLATFORM_DEF	= -DSVR4 -DSYSV -DSOLARIS -D_REENTRANT -DPLATFORM_SPECIFIC_STATS_ON
PLATFORM_LIB	= $(PRE_PLATFORM_LIB) pthread socket nsl dl posix4 kstat $(SUNWSPRO_CC_LIB)
PLATFORM_CC_OPTS += -mt -KPIC
PLATFORM_C_OPTS  += -mt -Kpic
PLATFORM_LD_OPTS += -mt -norunpath
RPATH_PREFIX = -R 

# template database location.
ifndef NO_STD_DATABASE_DEFINE
TEMPLATE_DATABASE_DIR=$(OBJDIR)
endif

ifdef TEMPLATE_DATABASE_DIR
CC_FLAGS += -ptr$(TEMPLATE_DATABASE_DIR)
LD_FLAGS += -ptr$(TEMPLATE_DATABASE_DIR)
endif

# The following needs to be done because of the fact that Solaris VM
# (1.2.1_03) has a bug in that once you compile libNSJvmControl.so with
# this VM you are locked in to using only Solaris VMs and that isn't
# acceptable to us. The following option to the linker is a work around
# for this bug.
LD_FLAGS += -z noversion

ifdef BROWSE
CC_BROWSE	= -xsb
C_BROWSE        =
LD_BROWSE	=
endif # BROWSE

#BUILD_ORACLE=1
#BUILD_SYBASE=1
#BUILD_INFORMIX=1
#BUILD_ODBC=1

################
#### XERCES ####
################
XERCES_DEFS += -features=rtti

##############
### PURIFY ###
##############

ifdef PURIFY
ifdef SUNWSPRO_CC_COMPAT4
PURIFY_VER= 4.1
else # SUNWSPRO_CC_COMPAT4
PURIFY_VER= 5.1
endif # SUNWSPRO_CC_COMPAT4
PURIFY_FLAGS	=-threads=yes -max_threads=100 -thread-stack-change=65536 
CC_PURIFY = /tools/ns/bin/purify-5.1 $(PURIFY_FLAGS)
C_PURIFY  = $(CC_PURIFY)
PRELINK	 += $(CC_PURIFY)

endif #PURIFY

ifdef PURECOV
PDIR=/h/iws-files/export/purecov/purecov-4.5.1-solaris2/
PURCOV_FLAGS	=-threads=yes -max_threads=100 -thread-stack-change=65536 -rtslave=yes -follow-child-processes=yes
CC_PURIFY = $(PDIR)purecov $(PURCOV_FLAGS)
C_PURIFY  = $(CC_PURIFY)
PRELINK	 += $(CC_PURIFY)

endif #PURECOV


################
### QUANTIFY ###
################

ifdef QUANTIFY
QUANTIFY_CACHEDIR = ./cache
QUANTIFY_OPTS   = -best-effort -collection-granularity=function \
		  -cache-dir=${QUANTIFY_CACHEDIR} \
		  -record-child-process-data=yes \
		  -max_threads=250 -record-data=yes -windows=yes \
		  -write-summary-file= -measure-timed-calls=user+system \
		  -avoid-recording-system-calls=210,87
QUANTIFY_FLAGS  = $(QUANTIFY_OPTS)
QUANTIFY_PROG   = /u/rchinta/util/Quantify/quantify-3.0-solaris2/quantify
CC_QUANTIFY     = $(QUANTIFY_PROG) $(QUANTIFY_OPTS)
C_QUANTIFY      = $(QUANTIFY_PROG) $(QUANTIFY_OPTS)
PRELINK+= $(CC_QUANTIFY)

CC_FLAGS += -DQUANTIFY
C_FLAGS  += -DQUANTIFY

PRELINK+= $(CC_QUANTIFY)
endif # QUANTIFY

