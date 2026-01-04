#include <OB/CORBA.h>
#include <InterfaceTestClass_impl.h>

//
// IDL:samples:1.0
//

//
// IDL:samples/rmi_iiop:1.0
//

//
// IDL:samples/rmi_iiop/cpp:1.0
//

//
// IDL:samples/rmi_iiop/cpp/ejb:1.0
//

//
// RMI:samples.rmi_iiop.cpp.ejb.InterfaceTestClass:E9F7CED4D26BBC81:F5709E5F52840B95
//
samples::rmi_iiop::cpp::ejb::InterfaceTestClass_impl::InterfaceTestClass_impl()
{
}

samples::rmi_iiop::cpp::ejb::InterfaceTestClass_impl::~InterfaceTestClass_impl()
{
}

CORBA::ValueBase*
samples::rmi_iiop::cpp::ejb::InterfaceTestClass_impl::_copy_value()
{
    InterfaceTestClass_impl* _r = new InterfaceTestClass_impl;
    // TODO: Implementation
    return _r;
}

//
// IDL:samples/rmi_iiop/cpp/ejb/InterfaceTest/setData:1.0
//
void
samples::rmi_iiop::cpp::ejb::InterfaceTestClass_impl::setData(CORBA::LongLong arg0)
{
    // TODO: Implementation
    data(arg0);
}

//
// IDL:samples/rmi_iiop/cpp/ejb/InterfaceTest/getData:1.0
//
CORBA::LongLong
samples::rmi_iiop::cpp::ejb::InterfaceTestClass_impl::getData()
{
    // TODO: Implementation
/*
    CORBA::LongLong _r = 0;
    return _r;
*/
    return data();
}

samples::rmi_iiop::cpp::ejb::InterfaceTestClassFactory_impl::InterfaceTestClassFactory_impl()
{
}

samples::rmi_iiop::cpp::ejb::InterfaceTestClassFactory_impl::~InterfaceTestClassFactory_impl()
{
}

CORBA::ValueBase*
samples::rmi_iiop::cpp::ejb::InterfaceTestClassFactory_impl::create_for_unmarshal()
{
    // TODO: Implementation
    return new samples::rmi_iiop::cpp::ejb::InterfaceTestClass_impl;
}

samples::rmi_iiop::cpp::ejb::InterfaceTestClass*
samples::rmi_iiop::cpp::ejb::InterfaceTestClassFactory_impl::create()
{
    // TODO: Implementation
    return new samples::rmi_iiop::cpp::ejb::InterfaceTestClass_impl;
}
