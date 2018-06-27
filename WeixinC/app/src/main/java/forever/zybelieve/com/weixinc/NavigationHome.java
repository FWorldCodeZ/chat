package forever.zybelieve.com.weixinc;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.HashMap;

import AdatperUntils.ListViewAdapter;
import Bean.StorageApplication;
import Dao.LocalSqltools;

public class NavigationHome extends Fragment {
    private ListViewHeight myListView;
    private ListViewAdapter adapter;
    private ArrayList<HashMap<String, String>> data;
    LocalSqltools sqltools;
    StorageApplication storageApplication;
    Intent intent;
    private Thread thread;
    private String path="user/load";
    Handler handler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_navigation_home, container, false);
        myListView = (ListViewHeight) view.findViewById(R.id.infomationListView);
        sqltools = new LocalSqltools(getActivity());
        storageApplication = (StorageApplication) getActivity().getApplication();
        thread=new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //2.获取新闻数据用list封装

                Message message=new Message();
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("username",storageApplication.getUserName());


                message.obj= HttpConnection.HttpConnection.getConnection(getActivity(),path,params);
              handler.sendMessage(message);
            }
        });
        thread.start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                data= (ArrayList<HashMap<String, String>>)msg.obj;
                adapter = new ListViewAdapter(getActivity(), data);
                myListView.setAdapter(adapter);
                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        intent = new Intent();

                        intent.setClass(getActivity(), ChatActivity.class);
                        String name = data.get(position).get("userName");
                        intent.putExtra("userName", name);
                        System.err.println("userName:" + name);
                        startActivity(intent);
                    }
                });
            }
        };
       // data = sqltools.getAllUsers(storageApplication.getUserName());

        return view;

    }
}
