package excelEntity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExcelCheckRule {
    List<CheckRule> checkRuleList;
}
