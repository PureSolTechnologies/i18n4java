package javax.swingx.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.apache.log4j.Logger;

/**
 * This class is a central register for applications to register classes
 * for interfaces. This is used to decouple dependencies.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class ClassRegistry {

    static private final Logger logger =
	    Logger.getLogger(ClassRegistry.class);

    static public final String REGISTRY_FILE = "/config/registry";

    static private Hashtable<String, ClassRegistryElement> register =
	    new Hashtable<String, ClassRegistryElement>();
    static private Hashtable<String, Object> classes =
	    new Hashtable<String, Object>();

    static public void register(Class<?> interfce,
	    ClassRegistryElement element) {
	register.put(interfce.getName(), element);
    }

    static public void register(Class<?> interfce) {
	try {
	    logger.info("Register interface '" + interfce.getName() + "'");
	    String className =
		    Configurator.getEntry(REGISTRY_FILE, interfce
			    .getName(), "class", true);
	    logger.debug("class: '" + className + "'");
	    String typeName =
		    Configurator.getEntry(REGISTRY_FILE, interfce
			    .getName(), "type", true);
	    logger.debug("type: '" + typeName + "'");
	    ClassRegistryElementType type =
		    ClassRegistryElementType.from(typeName);
	    register(interfce, new ClassRegistryElement(type, className));
	} catch (IllegalArgumentException e) {
	    logger.error(e.getMessage(), e);
	}
    }

    static public boolean isRegistered(Class<?> interfce) {
	return (register.containsKey(interfce.getName()));
    }

    static public void unregister(Class<?> interfce) {
	register.remove(interfce.getName());
    }

    static public Object create(Class<?> interfce) {
	logger.debug("create for interface '" + interfce.getName() + "'");
	if (!isRegistered(interfce)) {
	    register(interfce);
	    if (!isRegistered(interfce)) {
		return null;
	    }
	}
	ClassRegistryElement element = register.get(interfce.getName());
	if (element.getType() == ClassRegistryElementType.SINGLETON) {
	    return singleton(interfce, element);
	} else if (element.getType() == ClassRegistryElementType.CLONED) {
	    return cloned(interfce, element);
	}
	return factory(interfce, element);
    }

    static private Object factory(Class<?> interfce,
	    ClassRegistryElement element) {
	try {
	    Class<?> clazz = Class.forName(element.getClassName());
	    return clazz.getConstructor().newInstance();
	} catch (ClassNotFoundException e) {
	    logger.error(e.getMessage(), e);
	    return null;
	} catch (IllegalArgumentException e) {
	    logger.error(e.getMessage(), e);
	    return null;
	} catch (SecurityException e) {
	    logger.error(e.getMessage(), e);
	    return null;
	} catch (InstantiationException e) {
	    logger.error(e.getMessage(), e);
	    return null;
	} catch (IllegalAccessException e) {
	    logger.error(e.getMessage(), e);
	    return null;
	} catch (InvocationTargetException e) {
	    logger.error(e.getMessage(), e);
	    return null;
	} catch (NoSuchMethodException e) {
	    logger.error(e.getMessage(), e);
	    return null;
	}
    }

    static private Object singleton(Class<?> interfce,
	    ClassRegistryElement element) {
	if (!classes.containsKey(interfce.getName())) {
	    createSingleton(interfce, element);
	}
	return classes.get(interfce.getName());
    }

    /**
     * This method is used for a synchronized creation of the singleton
     * instance. The instance should be created here and not in singleton()
     * to avoid a permanent synchronized call to get the singleton instance
     * to avoid a speed issue.
     * 
     * @param interfce
     * @param element
     */
    static private synchronized void createSingleton(Class<?> interfce,
	    ClassRegistryElement element) {
	if (!classes.containsKey(interfce.getName())) {
	    classes.put(interfce.getName(), factory(interfce, element));
	}
    }

    static private Object cloned(Class<?> interfce,
	    ClassRegistryElement element) {
	try {
	    if (!classes.containsKey(interfce.getName())) {
		classes
			.put(interfce.getName(),
				factory(interfce, element));
	    }
	    Object o = classes.get(interfce.getName());
	    if (o == null) {
		return null;
	    }
	    if (Cloneable.class.isAssignableFrom(interfce)) {
		Method clone = o.getClass().getMethod("clone");
		return clone.invoke(o);
	    } else {
		return factory(interfce, element);
	    }
	} catch (SecurityException e) {
	    logger.error(e.getMessage(), e);
	    return null;
	} catch (NoSuchMethodException e) {
	    logger.error(e.getMessage(), e);
	    return null;
	} catch (IllegalArgumentException e) {
	    logger.error(e.getMessage(), e);
	    return null;
	} catch (IllegalAccessException e) {
	    logger.error(e.getMessage(), e);
	    return null;
	} catch (InvocationTargetException e) {
	    logger.error(e.getMessage(), e);
	    return null;
	}
    }
}
