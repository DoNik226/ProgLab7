package requests.requestAnnotations;

import authorization.UserRoles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {
    String[] users() default {};

    UserRoles[] roles() default {};
    // задел на будущее
}
