package hci.wlzz.android.Utils;

import android.content.Context;
import android.os.AsyncTask;

public class AsyncTaskUpdateRequest extends AsyncTask<Object, Object, Object> {

	Context ctx;
	String json, url;

	public AsyncTaskUpdateRequest(Context ctx, String json, String url) {
		this.ctx = ctx;
		this.json = json;
		this.url = url;
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

	}

	@Override
	protected void onProgressUpdate(Object... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
