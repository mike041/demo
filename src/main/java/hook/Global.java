package hook;

import controller.RequisitionControl;
import exception.MyException;

public class Global extends Hook {
    RequisitionControl control;

    public Global(RequisitionControl control) {
        this.control = control;
    }

    @Override
    public String execute(Object value)  {
        if (!control.parameterMap.containsKey(value)){
            try {
                throw new MyException("查询的结果："+value+"不在map中，请提前初始化或添加");
            } catch (MyException e) {
                e.printStackTrace();
            }
        }
        return control.parameterMap.get(value.toString()).toString();
    }


}
