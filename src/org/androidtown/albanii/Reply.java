package org.androidtown.albanii;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Reply extends Activity {
	int no;
	String name;
	ListView listView;
	ArrayList<MyData> dataArr;
	MyAdapter mAdapter;
	
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
	layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
	layoutParams.dimAmount= 0.7f;
	getWindow().setAttributes(layoutParams);
	setContentView(R.layout.reply);
	//no값 전달받기
	Intent intent = getIntent();
	no = intent.getExtras().getInt("no");
	dataArr = new ArrayList<MyData>();
	new HttpTask().execute();
	setupUI(findViewById(R.id.lin));
	Button inBtn = (Button) findViewById(R.id.inBtn);
	inBtn.setOnClickListener(new OnClickListener() {
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
                    hideSoftKeyboard(Reply.this);
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

	// 댓글 뿌리기
	class HttpTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
		    try {
		    	//id로 name얻어오기
   				HttpPost request = new HttpPost(LoginActivity.url+"getname.php");
   				//전달할 인자들
   				Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
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
   				name = total;
           		im.close();

           		//댓글 뿌리기
		        HttpPost request2 = new HttpPost(LoginActivity.url+"reply.php");
                //전달할 인자들
                Vector<NameValuePair> nameValue2 = new Vector<NameValuePair>();
                nameValue2.add(new BasicNameValuePair("no", String.valueOf(no)));
                
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
                JSONArray jArr = new JSONArray(total2);

        		
        		
        		for(int i=0; i<jArr.length();i++) {
        			JSONObject json = jArr.getJSONObject(i);
        			dataArr.add(new MyData( json.getString("id"), json.getString("name"), json.getString("contents"), json.getString("date")));
        		}

                im2.close();
                //결과창뿌려주기 - ui 변경시 에러
                //result.setText(total);
                return total2;
				}
		    	catch (Exception e) {
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
            mAdapter = new MyAdapter(Reply.this, R.layout.list_reply, dataArr);
    		listView.setAdapter(mAdapter);
        }        

	}
    // 댓글 작성
	class HttpTask2 extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... voids) {
			// TODO Auto-generated method stub
   		try {
   			EditText sodyd = (EditText) findViewById(R.id.sodyd);
   			String contentsString = sodyd.getText().toString();
   			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ("yyyy.MM.dd HH:mm", Locale.KOREA);
			Date currentTime = new Date();
			String date = mSimpleDateFormat.format(currentTime);
			
   			if(contentsString.equals("")) {
   				return "empty";
   			}
   			else {
           		//게시 - DB저장
           		HttpPost request = new HttpPost(LoginActivity.url+"rptlreply.php");
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("no", String.valueOf(no)));
                nameValue.add(new BasicNameValuePair("id", LoginActivity.memberId));
                nameValue.add(new BasicNameValuePair("name", name));
                nameValue.add(new BasicNameValuePair("contents", contentsString));
                nameValue.add(new BasicNameValuePair("date", date));
                
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
                dataArr.add(new MyData( LoginActivity.memberId, name, contentsString, date));
                im.close();
           		//결과창뿌려주기 - ui 변경시 에러
           		//result.setText(total);
           		return total;
   			}
   		} catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
               //오류시 null 반환
   		return null;
   		    
   	}
           //asyonTask 3번째 인자와 일치 매개변수값 -> doInBackground 리턴값이 전달됨
           //AsynoTask 는 preExcute - doInBackground - postExecute 순으로 자동으로 실행됩니다.
           //ui는 여기서 변경
		protected void onPostExecute(String value){
			super.onPostExecute(value);
			if(value.equals("empty")) {
				Toast.makeText(Reply.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
			}
			else if(value.equals("0")) {
				EditText sodyd = (EditText) findViewById(R.id.sodyd);
                sodyd.setText("");
				mAdapter.notifyDataSetChanged();
				int i = 0;
				while(true) {
					if(MainActivity.dataArr.get(i).no == no) {
						(MainActivity.dataArr.get(i).replynum)++;
						break;
					}
					i++;
				}
				MainActivity.mAdapter.notifyDataSetChanged();
				/*Intent myIntent = new Intent(Reply.this, Reply.class);
				myIntent.addFlags(myIntent.FLAG_ACTIVITY_NO_HISTORY);
				myIntent.putExtra("no", no);
				startActivity(myIntent);*/
			}
		}        

   }
	
	class MyData{
		String id;
		String name;
		String contents;
		String date;

		MyData(String id, String name, String contents, String date){
			this.id = id;
		    this.name = name;
		    this.contents = contents;
		    this.date = date;
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
			return String.valueOf(myDataArr.get(position).id);
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
			TextView name = (TextView)convertView.findViewById(R.id.name);
			name.setText(myDataArr.get(position).name);
			TextView contents = (TextView)convertView.findViewById(R.id.contents);
			contents.setText(myDataArr.get(position).contents);
			TextView date = (TextView)convertView.findViewById(R.id.date);
			date.setText(myDataArr.get(position).date);
			
			return convertView;
		}
	}
	

}