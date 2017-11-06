package org.androidtown.albanii;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewActivity extends Activity {
	int no; // DB no
	int num; // 게시물 수
	ListView listView;
	ArrayList<MyData> dataArr;
	MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity);
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText(AlbaMenuActivity.groupname);
		
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivity.this, MainActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		}); 
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(LoginActivity.memberPart.equals("true")) {
					Intent myIntent = new Intent(NewActivity.this, Tkwkdcnfxhlrms.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
				else if(LoginActivity.memberPart.equals("false")) {
					Intent myIntent = new Intent(NewActivity.this, NewActivitycnfxhlrms.class);
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
					Intent myIntent = new Intent(NewActivity.this, Tkwkdrmqdu.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
				else if(LoginActivity.memberPart.equals("false")){
					Intent myIntent = new Intent(NewActivity.this, NewActivityrmqdu.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
			}
		});
		
		Button button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivity.this, NewActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		
		Button button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivity.this, NewActivityrntjddnjs.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		new HttpTask().execute(); //게시물 뿌리기
			
		setupUI(findViewById(R.id.listView));
		//new HttpTask3().execute(); //좋아요 체크 
		
		Button rptlBtn = (Button) findViewById(R.id.rptlBtn);
		rptlBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new HttpTask2().execute();
			}
		});
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

	public void setupUI(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(NewActivity.this);
                    return false;
                }
            });
        }
      //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
	//좋아요 체크
	class HttpTask3 extends AsyncTask<Void, Void, String>{
		
        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
        	try {
        		HttpPost request = new HttpPost(LoginActivity.url+"likecheck.php");
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
        		
        		for(int i=0; i<jArr.length();i++) {
        			JSONObject json = jArr.getJSONObject(i);
        			int j=0;
        			while(j<num) {
        				if(dataArr.get(j).no == json.getInt("no")) {
        					dataArr.get(j).likebutton = true;
        				}
        				j++;
        				
        			}
        			
        		}
        		/*mAdapter = new MyAdapter(NewActivity.this, R.layout.list_item, dataArr);
        		mAdapter.notifyDataSetChanged();*/

        		im.close();
        		//결과창뿌려주기 - ui 변경시 에러
        		//result.setText(total);
        		return tmp;
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
        }
	}
	
	//게시 버튼용
	class HttpTask2 extends AsyncTask<Void, Void, String>{
		
        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
        	try {
        		HttpPost request = new HttpPost(LoginActivity.url+"rptl.php");
        		//전달할 인자들
        		EditText editText1 = (EditText) findViewById(R.id.editText1);
        		String contents=editText1.getText().toString();
        		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
        		nameValue.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
        		nameValue.add(new BasicNameValuePair("contents", contents));
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

        		/*dataArr = new ArrayList<MyData>();
        		
        		for(int i=0; i<jArr.length();i++) {
        			JSONObject json = jArr.getJSONObject(i);
        			dataArr.add(new MyData( json.getInt("no"), json.getString("contents"), json.getInt("like") ));
        		}*/
		
        		im.close();
        		//결과창뿌려주기 - ui 변경시 에러
        		//result.setText(total);
        		return tmp;
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
            Intent myIntent = new Intent(NewActivity.this, NewActivity.class);
			myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(myIntent);
        }
	}
	
	// 게시물 뿌리기
	class HttpTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
        	try {
        		HttpPost request = new HttpPost(LoginActivity.url+"board.php");
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
        		
        		num = jArr.length(); // 게시물 수
        		for(int i=0; i<jArr.length();i++) {
        			JSONObject json = jArr.getJSONObject(i);
        			dataArr.add(new MyData( json.getInt("no"), json.getString("contents"), json.getInt("likeplus") ));
        		}

        		im.close();
        		
        		//likecheck
        		HttpPost request2 = new HttpPost(LoginActivity.url+"likecheck.php");
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
        		
        		for(int i=0; i<jArr2.length();i++) {
        			JSONObject json2 = jArr2.getJSONObject(i);
        			for(int j=0; j<num; j++) {
        				if(dataArr.get(j).no == json2.getInt("no")) {
        					dataArr.get(j).likebutton = true;
        				}
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
            	mAdapter = new MyAdapter(NewActivity.this, R.layout.list_item, dataArr);
    			listView.setAdapter(mAdapter);    
        }
	}
	// 좋아요 추가
	class HttpTask4 extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
        	try {
        		HttpPost request = new HttpPost(LoginActivity.url+"likeplus.php");
        		//전달할 인자들
        		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
        		nameValue.add(new BasicNameValuePair("no", String.valueOf(no)));
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
        		im.close();
        		//결과창뿌려주기 - ui 변경시 에러
        		//result.setText(total);
        		return tmp;
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
    	
        }
	}        

	class MyData{
		Boolean likebutton = false;
		int no;
		String contents;
		int likeplus;

		MyData(int no, String contents, int likeplus){
		    this.no = no;
		    this.contents = contents;
		    this.likeplus = likeplus;
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
			
			TextView contents = (TextView)convertView.findViewById(R.id.contents);
			contents.setText(myDataArr.get(position).contents);
			TextView likenum = (TextView)convertView.findViewById(R.id.likenum);
			likenum.setText(String.valueOf(myDataArr.get(position).likeplus));
			Button like = (Button)convertView.findViewById(R.id.like);
			if((myDataArr.get(pos).likebutton) == false) {
				like.setBackgroundResource(R.drawable.likebefore);
			}
			else if((myDataArr.get(pos).likebutton) == true) {
				like.setBackgroundResource(R.drawable.likeafter);
			}
			//2번 구역
			like.setOnClickListener(new Button.OnClickListener()  {
				public void onClick(View v)  {
					if((myDataArr.get(pos).likebutton) == false) {
						Button like = (Button)findViewById(R.id.like);
						like.setBackgroundResource(R.drawable.likeafter);
						TextView likenum = (TextView)findViewById(R.id.likenum);
						//likenum.setText(String.valueOf((myDataArr.get(pos).likeplus + 1)));
						myDataArr.get(pos).likeplus = myDataArr.get(pos).likeplus + 1;
						myDataArr.get(pos).likebutton = true;
						Toast.makeText(NewActivity.this, "이 게시물을 좋아합니다.", Toast.LENGTH_SHORT).show();
						mAdapter.notifyDataSetChanged();
						no = myDataArr.get(pos).no;
						new HttpTask4().execute();
						
					}
					else if((myDataArr.get(pos).likebutton) == true) {
						Toast.makeText(NewActivity.this, "이미 좋아합니다.", Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			return convertView;
		}
	}
	
}
