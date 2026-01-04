#  Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
#  
#  This software is the proprietary information of Sun Microsystems, Inc.  
#  Use is subject to license terms.

TOOL_ROOT	=/usr
NSTOOL_ROOT     =/tools/ns

CC              =$(ECHO) define CC in the platform definitions.
C               =$(ECHO) define C  in the platform definitions.
AR              =$(ECHO) define AR in the platform definitions.
LD              =$(ECHO) define LD in the platform definitions.


### COMMON UNIX BINARIES
PERL_DIR        =$(NSTOOL_ROOT)
PERL5         	=$(PERL_DIR)/bin/perl5
PERL		=$(PERL_DIR)/bin/perl
RM		=$(TOOL_ROOT)/bin/rm
LS		=$(TOOL_ROOT)/bin/ls
CP		=$(TOOL_ROOT)/bin/cp
CP_R		=$(CP) -r
LN		=$(TOOL_ROOT)/bin/ln -f
CMP		=$(TOOL_ROOT)/bin/cmp
MV		=$(TOOL_ROOT)/bin/mv
SED		=$(TOOL_ROOT)/bin/sed
ECHO		=$(TOOL_ROOT)/bin/echo
DATE		=$(TOOL_ROOT)/bin/date
MKDIR           =$(TOOL_ROOT)/bin/mkdir
CHMOD           =$(TOOL_ROOT)/bin/chmod
CHMOD_DASH_R_755 =$(TOOL_ROOT)/bin/chmod -R 755
MKDIR_DASH_P	=$(MKDIR) -p
XARGS           =$(TOOL_ROOT)/bin/xargs
DIRNAME         =$(TOOL_ROOT)/bin/dirname
BASENAME        =$(TOOL_ROOT)/bin/basename
SHELL           =$(TOOL_ROOT)/bin/sh
SLEEP		=$(TOOL_ROOT)/bin/sleep
WC		=$(TOOL_ROOT)/bin/wc
GREP		=$(TOOL_ROOT)/bin/grep
FIND		=$(TOOL_ROOT)/bin/find
TOUCH		=$(TOOL_ROOT)/bin/touch
PRINTF		=$(TOOL_ROOT)/bin/printf
YACC		=$(TOOL_ROOT)/bin/yacc
TR              =$(TOOL_ROOT)/bin/tr
TAR		=$(TOOL_ROOT)/bin/tar
STRIP		=strip
RCP		=$(TOOL_ROOT)/bin/rcp
RCP_CMD		=$(RCP) -r $(RCPUSER)@$(RCPSERVER)

G++		=$(NSTOOL_ROOT)/bin/g++
GCC		=$(NSTOOL_ROOT)/bin/gcc
NMAKE		=$(NSTOOL_ROOT)/bin/gmake -f

##############
## PREFIXES ##
##############

# Modify as needed in platform defines
OBJ =o
SBR =o
CPP =cpp
STATIC_LIB_SUFFIX=a
DYNAMIC_LIB_SUFFIX=so
LIBPREFIX=lib

COMMENT=\#
ifndef LD_LIB_VAR
LD_LIB_VAR=LD_LIBRARY_PATH
endif # !LD_LIB_VAR


###################
### COMMON LIBS ###
###################

NSPR_LIB   = plc4 plds4 nspr4
SEC_LIB+= ssl nss cert secmod key crypto hash secutil dbm

###############################
### COMMON COMPILER OPTIONS ###
###############################

ifdef DEBUG_BUILD
CC_DEBUG = -g
C_DEBUG  = -g
else
# jsalter: remove -O4 option because not all compilers use
#          that option for high-level optimization
CC_DEBUG =
C_DEBUG  =
endif

ARFLAGS = -r

ZIPFLAGS = -ry

# Unix-generic defines
SYSTEM_DEF += -DXP_UNIX

# Library expansion code.

REAL_LIBS=$(addprefix -l,$(LIBS))
EXE_REAL_LIBS=$(addprefix -l,$(EXE_LIBS))

DLL_REAL_LIBS=$(addprefix -l,$(DLL_LIBS))

REAL_LIBDIRS=$(addprefix -L,$(LIBDIRS))
EXE_REAL_LIBDIRS=$(addprefix -L,$(EXE_LIBDIRS))
