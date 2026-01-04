#ifndef ___ComplexObject_impl_h__
#define ___ComplexObject_impl_h__

#include <ComplexObject_skel.h>

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
// RMI:samples.rmi_iiop.cpp.ejb.ComplexObject:24B0FD6047F49DE5:2B81C3CBB38A5FC7
//
class ComplexObject_impl : virtual public OBV_samples::rmi_iiop::cpp::ejb::ComplexObject,
                           virtual public CORBA::DefaultValueRefCountBase
{
    ComplexObject_impl(const ComplexObject_impl&);
    void operator=(const ComplexObject_impl&);

public:

    ComplexObject_impl();
    ~ComplexObject_impl();

    virtual CORBA::ValueBase* _copy_value();

    //
    // IDL:samples/rmi_iiop/cpp/ejb/ComplexObject/interfaceTest:1.0
    //
    virtual samples::rmi_iiop::cpp::ejb::InterfaceTest* interfaceTest();
    virtual void interfaceTest(samples::rmi_iiop::cpp::ejb::InterfaceTest*);

    //
    // IDL:samples/rmi_iiop/cpp/ejb/ComplexObject/employeeData:1.0
    //
    virtual samples::rmi_iiop::cpp::ejb::Employee* employeeData();
    virtual void employeeData(samples::rmi_iiop::cpp::ejb::Employee*);
};

class ComplexObjectFactory_impl : virtual public ComplexObject_init
{
    virtual CORBA::ValueBase* create_for_unmarshal();

public:
    ComplexObjectFactory_impl();
    virtual ~ComplexObjectFactory_impl();

    virtual ComplexObject* create();
};

} // End of namespace ejb

} // End of namespace cpp

} // End of namespace rmi_iiop

} // End of namespace samples

#endif
