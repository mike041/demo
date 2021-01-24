package excelEntity;

import exception.MyException;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import utils.TestLog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mikezhou
 * @description 从excel中获取的数据
 */

@Data

public class ExcelRequisition {
    TestLog log = new TestLog(this.getClass());
    Row row;

    public ExcelRequisition(Row row) {
        this.row = row;
        this.setDescription();
        this.setTestcaseName();
        this.setIsExecute();
        this.setType();
        this.setExcelUrl();
        this.setExcelBody();
        this.setSaveResult();
        this.setExcelCheckRule();
        this.setExcelExpect();
    }

    private String description;
    private String testcaseName;
    private String isExecute;
    private String type;
    private ExcelUrl excelUrl;
    private ExcelBody excelBody;
    private SaveResult saveResult;
    private ExcelCheckRule excelCheckRule;
    private ExcelExpect excelExpect;


    public enum anEnum {
        DESCRIPTION(0),
        TESTCASENAME(1),
        ISEXECUTE(2),
        TYPE(3),
        URL(4),
        BODY(5),
        SAVERESULT(6),
        CHECKRULE(7),
        EXPECT(8);

        public int row;

        anEnum(int row) {
            this.row = row;
        }
    }

    public void setDescription() {
        Cell cell = row.getCell(ExcelRequisition.anEnum.DESCRIPTION.row);
        if (this.getCellValue(cell).equals("")) {
            try {
                throw new MyException("描述不能为空");
            } catch (MyException e) {
                e.printStackTrace();
            }
            log.info("描述为空");
        }
        this.description = this.getCellValue(cell);

    }


    public void setTestcaseName() {
        Cell cell = row.getCell(ExcelRequisition.anEnum.TESTCASENAME.row);
        this.testcaseName = this.getCellValue(cell);

    }

    public void setIsExecute() {
        Cell cell = row.getCell(ExcelRequisition.anEnum.ISEXECUTE.row);
        this.isExecute = this.getCellValue(cell);

    }


    public void setType() {
        Cell cell = row.getCell(ExcelRequisition.anEnum.TYPE.row);
        this.type = this.getCellValue(cell);
    }


    public void setExcelUrl() {
        List<Parameter> parameterList = new ArrayList<>();
        Cell cell = row.getCell(ExcelRequisition.anEnum.URL.row);
        Pattern p = Pattern.compile("(\\$)([\\w]+)(.*?)(\\$)");
        String path = this.getCellValue(cell);
        Matcher m = p.matcher(path);
        while (m.find()) {
            String group = m.group();//规则中${值}中的 值 一样 的数据不
            String key = null;
            String className;
            String args;
            if (group.contains(":")) {
                key = group.split(":")[0].replace("$", "");
                className = group.split(":")[1].replaceAll("\\((.*?)\\)", "").replace("$", "");
                args = group.split(":")[1].substring(group.split(":")[1].indexOf("(") + 1, group.split(":")[1].indexOf(")"));
            } else {
                className = group.replaceAll("\\((.*?)\\)\\$", "").replace("$", "");
                args = group.substring(group.indexOf("(") + 1, group.indexOf(")"));
            }
            Parameter parameter = Parameter.builder()
                    .key(key)
                    .className(className)
                    .args(args)
                    .build();
            parameterList.add(parameter);
        }
        ExcelUrl excelUrl = ExcelUrl.builder()
                .path(path)
                .parameters(parameterList)
                .build();
        this.excelUrl = excelUrl;
    }


    public void setExcelBody() {
        List<Parameter> parameterList = new ArrayList<>();
        Cell cell = row.getCell(anEnum.BODY.row);
        Pattern p = Pattern.compile("(\\$)([\\w]+)(.*?)(\\$)");
        String body = this.getCellValue(cell);
        Matcher m = p.matcher(body);
        while (m.find()) {
            String group = m.group();//规则中${值}中的 值 一样 的数据不
            String key = null;
            String className;
            String args;
            if (group.contains(":")) {
                key = group.split(":")[0].replace("$", "");
                className = group.split(":")[1].replaceAll("\\((.*?)\\)", "").replace("$", "");
                args = group.split(":")[1].substring(group.split(":")[1].indexOf("(") + 1, group.split(":")[1].indexOf(")"));
            } else {
                className = group.replaceAll("\\((.*?)\\)\\$", "").replace("$", "");
                args = group.substring(group.indexOf("(") + 1, group.indexOf(")"));
            }
            Parameter parameter = Parameter.builder()
                    .key(key)
                    .className(className)
                    .args(args)
                    .build();
            parameterList.add(parameter);
        }
        ExcelBody excelBody = ExcelBody.builder()
                .body(body)
                .parameters(parameterList)
                .build();
        this.excelBody = excelBody;
    }


