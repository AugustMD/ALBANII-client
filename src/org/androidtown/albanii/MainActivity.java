package org.androidtown.albanii;	

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

import org.androidtown.albanii.Tkwkdcnfxhlrms.MyAdapter;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity  {
	ListView listView;
	static ArrayList<MyData> dataArr;
	static MyAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText(AlbaMenuActivity.groupname);
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(LoginActivity.memberPart.equals("true")) {
					Intent myIntent = new Intent(MainActivity.this, Tkwkdcnfxhlrms.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
				else if(LoginActivity.memberPart.equals("false")){
					Intent myIntent = new Intent(MainActivity.this, NewActivitycnfxhlrms.class);
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
					Intent myIntent = new Intent(MainActivity.this, Tkwkdrmqdu.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
				else if(LoginActivity.memberPart.equals("false")){
					Intent myIntent = new Intent(MainActivity.this, NewActivityrmqdu.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
			}
		});
		
		Button button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(MainActivity.this, NewActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		
		Button button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(MainActivity.this, NewActivityrntjddnjs.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		Button rptlBtn = (Button) findViewById(R.id.rptlBtn);
		if(LoginActivity.memberPart.equals("false")) {
			rptlBtn.setVisibility(View.GONE);
		}
		rptlBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(MainActivity.this, Notice.class);
				myIntent.addFlags(myIntent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(myIntent);
			}
		});
		
		new HttpTask().execute();
		
	}
	// 게시물 뿌리기
	class HttpTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
        	try {
        		HttpPost request = new HttpPost(LoginActivity.url+"notice.php");
        		//전달할 인자들
        		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
        		nameValue.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
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

        		dataArr = new ArrayList<MyData>();
        		
        		for(int i=0; i<jArr.length();i++) {
        			JSONObject json = jArr.getJSONObject(i);
        			dataArr.add(new MyData( json.getInt("no"), json.getString("title"), json.getString("contents"), json.getString("date"), json.getInt("replynum") ));
        		}

        		im.close();
        		//결과창뿌려주기 - ui 변경시 에러
        		//result.setText(total);
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
            if(value != null) {
            listView = (ListView) findViewById(R.id.listView);
            mAdapter = new MyAdapter(MainActivity.this, R.layout.list_item2, dataArr);
            listView.setAdapter(mAdapter);
            }
            
        }
	}
	
	class MyData{
		int no;
		String title;
		String contents;
		String date;
		int replynum;

		MyData(int no, String title, String contents, String date, int replynum){
		    this.no = no;
		    this.title = title;
		    this.contents = contents;
		    this.date = date;
		    this.replynum = replynum;
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
			if (convertView == null)  {
				convertView = Inflater.inflate(layoutId, parent, false);
			}
			TextView title = (TextView)convertView.findViewById(R.id.title);
			title.setText(myDataArr.get(position).title);
			TextView contents = (TextView)convertView.findViewById(R.id.contents);
			contents.setText(myDataArr.get(position).contents);
			TextView date = (TextView)convertView.findViewById(R.id.date);
			date.setText(myDataArr.get(position).date);
			TextView replynum = (TextView)convertView.findViewById(R.id.replynum);
			replynum.setText(String.valueOf(myDataArr.get(position).replynum));
			
			Button reply = (Button)convertView.findViewById(R.id.reply);	
			//2번 구역
			reply.setOnClickListener(new Button.OnClickListener()  {
				public void onClick(View v)  {
					Intent myIntent = new Intent(MainActivity.this, Reply.class);
					myIntent.addFlags(myIntent.FLAG_ACTIVITY_NO_HISTORY);
					myIntent.putExtra("no", myDataArr.get(pos).no); //댓글 버튼 누를 때 게시판 고유no 전달
					startActivity(myIntent);
				}
			});
			
			return convertView;
		}
	}


}
