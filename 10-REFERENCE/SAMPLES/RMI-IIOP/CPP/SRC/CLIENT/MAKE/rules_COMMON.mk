#
# Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
# 
# This software is the proprietary information of Sun Microsystems, Inc.  
# Use is subject to license terms.


# make sure all is first defined rule here
all::

ifndef GENERATE_PDB_FILES
SKIP_PDB=1
endif # GENERATE_PDB_FILES

ifdef SUPPRESS_PDB_FILES
SKIP_PDB=1
endif # SUPPRESS_PDB_FILES

ifndef GENERATE_MAP_FILES
SKIP_MAP=1
endif # GENERATE_MAP_FILES

ifdef SUPPRESS_MAP_FILES
SKIP_MAP=1
endif # SUPPRESS_MAP_FILES

ifndef BUILD_JAVA

#
# EXE[n]_TARGET,  EXE[n]_OBJS, [ EXE[n]_EXTRA ], [ EXE[n]_LIBS ]
#
_OBJS+=$(CSRCS:.c=.$(OBJ)) $(ASFILES:.s=$(OBJ)) $(CPPSRCS:.cpp=.$(OBJ))

ifdef _OBJS
OBJS+=$(addprefix $(OBJDIR)/,$(_OBJS))
endif #_OBJS

ifndef NO_STD_ALL_TARGET
all::  compile libraries link
endif #NO_STD_ALL_TARGET

ifndef NO_STD_HEADERS_TARGET
headers::
	@$(MAKE_OBJDIR)
	$(LOOP_OVER_DIRS)
endif #NO_STD_HEADERS_TARGET

ifndef NO_STD_COMPILE_TARGET
compile:: $(GENERATED_FILES) $(OBJS)
	+$(LOOP_OVER_DIRS)
endif #NO_STD_COMPILE_TARGET

ifeq ($(OS_ARCH),WINNT)
ifdef BSC_TARGET
compile:: $(OBJDIR)/$(BSC_TARGET)
endif #BSC_TARGET
endif #OS_ARCH==WINNT

ifeq ($(OS_ARCH),SunOS)
ifdef SB_INIT
compile:: $(SB_INIT)
endif #SB_INIT
endif #OS_ARCH-SunOS

ifndef CC_DASH_O
CC_DASH_O=-o 
endif #CC_DASH_O

ifndef LD_DASH_O
LD_DASH_O=-o 
endif #LD_DASH_O

ifndef NO_STD_OBJDIR_O_RULE
$(OBJDIR)/%.$(OBJ):%.cpp $(PCH_DEP)
	@$(MAKE_OBJDIR)
	$(PRECC) $(CC) $(CC_FLAGS) $($<_CC_FLAGS) -c \
		\
		$(CC_DASH_O)$@ \
		\
		$<
endif #NO_STD_OBJDIR_O_RULE

ifndef NO_STD_OBJDIR_CPP_TO_OBJDIR_O_RULE
$(OBJDIR)/%.$(OBJ):$(OBJDIR)/%.cpp $(PCH_DEP)
	$(PRECC) $(CC) $(CC_FLAGS) $($<_CC_FLAGS) -c \
		\
		$(CC_DASH_O)$@ \
		\
		$<
endif #NO_STD_OBJDIR_CPP_TO_OBJDIR_O_RULE

ifndef NO_STD_OBJDIR_O_FROM_C_RULE
$(OBJDIR)/%.$(OBJ):%.c
	@$(MAKE_OBJDIR)
	$(PREC) $(C) $(C_FLAGS) -c \
		\
		$(CC_DASH_O)$@ \
		\
		$<
endif #NO_STD_OBJDIR_O_FROM_C_RULE
ifdef EXE_TARGET

ifndef SKIP_AUTO_VERSION_INSERTION
EXE_NONPARSED_OBJS+=$(OBJDIR)/auto_$(EXE_TARGET).$(OBJ)
endif #SKIP_AUTO_VERSION_INSERTION

ifdef EXE_RES
_EXE_RES=$(OBJDIR)/$(EXE_RES).res
endif
_EXE_OBJS:=$(addprefix $(OBJDIR)/,$(EXE_OBJS:=.$(OBJ))) $(EXE_NONPARSED_OBJS) $(_EXE_RES)
_EXE_OUTPUT_FILE:=$(OBJDIR)/$(EXE_TARGET)$(EXE)
$(_EXE_OUTPUT_FILE) : $(_EXE_OBJS)
	$(PRELINK) $(CC) \
		\
		$(LD_DASH_O)$(_EXE_OUTPUT_FILE) \
		\
		$(_EXE_OBJS) $(EXE_EXTRA) $(PRELIB) $(LD_FLAGS) \
		$(EXE_REAL_LIBDIRS) $(EXE_REAL_LIBS) $(LD_LIBS) $(LD_RPATHS) $(SYSTEM_LINK_LIBS) $(PLATFORM_LD_OPTS_EXE)
