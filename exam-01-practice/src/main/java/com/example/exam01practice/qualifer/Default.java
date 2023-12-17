package com.example.exam01practice.qualifer;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Qualifier
@Target({FIELD, TYPE, METHOD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Default {
}
