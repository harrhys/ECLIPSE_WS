# Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
# 
# This software is the proprietary information of Sun Microsystems, Inc.  
# Use is subject to license terms.
#
top_srcdir	= ../../..
include $(top_srcdir)/make/cpp_def.mk

BUILD_ROOT=$(top_srcdir)
include ${BUILD_ROOT}/make/defines.mk

MODULE=LibJavaxRmiCorba

EXPORT_LIBRARIES= javaxrmicorba
AR_TARGET = javaxrmicorba

AR_OBJS		= ClassDesc \
	ClassDesc_skel \
	ClassDesc_impl
	
include ${BUILD_ROOT}/make/rules.mk

#ClassDesc.cpp ClassDesc.h : ClassDesc.idl
#	$(RM) -f ClassDesc.cpp ClassDesc.h
#	$(RM) -f ClassDesc_skel.cpp ClassDesc_skel.h
#	$(IDL) $(ALL_IDLFLAGS) --impl-all ClassDesc.idl

#ClassDesc_skel.cpp ClassDesc_skel.h:ClassDesc.h
#ClassDesc_impl.cpp ClassDesc_impl.h:ClassDesc_skel.h

docs ::
	#echo "nothing"

clean ::
	$(RM) -rf $(OBJDIR)
	$(RM) -rf ../$(OBJDIR)
