package excelEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExcelResponse {
    final static int SUCCEED_CODE = 10000;
    final static boolean SUCCEED_TRUE = true;
    final static boolean SUCCEED_FALSE = false;
    String code;
    String error;
    String msg;
    String succeed;
    String data;
    String response;
}
