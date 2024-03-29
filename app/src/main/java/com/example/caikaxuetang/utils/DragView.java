package com.example.caikaxuetang.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * 自定义的view，能够随意拖动。
 */

@SuppressLint("AppCompatCustomView")
public class DragView extends ImageView {
    public DragView(Context context) {
        super(context);
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DragView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



    int startX;
    int startY;
    int left;
    int top;
    int[] temp = new int[]{ 0, 0 };
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isMove = false;
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: // touch down so check if the
                startX = x;
                startY = y;
                temp[0] = (int) event.getX();
                temp[1] = y - getTop();
                break;
            case MotionEvent.ACTION_MOVE: // touch drag with the ball
                left = x - temp[0];
                top = y - temp[1];
                if(left < 0){//控制左边界不超出
                    left = 0;
                }
                layout(left, top, left + getWidth(),top + getHeight());//自由拖拽
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(x - startX) > 2 || Math.abs(y - startY) > 2){//判断是否移动,再一定范围内不算是移动，解决触发事件冲突
                    //将最后拖拽的位置定下来，否则页面刷新渲染后按钮会自动回到初始位置
                    //注意父容器
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) getLayoutParams();
                    lp.setMargins(left, top,0,0);
                    setLayoutParams(lp);
                    //确定是拖拽
                    isMove = true;
                }
                break;
        }
        return isMove ? true : super.onTouchEvent(event);
    }
}