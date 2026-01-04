#
# Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
# 
# This software is the proprietary information of Sun Microsystems, Inc.  
# Use is subject to license terms.
#

top_srcdir	= ../..
include $(top_srcdir)/make/cpp_def.mk


BUILD_ROOT=$(top_srcdir)
include ${BUILD_ROOT}/make/defines.mk

MODULE=LibJavaIO

EXPORT_LIBRARIES= javaio
AR_TARGET = javaio

AR_OBJS		= IOException \
	IOException_skel \
	IOException_impl \
	IOEx \
	IOEx_skel \
	OutputStream \
	OutputStream_skel \
	OutputStream_impl \
	FilterOutputStream \
	FilterOutputStream_skel \
	FilterOutputStream_impl \
	Writer \
	Writer_skel \
	Writer_impl \
	PrintStream \
	PrintStream_skel \
	PrintStream_impl \
	PrintWriter \
	PrintWriter_skel \
	PrintWriter_impl \
	Serializable \
	Serializable_skel \
	Serializable_impl

include ${BUILD_ROOT}/make/rules.mk

FilterOutputStream.cpp FilterOutputStream.h : FilterOutputStream.idl
	rm -f FilterOutputStream.cpp FilterOutputStream.h
	rm -f FilterOutputStream_skel.cpp FilterOutputStream_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all FilterOutputStream.idl

FilterOutputStream_skel.cpp FilterOutputStream_skel.h:FilterOutputStream.h
FilterOutputStream_impl.cpp FilterOutputStream_impl.h:FilterOutputStream_skel.h

IOEx.cpp IOEx.h : IOEx.idl
	rm -f IOEx.cpp IOEx.h
	rm -f IOEx_skel.cpp IOEx_skel.h
	$(IDL) $(ALL_IDLFLAGS) IOEx.idl

IOEx_skel.cpp IOEx_skel.h:IOEx.h

IOException.cpp IOException.h : IOException.idl
	rm -f IOException.cpp IOException.h
	rm -f IOException_skel.cpp IOException_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all IOException.idl

IOException_skel.cpp IOException_skel.h:IOException.h
IOException_impl.cpp IOException_impl.h:IOException_skel.h

OutputStream.cpp OutputStream.h : OutputStream.idl
	rm -f OutputStream.cpp OutputStream.h
	rm -f OutputStream_skel.cpp OutputStream_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all OutputStream.idl

OutputStream_skel.cpp OutputStream_skel.h:OutputStream.h
OutputStream_impl.cpp OutputStream_impl.h:OutputStream_skel.h

PrintStream.cpp PrintStream.h : PrintStream.idl
	rm -f PrintStream.cpp PrintStream.h
	rm -f PrintStream_skel.cpp PrintStream_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all PrintStream.idl

PrintStream_skel.cpp PrintStream_skel.h:PrintStream.h
PrintStream_impl.cpp PrintStream_impl.h:PrintStream_skel.h

PrintWriter.cpp PrintWriter.h : PrintWriter.idl
	rm -f PrintWriter.cpp PrintWriter.h
	rm -f PrintWriter_skel.cpp PrintWriter_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all PrintWriter.idl

PrintWriter_skel.cpp PrintWriter_skel.h:PrintWriter.h
PrintWriter_impl.cpp PrintWriter_impl.h:PrintWriter_skel.h

Writer.cpp Writer.h : Writer.idl
	rm -f Writer.cpp Writer.h
	rm -f Writer_skel.cpp Writer_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all Writer.idl

Writer_skel.cpp Writer_skel.h:Writer.h
Writer_impl.cpp Writer_impl.h:Writer_skel.h

Serializable.cpp Serializable.h : Serializable.idl
	rm -f Serializable.cpp Serializable.h
	rm -f Serializable_skel.cpp Serializable_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all Serializable.idl

Serializable_skel.cpp Serializable_skel.h:Serializable.h
Serializable_impl.cpp Serializable_impl.h:Serializable_skel.h

docs ::
	#echo "nothing"
clean ::
	$(RM) -rf $(OBJDIR)
	$(RM) -rf *.h
	$(RM) -rf *.cpp
