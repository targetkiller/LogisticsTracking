package hci.wlzz.android.UI;

import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class VerifyCode extends View{
	private Paint mPaint;
	private String sRand="";
	private final int Size=50;

	public VerifyCode(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init();
	}
	public VerifyCode(Context context, AttributeSet attrs){
		super(context, attrs);
		Init();
	}
	public VerifyCode(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		Init();
	}
	public void Init(){
		mPaint=new Paint();
		mPaint.setAntiAlias(false);
		mPaint.setStrokeWidth(4);
		mPaint.setTextSize(Size);
		mPaint.setTextAlign(Paint.Align.CENTER);
		
		Random random = new Random();
		for(int i=0;i<4;i++){
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
	}
	@SuppressLint("DrawAllocation")
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);

		canvas.drawColor(Color.GRAY);
		canvas.save();
		canvas.translate(10, 10);
		mPaint.setColor(Color.BLUE);

		canvas.drawText(sRand, 66, 44, mPaint);

		canvas.restore();
	}
	public String getString(){
		return sRand;
	}
}
