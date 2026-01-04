#  Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
#  
#  This software is the proprietary information of Sun Microsystems, Inc.  
#  Use is subject to license terms.

#BEGIN ORB SPECIFIC STUFF
#change this to your OB installation directory
OB_INSTDIR      = /usr/local
# do not touch the stuff below
OB_CPPFLAGS     =  -I$(OB_INSTDIR)/include
OB_LDFLAGS      =  -L$(OB_INSTDIR)/lib
OB_IDLFLAGS     =  -I$(OB_INSTDIR)/idl
OB_LIBS         =  -lOB -lJTC
IDL   = $(OB_INSTDIR)/bin/idl
HIDL    = $(OB_INSTDIR)/bin/hidl
IDLDIR    = $(OB_INSTDIR)/idl

IDLOPT_NOSKEL = --no-skeletons
IDLOPT_IMPLALL = --impl-all

IDLINCLUDES= -I. \
	-I$(top_srcdir) \
	-I$(OB_INSTDIR)/idl \
	-I$(OB_INSTDIR)/idl/OB

#OB_EXTRALIBS    =  -lsocket -lnsl -ldl -lposix4 -lpthread
#END ORB SPECIFIC STUFF

ALL_CXXINCLUDES= -I. \
	-I$(OB_INSTDIR)/include \
	-I$(top_srcdir) \
	-I$(top_srcdir)/java/lang \
	-I$(top_srcdir)/java/io \
	-I$(top_srcdir)/org/omg/boxedRMI/CORBA \
	-I$(top_srcdir)/org/omg/boxedRMI/java/lang \
	-I$(top_srcdir)/javax/ejb \
	-I$(top_srcdir)/org/omg/boxedRMI \
	-I$(top_srcdir)/javax/rmi/CORBA

ALL_LIBDIRS= $(OB_INSTDIR)/lib \
	$(top_srcdir)/java/lang/$(OBJDIR) \
	$(top_srcdir)/java/io/$(OBJDIR) \
	$(top_srcdir)/org/omg/boxedRMI/CORBA/$(OBJDIR) \
	$(top_srcdir)/org/omg/boxedRMI/java/lang/$(OBJDIR) \
	$(top_srcdir)/javax/ejb/$(OBJDIR) \
	$(top_srcdir)/org/omg/boxedRMI/$(OBJDIR) \
	$(top_srcdir)/javax/rmi/CORBA/$(OBJDIR)

# put it in a 7.0 Public Macro
LOCAL_INC = $(ALL_CXXINCLUDES)
LOCAL_LIBDIRS = $(ALL_LIBDIRS)

ALL_IDLFLAGS  = $(OB_IDLFLAGS) -DOB_CPLUSPLUS $(IDLINCLUDES)
