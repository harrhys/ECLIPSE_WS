#
# Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
# 
# This software is the proprietary information of Sun Microsystems, Inc.  
# Use is subject to license terms.

top_srcdir	= ../..
include $(top_srcdir)/make/cpp_def.mk


BUILD_ROOT=$(top_srcdir)
include ${BUILD_ROOT}/make/defines.mk

MODULE=LibJavaLang

EXPORT_LIBRARIES= javalang
AR_TARGET = javalang

AR_OBJS		= Object \
	Object_skel \
	StackTraceElement \
	StackTraceElement_skel \
	StackTraceElement_impl \
	Throwable \
	Throwable_skel \
	Throwable_impl \
	Exception \
	Exception_skel \
	Exception_impl \
	Ex \
	Ex_skel \
	ThrowableEx \
	ThrowableEx_skel

include ${BUILD_ROOT}/make/rules.mk

#Ex.cpp Ex.h : Ex.idl
#	$(RM) -f Ex.cpp Ex.h
#	$(RM) -f Ex_skel.cpp Ex_skel.h
#	$(IDL) $(ALL_IDLFLAGS) Ex.idl

#Ex_skel.cpp Ex_skel.h:Ex.h

Exception.cpp Exception.h : Exception.idl
	$(RM) -f Exception.cpp Exception.h
	$(RM) -f Exception_skel.cpp Exception_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all Exception.idl

Exception_skel.cpp Exception_skel.h:Exception.h
Exception_impl.cpp Exception_impl.h:Exception_skel.h

Object.cpp Object.h : Object.idl
	$(RM) -f Object.cpp Object.h
	$(RM) -f Object_skel.cpp Object_skel.h
	$(IDL) $(ALL_IDLFLAGS) Object.idl

Object_skel.cpp Object_skel.h:Object.h

StackTraceElement.cpp StackTraceElement.h : StackTraceElement.idl
	$(RM) -f StackTraceElement.cpp StackTraceElement.h
	$(RM) -f StackTraceElement_skel.cpp StackTraceElement_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all StackTraceElement.idl

StackTraceElement_skel.cpp StackTraceElement_skel.h:StackTraceElement.h
StackTraceElement_impl.cpp StackTraceElement_impl.h:StackTraceElement_skel.h

Throwable.cpp Throwable.h : Throwable.idl
	$(RM) -f Throwable.cpp Throwable.h
	$(RM) -f Throwable_skel.cpp Throwable_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all Throwable.idl

Throwable_skel.cpp Throwable_skel.h:Throwable.h
Throwable_impl.cpp Throwable_impl.h:Throwable_skel.h

ThrowableEx.cpp ThrowableEx.h : ThrowableEx.idl
	$(RM) -f ThrowableEx.cpp ThrowableEx.h
	$(RM) -f ThrowableEx_skel.cpp ThrowableEx_skel.h
	$(IDL) $(ALL_IDLFLAGS) ThrowableEx.idl

ThrowableEx_skel.cpp ThrowableEx_skel.h:ThrowableEx.h

docs ::
	#echo "nothing"
clean ::
	$(RM) -rf $(OBJDIR)
	$(RM) -rf $(top_srcdir)/java/$(OBJDIR)
	$(RM) -rf Throwable*.h
	$(RM) -rf Throwable*.cpp
	$(RM) -rf Exception*.h
	$(RM) -rf Exception*.cpp
	$(RM) -rf Object*.h
	$(RM) -rf Object*.cpp
	$(RM) -rf StackTraceElement*.h
	$(RM) -rf StackTraceElement*.cpp
