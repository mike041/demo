package utils;

import java.sql.*;

public class TestJDBC {
    static Connection conn = null;
    static Statement st = null;
    static ResultSet rs = null;


    public static void load() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://192.168.100.39:3306/resico-platform-new?characterEncoding=UTF-8", "root", "Resico@2020#dev");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insert(String sql) {
        execute(sql);
    }

    public static void delete(String sql) {
        execute(sql);
    }

    public static void update(String sql) {
        execute(sql);
    }

    private static void execute(String sql) {
        try {
            st = conn.createStatement();
            st.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 数据库的连接时有限资源，相关操作结束后，养成关闭数据库的好习惯
            // 先关闭Statement
            if (st != null)
                try {
                    st.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            // 后关闭Connection
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

        }
    }

    public static ResultSet query(String sql, String condition) {
        if (sql.contains("?")) {
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, condition);
                rs = ps.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            try {
                st = conn.createStatement();
                rs = st.executeQuery(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // 数据库的连接时有限资源，相关操作结束后，养成关闭数据库的好习惯
                // 先关闭Statement
                if (st != null)
                    try {
                        st.close();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                // 后关闭Connection
                if (conn != null)
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

            }
        }
        return rs;
    }

    public static void main(String[] args) {
        String sql = "INSERT INTO `resico-platform-new`.`fms_emp_bonus_log`(`id`, `emp_id`, `emp_name`, `bonus_from_type`, `entp_id`, `entp_code`, `entp_name`, `complete_type`, `org_id`, `org_name`, `bonus_type`, `received_amt`, `bonus_ratio`, `bonus_amt`, `distribute_date`, `status`, `voucher_bill_id`, `tax_type`, `tax_month`, `tax_amt`, `expense_type`, `summary_id`, `grant_by`, `income_confirm_month`, `bill_grant_log_id`, `park_id`, `park_name`, `delete_flag`, `created_by`, `created_at`, `updated_by`, `updated_at`, `version`, `bill_detail_id`, `summary_flag`, `commission_month`, `main_flag`, `emp_no`) " +
                "VALUES (?, 1316221234534420482, '周（渠道1）', 2, 1316565335386214402, '001990', '阶梯完全新客户入驻二', 1, 1292729681977716738, '山东财税', 20, 0.00000000, 0.08000, 118.25280000, NULL, 1, 1326009699356688386, 2, 202012, 30000.00000000, 2, NULL, NULL, 202011, NULL, 1316213784702005249, '测园区奖励阶梯式园区', 0, 0, '2020-11-10 11:52:03', 0, '2020-11-10 11:52:03', 0, 1326009553646567425, NULL, NULL, NULL, '11111');";

    }
}
