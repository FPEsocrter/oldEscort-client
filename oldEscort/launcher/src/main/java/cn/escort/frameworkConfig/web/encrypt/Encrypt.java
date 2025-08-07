package cn.escort.frameworkConfig.web.encrypt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Encrypt {
    //cn.escort.frameworkConfig.web.decrypt.DecryptRequestAdvice
}
