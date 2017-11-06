package org.androidtown.albanii;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import org.androidtown.albanii.NewActivitycnfxhlrms.MyAdapter;
import org.androidtown.albanii.NewActivitycnfxhlrms.MyData;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TkwkdInAttend extends Activity {

	String no2;
	String time2;
	String date2;
	String id;
	String name;
	
	ListView listView;
	ArrayList<MyData> dataArr;
	MyAdapter mAdapter;
	
	ListView listView2;
	ArrayList<MyData> dataArr2;
	MyAdapter2 mAdapter2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
		layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		layoutParams.dimAmount= 0.7f;
		getWindow().setAttributes(layoutParams);
		
		setContentView(R.layout.in_attend);

		Intent intent = getIntent();
		id = intent.getExtras().getString("id");
		name = intent.getExtras().getString("name");
		
		new HttpTask().execute();
		
	}
	
	class HttpTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
        	try {
        		////////////미승인
        		HttpPost request2 = new HttpPost(LoginActivity.url+"unapprovedtime.php");
        		//전달할 인자들
        		Vector<NameValuePair> nameValue2 = new Vector<NameValuePair>();
        		nameValue2.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
        		nameValue2.add(new BasicNameValuePair("id", id));
        		//웹 접속 - utf-8 방식으로
        		HttpEntity enty2 = new UrlEncodedFormEntity(nameValue2, HTTP.UTF_8);
        		request2.setEntity(enty2);
        		
        		HttpClient client2 = new DefaultHttpClient();
        		HttpResponse res2 = client2.execute(request2);

        		//웹 서버에서 값받기
        		HttpEntity entityResponse2 = res2.getEntity();
        		InputStream im2 = entityResponse2.getContent();
        		BufferedReader reader2 = new BufferedReader(new InputStreamReader(im2, HTTP.UTF_8));
        		String total2 = "";
        		String tmp2 = "";	
        		//버퍼에있는거 전부 더해주기
        		//readLine -> 파일내용을 줄 단위로 읽기
        		while((tmp2 = reader2.readLine())!= null)
        		{
        			total2 += tmp2;
        		}
        		
        		JSONArray jArr2 = new JSONArray(total2);
        		
        		dataArr = new ArrayList<MyData>();
        		
        		for(int i=0; i<jArr2.length(); i++) {
        			JSONObject json2 = jArr2.getJSONObject(i);
        			if(!json2.getString("date").equals("0")) {
        				dataArr.add(new MyData( json2.getInt("no"), json2.getString("groupname"), json2.getString("id"), json2.getString("name"), json2.getString("time"), json2.getString("date"), false ));
        			}
        		}
        		im2.close();
        		
        		////////////////승인
        		HttpPost request = new HttpPost(LoginActivity.url+"approvetime.php");
        		//전달할 인자들
        		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
        		nameValue.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
        		nameValue.add(new BasicNameValuePair("id", id));
        		//웹 접속 - utf-8 방식으로
        		HttpEntity enty = new UrlEncodedFormEntity(nameValue, HTTP.UTF_8);
        		request.setEntity(enty);
        		
        		HttpClient client = new DefaultHttpClient();
        		HttpResponse res = client.execute(request);

        		//웹 서버에서 값받기
        		HttpEntity entityResponse = res.getEntity();
        		InputStream im = entityResponse.getContent();
        		BufferedReader reader = new BufferedReader(new InputStreamReader(im, HTTP.UTF_8));
        		String total = "";
        		String tmp = "";	
        		
        		//버퍼에있는거 전부 더해주기
        		//readLine -> 파일내용을 줄 단위로 읽기
        		while((tmp = reader.readLine())!= null)
        		{
        			total += tmp;
        		}
        		
        		JSONArray jArr = new JSONArray(total);
        		
        		dataArr2 = new ArrayList<MyData>();
        		
        		for(int i=0; i<jArr.length();i++) {
        			JSONObject json = jArr.getJSONObject(i);
        			dataArr2.add(new MyData( json.getString("time"), json.getString("date"), json.getString("approvedate") ));
        			
        		}
        		im.close();

        		//결과창뿌려주기 - ui 변경시 에러

        		return total;
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
            //오류시 null 반환
            return null;
        }
        //asyonTask 3번째 인자와 일치 매개변수값 -> doInBackground 리턴값이 전달됨
        //AsynoTask 는 preExcute - doInBackground - postExecute 순으로 자동으로 실행됩니다.
        //ui는 여기서 변경
        protected void onPostExecute(String value){
            super.onPostExecute(value);
            TextView setname = (TextView)findViewById(R.id.name);
            setname.setText(name);
            listView2 = (ListView) findViewById(R.id.listView2);
            mAdapter2 = new MyAdapter2(TkwkdInAttend.this, R.layout.in_attend_list2, dataArr2);
    		listView2.setAdapter(mAdapter2);

            
            listView = (ListView) findViewById(R.id.listView);
            mAdapter = new MyAdapter(TkwkdInAttend.this, R.layout.in_attend_list, dataArr);
    		listView.setAdapter(mAdapter);
    		

        }
	}
		
	class MyData{
		int no;
		String groupname;
		String id;
		String name;
		String time;
		String date;
		Boolean approve;
		String approvedate;
		
		MyData(int no, String groupname, String id, String name, String time, String date, Boolean approve){
		    this.no = no;
		    this.groupname = groupname;
		    this.id = id;
		    this.name = name;
		    this.time = time;
		    this.date = date;
		    this.approve = approve;
		}
		MyData(String time, String date, String approvedate){
		    this.time = time;
		    this.date = date;
		    this.approvedate = approvedate;
		}
	}
	class MyAdapter extends BaseAdapter{
		Context context;
		int layoutId;
		ArrayList<MyData> myDataArr;
		LayoutInflater Inflater;
		MyAdapter(Context _context, int _layoutId, ArrayList<MyData> _myDataArr){
			context = _context;
			layoutId = _layoutId;
			myDataArr = _myDataArr;

			Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return myDataArr != null? myDataArr.size() : 0;
		}

		@Override
		public String getItem(int position) {
			return String.valueOf(myDataArr.get(position).time);
		}

		@Override
		public long getItemId(int position) {
		     return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final int pos = position;
			// 1번 구역
			convertView = Inflater.inflate(layoutId, parent, false);
			TextView date = (TextView)convertView.findViewById(R.id.date);
			date.setText(myDataArr.get(position).date);
			TextView time = (TextView)convertView.findViewById(R.id.time);
			double m = Double.parseDouble(myDataArr.get(position).time);
			int h = (int)m;
			String stTime = String.format("%02d시간 %02d분", h, (int)((m-h)*60));
			time.setText(stTime);
			Button ok = (Button)convertView.findViewById(R.id.ok);
			ok.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent myIntent = new Intent(TkwkdInAttend.this, TkwkdAttendOk.class);
					myIntent.addFlags(myIntent.FLAG_ACTIVITY_NO_HISTORY);
					myIntent.putExtra("no", myDataArr.get(pos).no);
					myIntent.putExtra("id", myDataArr.get(pos).id);
					myIntent.putExtra("name", myDataArr.get(pos).name);
					myIntent.putExtra("time", myDataArr.get(pos).time);
					myIntent.putExtra("date", myDataArr.get(pos).date);
					startActivity(myIntent);
				}
			});		
			
			return convertView;
		}
	}

	class MyAdapter2 extends BaseAdapter{
		Context context;
		int layoutId;
		ArrayList<MyData> myDataArr;
		LayoutInflater Inflater;
		MyAdapter2(Context _context, int _layoutId, ArrayList<MyData> _myDataArr){
			context = _context;
			layoutId = _layoutId;
			myDataArr = _myDataArr;

			Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return myDataArr != null? myDataArr.size() : 0;
		}

		@Override
		public String getItem(int position) {
			return String.valueOf(myDataArr.get(position).time);
		}

		@Override
		public long getItemId(int position) {
		     return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final int pos = position;
			// 1번 구역
			convertView = Inflater.inflate(layoutId, parent, false);
			TextView date2 = (TextView)convertView.findViewById(R.id.date2);
			date2.setText(myDataArr.get(position).date);
			TextView time2 = (TextView)convertView.findViewById(R.id.time2);
			double m = Double.parseDouble(myDataArr.get(position).time);
			int h = (int)m;
			String stTime = String.format("%02d시간 %02d분", h, (int)((m-h)*60));
			time2.setText(stTime);
			TextView okdate = (TextView)convertView.findViewById(R.id.okdate);
			okdate.setText(myDataArr.get(position).approvedate);
			
			return convertView;
		}
	}

}
