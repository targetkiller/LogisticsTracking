package hci.wlzz.android.UI;

import hci.wlzz.android.Models.StationManager;
import hci.wlzz.android.sql.DAOFactory;
import hci.wlzz.android.sql.StationManagerHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class StationManagerList extends Activity {
	private ListView managerlist = null;
	private SimpleAdapter adapter = null;
	private List<Map<String, Object>> list = null;
	private StationManagerHelper stationManagerHelper = DAOFactory
			.getStationManagerInstance(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.stationmanagerlist);

		Init();
	}

	public void Init() {

		managerlist = (ListView) findViewById(R.id.stationmanagerlist_list);
		list = getList();
		adapter = new SimpleAdapter(StationManagerList.this, list,
				R.layout.stationmanagerlist_temp, new String[] { "managername",
						"station" }, new int[] {
						R.id.stationmanagerlist_temp_managername,
						R.id.stationmanagerlist_temp_station });
		if (list != null) {
			managerlist.setOnItemClickListener(new clickItem());
			managerlist.setAdapter(adapter);
		}
	}

	@SuppressLint("UseValueOf")
	@SuppressWarnings("unused")
	public List<Map<String, Object>> getList() {
		List<Map<String, Object>> templist = new ArrayList<Map<String, Object>>();
		List<StationManager> list = null;
		StationManager temp = null;
		Iterator<StationManager> iter = null;

		try {
			list = stationManagerHelper.queryAll(stationManagerHelper
					.getReadableDatabase());
			iter = list.iterator();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// if(iter!=null){
		// while(iter.hasNext()){
		// Map<String, Object> map=new HashMap<String, Object>();
		// temp=iter.next();
		// map.put("id",new Integer(temp.getId()));
		// map.put("managername", temp.get)
		// }
		// }
		return templist;
	}

	public class clickItem implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			new AlertDialog.Builder(StationManagerList.this).setItems(
					new String[] { "分配工作站", "刪除职务" },
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							switch (which) {
							case 0:
								
								break;
							case 1:

								break;
							default:
								break;
							}
						}
					}).show();
		}
	}
}
