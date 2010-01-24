package javax.swingx.connect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used by the ConnectionManager object to check a method for
 * the permission to be handled as a signal. For details about the connection
 * handling see the manual and check the documentation on ConnectionManager.
 * 
 * @see ConnectionManager
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Signal {
}
