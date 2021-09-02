/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.yzf.autoid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface AutoIncrement {

}