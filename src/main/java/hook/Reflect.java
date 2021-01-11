package hook;


import controller.RequisitionControl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {

    public static Object execute(String ClassName, Object keyword) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (ClassName.equals("Global")) {
            String aClassName = Reflect.class.getPackage().getName() + "." + ClassName;
            Class hook = Class.forName(aClassName);
            Constructor constructor = hook.getConstructor(RequisitionControl.class);
            Object h = constructor.newInstance();
            Method method = hook.getMethod("execute", Object.class);
            return method.invoke(h, keyword);
        }

        String aClassName = Reflect.class.getPackage().getName() + "." + ClassName;
        Class hook = Class.forName(aClassName);
        Object h = hook.newInstance();
        Method method = hook.getMethod("execute", Object.class);
        return method.invoke(h, keyword);
    }

}
