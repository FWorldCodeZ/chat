package Controller;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import forever.zybelieve.com.weixinc.R;

public class SocketDemo extends Activity implements Runnable {
    private TextView tv_msg = null;//显示服务器传过来的信息
    private EditText ed_msg = null;//编辑要发送给服务器的消息
    private Button btn_send = null;//消息发送
    // private Button btn_login = null;
    private static final String HOST = "192.168.0.108";//服务器地址
    private static final int PORT = 9999;//端口号
    private Socket socket = null;//创建Socket对象
    private BufferedReader in = null;//创建
    private PrintWriter out = null;
    private String content = "";
    String msg;
    // 线程池
    // 为了方便展示,此处直接采用线程池进行线程管理,而没有一个个开线程
    private ExecutorService mThreadPool;

    //接收线程发送过来信息，并用TextView显示
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_msg.setText(content);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_msg = (TextView) findViewById(R.id.text);
//        ed_msg = (EditText) findViewById(R.id.editT);
//        btn_send = (Button) findViewById(R.id.btn2);
        // 初始化线程池
        mThreadPool = Executors.newCachedThreadPool();

        btn_send.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                msg = ed_msg.getText().toString();
                if (socket.isConnected()) {//Returns the connection state of the socket.
                    if (!socket.isOutputShutdown()) {//Returns whether the write-half of the socket connection is closed.
                        mThreadPool.execute(new Runnable() {//execute-执行
                            @Override
                            public void run() {
                              out.println(msg);
       /*                 out.write(msg+content);*/
                            }
                        });

                    }
                }
            }
        });
        //启动线程，接收服务器发送过来的数据
        new Thread(SocketDemo.this).start();
    }


    /**
     * 读取服务器发来的信息，并通过Handler发给UI线程
     */
    public void run() {

        try {
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);
        } catch (IOException ex) {
            ex.printStackTrace();
            ShowDialog("login exception" + ex.getMessage());
        }

        try {
            while (true) {
                if (!socket.isClosed()) {
                    if (socket.isConnected()) {
                        if (!socket.isInputShutdown()) {
                            if ((content = in.readLine()) != null) {
                                content += "\n";
                                mHandler.sendMessage(mHandler.obtainMessage());
                            } else {

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 如果连接出现异常，弹出AlertDialog！
     */
    public void ShowDialog(String msg) {
        new AlertDialog.Builder(this).setTitle("notification").setMessage(msg)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}