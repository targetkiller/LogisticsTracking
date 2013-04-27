package hci.wlzz.android.UI;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class ScrollTest extends Activity {
	private ViewPager awesomePager;

	private AwesomePagerAdapter awesomeAdapter;

	private LayoutInflater mInflater;
	private List<View> mListViews;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scroll_test);

		awesomeAdapter = new AwesomePagerAdapter();
		awesomePager = (ViewPager) findViewById(R.id.viewpager);
		awesomePager.setAdapter(awesomeAdapter);

		mListViews = new ArrayList<View>();
		mInflater = getLayoutInflater();

		mListViews.add(mInflater.inflate(R.layout.activity_table, null));
		mListViews.add(mInflater.inflate(R.layout.activity_paint_graphy, null));
	}

	private class AwesomePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View collection, int position) {
			if (position == 1) {
				LinearLayout ll = (LinearLayout) mListViews.get(position);

				DrawView dv = (DrawView) ll
						.findViewById(R.id.paintgraphy_drawview);
				dv.setXY(70, 450, 350, 400, "�·�", "������");
				dv.setYPoint(30, 10);
				dv.setData(new String[] { "һ", "��", "��", "��", "��", "��",
						"��", "��", "��", "ʮ", "ʮһ", "ʮ��" }, new int[] { 10,
						20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120 }, null);
				dv.show();
				((ViewPager) collection).addView(ll, 0);
				return ll;
			}
			((ViewPager) collection).addView(mListViews.get(position), 0);

			return mListViews.get(position);
		}

		@Override
		public void destroyItem(View collection, int position, Object view) {
			((ViewPager) collection).removeView(mListViews.get(position));
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == (object);
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

	}

}
