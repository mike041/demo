package excelEntity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExcelUrl {
    String host;
    String path;
    List<Parameter> parameters;
}
