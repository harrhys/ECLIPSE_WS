#
# Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
# 
# This software is the proprietary information of Sun Microsystems, Inc.  
# Use is subject to license terms.


# X86_NT_4.0 rules

#
# AR[n]_TARGET, AR[n]_OBJS 
#
ZIPFLAGS = -r

ifdef AR_TARGET
AR_OBJ_INT=$(addsuffix .$(OBJ),$(AR_OBJS))
REAL_AR_OBJS=$(addprefix $(OBJDIR)/, $(AR_OBJ_INT))
$(OBJDIR)/$(LIBPREFIX)$(AR_TARGET).$(STATIC_LIB_SUFFIX): $(REAL_AR_OBJS)
	$(RM) -f $@
	$(MSVC_LIB) /OUT:$@ $(AR_NONPARSED_OBJS) \
		$^
endif

ifndef NO_STD_OBJDIR_RES_RULE
$(OBJDIR)/%.rc:%.mc
	@$(MAKE_OBJDIR) 
	$(MC) $(MC_FLAGS) $^
	
$(OBJDIR)/%.res:%.rc
	@$(MAKE_OBJDIR) 
	$(RC) $(RC_FLAGS) \
                \
                $(CC_DASH_O)$@ \
                \
                $^
endif #NO_STD_OBJDIR_RES_RULE

ifdef BROWSE
ifdef BSC_TARGET
_BSC_OBJS=$(addprefix $(OBJDIR)/, $(addsuffix .sbr, $(BSC_OBJS)))
$(OBJDIR)/$(BSC_TARGET): $(_BSC_OBJS)
	$(BSCMAKE) $(BSC_FLAGS) /o $@ \
		$(_BSC_OBJS)
endif
else
ifdef BSC_TARGET
$(OBJDIR)/$(BSC_TARGET):
	@$(TOUCH) $@
endif
endif

ifdef SB_INIT
$(OBJDIR)/$(SB_INIT): ; \
	@$(ECHO) The .sbinit file is for Solaris only. > $@
endif

EXPORT__LIBS=$(EXPORT_LIBRARIES) \
	$(EXPORT_DYNAMIC_LIBRARIES:$(DYNAMIC_LIB_SUFFIX)=$(STATIC_LIB_SUFFIX))
EXPORT__DYN_LIBS= $(EXPORT_DYNAMIC_LIBRARIES)

EXPORT__EARLY_LIBS=$(EXPORT_EARLY_LIBRARIES) \
		   $(EXPORT_EARLY_DYNAMIC_LIBRARIES:.dll=.lib)
EXPORT__EARLY_DLLS=$(EXPORT_EARLY_DYNAMIC_LIBRARIES)

ifdef GENERATE_MAP_FILES
DLL_EXTRA+=$(CREATE_MSVC_MAP_FILES)$(OBJDIR)/$(DLL_TARGET).map
EXE_EXTRA+=$(CREATE_MSVC_MAP_FILES)$(OBJDIR)/$(EXE_TARGET).map
endif # GENERATE_MAP_FILES

ifdef GENERATE_PDB_FILES
DLL_REAL_LIBS+=-pdb:$(OBJDIR)/$(DLL_TARGET).pdb
EXE_REAL_LIBS+=-pdb:$(OBJDIR)/$(EXE_TARGET).pdb
else # GENERATE_PDB_FILES
DLL_REAL_LIBS+=-pdb:none
EXE_REAL_LIBS+=-pdb:none
endif # GENERATE_PDB_FILES

MAKE_RC = $(BUILD_ROOT)/tools/MakeRC/MakeRC.exe

ifdef EXE_TARGET
ifdef EXE_RES
_EXE_RES=$(OBJDIR)/$(EXE_RES).res
headers:: $(EXE_RES).rc
compile:: $(OBJDIR)/$(EXE_RES).res
endif # EXE_RES
endif # EXE_TARGET

ifndef SKIP_AUTO_RESOURCE


ifdef DLL_RES
DLL_RC_VERGEN_FLAGS+=-rcInclude ../$(DLL_RES).rc
$(OBJDIR)/auto_$(DLL_TARGET).res : $(DLL_RES).rc
endif

ifdef DLL_TARGET
$(OBJDIR)/auto_$(DLL_TARGET).rc:
	$(VERGEN) $(DLL_RC_VERGEN_FLAGS) -o $@
headers:: $(OBJDIR)/auto_$(DLL_TARGET).rc
compile:: $(OBJDIR)/auto_$(DLL_TARGET).res
endif # DLL_TARGET


ifdef EXE_TARGET
$(OBJDIR)/auto_$(EXE_TARGET).rc:
	$(VERGEN) $(EXE_RC_VERGEN_FLAGS) -o $@
headers:: $(OBJDIR)/auto_$(EXE_TARGET).rc
compile:: $(OBJDIR)/auto_$(EXE_TARGET).res
endif # EXE_TARGET

endif # SKIP_AUTO_RESOURCE

$(OBJDIR)/%.res : $(OBJDIR)/%.rc
	$(RC) $(RC_FLAGS) $($*_RC_FLAGS) /fo$@ $<

$(OBJDIR)/%.sbr : $(OBJDIR)/%.$(OBJ)
