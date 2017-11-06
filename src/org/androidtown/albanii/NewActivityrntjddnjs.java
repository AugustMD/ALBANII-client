package org.androidtown.albanii;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

import org.androidtown.albanii.NewActivityrmqdu.MyAdapter;
import org.androidtown.albanii.NewActivityrmqdu.MyData;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewActivityrntjddnjs extends Activity {
	
	ListView listView;
	ListView listView2;
	ArrayList<MyData> dataArr;
	ArrayList<MyData> dataArr2;
	MyAdapter mAdapter;
	MyAdapter mAdapter2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity_rntjddnjs);
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText(AlbaMenuActivity.groupname);
		
		//메뉴 5개
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivityrntjddnjs.this, MainActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(LoginActivity.memberPart.equals("true")) {
					Intent myIntent = new Intent(NewActivityrntjddnjs.this, Tkwkdcnfxhlrms.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
				else if(LoginActivity.memberPart.equals("false")) {
					Intent myIntent = new Intent(NewActivityrntjddnjs.this, NewActivitycnfxhlrms.class);
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
					Intent myIntent = new Intent(NewActivityrntjddnjs.this, Tkwkdrmqdu.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
				else if(LoginActivity.memberPart.equals("false")) {
					Intent myIntent = new Intent(NewActivityrntjddnjs.this, NewActivityrmqdu.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
			}
		});
		
		Button button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivityrntjddnjs.this, NewActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		
		Button button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivityrntjddnjs.this, NewActivityrntjddnjs.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		new HttpTask().execute();
	}
	class HttpTask extends AsyncTask<Void, Void, String>{
		
        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
		    try {
			HttpPost request = new HttpPost(LoginActivity.url+"rntjddnjs.php");
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
	                dataArr = new ArrayList<MyData>();
	                dataArr2 = new ArrayList<MyData>();
	                JSONArray jArr = new JSONArray(total);
	                
	                for(int i=0; i<jArr.length();i++) {
	                	JSONObject json2 = jArr.getJSONObject(i);
	                	if(json2.getString("part").equals("true")) {
	                		dataArr.add(0, new MyData(json2.getString("id"), json2.getString("name"), json2.getString("phone")));
	                	}
	                	else {		
	                		dataArr2.add(new MyData(json2.getString("id"), json2.getString("name"), json2.getString("phone")));
	                	}
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
            listView = (ListView) findViewById(R.id.listView);
            listView2 = (ListView) findViewById(R.id.listView2);
            mAdapter = new MyAdapter(NewActivityrntjddnjs.this, R.layout.list_rntjddnjs, dataArr);
            listView.setAdapter(mAdapter);
            mAdapter2 = new MyAdapter(NewActivityrntjddnjs.this, R.layout.list_rntjddnjs, dataArr2);
    		listView2.setAdapter(mAdapter2);
        }        

    }    
	
	class MyData{
		String id;
		String name;
		String phone;

		MyData(String id, String name, String phone){
			this.id = id;
		    this.name = name;
		    this.phone = phone;
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
			return String.valueOf(myDataArr.get(position).phone);
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
			
			TextView name = (TextView)convertView.findViewById(R.id.name);
			name.setText(myDataArr.get(position).name);			
			TextView phone = (TextView)convertView.findViewById(R.id.phone);
			phone.setText(myDataArr.get(position).phone);
			Button message = (Button)convertView.findViewById(R.id.message);
			message.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent myIntent = new Intent(NewActivityrntjddnjs.this, NewActivityrntjddnjsOk.class);
					myIntent.addFlags(myIntent.FLAG_ACTIVITY_NO_HISTORY);
					myIntent.putExtra("name", myDataArr.get(pos).name);
					myIntent.putExtra("phone", myDataArr.get(pos).phone);
					myIntent.putExtra("type", "sms");
					startActivity(myIntent);
				}
			});
			Button call = (Button)convertView.findViewById(R.id.call);
			call.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent myIntent = new Intent(NewActivityrntjddnjs.this, NewActivityrntjddnjsOk.class);
					myIntent.addFlags(myIntent.FLAG_ACTIVITY_NO_HISTORY);
					myIntent.putExtra("name", myDataArr.get(pos).name);
					myIntent.putExtra("phone", myDataArr.get(pos).phone);
					myIntent.putExtra("type", "tel");
					startActivity(myIntent);
				}
			});
			if(myDataArr.get(pos).id.equals(LoginActivity.memberId)) {
				message.setVisibility(View.INVISIBLE);
				call.setVisibility(View.INVISIBLE);
			}
			
		
			return convertView;
		}
	}


}
