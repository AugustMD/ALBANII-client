package org.androidtown.albanii;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;

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
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewActivitycnfxhlrms extends Activity {
	private static final int TIME_PICKER_INTERVAL=30;
	private boolean mIgnoreEvent=false;
	
	ListView listView;
	ArrayList<MyData> dataArr;
	MyAdapter mAdapter;
	
    static GregorianCalendar calendar = new GregorianCalendar();
    static int year = calendar.get(Calendar.YEAR);
    static int month = calendar.get(Calendar.MONTH);
    static int day = calendar.get(Calendar.DAY_OF_MONTH);
    static int inHour = calendar.get(Calendar.HOUR_OF_DAY);
    static int inMinute = calendar.get(Calendar.MINUTE);
    static int outHour = calendar.get(Calendar.HOUR_OF_DAY);
    static int outMinute = calendar.get(Calendar.MINUTE);
    String time = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity_cnfxhlrms);
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText(AlbaMenuActivity.groupname);
		
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivitycnfxhlrms.this, MainActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}	
		});
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(LoginActivity.memberPart.equals("true")) {
					Intent myIntent = new Intent(NewActivitycnfxhlrms.this, Tkwkdcnfxhlrms.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
				else if(LoginActivity.memberPart.equals("false")) {
					Intent myIntent = new Intent(NewActivitycnfxhlrms.this, NewActivitycnfxhlrms.class);
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
					Intent myIntent = new Intent(NewActivitycnfxhlrms.this, Tkwkdrmqdu.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
				else if(LoginActivity.memberPart.equals("false")) {
					Intent myIntent = new Intent(NewActivitycnfxhlrms.this, NewActivityrmqdu.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
			}
		});
		
		Button button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivitycnfxhlrms.this, NewActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		
		Button button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivitycnfxhlrms.this, NewActivityrntjddnjs.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		//피커
		
		findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
			@Override
            public void onClick(View v) {
                 new DatePickerDialog(NewActivitycnfxhlrms.this, dateSetListener, year, calendar.get(Calendar.MONTH), day).show();
            }
		});
		findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new TimePickerDialog(NewActivitycnfxhlrms.this, timeSetListener, 0, 0, true).show();
            }
		});
    
		new HttpTask().execute();//출퇴근 시간 뿌리기
		
		
		findViewById(R.id.wjwkd).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(time.equals("")) {}
				else {
					new HttpTask2().execute();
				}
			}
		});

	}
	//출퇴근 시간 뿌리기
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
        		JSONArray jArr = new JSONArray(total);
        		
        		double totaltime = 0;
        		
        		for(int i=0; i<jArr.length();i++) {
        			JSONObject json = jArr.getJSONObject(i);
        			dataArr.add(new MyData( json.getInt("no"), json.getString("groupname"), json.getString("id"), json.getString("name"), json.getString("time"), json.getString("date"), true ));
        			totaltime += Double.parseDouble(json.getString("time"));
        		}
        		im.close();
        		
        		String totaltext = String.format("총 %02d시간 %02d분", (int)totaltime, (int)((totaltime-(int)totaltime)*60));

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
            TextView total = (TextView) findViewById(R.id.total);
            total.setText(value);
            listView = (ListView) findViewById(R.id.listView);
            mAdapter = new MyAdapter(NewActivitycnfxhlrms.this, R.layout.list_time, dataArr);
    		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    		listView.setAdapter(mAdapter);
        }
	}

	//출퇴근 시간 저장
	class HttpTask2 extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
        	try {
        		////////////////승인
        		HttpPost request = new HttpPost(LoginActivity.url+"wjwkd.php");
        		//전달할 인자들
        		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
        		nameValue.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
        		nameValue.add(new BasicNameValuePair("id", LoginActivity.memberId));
        		nameValue.add(new BasicNameValuePair("time", time));
        		nameValue.add(new BasicNameValuePair("date", String.format("%d. %02d. %02d", year, month, day)));
        		
        		//웹 접속 - utf-8 방식으로
        		HttpEntity enty = new UrlEncodedFormEntity(nameValue, HTTP.UTF_8);
        		request.setEntity(enty);
        		
        		HttpClient client = new DefaultHttpClient();
        		HttpResponse res = client.execute(request);

        		//웹 서버에서 값받기
        		HttpEntity entityResponse = res.getEntity();
        		//결과창뿌려주기 - ui 변경시 에러
        		//result.setText(total);
        		return "";
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
            Intent myIntent = new Intent(NewActivitycnfxhlrms.this, NewActivitycnfxhlrms.class);
            startActivity(myIntent);
            finish();
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

		MyData(int no, String groupname, String id, String name, String time, String date, Boolean approve){
		    this.no = no;
		    this.groupname = groupname;
		    this.id = id;
		    this.name = name;
		    this.time = time;
		    this.date = date;
		    this.approve = approve;
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
			return String.valueOf(myDataArr.get(position).no);
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
			TextView dateTV = (TextView)convertView.findViewById(R.id.dateTV);
			dateTV.setText(myDataArr.get(position).date);
			TextView timeTV = (TextView)convertView.findViewById(R.id.timeTV);
			double m = Double.parseDouble(myDataArr.get(position).time);
			int h = (int)m;
			String stTime = String.format("%02d시간 %02d분", h, (int)((m-h)*60));
			timeTV.setText(stTime);
			if(myDataArr.get(pos).approve == true) {
				TextView approveTV = (TextView)convertView.findViewById(R.id.approveTV);
				approveTV.setTextColor(Color.parseColor("#0000ff"));
				approveTV.setText("승인");
				mAdapter.notifyDataSetChanged();
			}
			
			return convertView;
		}
	}
	////////////////////////////////////////////////////////////////
	private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    	   String msg = String.format("%d. %02d. %02d", year, monthOfYear+1, dayOfMonth);
    	   Toast.makeText(NewActivitycnfxhlrms.this, msg, Toast.LENGTH_SHORT).show();
    	   TextView test1 = (TextView) findViewById(R.id.test1);
    	   test1.setText(msg);
    	   NewActivitycnfxhlrms.year = year;
    	   NewActivitycnfxhlrms.month = monthOfYear+1;
    	   NewActivitycnfxhlrms.day = dayOfMonth;   
       }
	};

 	private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
 		@Override
 		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
 			if (mIgnoreEvent)
 	            return;
 	        if (minute%TIME_PICKER_INTERVAL!=0){
 	            int minuteFloor=minute-(minute%TIME_PICKER_INTERVAL);
 	            minute=minuteFloor + (minute==minuteFloor+1 ? TIME_PICKER_INTERVAL : 0);
 	            if (minute==60)
 	                minute=0;
 	            mIgnoreEvent=true;
 	            view.setCurrentMinute(minute);
 	            mIgnoreEvent=false;
 	        }
    	   String msg = String.format("%02d시간 %02d분", hourOfDay, minute);
    	   Toast.makeText(NewActivitycnfxhlrms.this, msg, Toast.LENGTH_SHORT).show();
    	   TextView test2 = (TextView) findViewById(R.id.test2);
    	   test2.setText(msg);
    	   NewActivitycnfxhlrms.inHour = hourOfDay;
    	   NewActivitycnfxhlrms.inMinute = minute;
    	   time = String.valueOf(inHour + inMinute/60.0);
       }
 	};

}
