package com.farbig.practice.cdi.qualifiers;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;
@Qualifier
@Retention(RUNTIME)
@Target({ TYPE,FIELD,METHOD, PARAMETER, CONSTRUCTOR,ElementType.LOCAL_VARIABLE})
public @interface ProducerChosen {

}
