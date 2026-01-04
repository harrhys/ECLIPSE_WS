#
# Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
# 
# This software is the proprietary information of Sun Microsystems, Inc.  
# Use is subject to license terms.
#
top_srcdir	= ../../../../..
include $(top_srcdir)/make/cpp_def.mk

BUILD_ROOT=$(top_srcdir)
include ${BUILD_ROOT}/make/defines.mk

MODULE=LibOrgOmgBoxedRMIJavaLang

EXPORT_LIBRARIES= orgomgbxrmijavalang
AR_TARGET = orgomgbxrmijavalang

AR_OBJS		= seq1_StackTraceElement \
	seq1_StackTraceElement_skel
	
include ${BUILD_ROOT}/make/rules.mk

#seq1_StackTraceElement.cpp seq1_StackTraceElement.h : seq1_StackTraceElement.idl
#	$(IDL) -I$(top_srcdir) -I/opt/OB/idl -I/opt/OB/idl/OB --output-dir $(top_srcdir)/java/lang --impl-all $(top_srcdir)/java/lang/StackTraceElement.idl
#	$(RM) -f seq1_StackTraceElement.cpp seq1_StackTraceElement.h
#	$(RM) -f seq1_StackTraceElement_skel.cpp seq1_StackTraceElement_skel.h
#	$(IDL) $(ALL_IDLFLAGS) seq1_StackTraceElement.idl

#seq1_StackTraceElement_skel.cpp seq1_StackTraceElement_skel.h:seq1_StackTraceElement.h
