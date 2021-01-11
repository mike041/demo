package excelEntity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExcelExpect {
    List<Expect> expectList;
}
