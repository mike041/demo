package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestJDBC {
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String sql = "INSERT INTO `resico-platform-new`.`fms_emp_bonus_log`(`id`, `emp_id`, `emp_name`, `bonus_from_type`, `entp_id`, `entp_code`, `entp_name`, `complete_type`, `org_id`, `org_name`, `bonus_type`, `received_amt`, `bonus_ratio`, `bonus_amt`, `distribute_date`, `status`, `voucher_bill_id`, `tax_type`, `tax_month`, `tax_amt`, `expense_type`, `summary_id`, `grant_by`, `income_confirm_month`, `bill_grant_log_id`, `park_id`, `park_name`, `delete_flag`, `created_by`, `created_at`, `updated_by`, `updated_at`, `version`, `bill_detail_id`, `summary_flag`, `commission_month`, `main_flag`, `emp_no`) " +
                "VALUES (?, 1316221234534420482, '周（渠道1）', 2, 1316565335386214402, '001990', '阶梯完全新客户入驻二', 1, 1292729681977716738, '山东财税', 20, 0.00000000, 0.08000, 118.25280000, NULL, 1, 1326009699356688386, 2, 202012, 30000.00000000, 2, NULL, NULL, 202011, NULL, 1316213784702005249, '测园区奖励阶梯式园区', 0, 0, '2020-11-10 11:52:03', 0, '2020-11-10 11:52:03', 0, 1326009553646567425, NULL, NULL, NULL, '11111');";
        try (Connection c = DriverManager.getConnection("jdbc:mysql://192.168.100.39:3306/resico-platform-new?characterEncoding=UTF-8", "root", "Resico@2020#dev")) {

            for (int i = 20000; i < 30000; i++) {
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setInt(1, i);
                ps.execute();
                System.out.println("添加" + (i - 101));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
