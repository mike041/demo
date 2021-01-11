package excelEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {
    String key;
    String jsonPath;
}
