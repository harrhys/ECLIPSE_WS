#include <OB/CORBA.h>
#include <ComplexObject_impl.h>

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
// RMI:samples.rmi_iiop.cpp.ejb.ComplexObject:24B0FD6047F49DE5:2B81C3CBB38A5FC7
//
samples::rmi_iiop::cpp::ejb::ComplexObject_impl::ComplexObject_impl()
{
}

samples::rmi_iiop::cpp::ejb::ComplexObject_impl::~ComplexObject_impl()
{
}

CORBA::ValueBase*
samples::rmi_iiop::cpp::ejb::ComplexObject_impl::_copy_value()
{
    ComplexObject_impl* _r = new ComplexObject_impl;
    // TODO: Implementation
    return _r;
}

//
// IDL:samples/rmi_iiop/cpp/ejb/ComplexObject/interfaceTest:1.0
//
samples::rmi_iiop::cpp::ejb::InterfaceTest*
samples::rmi_iiop::cpp::ejb::ComplexObject_impl::interfaceTest()
{
/*
    // TODO: Implementation
    samples::rmi_iiop::cpp::ejb::InterfaceTest* _r = 0;
    return _r;
*/
    return interfaceTestRef();
}

void
samples::rmi_iiop::cpp::ejb::ComplexObject_impl::interfaceTest(samples::rmi_iiop::cpp::ejb::InterfaceTest* a)
{
    // TODO: Implementation
    interfaceTestRef(a);

}

//
// IDL:samples/rmi_iiop/cpp/ejb/ComplexObject/employeeData:1.0
//
samples::rmi_iiop::cpp::ejb::Employee*
samples::rmi_iiop::cpp::ejb::ComplexObject_impl::employeeData()
{
    // TODO: Implementation
/*
    samples::rmi_iiop::cpp::ejb::Employee* _r = 0;
    return _r;
*/
    return employeeObj();
}

void
samples::rmi_iiop::cpp::ejb::ComplexObject_impl::employeeData(samples::rmi_iiop::cpp::ejb::Employee* a)
{
    // TODO: Implementation
    employeeObj(a);
}

samples::rmi_iiop::cpp::ejb::ComplexObjectFactory_impl::ComplexObjectFactory_impl()
{
}

samples::rmi_iiop::cpp::ejb::ComplexObjectFactory_impl::~ComplexObjectFactory_impl()
{
}

CORBA::ValueBase*
samples::rmi_iiop::cpp::ejb::ComplexObjectFactory_impl::create_for_unmarshal()
{
    // TODO: Implementation
    return new samples::rmi_iiop::cpp::ejb::ComplexObject_impl;
}

samples::rmi_iiop::cpp::ejb::ComplexObject*
samples::rmi_iiop::cpp::ejb::ComplexObjectFactory_impl::create()
{
    // TODO: Implementation
    return new samples::rmi_iiop::cpp::ejb::ComplexObject_impl;
}
