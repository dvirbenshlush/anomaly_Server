package com.example.demo;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value=TYPE)
@Retention(value=RUNTIME)
@Documented
@Inherited
//@Import(value=org.springframework.data.jpa.repository.config.JpaRepositoriesRegistrar.class)
public @interface EnableJpaRepositories {
}
