# 
# Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
# 
# This software is the proprietary information of Sun Microsystems, Inc.  
# Use is subject to license terms.
top_srcdir	= ../../../..
include $(top_srcdir)/make/cpp_def.mk

BUILD_ROOT=$(top_srcdir)
include ${BUILD_ROOT}/make/defines.mk

MODULE=CartClient

EXPORT_BINARIES = client
EXE_TARGET = client

EXE_OBJS	= BookException \
	BookEx \
	Employee \
	Employee_impl \
	InterfaceTest \
	InterfaceTest_impl \
	InterfaceTestClass \
	InterfaceTestClass_impl \
	ComplexObject \
	ComplexObject_impl \
	Cart \
	CartHome \
	BookException_impl \
	Client


MYJAVA_LIB = javaxejb \
	javaxrmicorba \
	javaio \
	javalang \
	orgomgboxedrmi \
	orgomgbxrmijavalang \
	orgomgbxrmicorba

ifeq ($(OS_ARCH),SunOS)
LOCAL_LIB = $(MYJAVA_LIB) CosNaming OB JTC socket nsl dl posix4 pthread
endif

ifeq ($(OS_ARCH),WINNT)
LOCAL_LIB = $(MYJAVA_LIB) CosNaming OB JTC
endif


include ${BUILD_ROOT}/make/rules.mk

BookEx.cpp BookEx.h: BookEx.idl
	$(RM) -f BookEx.cpp BookEx.h
	$(IDL) $(IDLOPT_NOSKEL) $(IDLINCLUDES) BookEx.idl

BookException.cpp BookException.h: BookException.idl
	$(RM) -f BookException.cpp BookException.h
	$(IDL) $(IDLOPT_IMPLALL) $(IDLINCLUDES) BookException.idl

BookException_skel.cpp BookException_skel.h: BookException.h
BookException_impl.cpp BookException_impl.h: BookException_skel.h

Cart.cpp Cart.h: Cart.idl
	$(RM) -f Cart.cpp Cart.h
	$(IDL) $(IDLOPT_NOSKEL) $(IDLINCLUDES) Cart.idl

CartHome.cpp CartHome.h: CartHome.idl
	$(RM) -f CartHome.cpp CartHome.h
	$(IDL) $(IDLOPT_NOSKEL) $(IDLINCLUDES) CartHome.idl

ComplexObject.cpp ComplexObject.h: ComplexObject.idl
	$(RM) -f ComplexObject.cpp ComplexObject.h
	$(IDL) $(IDLOPT_IMPLALL) $(IDLINCLUDES) ComplexObject.idl

#ComplexObject_skel.cpp ComplexObject_skel.h: ComplexObject.h
#ComplexObject_impl.cpp ComplexObject_impl.h: ComplexObject_skel.h

Employee.cpp Employee.h: Employee.idl
	$(RM) -f Employee.cpp Employee.h
	$(IDL) $(IDLOPT_IMPLALL) $(IDLINCLUDES) Employee.idl

#Employee_skel.cpp Employee_skel.h: Employee.h
#Employee_impl.cpp Employee_impl.h: Employee.h

InterfaceTest.cpp InterfaceTest.h: InterfaceTest.idl
	$(RM) -f InterfaceTest.cpp InterfaceTest.h
	$(IDL) $(IDLOPT_IMPLALL) $(IDLINCLUDES) InterfaceTest.idl

InterfaceTest_skel.cpp InterfaceTest_skel.h: InterfaceTest.h
InterfaceTest_impl.cpp InterfaceTest_impl.h: InterfaceTest_skel.h

InterfaceTestClass.cpp InterfaceTestClass.h: InterfaceTest.idl InterfaceTestClass.idl
	$(RM) -f InterfaceTestClass.cpp InterfaceTestClass.h
	$(IDL) $(IDLOPT_IMPLALL) $(IDLINCLUDES) InterfaceTestClass.idl

#InterfaceTestClass_skel.cpp InterfaceTestClass_skel.h: InterfaceTestClass.h
#InterfaceTestClass_impl.cpp InterfaceTestClass_impl.h: InterfaceTestClass_skel.h

docs ::
	$(RM) -rf $(top_srcdir)/../../docs/idl
	$(MKDIR_DASH_P) $(top_srcdir)/../../docs/idl
	$(HIDL) $(IDLINCLUDES) --output-dir $(top_srcdir)/../../docs/idl *.idl
	$(RM) -f $(top_srcdir)/../../docs/idl/_top_.html

clean ::
	$(RM) -rf $(OBJDIR)
	$(RM) -rf $(top_srcdir)/$(OBJDIR)
	$(RM) -rf ../$(OBJDIR)
	$(RM) -rf BookEx*.h
	$(RM) -rf BookEx*.cpp
	$(RM) -rf Cart*.h
	$(RM) -rf Cart*.cpp
	$(RM) -rf ComplexObject.[ch]*
	$(RM) -rf ComplexObject_skel.*
	$(RM) -rf Employee.[ch]*
	$(RM) -rf Employee_skel.*
	$(RM) -rf InterfaceTest.[ch]*
	$(RM) -rf InterfaceTest_skel.*
	$(RM) -rf InterfaceTest_impl.*
	$(RM) -rf InterfaceTestClass.[ch]*
	$(RM) -rf InterfaceTestClass_skel.*
