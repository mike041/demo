package entity;


import lombok.Getter;
import lombok.Setter;



/**
 * @author mikezhou
 * @description 请求的所有数据
 */

@Setter
@Getter
public class Requisition {
    private String type;
    private String url;
    private String body;
    private String response;

}
