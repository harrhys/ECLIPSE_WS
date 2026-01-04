#
# Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
# 
# This software is the proprietary information of Sun Microsystems, Inc.  
# Use is subject to license terms.


# SPARC_SOLARIS_2.5 rules

#
# AR[n]_TARGET, AR[n]_OBJS 
#

ifdef AR_TARGET
AR_OBJ_INT=$(addsuffix .$(OBJ),$(AR_OBJS))
REAL_AR_OBJS=$(addprefix $(OBJDIR)/,$(AR_OBJ_INT))
$(OBJDIR)/$(LIBPREFIX)$(AR_TARGET).$(STATIC_LIB_SUFFIX): $(REAL_AR_OBJS)
	$(RM) -f $@
	$(CC) -xar -ptr$(OBJDIR) -o $@ $(REAL_AR_OBJS) $(AR_NONPARSED_OBJS)
endif

ifdef BSC_TARGET
$(BSC_TARGET): ; \
	$(ECHO) The $@ file is for NT only. > $@
endif

ifdef SB_INIT
$(SB_INIT): ; \
	$(ECHO) export / into $(SB_DIR) > $@
endif

EXPORT__LIBS=$(EXPORT_LIBRARIES) $(EXPORT_DYNAMIC_LIBRARIES)
