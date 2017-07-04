package sing.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import sing.seal.R;

public class CustomerSeal extends View {

    // 控件宽
    private int mWidth;
    // 控件高
    private int mHeight;
    // 文字
    private String mTitle;
    // 字体的颜色
    private int mTextColor;
    // 系统默认背景颜色
    private int mBackgroundColor;
    // 字体的大小
    private int mTextSize;

    public CustomerSeal(Context context) {
        this(context, null);
    }

    public CustomerSeal(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerSeal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取系统默认的背景颜色
        TypedArray array = context.getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.colorBackground,
                android.R.attr.textColorPrimary,
        });
        mBackgroundColor = array.getColor(0, 0xFF00FF);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Customer_Seal, defStyleAttr, 0);

        mTitle = a.getString(R.styleable.Customer_Seal_seal_text);
        //默认黑色
        mTextColor = a.getColor(R.styleable.Customer_Seal_seal_textColor, Color.BLACK);
        mTextSize = a.getDimensionPixelSize(R.styleable.Customer_Seal_seal_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                16, getResources().getDisplayMetrics()));

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint mPaint = new Paint();
        // 得到画布宽高
        mWidth = getWidth();
        mHeight = getHeight();

        mPaint.setStrokeWidth(5);//设置画笔宽度
        mPaint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);//绘图样式，设置为填充

        //画圆
        Path circlePath = new Path();
        circlePath.addCircle(mWidth / 2, mHeight / 2, 180, Path.Direction.CW);  //圆心x坐标，y坐标，半径，方向
        canvas.drawPath(circlePath, mPaint);

        mPaint.setStrokeWidth(0);//设置画笔宽度
        mPaint.setColor(mBackgroundColor);//设置画笔颜色

        //再画一个圆,文字围绕这个圆
//        mPaint.setColor(Color.parseColor("#123123"));//设置画笔颜色
        Path textCirclePath = new Path();
        textCirclePath.addCircle(mWidth / 2, mHeight / 2, 180 - mTextSize - 10, Path.Direction.CW);  //圆心x坐标，y坐标，半径，方向
        canvas.drawPath(textCirclePath, mPaint);

        mPaint.setStrokeWidth(0);//设置画笔宽度
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        double per = 2 * Math.PI * (180 - mTextSize - 10);// 周长
        //写字
        mPaint.setTextSize(mTextSize);
        canvas.drawTextOnPath(mTitle, textCirclePath, (float) (per / 2), 0, mPaint);

        mPaint.setTextSize(24);
        canvas.drawTextOnPath("23123123", textCirclePath, 130, -20, mPaint);

        int radius = 80;// 五角星的大小
        int startX = mWidth / 2 - radius;  //起点x位置
        int startY = mHeight / 2 - radius; //起点y位置
        //画五角星
        Path path = new Path();
        float radian = degree2Radian(DEGREE);
        float radius_in = (float) (radius * Math.sin(radian / 2) / Math.cos(radian)); //中间五边形的半径

        path.moveTo((float) (radius * Math.cos(radian / 2) + startX), startY);
        path.lineTo((float) (radius * Math.cos(radian / 2) + radius_in * Math.sin(radian) + startX), (float) (radius - radius * Math.sin(radian / 2) + startY));
        path.lineTo((float) (radius * Math.cos(radian / 2) * 2 + startX), (float) (radius - radius * Math.sin(radian / 2) + startY));
        path.lineTo((float) (radius * Math.cos(radian / 2) + radius_in * Math.cos(radian / 2) + startX), (float) (radius + radius_in * Math.sin(radian / 2) + startY));
        path.lineTo((float) (radius * Math.cos(radian / 2) + radius * Math.sin(radian) + startX), (float) (radius + radius * Math.cos(radian) + startY));
        path.lineTo((float) (radius * Math.cos(radian / 2) + startX), (radius + radius_in + startY));
        path.lineTo((float) (radius * Math.cos(radian / 2) - radius * Math.sin(radian) + startX), (float) (radius + radius * Math.cos(radian) + startY));
        path.lineTo((float) (radius * Math.cos(radian / 2) - radius_in * Math.cos(radian / 2) + startX), (float) (radius + radius_in * Math.sin(radian / 2) + startY));
        path.lineTo(startX, (float) (radius - radius * Math.sin(radian / 2) + startY));
        path.lineTo((float) (radius * Math.cos(radian / 2) - radius_in * Math.sin(radian) + startX), (float) (radius - radius * Math.sin(radian / 2) + startY));
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private final static float DEGREE = 36; //五角星角度

    /**
     * 角度转弧度
     *
     * @param degree
     * @return
     */
    private float degree2Radian(float degree) {
        return (float) (Math.PI * degree / 180);
    }
}