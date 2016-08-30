package com.appheader.DingQuPostman.common.utils.json.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JSONField {

	String jsonKey() default "";

	String defaultStringValue() default "";

	int defaultIntValue() default 0;

	boolean defaultBooleanValue() default false;

	double defaultDoubleValue() default 0;

	long defaultLongValue() default 0;

}
