package hook;

import utils.RandomUtils;

public class RandomString extends Hook {
    @Override
    public String execute(Object value) {
        int v = Integer.valueOf(value.toString());
        return RandomUtils.getRandomString(v);
    }

    public static void main(String[] args) {
        System.out.println(RandomString.class.getCanonicalName()
        );
    }
}
