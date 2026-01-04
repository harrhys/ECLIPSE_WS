#ifndef ___ClassDesc_impl_h__
#define ___ClassDesc_impl_h__

#include <ClassDesc_skel.h>

//
// IDL:javax:1.0
//
namespace javax
{

//
// IDL:javax/rmi:1.0
//
namespace rmi
{

//
// IDL:javax/rmi/CORBA:1.0
//
namespace CORBA
{

//
// RMI:javax.rmi.CORBA.ClassDesc:2BABDA04587ADCCC:CFBF02CF5294176B
//
class ClassDesc_impl : virtual public OBV_javax::rmi::CORBA::ClassDesc,
                       virtual public ::CORBA::DefaultValueRefCountBase
{
    ClassDesc_impl(const ClassDesc_impl&);
    void operator=(const ClassDesc_impl&);

public:

    ClassDesc_impl();
    ~ClassDesc_impl();

    virtual ::CORBA::ValueBase* _copy_value();
};

class ClassDescFactory_impl : virtual public ::CORBA::ValueFactoryBase
{
    virtual ::CORBA::ValueBase* create_for_unmarshal();

public:
    ClassDescFactory_impl();
    virtual ~ClassDescFactory_impl();
};

} // End of namespace CORBA

} // End of namespace rmi

} // End of namespace javax

#endif
