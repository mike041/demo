package excelEntity;

import entity.Requisition;
import lombok.Data;

import java.util.List;

@Data
public class TestCase {
    Requisition requisition;

    List<ActualAssert> actualAssertList;
}
