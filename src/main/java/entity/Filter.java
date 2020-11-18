package entity;

import lombok.Data;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Filter {
    public List<String> empName;
    private String entpName;
    private String taxMonth;
    private String taxTypeName;
    private int taxType;
    private String taxAmt;
    private String entpId;
    private List<Map<String, String>> list = new ArrayList<>();
    private Logger logger = Logger.getLogger(this.getClass());

    public String toString() {
        return this.getEntpName()+"-" + this.getTaxMonth()+"-" + this.getTaxType()+"-" + this.getTaxAmt();
    }

    public void setTaxTypeId() {
        switch (this.getTaxTypeName()) {
            case "增值税":
                this.setTaxType(1);
                break;
            case "营改增":
                this.setTaxType(2);
                break;
            case "企业所得税":
                this.setTaxType(3);
                break;
            case "个人所得税（除工资薪金)":
                this.setTaxType(4);
                break;
            case "个人所得税（除工资薪金）":
                this.setTaxType(4);
                break;
            case "工资薪金所得":
                this.setTaxType(5);
                break;
            case "城市维护建设税":
                this.setTaxType(6);
                break;
            case "教育费附加":
                this.setTaxType(7);
                break;
            case "地方教育附加":
                this.setTaxType(8);
                break;
            case "印花税":
                this.setTaxType(9);
                break;
            case "水利建设专项收入":
                this.setTaxType(10);
                break;
            case "契税":
                this.setTaxType(11);
                break;
            case "车船税":
                this.setTaxType(12);
                break;
            case "消费税":
                this.setTaxType(13);
                break;
            case "资源税":
                this.setTaxType(14);
                break;
            case "文化事业建设费":
                this.setTaxType(15);
                break;
            case "工会经费":
                this.setTaxType(16);
                break;
            default:
                logger.error("税种不存在");
                break;
        }

    }

}
