#include <OB/CORBA.h>
#include <Employee_impl.h>

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
// RMI:samples.rmi_iiop.cpp.ejb.Employee:F6B8DCF76FA9E3B6:15FE1144B3E90901
//
samples::rmi_iiop::cpp::ejb::Employee_impl::Employee_impl()
{
}

samples::rmi_iiop::cpp::ejb::Employee_impl::~Employee_impl()
{
}

CORBA::ValueBase*
samples::rmi_iiop::cpp::ejb::Employee_impl::_copy_value()
{
    Employee_impl* _r = new Employee_impl;
    // TODO: Implementation
    return _r;
}

samples::rmi_iiop::cpp::ejb::EmployeeFactory_impl::EmployeeFactory_impl()
{
}

samples::rmi_iiop::cpp::ejb::EmployeeFactory_impl::~EmployeeFactory_impl()
{
}

CORBA::ValueBase*
samples::rmi_iiop::cpp::ejb::EmployeeFactory_impl::create_for_unmarshal()
{
    // TODO: Implementation
    return new samples::rmi_iiop::cpp::ejb::Employee_impl;
}

samples::rmi_iiop::cpp::ejb::Employee*
samples::rmi_iiop::cpp::ejb::EmployeeFactory_impl::create()
{
    // TODO: Implementation
    return new samples::rmi_iiop::cpp::ejb::Employee_impl;
}
void
samples::rmi_iiop::cpp::ejb::Employee_impl::setEmployee(::CORBA::WStringValue* arg0)
{
    employee_(arg0);
}

::CORBA::WStringValue*
samples::rmi_iiop::cpp::ejb::Employee_impl::getEmployee()
{
    return employee_();
}

void
samples::rmi_iiop::cpp::ejb::Employee_impl::setSalary(CORBA::Long arg0)
{
    salary(arg0);
}

CORBA::Long
samples::rmi_iiop::cpp::ejb::Employee_impl::getSalary()
{
    return salary();
}

