package excelEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActualAssert {
    Object actulResult;
    Object expectResult;


}
