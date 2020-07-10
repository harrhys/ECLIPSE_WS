package com.farbig.cart.entity.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
		{ AdminTestCases.class, 
		MerchantTestCases.class, 
		CustomerTestCases.class })
public class AllTests {

}
