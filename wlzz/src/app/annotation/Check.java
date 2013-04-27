package annotation;

import java.lang.annotation.*;

/**
 * 进行权限检测
 * @author haikang
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Check {
	String value();
}