ifeq ($(BUILD_VARIANT), OPTIMIZED)
ifdef STRIP
	$(STRIP) $(_EXE_OUTPUT_FILE)
endif
endif
endif #EXE_TARGET

#
# DLL[n]_TARGET, DLL[n]_OBJS, [ DLL[n]_EXTRA ], [ DLL[n]_LIBS ]
#

ifdef DLL_DEF_EXPORT
DLL_DEF_EXPORT_FLAG=/DEF:$(DLL_DEF_EXPORT)
endif

ifdef DLL_TARGET
DLL_REAL_OBJS:=$(addprefix $(OBJDIR)/, $(DLL_OBJS:=.$(OBJ)))
DLL_OUTPUT_FILE:=$(OBJDIR)/$(LIBPREFIX)$(DLL_TARGET).$(DYNAMIC_LIB_SUFFIX)
$(DLL_OUTPUT_FILE): $(DLL_REAL_OBJS) $(DLL_NONPARSED_OBJS)
	$(CC) $(CC_SHARED_LIB_FLAGS) $(LD_DYNAMIC) \
		\
		$(LD_DASH_O)$(DLL_OUTPUT_FILE) \
		\
		$(DLL_REAL_OBJS) $(DLL_NONPARSED_OBJS) \
                $(DLL_EXTRA) $(PRELIB) $(LD_FLAGS) \
		$(DLL_REAL_LIBS) $(DLL_NONPARSED_LIBS) $(LD_LIBS) $(LD_RPATHS)
ifeq ($(BUILD_VARIANT), OPTIMIZED)
ifdef STRIP
	$(STRIP) $(DLL_OUTPUT_FILE)
endif
endif
endif # DLL_TARGET


ifndef OBJDIR_BIN
OBJDIR_BIN=$(OBJDIR)
endif

ifndef OBJDIR_LIB
OBJDIR_LIB=$(OBJDIR)
endif

ifndef OBJDIR_DYNLIB
OBJDIR_DYNLIB=$(OBJDIR)
endif

ifndef OBJDIR_HDR
OBJDIR_HDR=.
endif


ifdef PUBLIC_BINARIES
SOMETHING_EXPORTED=1
ifdef EXE
ifdef NO_EXE_SUFFIX
_PUBLIC_BINARIES+=$(addprefix $(OBJDIR_BIN)/, $(PUBLIC_BINARIES))
else
_PUBLIC_BINARIES+=$(addprefix $(OBJDIR_BIN)/, \
                   $(addsuffix $(EXE), $(PUBLIC_BINARIES)))
ifeq ($(OS_ARCH),WINNT) 
ifndef SKIP_PDB
_PUBLIC_BINARIES+= $(addprefix $(OBJDIR_BIN)/, $(addsuffix .pdb, $(PUBLIC_BINARIES)))
endif # SKIP_PDB
ifndef SKIP_MAP
_PUBLIC_BINARIES+= $(addprefix $(OBJDIR_BIN)/, $(addsuffix .map, $(PUBLID_BINARIES)))
endif # SKIP_MAP

endif # OS==WINNT
endif # EXE && !NO_EXE_SUFFIX
else
_PUBLIC_BINARIES+=$(addprefix $(OBJDIR_BIN)/, $(PUBLIC_BINARIES))
endif #ifndef EXE

ifeq ($(OS_ARCH),WINNT) 
ifndef SKIP_PDB
_PUBLIC_BINARIES+= $(addprefix $(OBJDIR_BIN)/, $(addsuffix .pdb, $(PUBLIC_BINARIES)))
endif # SKIP_PDB
ifndef SKIP_MAP
_PUBLIC_BINARIES+= $(addprefix $(OBJDIR_BIN)/, $(addsuffix .map, $(PUBLIC_BINARIES)))
endif # SKIP_MAP
endif # OS==WINNT

endif #PUBLIC_BINARIES

