#include <OB/CORBA.h>
#include <ClassDesc_impl.h>

//
// IDL:javax:1.0
//

//
// IDL:javax/rmi:1.0
//

//
// IDL:javax/rmi/CORBA:1.0
//

//
// RMI:javax.rmi.CORBA.ClassDesc:2BABDA04587ADCCC:CFBF02CF5294176B
//
javax::rmi::CORBA::ClassDesc_impl::ClassDesc_impl()
{
}

javax::rmi::CORBA::ClassDesc_impl::~ClassDesc_impl()
{
}

::CORBA::ValueBase*
javax::rmi::CORBA::ClassDesc_impl::_copy_value()
{
    ClassDesc_impl* _r = new ClassDesc_impl;
    // TODO: Implementation
    return _r;
}

javax::rmi::CORBA::ClassDescFactory_impl::ClassDescFactory_impl()
{
}

javax::rmi::CORBA::ClassDescFactory_impl::~ClassDescFactory_impl()
{
}

::CORBA::ValueBase*
javax::rmi::CORBA::ClassDescFactory_impl::create_for_unmarshal()
{
    // TODO: Implementation
    return new javax::rmi::CORBA::ClassDesc_impl;
}
