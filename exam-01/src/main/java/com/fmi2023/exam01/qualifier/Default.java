package com.fmi2023.exam01.qualifier;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;

@Qualifier
@Target({FIELD, TYPE, METHOD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Default {
}
