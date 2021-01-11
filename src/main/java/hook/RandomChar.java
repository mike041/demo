package hook;

import utils.RandomUtils;

public class RandomChar extends Hook {
    final int LENGTH = 5;

    @Override
    public String execute(Object value) {
        int v = LENGTH;
        if (!value.toString().equals("")) {
            v = Integer.valueOf(value.toString());
        }
        return RandomUtils.getChar(v);
    }
}
