package entity;

import lombok.Builder;
import lombok.Data;
import org.apache.http.Header;

import java.util.List;

@Data
@Builder
public class Response {
    final static int SUCCEED_CODE = 10000;
    final static boolean SUCCEED_TRUE = true;
    final static boolean SUCCEED_FALSE = false;
    int code;
    String error;
    String msg;
    boolean succeed;
    Object data;
    String jsonResponse;
    List<Header> headerList;
}
