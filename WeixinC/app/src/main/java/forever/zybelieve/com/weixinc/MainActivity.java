package forever.zybelieve.com.weixinc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import ActivityManager.AppManager;
import AdatperUntils.ViewPagerAdatper;

public class MainActivity extends AppCompatActivity {

    private ViewPager mIn_vp;
    private LinearLayout mIn_ll;
    private List<View> mViewList;
    private ImageView mLight_dots;
    private int mDistance;
    private ImageView mOne_dot;
    private ImageView mTwo_dot;
    private ImageView mThree_dot;
    private Intent intent;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private boolean remember;
//    private Button mBtn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去标题
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_main);
       // SqlScoutServer.create(this, getPackageName());

        initView();
        sp = getSharedPreferences("tiaozhuan", Context.MODE_APPEND);
        editor = sp.edit();
        remember = sp.getBoolean("NumFlg", false);
        if(remember==true){
            startActivity(intent);
        }
        initData();
        //绑定适配器
        mIn_vp.setAdapter(new ViewPagerAdatper(mViewList));
        addDots();
        moveDots();
        mIn_vp.setPageTransformer(true,new DepthPageTransformer());
    }

//初始化
    private void initView() {
        mIn_vp = (ViewPager) findViewById(R.id.in_viewpager);//ViewPager
        mIn_ll = (LinearLayout) findViewById(R.id.in_ll);//圆点容器
        mLight_dots = (ImageView) findViewById(R.id.iv_light_dots);//亮点
        intent=new Intent();
        intent.setClass(MainActivity.this,Login.class);
        //   mBtn_next = (Button) findViewById(R.id.bt_next);
    }
    //初始化引导页，并且装入集合中
    private void initData() {
        mViewList = new ArrayList<View>();
        LayoutInflater lf = getLayoutInflater().from(MainActivity.this);
        View view1 = lf.inflate(R.layout.item01, null);
        View view2 = lf.inflate(R.layout.item02, null);
        View view3 = lf.inflate(R.layout.item03, null);
        View view4 = lf.inflate(R.layout.item03, null);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
    }

    private void moveDots() {
        mLight_dots.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获得两个圆点之间的距离
                mDistance = mIn_ll.getChildAt(1).getLeft() - mIn_ll.getChildAt(0).getLeft();
                mLight_dots.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        });
        //ViewPager容器监听器
        mIn_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //页面滚动时小白点移动的距离，并通过setLayoutParams(params)不断更新其位置
                float leftMargin = mDistance * (position + positionOffset);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLight_dots.getLayoutParams();
                params.leftMargin = (int) leftMargin;
                mLight_dots.setLayoutParams(params);
                if(position==2){
//                    mBtn_next.setVisibility(View.VISIBLE);

                }
//                if(position!=2&&mBtn_next.getVisibility()==View.VISIBLE){
//                    mBtn_next.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onPageSelected(int position) {
                //页面跳转时，设置小圆点的margin
                float leftMargin = mDistance * position;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLight_dots.getLayoutParams();
                params.leftMargin = (int) leftMargin;
                mLight_dots.setLayoutParams(params);
                if(position>2){
//                    mBtn_next.setVisibility(View.VISIBLE);
                    editor.putBoolean("NumFlg", true);
                    editor.commit();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    MainActivity.this.finish();
                    System.exit(0);
                }
//                if(position!=2&&mBtn_next.getVisibility()==View.VISIBLE){
//                    mBtn_next.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        }
//添加3个圆点
    private void addDots() {
        mOne_dot = new ImageView(this);
        mOne_dot.setImageResource(R.drawable.gray_dot);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 40, 0);
        mIn_ll.addView(mOne_dot, layoutParams);
        mTwo_dot = new ImageView(this);
        mTwo_dot.setImageResource(R.drawable.gray_dot);
        mIn_ll.addView(mTwo_dot, layoutParams);
        mThree_dot = new ImageView(this);
        mThree_dot.setImageResource(R.drawable.gray_dot);
        mIn_ll.addView(mThree_dot, layoutParams);
        setClickListener();

    }
    ///为圆点添加事件监听器
    private void setClickListener() {
        mOne_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIn_vp.setCurrentItem(0);
            }
        });
        mTwo_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIn_vp.setCurrentItem(1);
            }
        });
        mThree_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIn_vp.setCurrentItem(2);
            }
        });
    }




}