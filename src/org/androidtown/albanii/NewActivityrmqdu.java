package org.androidtown.albanii;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewActivityrmqdu extends Activity {

	ListView listView;
	ArrayList<MyData> dataArr;
	MyAdapter mAdapter;
	String totaltext = "총 0시간 0분";
	String won;
	int monthlypay = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity_rmqdu);
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText(AlbaMenuActivity.groupname);
		
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivityrmqdu.this, MainActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(LoginActivity.memberPart.equals("true")) {
					Intent myIntent = new Intent(NewActivityrmqdu.this, Tkwkdcnfxhlrms.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
				else if(LoginActivity.memberPart.equals("false")) {
					Intent myIntent = new Intent(NewActivityrmqdu.this, NewActivitycnfxhlrms.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
			}
		});
		
		Button button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(LoginActivity.memberPart.equals("true")) {
					Intent myIntent = new Intent(NewActivityrmqdu.this, Tkwkdrmqdu.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
				else if(LoginActivity.memberPart.equals("false")) {
					Intent myIntent = new Intent(NewActivityrmqdu.this, NewActivityrmqdu.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
			}
		});
		
		Button button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivityrmqdu.this, NewActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		
		Button button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivityrmqdu.this, NewActivityrntjddnjs.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		new HttpTask().execute();
		
	}
	
	//시간,급여,시급뿌리기
	class HttpTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
        	try {        		
        		//시급뿌리기
        		HttpPost request = new HttpPost(LoginActivity.url+"timepay.php");
        		//전달할 인자들
        		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
        		nameValue.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
        		nameValue.add(new BasicNameValuePair("id", LoginActivity.memberId));
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
        		won = total;
        		im.close();
        		
        		//시간 뿌리기
        		HttpPost request2 = new HttpPost(LoginActivity.url+"time.php");
        		//전달할 인자들
        		Vector<NameValuePair> nameValue2 = new Vector<NameValuePair>();
        		nameValue2.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
        		nameValue2.add(new BasicNameValuePair("id", LoginActivity.memberId));

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
        		double totaltime = 0;
        		
        		for(int i=0; i<jArr2.length();i++) {
        			JSONObject json2 = jArr2.getJSONObject(i);
        			if(!json2.getString("time").equals("0")) {
        				totaltime += Double.parseDouble(json2.getString("time"));
        			}
        		}
       			im2.close();
        		
       			//급여 뿌리기
       			totaltext = String.format("총 %02d시간 %02d분", (int)totaltime, (int)((totaltime-(int)totaltime)*60));
       			monthlypay = (int)(totaltime * Integer.parseInt(won));
        		
        		//지난급여
        		HttpPost request3 = new HttpPost(LoginActivity.url+"lastwage.php");
        		//전달할 인자들
        		Vector<NameValuePair> nameValue3 = new Vector<NameValuePair>();
        		nameValue3.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
        		nameValue3.add(new BasicNameValuePair("id", LoginActivity.memberId));
        		//웹 접속 - utf-8 방식으로
        		HttpEntity enty3 = new UrlEncodedFormEntity(nameValue3, HTTP.UTF_8);
        		request3.setEntity(enty3);
        		
        		HttpClient client3 = new DefaultHttpClient();
        		HttpResponse res3 = client3.execute(request3);

        		//웹 서버에서 값받기
        		HttpEntity entityResponse3 = res3.getEntity();
        		InputStream im3 = entityResponse3.getContent();
        		BufferedReader reader3 = new BufferedReader(new InputStreamReader(im3, HTTP.UTF_8));
        		String total3 = "";
        		String tmp3 = "";	
        		//버퍼에있는거 전부 더해주기
        		//readLine -> 파일내용을 줄 단위로 읽기
        		while((tmp3 = reader3.readLine())!= null)
        		{
        			total3 += tmp3;
        		}
        		JSONArray jArr3 = new JSONArray(total3);
        		
        		dataArr = new ArrayList<MyData>();
        		
        		for(int i=0; i<jArr3.length();i++) {
        			JSONObject json3 = jArr3.getJSONObject(i);
        			dataArr.add(new MyData( json3.getString("date"), json3.getString("time"), json3.getString("mpay")));
        		}
        		im3.close();

        		//결과창뿌려주기 - ui 변경시 에러
        		//result.setText(total);
        		return totaltext;
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
            TextView timepay = (TextView) findViewById(R.id.timepay);
            timepay.setText(won+"원");
    		TextView time = (TextView) findViewById(R.id.time);
    		time.setText(totaltext);
    		TextView wage = (TextView) findViewById(R.id.wage);
    		wage.setText("총 "+String.valueOf(monthlypay)+"원");

            listView = (ListView) findViewById(R.id.listView);
            mAdapter = new MyAdapter(NewActivityrmqdu.this, R.layout.list_lastwage, dataArr);
    		listView.setAdapter(mAdapter);
        }
	}
	
	class MyData{
		String date;
		String time;
		String mpay;

		MyData(String date, String time, String mpay){
		    this.date = date;
		    this.time = time;
		    this.mpay = mpay;
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
			return String.valueOf(myDataArr.get(position).date);
		}

		@Override
		public long getItemId(int position) {
		     return position;
		}
 
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final int pos = position;
			// 1번 구역
			if (convertView == null)  {
				convertView = Inflater.inflate(layoutId, parent, false);
			}
			TextView ldate = (TextView)convertView.findViewById(R.id.ldate);
			ldate.setText(myDataArr.get(position).date);
			TextView ltime = (TextView)convertView.findViewById(R.id.ltime);
			double dtime = Double.parseDouble(myDataArr.get(position).time);
			ltime.setText(String.format("총 %02d시간 %02d분", (int)dtime, (int)((dtime-(int)dtime)*60)));
			TextView lwage = (TextView)convertView.findViewById(R.id.lwage);
			lwage.setText(myDataArr.get(position).mpay+"원");
		
			return convertView;
		}
	}



}
