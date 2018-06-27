package forever.zybelieve.com.weixinc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import AdatperUntils.ListViewAdapter;

public class ChatActivity extends AppCompatActivity {
    private ListViewHeight myListView;
    private TextView titleName;
    private String userName;
    Button sendMessage;
    EditText input_message;
    private ArrayList<HashMap<String, String>> data;
    private ListViewAdapter adapter;
    private int Flags;
    private Intent intent, getIntent;
    String name;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    /**
                     * ListView条目控制在最后一行
                     */
                    myListView.setSelection(data.size());
                    break;

                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initView();
        titleName.setText(userName);
        /**
         * 虚拟4条发送方的消息
         */
        for (int i = 0; i <= 3; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("message", i + "message");
            Flags = 2;
            map.put("Flags", Flags + "");
            data.add(map);
        }

        adapter = new ListViewAdapter(ChatActivity.this, data);
        myListView.setAdapter(adapter);
        actionListener();

    }

    public void initView() {
        myListView = (ListViewHeight) findViewById(R.id.message_bubble);
        sendMessage = (Button) findViewById(R.id.send_message_btn);
        titleName = (TextView) findViewById(R.id.name);
        input_message = (EditText) findViewById(R.id.input_message);
        data = new ArrayList<>();

        getIntent = getIntent();
        userName = getIntent.getStringExtra("userName");
        name = userName;

    }
    public void actionListener() {
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                view.findViewById(R.id.ivicon).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        intent = new Intent(ChatActivity.this, OnselfActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(input_message.getText().toString())) {
                    Toast.makeText(ChatActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    HashMap<String, String> map = new HashMap<>();
                    map.put("message", input_message.getText().toString());
                    Flags =1;
                    map.put("Flags", Flags + "");
                    data.add(map);
                    input_message.setText("");
                    adapter.notifyDataSetChanged();
                    handler.sendEmptyMessage(1);
                }
            }
        });
    }
}
