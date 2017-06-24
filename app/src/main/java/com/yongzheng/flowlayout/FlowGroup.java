package com.yongzheng.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * item 过多问题,如果一个item多余一行的问题
 * Created by yongzheng on 17-6-5.
 * 计算每个item的高度和宽度
 * 瀑布流效果,记录没行的高度
 *
 * 先把子view添加到父控件,然后 onMeasure -> onLayout ->
 *
 * 代码优化不太好
 *
 */
public class FlowGroup extends ViewGroup{

    private static final String TAG = "FlowGroup";
    private Context context;

    private BaseAdapter mAdapter;

    public FlowGroup(Context context) {
        super(context);
        init(context);
    }

    public FlowGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FlowGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

    }

    private Map<Integer,Integer> itemHeight = new HashMap<>();//保存每行的高度

    /**
     * 设置子view的布局
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.v(TAG,l+" "+t+" "+r+" "+b);
        int  w = r / 2;
        int h = b;

        int itemMaxWith = 0;
        int itemMaxHeight = 0;
        int left,top,right,button;

        int line = 1;
        for (int i=0;i<getChildCount();i++){
            View view = getChildAt(i);
            int itemW = view.getMeasuredWidth();
            int itemH = itemHeight.get(line);

            Log.v(TAG+i,"测量高度和宽度:"+itemW+" "+itemH+"  "+line);


            left = itemMaxWith;
            top = itemMaxHeight;
            right = left + itemW;
            button = top + itemH;

            //累加with
            itemMaxWith += itemW;

            if (itemMaxWith>=r){
                //长度超过屏幕,进行换行
                itemMaxWith = 0;
                itemMaxHeight += itemHeight.get(line);
                left = itemMaxWith;
                top = itemMaxHeight;
                right = left + itemW;

                //进行换行
                line++;
                if (line>=itemHeight.size()){
                    line = itemHeight.size();
                }

                button = top + itemHeight.get(line);
                Log.v(TAG+i,"布局item位置换:"+left+" "+top+" "+right+" "+button);
                itemMaxWith += itemW;

            }else {
                //没有超过,在尾部追加
                Log.v(TAG+i,"布局item位置:"+left+" "+top+" "+right+" "+button);
            }


            view.layout(left,top,right,button);

//            view.layout(120*i,t,w,b);

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //测量子view的大小
        int wantHeight = 0;
        int wantWidth = resolveSize(0, widthMeasureSpec);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int childLeft = paddingLeft;
        int childTop = paddingTop;
        int lineHeight = 0;

        //TODO 固定列的数量所需要的代码
        //核心测量部分
        int parentHeight = 0;//父布局的高度
        int parentWidth = getMeasuredWidth();//父控件的宽度
        int itemMaxWith = 0;
        int itemMaxHeight = 0;//每行的最高
        int line = 1;//行数目
        for (int i = 0; i < getChildCount(); i++) {
            final View childView = getChildAt(i);
            LayoutParams params = childView.getLayoutParams();
            childView.measure(
                    getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, params.width),
                    getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, params.height)
            );
            int childHeight = childView.getMeasuredHeight();
            int childWidth = childView.getMeasuredWidth();
            parentHeight += childHeight;
            Log.v(TAG,"测量item大小:"+childWidth+" "+childHeight);

            //累加with
            itemMaxWith += childWidth;
            if (itemMaxWith>=parentWidth){
                //长度超过屏幕,进行换行
                itemMaxWith = 0;
                itemMaxWith += childWidth;
                parentHeight += itemMaxHeight;
                Log.v(TAG + i,"换行item高度 :  "+i+"  "+itemMaxHeight);
                itemHeight.put(line++,itemMaxHeight);
                itemMaxHeight = 0;
            }else {
//                Log.v(TAG + i,"不换行item高度:"+childHeight);
            }


            if (itemMaxHeight<childHeight){
                itemMaxHeight = childHeight;
            }

        }

        setMeasuredDimension(getMeasuredWidth(),parentHeight);
    }

    public void setAdapter(BaseAdapter adapter){
        if (mAdapter == null){
            mAdapter = adapter;
            drawLayout();
        }
    }

    private void drawLayout() {
        this.removeAllViews();
        for (int i=0;i<mAdapter.getCount();i++){
            addView(mAdapter.getView(i,null,this));
        }
    }

}
