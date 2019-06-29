package com.myasciiphoto.image.gjl;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;

public class nzoom extends ImageView {
	private Matrix matrix = new Matrix();
	private Matrix savedMatrix = new Matrix();

	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	private int mode = NONE;
	private static final float MAX_SCALE = 3;
	private static float mCurScale = 1.0f;
	private static float mStartScale;
	// 第一个按下的手指的点
	private PointF startPoint = new PointF();
	// 两个按下的手指的触摸点的中点
	private PointF midPoint = new PointF();
	// 初始的两个手指按下的触摸点的距离
	private float oriDis = 1f;

	public nzoom(Context context, AttributeSet attrs) {
		super(context, attrs);
		// mGestureDetector=new GestureDetector(context, new GestureListener());

	}

	private int count = 0;

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (count == 0) {
			float[] values = new float[9];
			getImageMatrix().getValues(values);

			mStartScale = values[Matrix.MSCALE_X];
			Log.e("", "s=" + mStartScale);
			count++;
		}

	}

	private float isZoomChanged() {
		float[] values = new float[9];
		getImageMatrix().getValues(values);

		float scale = values[Matrix.MSCALE_X];
		if (scale != mStartScale)
			return mStartScale / scale;
		else
			return 2.0f;
		// return scale!=1.0f;
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		// TODO Auto-generated method stub
		super.setImageBitmap(bm);

	}

	private long startTime = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 进行与操作是为了判断多点触摸
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				// 第一个手指按下事件
				setScaleType(ScaleType.MATRIX);
				long interval = event.getEventTime() - startTime;
				Log.e("", "now-st=" + interval + "st=" + startTime);

				if (interval < 300) {
					Log.e("", "double click");
					matrix.set(getImageMatrix());

					float scale = isZoomChanged();
					Log.e("", "scale=" + scale);
					matrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
					setImageMatrix(matrix);
					break;
				}

				startTime = event.getEventTime();
				Log.e("", "down");
				Log.e("", "width=" + getDrawable().getBounds().width());
				matrix.set(getImageMatrix());
				savedMatrix.set(matrix);
				startPoint.set(event.getX(), event.getY());
				mode = DRAG;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				// 第二个手指按下事件

				Log.e("", "double down");
				oriDis = distance(event);
				if (oriDis > 10f) {
					savedMatrix.set(matrix);
					midPoint = middle(event);
					mode = ZOOM;
				}
				break;
			case MotionEvent.ACTION_UP:
				// 手指放开事件
				mode = NONE;
				break;
			case MotionEvent.ACTION_POINTER_UP:
				// 手指放开事件
				mode = NONE;
				break;
			case MotionEvent.ACTION_MOVE:

				// 手指滑动事件
				if (mode == DRAG) {
					// 是一个手指拖动

					matrix.set(savedMatrix);
					Log.e("mmm", "event.getX() - startPoint.x＝" + event.getX()
						  + "-" + startPoint.x);
					Log.e("mmm", matrix.toString());
					matrix.postTranslate(event.getX() - startPoint.x, event.getY()
										 - startPoint.y);
					Log.e("mmm", "x=" + getX() + "y=" + getY());
				} else if (mode == ZOOM) {
					// 两个手指滑动
					float newDist = distance(event);
					if (newDist > 10f) {
						matrix.set(savedMatrix);
						float scale = newDist / oriDis;
						float[] values = new float[9];
						matrix.getValues(values);
						scale = checkMaxScale(scale, values);
					}
				}
				break;
		}

		// 设置ImageView的Matrix
		this.setImageMatrix(matrix);
		return true;
	}

	/**
	 * 检验scale，使图像缩放后不会超出最大倍数
	 * 
	 * @param scale
	 * @param values
	 * @return
	 */
	private float checkMaxScale(float scale, float[] values) {
		if (scale * values[Matrix.MSCALE_X] > MAX_SCALE)
			scale = MAX_SCALE / values[Matrix.MSCALE_X];
		else if (scale * values[Matrix.MSCALE_X] < mStartScale)
			scale = mStartScale / values[Matrix.MSCALE_X];
		matrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
		return scale;
	}

	// 计算两个触摸点之间的距离
	private float distance(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		float sqrt = (float) Math.sqrt(x * x + y * y);
		return sqrt;

	}

	// 计算两个触摸点的中点
	private PointF middle(MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		return new PointF(x / 2, y / 2);
	}

}

/*
float sqrt = FloatMath.sqrt(x * x+ y* y);

       解决方法：Math.sqrt()


 float sqrt = (float) Math.sqrt(x * x + y * y);
*/