ifdef EXPORT_BINARIES
SOMETHING_EXPORTED=1
ifdef EXE
ifdef NO_EXE_SUFFIX
_EXPORT_BINARIES+=$(addprefix $(OBJDIR_BIN)/, $(EXPORT_BINARIES))
else
_EXPORT_BINARIES+=$(addprefix $(OBJDIR_BIN)/, \
                   $(addsuffix $(EXE), $(EXPORT_BINARIES)))
ifeq ($(OS_ARCH),WINNT) 
ifndef SKIP_PDB
_EXPORT_BINARIES+= $(addprefix $(OBJDIR_BIN)/, $(addsuffix .pdb, $(EXPORT_BINARIES)))
endif # SKIP_PDB

ifndef SKIP_MAP
_EXPORT_BINARIES+= $(addprefix $(OBJDIR_BIN)/, $(addsuffix .map, $(EXPORT_BINARIES)))
endif # SKIP_MAP

endif # OS==WINNT
endif # EXE && !NO_EXE_SUFFIX
else
_EXPORT_BINARIES+=$(addprefix $(OBJDIR_BIN)/, $(EXPORT_BINARIES))
endif #ifndef EXE

ifeq ($(OS_ARCH),WINNT) 

ifndef SKIP_PDB
_EXPORT_BINARIES+= $(addprefix $(OBJDIR_BIN)/, $(addsuffix .pdb, $(EXPORT_BINARIES)))
endif # SKIP_PDB

ifndef SKIP_MAP
_EXPORT_BINARIES+= $(addprefix $(OBJDIR_BIN)/, $(addsuffix .map, $(EXPORT_BINARIES)))
endif # SKIP_MAP

endif # OS==WINNT

endif #EXPORT_BINARIES

ifdef EXPORT_LIBRARIES
SOMETHING_EXPORTED=1
_EXPORT_LIBRARIES+=$(addprefix $(OBJDIR_LIB)/$(LIBPREFIX), \
                      $(addsuffix .$(STATIC_LIB_SUFFIX), $(EXPORT_LIBRARIES)))
endif

ifdef EXPORT_DYNAMIC_LIBRARIES
SOMETHING_EXPORTED=1
_EXPORT_DYNAMIC_LIBRARIES=$(addprefix $(OBJDIR_DYNLIB)/$(LIBPREFIX), \
	                    $(addsuffix .$(DYNAMIC_LIB_SUFFIX), \
                                         $(EXPORT_DYNAMIC_LIBRARIES)))
ifeq ($(OS_ARCH),WINNT) 
_EXPORT_STAT_LIBRARIES+=$(addprefix $(OBJDIR_LIB)/$(LIBPREFIX), \
                        $(addsuffix .$(STATIC_LIB_SUFFIX), \
                                     $(EXPORT_DYNAMIC_LIBRARIES)))
ifndef SKIP_PDB
_EXPORT_DYNAMIC_LIBRARIES+=$(addprefix $(OBJDIR_DYNLIB)/$(LIBPREFIX), \
                        $(addsuffix .pdb, $(EXPORT_DYNAMIC_LIBRARIES)))
endif # SKIP_PDB

ifndef SKIP_MAP
_EXPORT_DYNAMIC_LIBRARIES+=$(addprefix $(OBJDIR_DYNLIB)/$(LIBPREFIX), \
			$(addsuffix .map, $(EXPORT_DYNAMIC_LIBRARIES)))
endif # SKIP_MAP

_EXPORT_LIBRARIES+=$(_EXPORT_STAT_LIBRARIES)
$(_EXPORT_STAT_LIBRARIES): $(_EXPORT_DYNAMIC_LIBRARIES)
endif # OS==WINNT
endif # EXPORT_DYNAMIC_LIBRARIES

ifndef NO_STD_EARLY_LIBRARIES_TARGET
early_libraries:: $(_EXPORT_EARLY_DYNAMIC_LIBRARIES) \
                  $(_EXPORT_EARLY_LIBRARIES) \
				  $(_PUBLIC_EARLY_DYNAMIC_LIBRARIES) \
                  $(_PUBLIC_EARLY_LIBRARIES) \
				  $(_LOCAL_EARLY_LIBRARIES)
	$(LOOP_OVER_DIRS)
endif #NO_STD_EARLY_LIBRARIES_TARGET

