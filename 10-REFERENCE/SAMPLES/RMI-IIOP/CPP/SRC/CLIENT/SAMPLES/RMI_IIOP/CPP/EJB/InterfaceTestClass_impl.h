#ifndef ___InterfaceTestClass_impl_h__
#define ___InterfaceTestClass_impl_h__

#include <InterfaceTestClass_skel.h>

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
// RMI:samples.rmi_iiop.cpp.ejb.InterfaceTestClass:E9F7CED4D26BBC81:F5709E5F52840B95
//
class InterfaceTestClass_impl : virtual public OBV_samples::rmi_iiop::cpp::ejb::InterfaceTestClass,
                                virtual public CORBA::DefaultValueRefCountBase
{
    InterfaceTestClass_impl(const InterfaceTestClass_impl&);
    void operator=(const InterfaceTestClass_impl&);

public:

    InterfaceTestClass_impl();
    ~InterfaceTestClass_impl();

    virtual CORBA::ValueBase* _copy_value();

    //
    // IDL:samples/rmi_iiop/cpp/ejb/InterfaceTest/setData:1.0
    //
    virtual void setData(CORBA::LongLong arg0);

    //
    // IDL:samples/rmi_iiop/cpp/ejb/InterfaceTest/getData:1.0
    //
    virtual CORBA::LongLong getData();
};

class InterfaceTestClassFactory_impl : virtual public InterfaceTestClass_init
{
    virtual CORBA::ValueBase* create_for_unmarshal();

public:
    InterfaceTestClassFactory_impl();
    virtual ~InterfaceTestClassFactory_impl();

    virtual InterfaceTestClass* create();
};

} // End of namespace ejb

} // End of namespace cpp

} // End of namespace rmi_iiop

} // End of namespace samples

#endif
