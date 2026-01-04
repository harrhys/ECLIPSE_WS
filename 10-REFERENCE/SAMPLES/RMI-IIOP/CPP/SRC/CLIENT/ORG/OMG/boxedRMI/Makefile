#
# Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
# 
# This software is the proprietary information of Sun Microsystems, Inc.  
# Use is subject to license terms.
#
top_srcdir	= ../../..
include $(top_srcdir)/make/cpp_def.mk

BUILD_ROOT=$(top_srcdir)
include ${BUILD_ROOT}/make/defines.mk

DIRS		= CORBA java

MODULE=LibOrgOmgBoxedRMI

EXPORT_LIBRARIES= orgomgboxedrmi
AR_TARGET = orgomgboxedrmi

AR_OBJS    =seq1_octet \
	seq1_octet_skel \
	seq1_wchar \
	seq1_wchar_skel

include ${BUILD_ROOT}/make/rules.mk

seq1_octet.cpp seq1_octet.h : seq1_octet.idl
	$(RM) -f seq1_octet.cpp seq1_octet.h
	$(RM) -f seq1_octet_skel.cpp seq1_octet_skel.h
	$(IDL) $(ALL_IDLFLAGS)  seq1_octet.idl

seq1_octet_skel.cpp seq1_octet_skel.h:seq1_octet.h

# hack here to pregenerate some .h and .cpp's
seq1_wchar.cpp seq1_wchar.h : seq1_wchar.idl
	$(RM) -f seq1_wchar.cpp seq1_wchar.h
	$(RM) -f seq1_wchar_skel.cpp seq1_wchar_skel.h
	$(IDL) $(ALL_IDLFLAGS)  seq1_wchar.idl
	$(IDL) $(ALL_IDLFLAGS)  --output-dir $(top_srcdir)/java/lang --impl-all $(top_srcdir)/java/lang/StackTraceElement.idl
	$(IDL) $(ALL_IDLFLAGS)  --output-dir $(top_srcdir)/java/lang --impl-all $(top_srcdir)/java/lang/Object.idl

seq1_wchar_skel.cpp seq1_wchar_skel.h:seq1_wchar.h

docs::
	#echo "nothing"

clean ::
	$(RM) -rf $(OBJDIR)	
	$(RM) -rf ./java/lang/$(OBJDIR)
	$(RM) -rf ./CORBA/$(OBJDIR)
	$(RM) -rf ../$(OBJDIR)
	$(RM) -rf ../../$(OBJDIR)
	$(RM) -rf ./java/$(OBJDIR)
	$(RM) -rf *.h
	$(RM) -rf *.cpp