ifndef NO_STD_LIBRARIES_TARGET
libraries::$(_LOCAL_LIBRARIES) $(_EXPORT_LIBRARIES) $(_PUBLIC_LIBRARIES) $(_EXPORT_DYNAMIC_LIBRARIES) $(_PUBLIC_DYNAMIC_LIBRARIES) $(_PUBLIC_DYNAMIC_LIBRARIES_SPECIAL) $(_LOCAL_DYNAMIC_LIBRARIES) $(_EXPORT_TESTS_DYNAMIC_LIBRARIES)
	$(LOOP_OVER_DIRS)
endif #NO_STD_LIBRARIES_TARGET

ifndef NO_STD_LINK_TARGET
link:: $(_LOCAL_BINARIES) $(_EXPORT_BINARIES) $(_PUBLIC_BINARIES) $(_PUBLIC_ADMIN_BINARIES)$(_PUBLIC_HTTPADMIN_BINARIES) $(_EXPORT_TESTS_BINARIES)
	$(LOOP_OVER_DIRS)
endif #NO_STD_LINK_TARGET

else #BUILD_JAVA
#
endif #BUILD_JAVA

ifdef DIRS
ifndef NO_LOOP_OVER_DIRS
LOOP_OVER_DIRS=	for dir in $(DIRS) ; do \
			( cd $${dir} &&  \
			$(MAKE) MAKEFLAGS='$(MAKEFLAGS) $(EXTRA_MAKEFLAGS)' $@ ) || exit 255 ; \
	 	done
else #NO_LOOP_OVER_DIRS
LOOP_OVER_DIRS= $(ECHO) directory traversal skipped
endif #NO_LOOP_OVER_DIRS
endif #DIRS


ifndef MAKE_OBJDIR
define MAKE_OBJDIR
if test ! -d $(OBJDIR); then rm -rf $(OBJDIR); $(MKDIR) -p $(OBJDIR); fi
endef
endif #MAKE_OBJDIR


####################################
###### PUBLICATION CODE ############
####################################

print_exports::
	$(LOOP_OVER_DIRS)

print_exports::
	@$(ECHO) files to be exported for module $(MODULE):

ifdef SOMETHING_EXPORTED
ifndef NO_STD_PUBLISH_RULE
publish: local_pre_publish pre_publish publish_copy local_post_publish
ifndef NO_PUBLISH_RECURSE
	$(LOOP_OVER_DIRS)
endif #NO_PUBLISH_RECURSE
endif #NO_STD_PUBLISH_RULE

# can be used by local makefiles to prepare for publish:
local_pre_publish::

# can unpack tarfiles, etc.
local_post_publish::
else # n SOMETHING_EXPORTED

publish: pre_publish
	@$(ECHO) Nothing to publish. Traversing subdirs
ifndef NO_PUBLISH_RECURSE
	$(LOOP_OVER_DIRS)
endif #NO_PUBLISH_RECURSE
endif # SOMETHING_EXPORTED

.PHONY: pre_publish publish_copy

ifndef BUILD_JAVA

.PHONY: publish pre_publish 

#
# _LIBRARIES
#
ifdef _EXPORT_LIBRARIES
print_exports::
	@$(ECHO) "LIBRARIES: $(_EXPORT_LIBRARIES)"
publish_copy::
	$(MKDIR_DASH_P) $(INTERNAL_ROOT)/lib
	$(CP) -f $(_EXPORT_LIBRARIES) $(INTERNAL_ROOT)/lib
endif #_EXPORT_LIBRARIES


#
# _DYNAMIC_LIBRARIES
#

ifdef _EXPORT_DYNAMIC_LIBRARIES
ifeq ($(OS_ARCH),WINNT) 
DLL_PUBLISH_DIR=$(INTERNAL_ROOT)/bin
else #OS=NT
DLL_PUBLISH_DIR=$(INTERNAL_ROOT)/lib
endif #OS=NT

print_exports::
	@$(ECHO) "DLLS: $(_EXPORT_DYNAMIC_LIBRARIES)"
publish_copy::
	$(MKDIR_DASH_P) $(DLL_PUBLISH_DIR)
	$(CP) -f $(_EXPORT_DYNAMIC_LIBRARIES) $(DLL_PUBLISH_DIR)
endif #_EXPORT_DYNAMIC_LIBRARIES

#
# _BINARIES
#
ifdef _EXPORT_BINARIES
print_exports::
	@$(ECHO) "BINARIES: $(_EXPORT_BINARIES)"	
publish_copy::
	$(MKDIR_DASH_P) $(INTERNAL_ROOT)/bin
	$(CP) -f $(_EXPORT_BINARIES) $(INTERNAL_ROOT)/bin
