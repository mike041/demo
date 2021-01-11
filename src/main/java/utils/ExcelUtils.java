package utils;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class ExcelUtils {
    public int numOfRows;
    private String filePath;
    private String sheetName;
    private Workbook workBook;
    private Sheet sheet;
    public List<String> columnHeaderList;
    private List<List<String>> listData;
    private List<Map<String, String>> mapData;
    private boolean flag;

    public ExcelUtils(String filePath, String sheetName) {
        this.filePath = filePath;
        this.sheetName = sheetName;
        this.flag = false;
        this.load();
    }

    public ExcelUtils(String filePath) {
        this.filePath = filePath;
        this.flag = false;
        this.load();
    }


    public void load() {
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(new File(filePath));
            workBook = WorkbookFactory.create(inStream);
            if (sheetName == null) {
                sheet = workBook.getSheetAt(0);
            } else {
                sheet = workBook.getSheet(sheetName);
            }
            numOfRows = sheet.getLastRowNum() + 1;
            this.getColumnHeaderList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getColumnHeaderList() {
        columnHeaderList = new ArrayList<String>();
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            Cell cell = sheet.getRow(0).getCell(i);
            columnHeaderList.add(this.getCellValue(cell));
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


    /**
     * 获取列表所有数据
     */
    public void getSheetData() {
        listData = new ArrayList<List<String>>();
        columnHeaderList = new ArrayList<String>();
        mapData = new ArrayList<Map<String, String>>();
        //numOfRows = sheet.getLastRowNum() + 1;
        for (int i = 0; i < numOfRows; i++) {
            Row row = sheet.getRow(i);
            Map<String, String> map = new HashMap<String, String>();
            List<String> list = new ArrayList<String>();
            if (row != null && !this.isRowEmpty(row)) {
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    if (i == 0) {
                        columnHeaderList.add(getCellValue(cell));
                    } else {
                        map.put(columnHeaderList.get(j),
                                this.getCellValue(cell));
                    }
                    list.add(this.getCellValue(cell));
                }
            }
            if (i > 0 && !map.isEmpty()) {
                mapData.add(map);
            }
            if (!list.isEmpty()) {
                listData.add(list);
            }
        }
        flag = true;
    }


    public boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            String value = this.getCellValue(cell);
            if (value != "")
                return false;
        }
        return true;
    }

    public List<Map<String, String>> getData(int begin, int end) {
        listData = new ArrayList<List<String>>();
        columnHeaderList = new ArrayList<String>();
        mapData = new ArrayList<Map<String, String>>();
        for (int i = begin; i < end; i++) {
            Row row = sheet.getRow(i);
            Map<String, String> map = new HashMap<String, String>();
            List<String> list = new ArrayList<String>();
            if (row != null) {
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    map.put(columnHeaderList.get(j),
                            this.getCellValue(cell));

                }
            }
            if (i > 0) {
                mapData.add(map);
            }
            listData.add(list);
        }
        return mapData;
    }


    public Map<String, String> getRowData(int index) {
        Row row = sheet.getRow(index);
        Map<String, String> map = new HashMap<String, String>();
        if (row != null) {
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                map.put(columnHeaderList.get(j),
                        this.getCellValue(cell));

            }
        }
        return map;
    }

    public Map<String, String> getRowData(Row row) {
        Map<String, String> map = new HashMap<String, String>();
        if (row != null) {
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                map.put(columnHeaderList.get(j),
                        this.getCellValue(cell));

            }
        }
        return map;
    }


    public String getCellData(int row, int col) {
        if (row <= 0 || col <= 0) {
            return null;
        }
        if (!flag) {
            this.getSheetData();
        }
        if (listData.size() >= row && listData.get(row - 1).size() >= col) {
            return listData.get(row - 1).get(col - 1);
        } else {
            return null;
        }
    }

    public String getCellData(int row, String headerName) {
        if (row <= 0) {
            return null;
        }
        if (!flag) {
            this.getSheetData();
        }
        if (mapData.size() >= row &&
                mapData.get(row - 1).containsKey(headerName)) {
            return mapData.get(row - 1).get(headerName);
        } else {
            return null;
        }
    }

    public List<List<String>> getListData() {
        return listData;
    }

    public List<Map<String, String>> getMapData() {
        return mapData;
    }

    public Sheet getSheet() {
        return this.sheet;
    }

    public static void main(String[] args) {
        ExcelUtils excelUtils = new ExcelUtils("F:\\数据\\demo.xlsx", "Sheet1");
        excelUtils.getSheetData();
        System.out.println(excelUtils.getMapData().subList(0, 10));

    }
}
