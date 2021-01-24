package hook;

import exception.MyException;
import utils.RandomUtils;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Hook {

    final static int LENGTH = 5;

    public String randomChar(Object value) {
        int v = LENGTH;
        if (!value.toString().equals("")) {
            v = Integer.valueOf(value.toString());
        }
        return RandomUtils.getChar(v);
    }

    public String randomNumber(Object value) {
        int v = LENGTH;
        if (!value.toString().equals("")) {
            v = Integer.valueOf(value.toString());
        }
        return RandomUtils.getNumber(v);
    }

    public String randomString(Object value) {
        int v = LENGTH;
        if (!value.toString().equals("")) {
            v = Integer.valueOf(value.toString());
        }
        return RandomUtils.getRandomString(v);
    }


    public Object global(Object value) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class clazz = Class.forName("DemoTest");
        Field field = clazz.getField("testCaseMap");
        HashMap<String, Object> map = (HashMap<String, Object>) field.get(clazz);

        try {
            if (!map.containsKey(value)) {
                throw new MyException("----------------------------------------数据中不存在该值：" + value + "--------------------------------------------------------");
            }
        } catch (MyException e) {
            e.printStackTrace();
        }


        return map.get(value);
    }


}