endif # _EXPORT_BINARIES

.PHONY: all clean clobber headers compile early_libraries libraries link

endif #BUILD_JAVA

ifndef NO_STD_CLEAN_TARGET
clean::
	$(RM) -f $(OBJS) $(NOSUCHFILE) 
ifeq ($(OS_ARCH),SunOS)
	- $(RM) -rf Templates.DB
endif #OS==SunOS
	$(LOOP_OVER_DIRS)
endif #NO_STD_CLEAN_TARGET

ifndef NO_STD_CLOBBER_TARGET
clobber::
ifdef OBJDIR
	$(RM) -rf $(OBJDIR) $(NOSUCHFILE)
endif #OBJDIR
ifeq ($(OS_ARCH),SunOS)
	$(RM) -rf .sb
	$(RM) -rf SunWS_cache        
endif #OS_ARCH=SunOS
ifeq ($(OS_ARCH),WINNT)
	$(RM) -f pmc*.log
endif #OS_ARCH=WINNT
	$(LOOP_OVER_DIRS)
endif #NO_STD_CLOBBER_TARGET


ifndef SKIP_AUTO_VERSION_INSERTION

######################## DLL_AUTO_VERSION ############################
DLL_VERGEN_FLAGS+=-filename $(DLL_TARGET).$(DYNAMIC_LIB_SUFFIX)
ifdef DLL_DESC
DLL_VERGEN_FLAGS+=-description $(DLL_DESC)
endif
ifdef DLL_COPYRIGHT
DLL_VERGEN_FLAGS+=-copyright $(DLL_COPYRIGHT)
endif
ifdef DLL_VER
DLL_VERGEN_FLAGS+=-v $(DLL_VER)
endif
ifdef DLL_COMMENT
DLL_VERGEN_FLAGS+= -comment $(DLL_COMMENT)
endif
ifdef DLL_SPECIAL
DLL_VERGEN_FLAGS+= -special $(DLL_SPECIAL)
endif

DLL_RC_VERGEN_FLAGS+=$(DLL_VERGEN_FLAGS) -rc


ifdef DLL_TARGET
$(OBJDIR)/auto_$(DLL_TARGET).cpp:
	$(VERGEN) $(VERGEN_FLAGS) $(DLL_VERGEN_FLAGS) -o $@
ifndef BUILD_JAVA
headers:: $(OBJDIR)/auto_$(DLL_TARGET).cpp
compile::$(OBJDIR)/auto_$(DLL_TARGET).$(OBJ)
endif
endif
DLL_NONPARSED_OBJS+=$(OBJDIR)/auto_$(DLL_TARGET).$(OBJ)
$(OBJDIR)/$(DLL_TARGET).$(DYNAMIC_LIB_SUFFIX): $(OBJDIR)/auto_$(DLL_TARGET).$(OBJ)

######################## EXE_AUTO_VERSION ############################
EXE_VERGEN_FLAGS+=-filename $(EXE_TARGET)$(EXE)
ifdef EXE_DESC
EXE_VERGEN_FLAGS+=-description $(EXE_DESC)
endif
ifdef EXE_COPYRIGHT
EXE_VERGEN_FLAGS+=-copyright $(EXE_COPYRIGHT)
endif
ifdef EXE_VER
EXE_VERGEN_FLAGS+=-v $(EXE_VER)
endif
ifdef EXE_COMMENT
EXE_VERGEN_FLAGS+= -comment $(EXE_COMMENT)
endif
ifdef EXE_SPECIAL
EXE_VERGEN_FLAGS+= -special $(EXE_SPECIAL)
endif

EXE_RC_VERGEN_FLAGS+=$(EXE_VERGEN_FLAGS) -rc

ifdef EXE_TARGET
$(OBJDIR)/auto_$(EXE_TARGET).cpp:
	$(VERGEN) $(VERGEN_FLAGS) $(EXE_VERGEN_FLAGS) -o $@
ifndef BUILD_JAVA
headers:: $(OBJDIR)/auto_$(EXE_TARGET).cpp
compile::$(OBJDIR)/auto_$(EXE_TARGET).$(OBJ)
endif
endif

$(OBJDIR)/$(EXE_TARGET)$(EXE): $(OBJDIR)/auto_$(EXE_TARGET).$(OBJ)
######################## EXE_AUTO_VERSION ############################
endif #SKIP_AUTO_VERSION_INSERTION
