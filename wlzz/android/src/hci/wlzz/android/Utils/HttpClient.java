package hci.wlzz.android.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

public class HttpClient {

	public static final String BASE_URL = "http://172.26.14.209:9001/android";
	DefaultHttpClient httpClient = new DefaultHttpClient();;
	HttpResponse response;

	// return JsonString
	public String sendHttpGet(String url) {

		StringBuilder builder = new StringBuilder();
		HttpGet getRequest = new HttpGet(this.BASE_URL + url);
		try {
			response = httpClient.execute(getRequest);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
			inputStream.close();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	}

	public String sendHttpPost(String url, List<NameValuePair> param) {
		StringBuilder builder = new StringBuilder();
		HttpPost postRequest = new HttpPost(this.BASE_URL + url);
		try {
			postRequest.setEntity(new UrlEncodedFormEntity(param, HTTP.UTF_8));
			response = httpClient.execute(postRequest);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
			inputStream.close();
			reader.close();

			// tested before
			/*
			 * JSONObject result = new JSONObject(builder.toString());
			 * JSONObject result = new JSONObject(EntityUtils.toString(response
			 * .getEntity())); JsonUtils ju = new JsonUtils();
			 * ju.parseJson(builder.toString());
			 */

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	}

	public String sendJSON(String url, JSONObject param) {

		StringBuilder builder = new StringBuilder();
		HttpPost postRequest = new HttpPost(url);
		try {
			StringEntity se = new StringEntity(param.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			postRequest.setEntity(se);
			response = httpClient.execute(postRequest);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
			inputStream.close();
			reader.close();

			// tested before
			/*
			 * JSONObject result = new JSONObject(builder.toString());
			 * JSONObject result = new JSONObject(EntityUtils.toString(response
			 * .getEntity())); JsonUtils ju = new JsonUtils();
			 * ju.parseJson(builder.toString());
			 */

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	}
}
