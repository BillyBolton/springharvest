package dev.springharvest.codegen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Harvest {

  String domainContextPath() default "";

  String domainNameSingular() default "";

  String domainNamePlural() default "";

  String parentDomainName() default "";

}
