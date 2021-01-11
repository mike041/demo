package excelEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckRule {
    public final static String ALL = "all";
    public final static String JSON = "json";
    public final static String CODE = "code";
    public final static String VALUE = "value";
    public final static String DEFAULT = "default";
    String type;
    String JsonPath;
}
