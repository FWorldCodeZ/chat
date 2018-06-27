package forever.zybelieve.com.weixinc;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Bean.UserBean;

public class OnselfActivity extends AppCompatActivity {
    private ListViewHeight lsitinfo;
    private SimpleAdapter simAdapt;
    private TextView mySingleContent,myname;
    Button button;

    private Intent getIntent;

    private List<Map<String, Object>> data;
    String itemName[] = { "地区", "个性签名", "手机号码" };
    int itemPhoto[] = { R.drawable.setting, R.drawable.saveall, R.drawable.photosave};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onself);

//        getIntent = getIntent();
        myname = (TextView)findViewById(R.id.myname);
        mySingleContent = (TextView)findViewById(R.id.mySingleContent);
        mySingleContent.setText("青春自由");

        lsitinfo = (ListViewHeight)findViewById(R.id.listInfo);
        button=(Button)findViewById(R.id.send_message_btn) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        myname.setText("xiaohong");
        data = new ArrayList<>();

        for (int i = 0; i < itemName.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("pictrue", itemPhoto[i]);
            item.put("name", itemName[i]);
            data.add(item);
        }

        simAdapt = new SimpleAdapter(OnselfActivity.this, data, R.layout.list_item_function_style,
                new String[] { "pictrue", "name" }, // 与下面数组元素要一一对应
                new int[] { R.id.itemStyleImage, R.id.itemText });

        lsitinfo.setAdapter(simAdapt);
        lsitinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        }
    public void btn_back(View v) {     //标题栏 返回按钮
        this.finish();
    }
    public void btn_back_send(View v) {     //标题栏 返回按钮
        this.finish();
    }
    public void head_headimgsmall(View v) {     //头像按钮
        Intent intent = new Intent();
        intent.setClass(OnselfActivity.this,ImageMax.class);
        startActivity(intent);
    } }