package AdatperUntils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import forever.zybelieve.com.weixinc.R;


/**
 * Created by 14063 on 2017/11/27.
 */

public class ListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;
    private ArrayList<HashMap<String, String>> data;
    private int Flgs;
    private int num;


    // ViewHolder静态类
    static class ViewHolder {
        public TextView title;
        public TextView info;
        public TextView time;
        public ImageView image;

    }

    public ListViewAdapter(Context context, ArrayList<HashMap<String, String>> list) {
        // 根据context上下文加载布局，这里的是Demo17Activity本身，即this
        this.mInflater = LayoutInflater.from(context);
        data = list;
    }

    @Override
    public int getCount() {
        // How many items are in the data set represented by this Adapter.
        // 在此适配器中所代表的数据集中的条目数
        // Log.d("data.size()", ""+data.size());
        return data.size();


    }

    @Override
    public Object getItem(int position) {
        // Get the data item associated with the specified position in the
        // data set.
        // 获取数据集中与指定索引对应的数据项
        return data.get(position - 1);

    }

    @Override
    public long getItemId(int position) {
        // Get the row id associated with the specified position in the
        // list.
        // 获取在列表中与指定索引对应的行id
        return position;
    }
    //判断发来的数据
    @Override
    public int getViewTypeCount() {
        return 3;
    }
    @Override
    public int  getItemViewType(int position) {
        Flgs = Integer.parseInt(data.get(position).get("Flags"));
        return Flgs;
    }
    // Get a View that displays the data at the specified position in the
    // data set.
    // 获取一个在数据集中指定索引的视图来显示数据

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        //
        ViewHolder holder = null;
        // 如果缓存convertView为空，则需要创建View

        switch (getItemViewType(position)) {
            case 0:
                if (convertView == null||num!=getItemViewType(position)) {
                   holder = new ViewHolder();
                    // 根据自定义的Item布局加载布局
                    convertView = mInflater.inflate(R.layout.list_item_information_style, null);
                    holder.title = (TextView) convertView.findViewById(R.id.InfoTitle);
                    holder.info = (TextView) convertView.findViewById(R.id.InfoContent);
                    num=0;
                    // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                break;

            case 1:
                if (convertView == null|| num!=getItemViewType(position)) {
                    holder = new ViewHolder();
                    // 根据自定义的Item布局加载布局
                    convertView = mInflater.inflate(R.layout.right_list_style, null);
                    holder.info = (TextView) convertView.findViewById(R.id.tv_chat_me_message);
                    holder.image=(ImageView) convertView.findViewById(R.id.iv_chat_imagr_right);
                    holder.time=(TextView) convertView.findViewById(R.id.tv_time);
                    num=1;
                    // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                    convertView.setTag(holder);
                } else {
                    num=1;
                    holder = (ViewHolder) convertView.getTag();
                }
                break;
            case 2:
                if (convertView == null||num!=getItemViewType(position)) {
                    holder = new ViewHolder();// 根据自定义的Item布局加载布局
                    convertView = mInflater.inflate(R.layout.letf_list_style, null);
                    holder.info = (TextView) convertView.findViewById(R.id.tvname);
                    holder.time=(TextView) convertView.findViewById(R.id.tv_time);
                    num=2;
                    // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                    convertView.setTag(holder);
                } else {
                    num=2;
                    holder = (ViewHolder) convertView.getTag();
                }
                break;
        }

        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        String   str   =   formatter.format(curDate);
        switch (getItemViewType(position)) {
            case 0:
                holder.title.setText(data.get(position).get("userName"));
                holder.info.setText(data.get(position).get("InfoContent"));
                break;
            case 1:
                holder.info.setText(data.get(position).get("message"));
                holder.time.setText(str);
                break;
            case 2:
                holder.info.setText(data.get(position).get("message"));
                holder.time.setText(str);
                break;
        }

        return convertView;
    }
}

