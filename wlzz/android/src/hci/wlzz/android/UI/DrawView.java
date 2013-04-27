package hci.wlzz.android.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class DrawView extends View {
	private int zeroX = 0, zeroY = 0, X = 0, Y = 0;
	private int space = 0, everyL = 0;
	private String Xunit = "", Yunit = "";
	private boolean Flag = false;
	private String[] Xtitle = null;
	private int[] y = null;
	private int[] color = null;

	private int YNSize = 20;
	private int XNSize = 14;

	public DrawView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DrawView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas) {
		if (!Flag)
			return;
		super.onDraw(canvas);

		canvas.drawColor(Color.WHITE);
		drawXY(canvas);
		drawpillar(canvas);

	}

	public void drawXY(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);

		paint.setColor(Color.BLACK);
		canvas.drawLine(zeroX, zeroY, zeroX + X, zeroY, paint);
		// paint.setColor(Color.BLACK);
		canvas.drawLine(zeroX, zeroY, zeroX, zeroY - Y, paint);
		paint.setTextSize(YNSize);
		canvas.drawText(Xunit, zeroX + X + 5, zeroY + 5, paint);
		canvas.drawText(Yunit, zeroX - 5, zeroY - Y - YNSize - 5, paint);

		// ���ͷ
		Path path1 = new Path();
		path1.moveTo(zeroX + X, zeroY);
		path1.lineTo(zeroX + X - 5, zeroY - 5);
		path1.lineTo(zeroX + X + 5, zeroY);
		path1.lineTo(zeroX + X - 5, zeroY + 5);
		canvas.drawPath(path1, paint);
		// �ϼ�ͷ
		Path path2 = new Path();
		path2.moveTo(zeroX, zeroY - Y);
		path2.lineTo(zeroX - 5, zeroY - Y + 5);
		path2.lineTo(zeroX, zeroY - Y - 5);
		path2.lineTo(zeroX + 5, zeroY - Y + 5);
		canvas.drawPath(path2, paint);

		for (int i = zeroY, value = 0; i > zeroY - Y - 5; i -= space, value += everyL) {
			paint.setColor(Color.BLACK);
			paint.setTextSize(YNSize);
			canvas.drawLine(zeroX, i, zeroX + 2, i, paint);
			String str = value + "";
			canvas.drawText(str, (float) (zeroX - (str.length() / 2 + 0.5)
					* YNSize - 5), i, paint);

			paint.setColor(Color.GRAY);
			canvas.drawLine(zeroX, i, zeroX + X, i, paint);
		}

	}

	public void drawpillar(Canvas canvas) {
		Paint paint = new Paint();
		// ������
		int valueNum = (y != null) ? y.length : 0;
		int everyx = (X - 2) / (2 * valueNum + 1);

		for (int i = 1; i <= valueNum; i++) {
			if (color != null && color.length >= i)
				paint.setColor(color[i - 1]);
			else
				paint.setColor(Color.GREEN);

			canvas.drawRect(zeroX + (2 * i - 1) * everyx, zeroY - y[i - 1]
					* (space / everyL), zeroX + 2 * i * everyx, zeroY, paint);

			// ������
			paint.setColor(Color.BLACK);
			paint.setTextSize(XNSize);
			canvas.drawText(y[i - 1] + "", zeroX + (2 * i - 1) * everyx, zeroY
					- y[i - 1] * (space / everyL) - XNSize, paint);

			if (Xtitle != null && Xtitle.length >= i) {
				paint.setColor(Color.BLACK);
				paint.setTextSize(XNSize);
				canvas.drawText(Xtitle[i - 1], (float) (zeroX + (2 * i - 1.5)
						* everyx), zeroY + XNSize, paint);
			}
		}
	}

	public void setXY(int zeroX, int zeroY, int X, int Y, String Xunit,
			String Yunit) {
		this.zeroX = zeroX;
		this.zeroY = zeroY;
		this.X = X;
		this.Y = Y;
		this.Xunit = Xunit;
		this.Yunit = Yunit;
	}

	public void setYPoint(int space, int everyL) {
		this.space = space;
		this.everyL = everyL;
	}

	public void setXNSize(int XNSize) {
		this.XNSize = XNSize;
	}

	public void setYNSize(int YNSize) {
		this.YNSize = YNSize;
	}

	public void setData(String[] Xtitle, int[] y, int[] color) {
		this.Xtitle = Xtitle;
		this.y = y;
		this.color = color;
	}

	public void show() {
		Flag = true;
	}

	public void cancel() {
		Flag = false;
	}
}