    public void setSaveResult() {
        List<Result> resultList = new ArrayList<>();
        Cell cell = row.getCell(anEnum.SAVERESULT.row);
        String ResultString = this.getCellValue(cell);
        if (ResultString == "" || ResultString.isEmpty() || ResultString == null) {
            this.saveResult = null;
        } else {
            if (ResultString.contains(",")) {
                String[] saves = ResultString.split(",");
                for (int i = 0; i < saves.length; i++) {

                    String key = saves[i].split(":")[0];
                    String jsonPath = saves[i].split(":")[1];
                    Result result = Result.builder()
                            .key(key)
                            .jsonPath(jsonPath)
                            .build();
                    resultList.add(result);
                }
            } else {
                String key = ResultString.split(":")[0];
                String jsonPath = ResultString.split(":")[1];
                Result result = Result.builder()
                        .key(key)
                        .jsonPath(jsonPath)
                        .build();
                resultList.add(result);
            }
            this.saveResult = SaveResult.builder().results(resultList).build();
        }
    }

    public void setExcelCheckRule() {
        List<CheckRule> checkRuleList = new ArrayList<>();
        Cell cell = row.getCell(anEnum.CHECKRULE.row);
        String checkRuleString = this.getCellValue(cell);

        if (checkRuleString.isEmpty() || checkRuleString.equals("")) {
            this.excelCheckRule = null;
        } else {
            if (checkRuleString.contains(",")) {
                String[] checkRules = checkRuleString.split(",");
                for (int i = 0; i < checkRules.length; i++) {
                    String type = checkRules[i].split(":")[0];
                    String jsonPath = null;
                    if (type == CheckRule.VALUE) {
                        jsonPath = checkRules[i].split(":")[1];
                    }
                    checkRuleList.add(CheckRule.builder().type(type).JsonPath(jsonPath).build());
                }
            } else {
                String type = checkRuleString.split(":")[0];
                String jsonPath = null;
                if (type == CheckRule.VALUE) {
                    jsonPath = checkRuleString.split(":")[1];
                }
                checkRuleList.add(CheckRule.builder().type(type).JsonPath(jsonPath).build());
            }
            this.excelCheckRule = ExcelCheckRule.builder().checkRuleList(checkRuleList).build();
        }

    }

    public void setExcelExpect() {
        List<Expect> expectList = new ArrayList<>();
        Cell cell = row.getCell(anEnum.EXPECT.row);
        String expectString = this.getCellValue(cell);
        if (expectString.equals("") || expectString.isEmpty()) {
            this.excelExpect = null;
        } else {
            if (expectString.contains("&&")) {
                String[] expects = expectString.split("&&");
                for (int i = 0; i < expects.length; i++) {
                    String value = expects[i];
                    expectList.add(Expect.builder().value(value).build());
                }
            } else {
                String value = expectString;
                expectList.add(Expect.builder().value(value).build());
            }
            this.excelExpect = ExcelExpect.builder().expectList(expectList).build();
        }
    }


    public String getCellValue(Cell cell) {
        String cellValue = "";
        DataFormatter formatter = new DataFormatter();
        if (cell != null) {
            switch (cell.getCellTypeEnum()) {
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = formatter.formatCellValue(cell);
                    } else {
                        double value = cell.getNumericCellValue();
                        int intValue = (int) value;
                        cellValue = value - intValue == 0 ?
                                String.valueOf(intValue) : String.valueOf(value);
                    }
                    break;
                case STRING:
                    cellValue = cell.getStringCellValue().replaceAll("\n", "");
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case _NONE:
                    cellValue = "";
                    break;
                case BLANK:
                    cellValue = "";
                    break;
                case ERROR:
                    cellValue = "";
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }
        }
        return cellValue.trim();
    }
}