/*
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY YYYYYYYYYYYYYYYYYYYC        YY    YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
YYYYYYYYYYYYYY  YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY            YYYYYYYYYYY Y YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
YYYYYYYYYYYYYYY   YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY Y           YYYYYYYYY YYYYY    YYYYYYYYY  YYYYYYYYYYYYYYYYYYYYY  YYYYYYYYYYYYYYYYYYYY
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY         YYYYYYYYYY YYYYYY     Y  CYYYYYYYYYYYYYYYYYYYYYYYYYYYYY    YYYYYYYYYYYYYYYYYYYY
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY     Y   YYYYY YYYYYYYC YYY   YCCCC2CC Y Y   YYYYYYYYYYYYYYYYYYYYYYYYYY Y  YYYYYYYYYYYYYYYY
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY  YYYYYYYYYYY YY  Y Y   Y       CCCCCCCCYCCC2YC YYYYYYYYYYYYYY Y YYYYYYY    YYYYYYYYYYYYYYYY
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY YYYYYY  YYYY   Y      Y        YC22CCCC2CCC22C2 YYYYYYYYYYYYYY   Y YYYYYYYYYYYYYYYYYYYYYYYYY
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY YYYYYYYYYYYYYYYYYY YY    YYYY  Y      Y      C  Y   C   2CCCCCCCCCCCC2  YYYYYYYYYYYYY  YYYYYYYYYYYYYYYYYYYYYYYYYYYY
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY YYC   YYYYY    Y    YY Y        Y   YYCCCCCCCCC2CCC22CYYYYYYYYYYYY  YYYYYYYYYYYYYYYYYYYYYYYYYYYYY
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY     YYYYY  YYC    YYYYY   Y     Y   CCCYC2CCCCCCCC2C YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY          Y  Y Y  Y  Y      Y      YY Y CCC2YCCC2CC2CYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY 
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY YYYYYYYYY YY Y     Y Y     Y  YYY Y   Y  YY        YY  CCCCCCC2CC2CYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYC  
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY YYYYY Y  YY Y   Y   Y    Y  Y               CYCCC2CCCCC2CYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY    
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY  YY YY   Y Y Y YY          Y        CCCYCYCC   Y   CC2CC2C2CYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY     
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY    YY     Y  Y                      Y Y YY YY  YYY C2CCCCC2YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY     YY
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY Y    Y     C  Y    YY   Y                   Y   Y   CCC2CC2YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY     YC 
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY      Y         Y CCCYCYY                    C  CYY CCCCCC2CYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY     YY   
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY      Y          C YC Y  Y                       Y CYCYYCCYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY     YY    
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY    YYYYYY YY           CY        C                          YYCCCCC  Y YYYYYYYYYYYYYYYYYYYYYYYYYYYYYY    CY     Y
     YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY         YY           Y    Y YY                             CYYCYCCCCCC   YYYYYYY  YYYYYYYYYYYYYYYYYYYYY   YY      Y
      YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY                          YY YCCY                           YYCYCCCCCC    Y      YYYYYYY YYYYYYYYYCCYYY YY      YYY
Y     YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY                             Y Y    Y                       YYYYYYYY CC  YYYYYYYYYY  YYYY   YYYYYYYYYYYYY Y     YYYYY
         YYYYYYYYYYYYYYYYYY       Y              Y                   Y Y            Y           CYCYYYYC    YYYYYYYYYYYYYYYYYYYYYYYYYYYCYYYYY CYYYYYYYYYYY
  YYY  YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY   YY    Y YY                        CYYYYYYYYYYYYYYYYYYYYYY YYYYYYYYYYYYYYYYCY2  YCCYYCYYYYYYY
YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY   YYY      YY                   Y   YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYC2CCCCC2CYYYYYYCYYYYYYY
YYY YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY  YY YY       YY YY Y          Y     YYYYYYYYYYYYYYYYYYYYYYYYYYYYY  YYYYYYYYCYCYCYYYYYYYYYYYYYY
       Y YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY YYYYYYYYYY YYYYYYYYY     YYYYYYYYYY YYYYYYYYYYYYYYYYYYYYYYYYYYY        Y YY YYYCYYYYYYYYYYYYYY
       Y YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY YYYYYYYYYYYYY  YYYYYYYYYCYYY         YYYYYY  CYCYC           YYYYYYYYY             YYYYY2YYYYYYYYYYYYY
              YYYYYYY YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY           YCCCCY               YY YYCCCYYYYYY       YY  Y             Y     YYYYYYYYYYYYYY
                       Y  YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY           Y CCCCCCCC  Y          YY YYCCCCCYYYYY                    CYY  Y   YYYYYYYYYYYYYYY
                            Y YYYYYYYYYYYYYYYYYYYY    YYYYY                YYYCCCCCY YYC       YCYYYCCCCCY CY                C  YYY Y   Y  YYYYYYYYYYYYYYY
                                 Y Y YYYYYYYYYYYY       Y                     YYCCCCCYYYYY     YYYCCCCCCCYY                      Y       YYYYYYYYYYYYYYYYY
                          YYYYYYY YYYYYYYYYYYYYYYYYYYYY                        YCCCCCCCYYYYYY  YYYYCCCYCYC                            Y YYYYYYYYYYYYYYYYYY
                            YYYYYYYYYYYYYYYYYYYYYYYYYYY                          CCCCCCCCYYYYYY  CCCCYYYY                    Y      Y               Y     
                                YY      YY  YYYYYYYYYY                             CYYYCYCC Y    YYYYYYY                 Y Y       Y                      
                                                  YY                                 CYYYYYYYC  YCYYYYY             Y     Y                       Y       
                                                                                       CCYYYYCYYYYYY Y                  Y                                 
                                                                                        YCYYYYYYYY  Y                           YYYYYYY                   
                                                  Y                                       CCCCYYC                    C Y        YYY                       
                                                                                            CCYCCY                              Y                         
                                                                                  Y      Y    CCCYY    Y          C     Y      C                          
                                                 Y                               Y          C  CCC                                                        
                                                                                 Y                 Y           Y Y       C      YC                        
                                                                                 Y                                      22        CC                      
                                                                                 Y               C            C        C2        CCC2                     
                                                     Y                                          YYCYY         C        C2        YCCCC                    
                                                    Y CCCC                                  YYYYYYYCYYYY              CC  C       CCCCC                   
                                                     YYYCCCCCCCCCY                C      YYYYYYY YCYYCYYY             C2        C2CYCC2C                  
                                                     CCCCCCCCCCCCY     YYYYYYYYYY     YYYYYYYYYYYYY YYY  YY   Y     CCCY       Y CCCCC22CY                
                                                    YCCCCCCCCCCCY   Y  YYYYYYY       Y   CYYYY YYY   C YYY   YYY    C22    C   C2C2CCC2C2C                
                                                    CCCCCCCCCCCCYY     YYYYYYYY          Y    YYY           Y YYY   2C22      YY22CCC222CCC               
                                                 Y YYCCCCCCCCCCCCYY    YYYYYYYYY             CYY  Y Y Y    YYYYYYY  CC22     YY2CCCCC22CYY Y              
                                                      YCCCCCCCCCYCCY   YYYY   YYYY          CYY        YY  C YYYYYYY2C222   CYCYYCCC22YY Y                
                                                Y      YCCCCCCCCCCCCCCCYYYYYY  YYYYY    C  CYYYY C   CYYYYY     YYYYCCC222 Y Y    YC2Y  YY                
                                                       YCCCCCCYCCCCCCC2YYYYYY   YYY YYY C YYYYYY C  CCYYYYYYY YYYYYY2CC2222YYY     YCYYYYYY               
                                               Y        Y        YYCC2CCCCCCCCC22CY     Y YYYYCYC CYYYYYYYYYY      222CC2Y2CC      YYYYYY                 
                                                                    Y222YYYYYYY  YYYCC222YYY YYYY2CCYYYYYYYYYCC2222222CCC222C     YYYYYYY                 
                                                               YY Y YC222YYYYYYYYYCY C   YYYYYYYYY2YYYYYYYYYYYYYYYY2222CC222     YYYY  YY                 
                                                 YY            YY  YYC2222CYYCC2        YYYYYYYC22CYYYYYYYYYYYYYYYC2Y222CC22Y   YYYY YYYC                 
                                                    Y           YY  YCC22CCCCC YY         YY  Y2C      CYCYYYYYYYY2222CC2C22YYYYYYY   Y                   
                                                YYY  YY         YYYCCCCCCCC2YYY       YC      YC            YYYYYY22222Y2222YYYYYY  Y                     
                                                     Y  YY    Y2222CCCYCC2C YY          C  Y YYYY              YY YYYYC22222YYYYYYY YYC                   
                                                   Y      C222222CCCC22222 YY       Y       YYYYYYY                   YY222YYYYYYYYYY                     

												   */
