package hook;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {

    public static String replace(String method, Object... args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        Class clazz = Hook.class;
        Method aMethod = clazz.getMethod(method, Object.class);
        return aMethod.invoke(new Hook(), args).toString();

    }

    public static void main(String[] args) {
        String method = "randomString";
        try {
            System.out.println(Reflect.replace(method, "11"));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
