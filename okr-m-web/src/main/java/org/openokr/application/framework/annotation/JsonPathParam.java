package org.openokr.application.framework.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonPathParam {

	String value() default "$";

	boolean required() default true;

	String defaultValue() default "{}";
}
