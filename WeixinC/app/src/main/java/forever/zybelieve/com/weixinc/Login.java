package forever.zybelieve.com.weixinc;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import ActivityManager.AppManager;
import Bean.StorageApplication;
import Bean.UserBean;
import Controller.UsersController;
import Dao.LocalSqltools;

public class Login extends AppCompatActivity {
    private TextView buttonLogin, signIn;
    private View progress;
    private View inputLayout;
    private float mWidth, mHeight;
    private LinearLayout passWord, passName;
    private Intent intent;
    private EditText name, pass;
    private String userName, userPassWord;
    private Thread thread;
    private String path="user/login";
    StorageApplication storageApplication;
    LocalSqltools sqltools;
    UserBean userBean;
    UsersController usersController;
    ArrayList<HashMap<String, String>> data;
    //定义一个变量，来标示是否退出
    private static boolean exit = false;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        buttonLogin = (TextView) findViewById(R.id.main_btn_login);
        signIn = (TextView) findViewById(R.id.signIn);
        progress = findViewById(R.id.layout_progress);//进度条
        inputLayout = findViewById(R.id.input_layout);
        passName = (LinearLayout) findViewById(R.id.input_layout_name);
        passWord = (LinearLayout) findViewById(R.id.input_layout_psw);
        name = (EditText) findViewById(R.id._name);
        pass = (EditText) findViewById(R.id._pass);
        storageApplication = (StorageApplication) this.getApplicationContext();
        userBean = new UserBean();
        sqltools = new LocalSqltools(Login.this);
        usersController = new UsersController(Login.this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getMeasuredWidth()获得测量的的宽度
                mWidth = buttonLogin.getMeasuredWidth();
                mHeight = buttonLogin.getMeasuredHeight();
                userBean.userName = name.getText().toString();
                userBean.userPassWord = pass.getText().toString();
                if(!userBean.userName.isEmpty()){
                    if(!userBean.userPassWord.isEmpty()){
                    thread=new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            //2.获取新闻数据用list封装

                            Message message=new Message();
                            HashMap<String, String> params = new HashMap<String, String>();
                            params.put("username",userBean.userName );
                            params.put("userpassword", userBean.userPassWord);
                            message.obj= HttpConnection.HttpConnection.getConnection(Login.this,path,params);

                            handler.sendMessage(message);
                        }
                    });
                    thread.start();

                }else{
                    name.setText("");
                    pass.setText("");
                    Toast.makeText(Login.this,"密码不能为空，请填写密码！" , Toast.LENGTH_LONG).show();
            }
        }else{
            name.setText("");
            pass.setText("");
            Toast.makeText(Login.this,"用户名不能为空，请填写用户名！" , Toast.LENGTH_LONG).show();
        }

//
//                if (usersController.getUsersInfo(userBean).equals("登陆成功!")) {
//                    storageApplication.setUserName(userBean.userName);
                handler= new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        data= (ArrayList<HashMap<String, String>>)msg.obj;
                        System.out.println("message"+data);
                        if (data.get(0).get("message").equals("登陆成功") ){
                            //点击后输入框隐藏
                            passName.setVisibility(View.INVISIBLE);
                            passWord.setVisibility(View.INVISIBLE);
                            inputAnimator(inputLayout, mWidth, mHeight);

                        } else {

                            Toast.makeText(Login.this,data.get(0).get("message") , Toast.LENGTH_LONG).show();
                            // Toast.makeText(Login.this, usersController.getUsersInfo(userBean), Toast.LENGTH_LONG).show();
                        }
                        exit = false;
                    }
                };

            }
        });
        intent = new Intent();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(Login.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });
    }


    //输入框的宽度
    private void inputAnimator(final View view, float w, float h) {
        /*
        * AnimatorSet这个类用于将一个动画集合按特定的顺序播放。
        * 动画可以设置成同时播放、顺序播放或者在一定的延时后播放。
        * 调用playTogether()或者playSequentially()一次性地添加并播放动画。
        * 使用play(Animator)与Builder类中的方法，逐个添加并播放动画。
        * */
        AnimatorSet set = new AnimatorSet();
        /*
        * ValueAnimator是属性动画的核心类，最常用的ObjectAnimator就是它的子类。
        * 此类只是以特定的方式（可以自定义）对值进行不断的修改,
        * 已达到某种想要的过渡效果。
        * 它提供设置播放次数、动画间隔、重复模式、开始动画以及设置动画监听器的方法。
        *
        * .ofFloat(0,w),从0到W变化
        * */
        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 获取到动画每次该变得float值，赋值给value
                float value = (float) animation.getAnimatedValue();

                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(inputLayout, "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator1);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                progress.setVisibility(view.VISIBLE);
                buttonLogin.setVisibility(View.INVISIBLE);
                progressAnimation(progress);
                inputLayout.setVisibility(View.INVISIBLE);
                intent.setClass(Login.this, MainCustomerUser.class);
                startActivity(intent);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 出现进度动画
     *
     * @param view
     */
    private void progressAnimation(View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view, animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (!exit) {
                exit = true;
                Toast.makeText(Login.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //利用handle延迟发送更改状态信息
                handler.sendEmptyMessageDelayed(0, 3000);
            } else {
                AppManager.getAppManager().exitApp(Login.this);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
