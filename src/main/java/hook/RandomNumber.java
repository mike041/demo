package hook;

import utils.RandomUtils;

public class RandomNumber extends Hook {
    @Override
    public String execute(Object value) {
        int v=Integer.valueOf(value.toString());
        return RandomUtils.getNumber(v);
    }
}
