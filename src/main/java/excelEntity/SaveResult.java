package excelEntity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SaveResult {
    List<Result> results;
}
