#
# Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
# 
# This software is the proprietary information of Sun Microsystems, Inc.  
# Use is subject to license terms.
#
top_srcdir	= ../../../..
include $(top_srcdir)/make/cpp_def.mk

BUILD_ROOT=$(top_srcdir)
include ${BUILD_ROOT}/make/defines.mk

MODULE=LibOrgOmgBoxedRMICorba

EXPORT_LIBRARIES= orgomgbxrmicorba
AR_TARGET = orgomgbxrmicorba

AR_OBJS		= seq1_WStringValue \
		seq1_WStringValue_skel

include ${BUILD_ROOT}/make/rules.mk

#seq1_WStringValue.cpp seq1_WStringValue.h : seq1_WStringValue.idl
#	$(RM) -f seq1_WStringValue.cpp seq1_WStringValue.h
#	$(RM) -f seq1_WStringValue_skel.cpp seq1_WStringValue_skel.h
#	$(IDL) $(ALL_IDLFLAGS) seq1_WStringValue.idl

#seq1_WStringValue_skel.cpp seq1_WStringValue_skel.h:seq1_WStringValue.h
