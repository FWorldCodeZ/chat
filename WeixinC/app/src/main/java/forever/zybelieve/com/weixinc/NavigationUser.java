package forever.zybelieve.com.weixinc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ActivityManager.AppManager;
import Bean.StorageApplication;
import Bean.UserBean;

public class NavigationUser extends Fragment {

    private ListViewHeight lsitinfo;
    private SimpleAdapter simAdapt;
    private TextView mySingleContent, myname;
    private Intent intent;
    private Button edit;
    StorageApplication storageApplication;
    private List<Map<String, Object>> data;
    //定义一个变量，来标示是否退出
    private static boolean exit = false;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            exit = false;
        }
    };

    String itemName[] = {"我的钱包", "我的收藏", "我的相册", "摇一摇", "设置"};
    int itemPhoto[] = {R.drawable.money, R.drawable.saveall, R.drawable.photosave, R.drawable.shark, R.drawable.setting};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_navigation_user, container, false);

        myname = (TextView) view.findViewById(R.id.myname);
        mySingleContent = (TextView) view.findViewById(R.id.mySingleContent);
        edit = (Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示：")
                        .setMessage("您确定要退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppManager.getAppManager().exitApp(getActivity());
                            }
                        }).setNegativeButton("取消", null).show();
                if (!exit) {
                    exit = true;
                    Toast.makeText(getActivity(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    //利用handle延迟发送更改状态信息
                    handler.sendEmptyMessageDelayed(0, 3000);
                } else {

                }

            }
        });
        mySingleContent.setText("青春自由");
        lsitinfo = (ListViewHeight) view.findViewById(R.id.listInfo);
        UserBean bean = new UserBean();
        storageApplication = (StorageApplication) getActivity().getApplication();
        myname.setText(storageApplication.getUserName());
        //获取数据库封装方法
//        helper=new sqlhepler();
//        helper.Context(ListViewActivity.this);
        data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < itemName.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("pictrue", itemPhoto[i]);
            item.put("name", itemName[i]);
            data.add(item);
        }

        simAdapt = new SimpleAdapter(getActivity(), data, R.layout.list_item_function_style,
                new String[]{"pictrue", "name"}, // 与下面数组元素要一一对应
                new int[]{R.id.itemStyleImage, R.id.itemText});

        lsitinfo.setAdapter(simAdapt);
        lsitinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent();
                intent.setClass(getActivity(), SharkActivity.class);
                System.err.println("postiongdd" + position);
                if (position == 3) {
                    startActivity(intent);
                }
            }
        });
        return view;
    }

}
