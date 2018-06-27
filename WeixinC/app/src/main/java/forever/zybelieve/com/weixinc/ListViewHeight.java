package forever.zybelieve.com.weixinc;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by 14063 on 2017/11/27.
 */

public class ListViewHeight extends ListView {
    public ListViewHeight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ListViewHeight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ListViewHeight(Context context) {
        super(context);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置为Integer.MAX_VALUE>>2 是listview全部展开
        int measureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
//设置为400是设置listview的高度只能有600 不全部展开   实现可以滑动的效果
//        int measureSpec1 = MeasureSpec.makeMeasureSpec(600, MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, measureSpec1);
        super.onMeasure(widthMeasureSpec, measureSpec);
    }

}