package forever.zybelieve.com.weixinc;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.zaaach.toprightmenu.TopRightMenu;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ActivityManager.AppManager;
import AdatperUntils.FragAdapter;
import Bean.StorageApplication;

public class MainCustomerUser extends AppCompatActivity {

    private ViewPager pagers;
    BottomNavigationView navigation;
    ArrayList<Fragment> fragments;
    private ImageView moreBtn;
    private Thread thread;
    private String path="user/load";
    StorageApplication storageApplication;
    private TopRightMenu mTopRightMenu;
    private  Intent intent;
    //定义一个变量，来标示是否退出
    private static boolean exit = false;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            exit = false;
        }
    };
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    pagers.setCurrentItem(0);

                    return true;
                case R.id.navigation_dashboard:
                    pagers.setCurrentItem(1);

                    return true;
                case R.id.navigation_notifications:
                    pagers.setCurrentItem(2);
                    return true;
                case R.id.navigation_user:
                    pagers.setCurrentItem(3);
                    return true;
            }
            return false;
        }

    };
/*    float x1, x2;
    float y1, y2;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
                //当手指按下的时候

                x1 = event.getX();
                 y1 = event.getY();
            }
            if(event.getAction() == MotionEvent.ACTION_UP) {
                //当手指离开的时候
                x2 = event.getX();
                y2 = event.getY();
                if(y1 - y2 > 50) {
                    Toast.makeText(MainCustomerUser.this, "向上滑", Toast.LENGTH_SHORT).show();
                } else if(y2 - y1 > 50) {
                    Toast.makeText(MainCustomerUser.this, "向下滑", Toast.LENGTH_SHORT).show();
                } else if(x1 - x2 > 50) {
                    Toast.makeText(MainCustomerUser.this, "向左滑", Toast.LENGTH_SHORT).show();
                } else if(x2 - x1 > 50) {
                    Toast.makeText(MainCustomerUser.this, "向右滑", Toast.LENGTH_SHORT).show();
                }
            }
            return true;

        }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去标题
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_main_customer_user);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initData();
        move();
        pagers.setAdapter(new FragAdapter(getSupportFragmentManager(), fragments));

        pagers.setPageTransformer(true, new DepthPageTransformer());
    }

    private void initView() {
        pagers = (ViewPager) findViewById(R.id.pagers);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        storageApplication = (StorageApplication)MainCustomerUser.this.getApplication();
        moreBtn = (ImageView) findViewById(R.id.more);

        thread=new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //2.获取新闻数据用list封装

                Message message=new Message();
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("username",storageApplication.getUserName() );
//                                                    params.put("name",);
                HttpConnection.HttpConnection.getConnection(MainCustomerUser.this,path,params);

                message.obj= HttpConnection.HttpConnection.getConnection(MainCustomerUser.this,path,params);

                handler.sendMessage(message);
            }
        });
        thread.start();
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopRightMenu = new TopRightMenu(MainCustomerUser.this);
                List<com.zaaach.toprightmenu.MenuItem> menuItems = new ArrayList<>();
                menuItems.add(new com.zaaach.toprightmenu.MenuItem(R.mipmap.multichat, "发起多人聊天"));
                menuItems.add(new com.zaaach.toprightmenu.MenuItem(R.mipmap.addmember, "加好友"));
                menuItems.add(new com.zaaach.toprightmenu.MenuItem(R.mipmap.qr_scan, "扫一扫"));
                mTopRightMenu
                        .setHeight(480)     //默认高度480
                        .setWidth(320)      //默认宽度wrap_content
                        .showIcon(true)     //显示菜单图标，默认为true
                        // .dimBackground(dimBg)           //背景变暗，默认为true
                        .needAnimationStyle(true)   //显示动画，默认为true
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .addMenuList(menuItems)
                        .addMenuItem(new com.zaaach.toprightmenu.MenuItem(R.mipmap.facetoface, "面对面快传"))
                        .addMenuItem(new com.zaaach.toprightmenu.MenuItem(R.mipmap.pay, "付款"))
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                switch (position){
                                    case 1:
                                        intent=new Intent();
                                        intent.setClass(MainCustomerUser.this,AddFreindsActivity.class);
                                        startActivity(intent);
                                        break;
                                }
                                Toast.makeText(MainCustomerUser.this, "点击菜单:" + position, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .showAsDropDown(moreBtn, -200, 0);
//                        .showAsDropDown(moreBtn);
            }
        });
    }

    private void initData() {

        fragments = new ArrayList<Fragment>();
        NavigationHome view1 = new NavigationHome();
        NavigationDashboard view2 = new NavigationDashboard();
        NavigationNotifications view3 = new NavigationNotifications();
        NavigationUser view4 = new NavigationUser();
        fragments.add(view1);
        fragments.add(view2);
        fragments.add(view3);
        fragments.add(view4);

    }

    private void move() {

        pagers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        navigation.setSelectedItemId(R.id.navigation_home);
                        break;

                    case 1:
                        navigation.setSelectedItemId(R.id.navigation_dashboard);
                        break;

                    case 2:
                        navigation.setSelectedItemId(R.id.navigation_notifications);
                        break;

                    case 3:
                        navigation.setSelectedItemId(R.id.navigation_user);
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (!exit) {
                exit = true;
                Toast.makeText(MainCustomerUser.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //利用handle延迟发送更改状态信息
                handler.sendEmptyMessageDelayed(0, 3000);
            } else {
                AppManager.getAppManager().exitApp(MainCustomerUser.this);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
