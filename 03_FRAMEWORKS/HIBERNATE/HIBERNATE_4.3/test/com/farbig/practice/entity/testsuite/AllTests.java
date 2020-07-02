package com.farbig.practice.entity.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.farbig.practice.entity.testcases.CollectionTestCases;
import com.farbig.practice.entity.testcases.CompositeIdTestCases;
import com.farbig.practice.entity.testcases.ManyToManyTestCases;
import com.farbig.practice.entity.testcases.OneToManyTestCases;
import com.farbig.practice.entity.testcases.OneToOneTestCases;

@RunWith(Suite.class)
@SuiteClasses({ 
	CollectionTestCases.class, 
	CompositeIdTestCases.class, 
	OneToOneTestCases.class,
	OneToManyTestCases.class, 
	ManyToManyTestCases.class })
public class AllTests {

}
