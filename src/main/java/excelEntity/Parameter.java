package excelEntity;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Parameter {
    String key;
    String className;
    String args;



}
