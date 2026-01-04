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

MODULE=LibJavaxEJB

EXPORT_LIBRARIES= javaxejb
AR_TARGET = javaxejb

AR_OBJS		= RemoveException \
	RemoveException_skel \
	RemoveException_impl \
	RemoveEx \
	RemoveEx_skel \
	EJBHome \
	EJBHome_skel \
	EJBHome_impl \
	EJBMetaData \
	EJBMetaData_skel \
	EJBMetaData_impl \
	EJBObject \
	EJBObject_skel \
	EJBObject_impl \
	Handle \
	Handle_skel \
	Handle_impl \
	HomeHandle \
	HomeHandle_skel \
	HomeHandle_impl \
	CreateException \
	CreateException_skel \
	CreateException_impl \
	CreateEx \
	CreateEx_skel
	
include ${BUILD_ROOT}/make/rules.mk

RemoveException.cpp RemoveException.h : RemoveException.idl
	$(RM) -f RemoveException.cpp RemoveException.h
	$(RM) -f RemoveException_skel.cpp RemoveException_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all RemoveException.idl

RemoveException_skel.cpp RemoveException_skel.h:RemoveException.h
RemoveException_impl.cpp RemoveException_impl.h:RemoveException_skel.h

RemoveEx.cpp RemoveEx.h : RemoveEx.idl
	$(RM) -f RemoveEx.cpp RemoveEx.h
	$(RM) -f RemoveEx_skel.cpp RemoveEx_skel.h
	$(IDL) $(ALL_IDLFLAGS) RemoveEx.idl

RemoveEx_skel.cpp RemoveEx_skel.h:RemoveException.h


HomeHandle.cpp HomeHandle.h : HomeHandle.idl
	$(RM) -f HomeHandle.cpp HomeHandle.h
	$(RM) -f HomeHandle_skel.cpp HomeHandle_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all HomeHandle.idl

HomeHandle_skel.cpp HomeHandle_skel.h:HomeHandle.h
HomeHandle_impl.cpp HomeHandle_impl.h:HomeHandle_skel.h

EJBObject.cpp EJBObject.h : EJBObject.idl
	$(RM) -f EJBObject.cpp EJBObject.h
	$(RM) -f EJBObject_skel.cpp EJBObject_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all EJBObject.idl

EJBObject_skel.cpp EJBObject_skel.h:EJBObject.h
EJBObject_impl.cpp EJBObject_impl.h:EJBObject_skel.h

Handle.cpp Handle.h : Handle.idl
	$(RM) -f Handle.cpp Handle.h
	$(RM) -f Handle_skel.cpp Handle_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all Handle.idl

Handle_skel.cpp Handle_skel.h:Handle.h
Handle_impl.cpp Handle_impl.h:Handle_skel.h


EJBMetaData.cpp EJBMetaData.h : EJBMetaData.idl
	$(RM) -f EJBMetaData.cpp EJBMetaData.h
	$(RM) -f EJBMetaData_skel.cpp EJBMetaData_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all EJBMetaData.idl

EJBMetaData_skel.cpp EJBMetaData_skel.h:EJBMetaData.h
EJBMetaData_impl.cpp EJBMetaData_impl.h:EJBMetaData_skel.h

EJBHome.cpp EJBHome.h : EJBHome.idl
	$(RM) -f EJBHome.cpp EJBHome.h
	$(RM) -f EJBHome_skel.cpp EJBHome_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all EJBHome.idl
	$(IDL) $(ALL_IDLFLAGS) --impl-all EJBMetaData.idl
	$(IDL) $(ALL_IDLFLAGS) --impl-all Handle.idl
	$(IDL) $(ALL_IDLFLAGS) --impl-all HomeHandle.idl
	$(IDL) $(ALL_IDLFLAGS) --impl-all EJBObject.idl

EJBHome_skel.cpp EJBHome_skel.h:EJBHome.h
EJBHome_impl.cpp EJBHome_impl.h:EJBHome_skel.h

CreateException.cpp CreateException.h : CreateException.idl
	$(RM) -f CreateException.cpp CreateException.h
	$(RM) -f CreateException_skel.cpp CreateException_skel.h
	$(IDL) $(ALL_IDLFLAGS) --impl-all CreateException.idl

CreateException_skel.cpp CreateException_skel.h:CreateException.h
CreateException_impl.cpp CreateException_impl.h:CreateException_skel.h

CreateEx.cpp CreateEx.h : CreateEx.idl
	$(RM) -f CreateEx.cpp CreateEx.h
	$(RM) -f CreateEx_skel.cpp CreateEx_skel.h
	$(IDL) $(ALL_IDLFLAGS) CreateEx.idl

CreateEx_skel.cpp CreateEx_skel.h:CreateEx.h

docs ::
	#echo "nothing"
clean ::
	$(RM) -rf $(OBJDIR)
	$(RM) -rf $(top_srcdir)/javax/$(OBJDIR)
	$(RM) -rf *.h
	$(RM) -rf *.cpp
