package swing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class swing {

    public static boolean checkIsBlank(ArrayList<JTextField> jt) {
        for (JTextField t : jt) {
            if (t.getText().isEmpty()) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        JFrame jf = new JFrame("AutoText");
        jf.setBounds(500, 200, 430, 500);
        jf.setLayout(null);

        JPanel jp1 = new JPanel();
        jp1.setBounds(8, 8, 400, 150);
        jp1.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));
        ArrayList<String> strs = new ArrayList<>();
        strs.add("姓名：");
        strs.add("公司类型：");
        strs.add("公司名称：");
        strs.add("老板名称：");
        strs.add("金额：");
        strs.add("产品：");
        strs.add("价格计量单位：");
        ArrayList<JLabel> jl = new ArrayList<>();
        ArrayList<JTextField> jt = new ArrayList<>();
        for (int i = 0; i < strs.size(); i++) {
            JLabel l = new JLabel(strs.get(i));
            l.setPreferredSize(new Dimension(90, 30));
            JTextField t = new JTextField("");
            t.setPreferredSize(new Dimension(90, 30));
            jl.add(l);
            jt.add(t);
            jp1.add(jl.get(i));
            jp1.add(jt.get(i));
        }
        jf.add(jp1);

        JPanel jp3 = new JPanel();
        jp3.setBounds(8, 220, 400, 270);
        JTextArea ja = new JTextArea("");
        ja.setPreferredSize(new Dimension(400, 200));
        ja.setLineWrap(true);

        jp3.add(ja);
        jf.add(jp3);

        JPanel jp2 = new JPanel();
        jp2.setBounds(8, 170, 400, 40);
        JButton jb = new JButton("生成");
        jb.addActionListener(e -> {
            if (!checkIsBlank(jt)) {
                ArrayList<String> newStr = new ArrayList<>();
                for (JTextField t : jt) {
                    newStr.add(t.getText());
                }
                String s = String.format("      %s最大%s%s倒闭了，王八蛋老板%s吃喝嫖赌，欠下了%s个亿，" +
                                "带着他的小姨子跑了！我们没有办法，拿着%s抵工资，原价都是一%s多、二%s多、三%s多" +
                                "的代码，现在通通只卖二十块，通通只卖二十块！%s王八蛋，你不是人！我们辛辛苦苦给你" +
                                "干了大半年，你不发工资，你还我血汗钱，还我血汗钱！", newStr.get(0), newStr.get(1),
                        newStr.get(2), newStr.get(3), newStr.get(4), newStr.get(5), newStr.get(6),
                        newStr.get(6), newStr.get(6), newStr.get(3));
                ja.setText(s);
            }
        });
        jp2.add(jb);
        jf.add(jp2);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}
