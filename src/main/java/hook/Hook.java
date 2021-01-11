package hook;

import exception.MyException;

public abstract class Hook {

    public abstract String execute(Object value) throws MyException;


}


