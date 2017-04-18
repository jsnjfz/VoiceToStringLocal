import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.iflytek.voicecloud.client.LfasrClient;
import com.iflytek.voicecloud.exception.LfasrException;
import com.iflytek.voicecloud.model.LfasrType;
import com.iflytek.voicecloud.model.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author fz
 * @Date 2017-04-18 15:38
 */
public class FormMain {
    public FormMain() {
        textArea1.setLineWrap(true);        //激活自动换行功能
        textArea1.setWrapStyleWord(true);            // 激活断行不断字功能

//        scroll.setVerticalScrollBarPolicy(
//                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        taskidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String appid = textField1.getText().trim();
                String secret_key = textField2.getText().trim();
                String file_path = textField3.getText().trim();
                LfasrType type = LfasrType.LFASR_STANDARD_RECORDED_AUDIO;
                LfasrClient client = null;
                try {
                    client = LfasrClient.InitClient(appid, secret_key, type);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                Message message = null;
                try {
                    message = client.lfasr_upload(file_path);
                } catch (LfasrException e2) {
                    e2.printStackTrace();
                }
                if(message.getErr_no()==0 && message.getOk()==0){
                    textField4.setText(message.getData());
                }
            }
        });
        getTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String appid = textField1.getText().trim();
                String secret_key = textField2.getText().trim();
                String taskid = textField4.getText().trim();
                String content = "";

                LfasrType type = LfasrType.LFASR_STANDARD_RECORDED_AUDIO;
                LfasrClient client = null;
                try {
                    client = LfasrClient.InitClient(appid, secret_key, type);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                Message message = null;
                try {
                    message = client.lfasr_get_result(taskid);
//                    System.out.println(message.getData());
                } catch (LfasrException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
                if (message.getErr_no() == 0 && message.getOk() == 0){
                    Gson gson = new Gson();
                    String json = message.getData();
                    if(json != null){
                        List persons=new ArrayList();
                        List<JsonElement> list=new ArrayList();
                        JsonParser jsonParser=new JsonParser();
                        JsonElement jsonElement=jsonParser.parse(json);  //将json字符串转换成JsonElement
                        JsonArray jsonArray=jsonElement.getAsJsonArray();  //将JsonElement转换成JsonArray
                        Iterator it=jsonArray.iterator();  //Iterator处理
                        while(it.hasNext()){  //循环
                            jsonElement=(JsonElement) it.next(); //提取JsonElement
                            json=jsonElement.toString();
                            Map<String, String> map = gson.fromJson(json, new TypeToken<Map<String, String>>()
                            {
                            }.getType());
                            content += map.get("onebest");
                        }
                    }
                }
                else{
                    content = message.getFailed() + "错误代码:" + message.getErr_no();
                }

//                panel1.add(new JScrollPane(textArea1));
                textArea1.setText(content);
            }
        });
        getFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == getFileButton){
                    final JFileChooser jFileChooser = new JFileChooser();
                    int returnVal = jFileChooser.showOpenDialog(null);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = jFileChooser.getSelectedFile();
                        String fileExtName = file.getName().substring(file.getName().lastIndexOf(".")+1);
                        if(!"wav".equals(fileExtName)&&!"mp3".equals(fileExtName)&&!"flac".equals(fileExtName)&&!"opus".equals(fileExtName)&&!"m4a".equals(fileExtName)){
                            JOptionPane.showMessageDialog(jPanelMessage, "提示消息:上传文件的类型不符合！请上传wav,mp3,flac,opus,m4a结尾的文件！", "错误",JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        textField3.setText(file.getPath());
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("FormMain");
        frame.setContentPane(new FormMain().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }


    private JPanel panel1;
    private JPanel jPanelMessage;
    private JButton getTextButton;
    private JTextArea textArea1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton getFileButton;
    private JTextField textField3;
    private JButton taskidButton;
    private JTextField textField4;
//    private JScrollPane scroll = new JScrollPane(textArea1);



}
