package cn.com.fyl.learn.scratchview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Date:2019/9/25 0025
 * Time:上午 8:40
 * author:fengyalu
 */

public class ScratchView extends View {

    private Paint paint;
    private Path path;
    private Bitmap bitmap;
    private BitmapShader bitmapShader;

    public ScratchView(Context context) {
        this(context, null);
    }

    public ScratchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScratchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //描边
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(70);
        path = new Path();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scrach);

        /**
         * bitmap用来指定图案，tileX用来指定当X轴超出单个图片大小时时所使用的重复策略，同样tileY用于指定当Y轴超出单个图片大小时时所使用的重复策略
         其中TileMode的取值有：
         TileMode.CLAMP:用边缘色彩填充多余空间
         TileMode.REPEAT:重复原图像来填充多余空间
         TileMode.MIRROR:重复使用镜像模式的图像来填充多余空间
         */
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        paint.setShader(bitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制路径
        canvas.drawPath(path, paint);
    }

    float fromX = 0;
    float fromY = 0;

    //路径随着手的移动绘制
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                fromX = x;
                fromY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                //Math.abs()取绝对值
                if (Math.abs(x - fromX) > 0 || Math.abs(y - fromY) > 0) {
                    path.lineTo(fromX, fromY);
                    fromX = x;
                    fromY = y;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;

        }

        invalidate();
        return true;
    }
}
