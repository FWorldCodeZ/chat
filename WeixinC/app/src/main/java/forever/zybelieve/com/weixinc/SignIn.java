package forever.zybelieve.com.weixinc;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import Bean.UserBean;
import Controller.UsersController;
import Dao.LocalSqltools;

public class SignIn extends AppCompatActivity {
    private TextView signIn, login;
    private EditText name, pass;
    UserBean userBean;
    UsersController usersController;
    private Intent intent;
    private Thread thread;
    private String path="user/register";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        initView();
        userBean = new UserBean();
        usersController = new UsersController(SignIn.this);
        intent = new Intent();
        Listener();

    }

    private void initView() {
        signIn = (TextView) findViewById(R.id.main_btn_signIn);
        login = (TextView) findViewById(R.id.Login);
        name = (EditText) findViewById(R.id._name);
        pass = (EditText) findViewById(R.id._pass);
    }

    private void Listener() {
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userBean.userName = name.getText().toString();
                userBean.userPassWord = pass.getText().toString();
                if (userBean.userName.isEmpty()) {
                    Toast.makeText(SignIn.this, "请输入用户名，用户名不能为空！", Toast.LENGTH_LONG).show();
                } else {
                    if (userBean.userPassWord.isEmpty()) {
                        Toast.makeText(SignIn.this, "请输入密码，密码不能为空！", Toast.LENGTH_LONG).show();
                    } else {

                        if (usersController.userOnlyName(userBean)) {
                            thread=new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    //2.获取新闻数据用list封装
                                    System.out.println(11112222);
                                    Message message=new Message();
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("username",userBean.userName );
                                    params.put("userpassword", userBean.userPassWord);
                                   HttpConnection.HttpConnection.getConnection(SignIn.this,path,params);


                                   message.obj=HttpConnection.HttpConnection.getConnection(SignIn.this,path,params);

                                   handler.sendMessage(message);
                                }
                            });
                            thread.start();

                            Toast.makeText(SignIn.this, "注册成功！", Toast.LENGTH_LONG).show();
                        } else {
                            name.setText("");
                            pass.setText("");
                            Toast.makeText(SignIn.this, "用户名存在，请重新输入！", Toast.LENGTH_LONG).show();
                        }
                    }
                }


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(SignIn.this, Login.class);
                startActivity(intent);
                SignIn.this.finish();
                System.exit(0);
            }
        });
    }
}
