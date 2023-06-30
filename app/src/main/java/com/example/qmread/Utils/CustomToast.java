package com.example.qmread.Utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qmread.R;

public class CustomToast extends View {

    /**
     * 初始化一些变量
     * 实现3个构造函数
     * 初始化画笔参数和矩形参数
     * 设置画笔的参数及矩形的参数
     * 重写onMeasure：onMeasure()方法中主要负责测量，决定控件本身或其子控件所占的宽高
     * 重写onDraw：onDraw()方法负责绘制，即如果我们希望得到的效果在Android原生控件中没有现成的支持，那么我们就需要自己绘制我们的自定义控件的显示效果。
     * 自定义View中的动画效果实现
     */
    private Toast toast;
    private final Context context;

    RectF rectF = new RectF();  //矩形，设置Toast布局时使用
    ValueAnimator valueAnimator;    //属性动画
    private Paint paint;    //自定义View的画笔

    float mAnimatedValue = 0f;
    private float mWidth = 0f;  //view的宽
    private float mPadding = 0f;    //view的内边距
    private float endAngle = 0f;    //圆弧结束的度数

    private float mEyeWidth = 0f;   //笑脸的眼睛半径
    private boolean isSmileLeft = false;
    private boolean isSmileRight = false;

    public CustomToast(Context context) {
        super(context);
        this.context = context;
    }

    public CustomToast(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CustomToast(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);   //抗锯齿
        paint.setStyle(Paint.Style.STROKE); //画笔的样式：空心
        paint.setColor(Color.parseColor("#5cb85c"));    //绘制的颜色
        paint.setStrokeWidth(dip2px(2));    //设置笔刷的粗细
    }

    private void initRect() {
        rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
    }

    //dip转px。为了支持多分辨率手机
    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initPaint();
        initRect();
        mWidth = getMeasuredWidth();    //view的宽度
        mPadding = dip2px(10);
        mEyeWidth = dip2px(3);
    }

    //每次触摸了自定义View/ViewGroup时都会触发onDraw()方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rectF, 180, endAngle, false, paint);    //画微笑圆弧
        paint.setStyle(Paint.Style.FILL);   //画笔的样式：实心
        if (isSmileLeft) {
            canvas.drawCircle(mPadding + mEyeWidth + mEyeWidth / 2, mWidth / 3, mEyeWidth, paint);  //绘制圆圈
        }
        if (isSmileRight) {
            canvas.drawCircle(mWidth - mPadding - mEyeWidth - mEyeWidth / 2, mWidth / 3, mEyeWidth, paint);
        }
    }

    //开启动画
    public void startAnimator(boolean playAnimate) {
        if (playAnimate) {
            stopAnimator();
            startViewAnim(0f, 1f, 2000);
        }
    }

    //停止动画
    public void stopAnimator() {
        if (valueAnimator != null) {
            clearAnimation();
            isSmileLeft = false;
            isSmileRight = false;
            mAnimatedValue = 0f;
            valueAnimator.end();
        }
    }

    /**
     * 开始动画
     *
     * @param start 起始值
     * @param end   结束值
     * @param time  动画的时间
     * @return
     */
    public ValueAnimator startViewAnim(float start, float end, long time) {

        valueAnimator = ValueAnimator.ofFloat(start, end);  //设置 ValueAnimator 的起始值和结束值
        valueAnimator.setDuration(time);    //设置动画时间
        valueAnimator.setInterpolator(new LinearInterpolator());    //设置补间器，控制动画的变化速率
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {    //设置监听器。监听动画值的变化，做出相应方式
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatedValue = (float) valueAnimator.getAnimatedValue();
                if (mAnimatedValue < 0.5) {
                    isSmileLeft = false;
                    isSmileRight = false;
                    endAngle = -360 * (mAnimatedValue);
                } else if (mAnimatedValue > 0.55 && mAnimatedValue < 0.7) {
                    endAngle = -180;
                    isSmileLeft = true;
                    isSmileRight = false;
                } else {
                    endAngle = -180;
                    isSmileLeft = true;
                    isSmileRight = true;
                }
                postInvalidate();      //重绘
            }
        });

        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }
        return valueAnimator;
    }

    /**
     * 本质上还是依赖Android原生Toast的显示方法来进行显示，
     * 只是引入了自定义的布局，添加了自定义动画
     */
    public void show(String message, boolean playAnimate) {

        /* 解决多次点击Toast一直提示不消失问题 */
        if (toast == null) {
            toast = new Toast(context);
        }
        View customToastView = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
        TextView msg2 = (TextView) customToastView.findViewById(R.id.tv_msg);
        msg2.setText(message);
        // msg2.setBackgroundResource(R.drawable.shape_text_toast);
        msg2.setTextColor(Color.parseColor("#ffffff"));

        ImageView img2 = (ImageView) customToastView.findViewById(R.id.iv_img);
        //img2.setImageResource(R.mipmap.jyfr_icon_mpossh3x);
//        img2.setVisibility(View.GONE);

        // CustomToast customToast = (CustomToast) customToastView.findViewById(R.id.smileView);
        //customToast.startAnimator(playAnimate);

        toast.setView(customToastView);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

}
