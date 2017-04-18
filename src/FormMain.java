import javax.swing.*;

/**
 * @Author fz
 * @Date 2017-04-18 15:38
 */
public class FormMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame("FormMain");
        frame.setContentPane(new FormMain().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel panel1;
    private JTextField textField1;
    private JLabel secret_key;
    private JTextField textField2;
    private JTextField textField3;
    private JButton 上传Button;
    private JButton 获取taskidButton;
    private JTextField textField4;
    private JButton 获取文字Button;
    private JTextArea textArea1;
    private JButton 选择文件Button;
}
