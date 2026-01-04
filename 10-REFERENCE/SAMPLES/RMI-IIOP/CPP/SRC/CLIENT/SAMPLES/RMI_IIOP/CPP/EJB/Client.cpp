/*
 *
 * Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 */


#include <OB/CORBA.h>
#include <OB/CosNaming.h>
#include "Cart.h"
#include "CartHome.h"
#include "BookEx.h"
#include "ComplexObject.h"
#include "ComplexObject_impl.h"
#include "Employee.h"
#include "Employee_impl.h"
#include "InterfaceTestClass.h"
#include "InterfaceTestClass_impl.h"
#include "InterfaceTest_impl.h"

#include <javax/ejb/CreateEx.h>
#include <javax/ejb/RemoveEx.h>

#include "BookException_impl.h"
#include <javax/ejb/CreateException_impl.h>
#include <javax/ejb/RemoveException_impl.h>

#include <stdlib.h>
#include <iostream.h>
#include <wchar.h>

using namespace ::samples::rmi_iiop::cpp::ejb;


ostream& operator <<(ostream& str, const CORBA::WChar* buf);
 


int
run(CORBA::ORB_ptr orb, int argc, char* argv[])
{

   try {

    /**
     * Create and Register value factories
     */
    //BookException can be thrown by Cart->remove() 
    samples::rmi_iiop::cpp::ejb::BookExceptionFactory_impl* bookExVF =
    new samples::rmi_iiop::cpp::ejb::BookExceptionFactory_impl();
    //CreateException can be thrown by CartHome->create()
    javax::ejb::CreateExceptionFactory_impl* createExVF =
    new javax::ejb::CreateExceptionFactory_impl();
    //RemoveException can be thrown by CartHome->remove()
    javax::ejb::RemoveExceptionFactory_impl* removeExVF =
    new javax::ejb::RemoveExceptionFactory_impl();
    // Create and register seq1_WStringValue factory here
    org::omg::boxedRMI::CORBA::seq1_WStringValue_init* seqwstrVF =
    new org::omg::boxedRMI::CORBA::seq1_WStringValue_init();

    samples::rmi_iiop::cpp::ejb::ComplexObjectFactory_impl *complexObjectVf = 
       new samples::rmi_iiop::cpp::ejb::ComplexObjectFactory_impl();
    samples::rmi_iiop::cpp::ejb::EmployeeFactory_impl *employeeVf = 
       new samples::rmi_iiop::cpp::ejb::EmployeeFactory_impl();
    samples::rmi_iiop::cpp::ejb::InterfaceTestClassFactory_impl 
       *interfaceTestClassVf = 
       new samples::rmi_iiop::cpp::ejb::InterfaceTestClassFactory_impl();

    cout << "Cart Client: Registering ValueFactories with ORB" << endl;

    // register valueFactories with ORB
    orb->register_value_factory(
         samples::rmi_iiop::cpp::ejb::BookException::_OB_id(),
         bookExVF);
    orb->register_value_factory(
         javax::ejb::CreateException::_OB_id(),
         createExVF);
    orb->register_value_factory(
         javax::ejb::RemoveException::_OB_id(),
         removeExVF);
    // register seq1_WStringValue factory here
    orb->register_value_factory(
         org::omg::boxedRMI::CORBA::seq1_WStringValue::_OB_id(),seqwstrVF);

    orb->register_value_factory( 
         samples::rmi_iiop::cpp::ejb::ComplexObject::_OB_id(),complexObjectVf);
    orb->register_value_factory( 
         samples::rmi_iiop::cpp::ejb::Employee::_OB_id(),employeeVf);
    orb->register_value_factory( 
         samples::rmi_iiop::cpp::ejb::InterfaceTestClass::_OB_id(),
         interfaceTestClassVf);

    cout << "Cart Client: Obtaining Reference to NameService of IAS" << endl;
    /**
     * Get a reference to the NamingService RootContext
     */
     CosNaming::NamingContext_var rootContext = CosNaming::NamingContext::
                   _narrow(orb->resolve_initial_references("NameService"));
    if (CORBA::is_nil(rootContext)) {
       cout <<"Cart Client: Resolve Initial References NameService Failed," 
            << "bailing out.." << endl;
	     return EXIT_FAILURE;
     }
			
    /**
     * Create the NameComponent for looking up the Cart Bean
     * "ejb/CartEJB"
     */
     CosNaming::Name homeNameComponent;
     homeNameComponent.length(2);
     homeNameComponent[0].id=(const char *)"ejb";
     homeNameComponent[0].kind = (const char *) "";
     homeNameComponent[1].id=(const char *)"CartEJB";
     homeNameComponent[1].kind = (const char *) "";

    cout << "Cart Client: Looking up the Cart Home reference \"ejb/CartEJB\"" 
         << endl;

    /**
     * Get "CartHome" object by calling resovle on rootcontext
     */
    CORBA::Object_var obj = rootContext->resolve(homeNameComponent);
    if (CORBA::is_nil(obj)) {
       cout <<"Cart Client:NameService Resolve Failed," 
            << "for Component \"ejb/CartEJB\"," <<" bailing out.." << endl;
	     return EXIT_FAILURE;
     }
    /**
     * Narrow the reference obtained into a CartHome
     */
    cout << "Cart Client: Narrowing the received object to CartHome" << endl;
    samples::rmi_iiop::cpp::ejb::CartHome_var cartHome=
    samples::rmi_iiop::cpp::ejb::CartHome::_narrow(obj);
    if (CORBA::is_nil(cartHome)) {
       cout <<"Cart Client:Narrow for CartHome Failed, bailing out.." 
            << endl;
	     return EXIT_FAILURE;
     }
    cout << "Cart Client: Narrowed CartHome... " << endl;

    /**
     * Now we are ready to create a Cart using the Home
     */
     CORBA::WStringValue_var personvar = 
     new CORBA::WStringValue((const CORBA::WChar*)L"Duke DeEarl");

     cout << "Cart Client: Calling Create on Cart Home" << endl;

     samples::rmi_iiop::cpp::ejb::Cart_var cart=
     cartHome->create__CORBA_WStringValue(personvar);
     if (CORBA::is_nil(cart)) {
       cout <<"Cart Client:CartHome->create(), returned NULL CART," 
            <<" bailing out.." << endl;
	     return EXIT_FAILURE;
     }

     cout << "Cart Client: Cart Created ....." << endl;
     /**
      * Now create some book titles
      */
     CORBA::WStringValue_var title1 = 
     new CORBA::WStringValue((const CORBA::WChar*)L"The Martian Chronicles");
     CORBA::WStringValue_var title2 = 
     new CORBA::WStringValue((const CORBA::WChar*)L"2001 A Space Odyssey");
     CORBA::WStringValue_var title3 = 
     new CORBA::WStringValue((const CORBA::WChar*)L"The Left Hand of Darkness");
     /**
      * Now add the books to CART
      */
     cout << "Cart Client: Adding Book Titles to the Cart" << endl;

     cart->addBook(title1);
     cout << "Cart Client: Added Book Title 1: The Martian Chronicles" << endl;
     cart->addBook(title2);
     cout << "Cart Client: Added Book Title 2: 2001 A Space Odyssey" << endl;
     cart->addBook(title3);
     cout << "Cart Client: Added Book Title 3: The Left Hand of Darkness" 
          << endl;

     /**
      * Now List the Contents of the CART
      */
     cout << "Cart Client: Listing the Cart Contents" << endl;
     ::org::omg::boxedRMI::CORBA::seq1_WStringValue* contents =
     cart->contents();
     cout << "------------------------------------" << endl;
     for (CORBA::ULong i=0; i < contents->length(); i++)
     {
        const CORBA::WChar* tmp = ((*contents)[i])->_value();
	cout << "Title[" << (i+1) << "]: " << tmp << endl;
     }
     cout << "------------------------------------" << endl;
     /**
      * release the contents
      */
     contents->release();
     /**
      * Now try to remove a book title which does not exist in the CART
      */
     cout << "Cart Client: Calling cart->removeBook(title) with invalid Title" 
          << endl;
     cout << "Cart Client: This should result in a BookException being Thrown" 
          << endl;
     CORBA::WStringValue_var title4 = new CORBA::WStringValue(
              (const CORBA::WChar*)L"Alice in Wonderland");
     try {
       cart->removeBook(title4);
     } 
     catch(const samples::rmi_iiop::cpp::ejb::BookEx& ex1)
     {
      cout << "Cart Client: Caught BookException: " << ex1 << endl; 
      cout << "Possible Cause : Invalid BookName given to Cart->remove() " 
           << endl; 
     }
     /*-----------------------------------------------------------------*/

       cout << "---------------------------------------------------" << endl;
       cout << "Starting Here, the sample demonstrates passing IDL-valuetypes"
            << " (i.e java serializable objects)" << endl;
       cout << "Dummy methods added to Cart interface will be used to"
            << " demonstrate passing ValueTypes" << endl;

       //  The java serializable object has been defined as ComplexObject.
       // It contains another java serializable user defined object Employee
       // and a reference to an Interface, called InterfaceTest.
       samples::rmi_iiop::cpp::ejb::ComplexObject* input_complexobject_ptr = 
       new samples::rmi_iiop::cpp::ejb::ComplexObject_impl();

       samples::rmi_iiop::cpp::ejb::Employee_impl* input_employee_ptr = 
       new samples::rmi_iiop::cpp::ejb::Employee_impl();

       CORBA::WStringValue_var employeeName = new CORBA::WStringValue(
              (const CORBA::WChar*)L"Bruce Willis");

      input_employee_ptr->setSalary(200000);
      input_employee_ptr->setEmployee(employeeName);


      // Setting the data in InterfaceTest class
      samples::rmi_iiop::cpp::ejb::InterfaceTestClass_impl *trf = 
      new samples::rmi_iiop::cpp::ejb::InterfaceTestClass_impl();
      CORBA::LongLong inputdata = 10000678;
      trf->setData(inputdata);

      input_complexobject_ptr->employeeData(input_employee_ptr);

      (dynamic_cast<samples::rmi_iiop::cpp::ejb::ComplexObject_impl*>
      (input_complexobject_ptr))->interfaceTest(trf);

       cout << "Cart Client: Sending ComplexObject Valuetype" << endl;
       cart->complexObject(input_complexobject_ptr);
       cout << "Cart Client: Receiving ComplexObject Valuetype" << endl;
       samples::rmi_iiop::cpp::ejb::ComplexObject* output_complexobject_ptr = 
       cart->complexObject();
       const CORBA::WChar *output_employeeName = 
       (dynamic_cast<samples::rmi_iiop::cpp::ejb::Employee_impl*>
       (output_complexobject_ptr->employeeData()))->getEmployee()->_value();
       cout << "Employee name is " << output_employeeName << endl;
       cout << "Employee salary  is "; 
       cout << (dynamic_cast<samples::rmi_iiop::cpp::ejb::Employee_impl*>
       (output_complexobject_ptr->employeeData()))->getSalary() << endl;

       samples::rmi_iiop::cpp::ejb::InterfaceTest *output_trf = 
       (dynamic_cast<samples::rmi_iiop::cpp::ejb::ComplexObject_impl*>
       (output_complexobject_ptr))->interfaceTest();
       cout << "data in the InterfaceTest object is " 
            << (int)output_trf->getData() << endl;
       cout << "---------------------------------------------------" << endl;


     /*-----------------------------------------------------------------*/

     /**
      * Now try to call remove on the CART itself
      */
     cout << "Cart Client: Calling cart->remove(), (i.e remove() on EJBObject)" 
          << " indicating we are done with the cart" << endl;
     cart->remove();
     cout << "Cart Client: cart->remove() successful, exiting program" << endl; 

    } 
    catch(const samples::rmi_iiop::cpp::ejb::BookEx& ex1)
    {
      cout << "Cart Client: Caught BookException: " << ex1 << endl; 
      cout << "Possible Cause : Invalid BookName given to Cart->remove() " 
           << endl; 
	    return EXIT_FAILURE;
    }
    catch(const javax::ejb::CreateEx& ex2)
    {
      cout << "Cart Client:Caught javax::ejb::CreateException: " << ex2 
           << endl; 
      cout << "Possible Cause : AppServer Unable to create Cart" << endl; 
	    return EXIT_FAILURE;
    }
    catch(const javax::ejb::RemoveEx& ex3)
    {
      cout << "Cart Client:Caught javax::ejb::RemoveException: " << ex3 
           << endl; 
      cout << "Possible Cause : cartHome->remove() failed OR invalid Cart" 
           << endl;
	    return EXIT_FAILURE;
    }
    catch(const CORBA::BAD_PARAM& ex4)
    {
      cout << "Cart Client:Caught BAD PARAMETER Exception: " << ex4 << endl; 
	    return EXIT_FAILURE;
    }
    catch(const CORBA::Exception& ex5)
    {
      cout << "Cart Client:Caught Unexpected Exception: " << ex5 << endl; 
	    return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}

int
main(int argc, char* argv[], char*[])
{
    int status = EXIT_SUCCESS;
    CORBA::ORB_var orb;

    try
    {
	orb = CORBA::ORB_init(argc, argv);
	status = run(orb, argc, argv);
    }
    catch(const CORBA::Exception& ex)
    {
	cerr << ex << endl;
	status = EXIT_FAILURE;
    }

    if(!CORBA::is_nil(orb))
    {
	try
	{
	    orb -> destroy();
	}
	catch(const CORBA::Exception& ex)
	{
	    cerr << ex << endl;
	    status = EXIT_FAILURE;
	}
    }
    
    return status;
}

/**
 * This is an implementation of the << operator to
 * print a wchar_t* buffer. 
 * Note : buf[0] seems to contain some Junk, and hence
 * the use of i=1 below.
 *@param str, the outputstream to be written to
 *@param buf, the wide-character string to be printed
 *@return the outputstream.
 */
ostream& operator <<(ostream& str, const CORBA::WChar* buf) 
{
  const wchar_t* tmp=(const wchar_t*)buf;

  for (int i=1; i < wcslen(tmp); i++)
  {
    char a = tmp[i];
    str << a;
  }
return str;
}
