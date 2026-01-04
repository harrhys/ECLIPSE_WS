#ifndef ___Employee_impl_h__
#define ___Employee_impl_h__

#include <Employee_skel.h>

//
// IDL:samples:1.0
//
namespace samples
{

//
// IDL:samples/rmi_iiop:1.0
//
namespace rmi_iiop
{

//
// IDL:samples/rmi_iiop/cpp:1.0
//
namespace cpp
{

//
// IDL:samples/rmi_iiop/cpp/ejb:1.0
//
namespace ejb
{

//
// RMI:samples.rmi_iiop.cpp.ejb.Employee:F6B8DCF76FA9E3B6:15FE1144B3E90901
//
class Employee_impl : virtual public OBV_samples::rmi_iiop::cpp::ejb::Employee,
                      virtual public CORBA::DefaultValueRefCountBase
{
    Employee_impl(const Employee_impl&);
    void operator=(const Employee_impl&);

public:

    Employee_impl();
    ~Employee_impl();

    virtual CORBA::ValueBase* _copy_value();
    CORBA::Long getSalary();
    void setSalary(CORBA::Long);
    ::CORBA::WStringValue* getEmployee();
    void setEmployee(CORBA::WStringValue*);

};

class EmployeeFactory_impl : virtual public Employee_init
{
    virtual CORBA::ValueBase* create_for_unmarshal();

public:
    EmployeeFactory_impl();
    virtual ~EmployeeFactory_impl();

    virtual Employee* create();
};

} // End of namespace ejb

} // End of namespace cpp

} // End of namespace rmi_iiop

} // End of namespace samples

#endif
