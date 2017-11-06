package org.androidtown.albanii;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AlbaMenuActivity extends Activity  {
	static String groupname;
	static String gn[] = new String[3];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		gn[0] = gn[1] = gn[2] = null; // 로그인 시 초기화
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.albamenu);
		new HttpTask().execute();
		
		Button button1 = (Button) findViewById(R.id.button1);
    	Button button2 = (Button) findViewById(R.id.button2);
    	Button button3 = (Button) findViewById(R.id.button3);
		Button plusBtn = (Button) findViewById(R.id.plusBtn);
		plusBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(AlbaMenuActivity.this, InGroup.class);
				myIntent.addFlags(myIntent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(myIntent);	 
			}
		});	
		
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				groupname = gn[0]; 
				Intent myIntent = new Intent(AlbaMenuActivity.this, MainActivity.class);
				Toast.makeText(AlbaMenuActivity.this, groupname, Toast.LENGTH_LONG).show();
				startActivity(myIntent);	 
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				groupname = gn[1]; 
				Intent myIntent = new Intent(AlbaMenuActivity.this, MainActivity.class);
				Toast.makeText(AlbaMenuActivity.this, groupname, Toast.LENGTH_LONG).show();
				startActivity(myIntent);	 
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				groupname = gn[2]; 
				Intent myIntent = new Intent(AlbaMenuActivity.this, MainActivity.class);
				Toast.makeText(AlbaMenuActivity.this, groupname, Toast.LENGTH_LONG).show();
				startActivity(myIntent);	 
			}
		});
		
	}
	class HttpTask extends AsyncTask<Void, Void, String>{
		
        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
		    try {
				if(LoginActivity.memberPart.equals("true")) {
					HttpPost request = new HttpPost(LoginActivity.url+"tmenu.php");
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
	                	if(tmp.equals("0")) {
	                		return tmp;
	                	}
	                	total += tmp;
	                }
	                JSONArray jArr = new JSONArray(total);
	                for(int i=0; i<jArr.length();i++) {
	                	JSONObject json = jArr.getJSONObject(i);
	                	
	                	gn[i] = json.getString("groupname");
	                }
	                
	                im.close();
	                //결과창뿌려주기 - ui 변경시 에러
	                //result.setText(total);
	                return tmp;
				}
				
				else if(LoginActivity.memberPart.equals("false")) {
					HttpPost request = new HttpPost(LoginActivity.url+"fmenu.php");
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
	                	if(tmp.equals("0")) {
	                		return tmp;
	                	}
	                	total += tmp;
	                }
	                JSONArray jArr = new JSONArray(total);
	                for(int i=0; i<jArr.length();i++) {
	                	JSONObject json = jArr.getJSONObject(i);
	                	
	                	gn[i] = json.getString("groupname");
	                }
	                
	                im.close();
	                //결과창뿌려주기 - ui 변경시 에러
	                //result.setText(total);
	                return tmp;
				}       	
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
            Button button1 = (Button) findViewById(R.id.button1);
            Button button2 = (Button) findViewById(R.id.button2);
            Button button3 = (Button) findViewById(R.id.button3);

            if(gn[2] != null) {
            	button1.setVisibility(View.VISIBLE);
            	button1.setText(gn[0]);
            	button2.setVisibility(View.VISIBLE);
            	button2.setText(gn[1]);
            	button3.setVisibility(View.VISIBLE);
            	button3.setText(gn[2]);
            }
            else if(gn[1] != null) {
            	button1.setVisibility(View.VISIBLE);
            	button1.setText(gn[0]);
            	button2.setVisibility(View.VISIBLE);
            	button2.setText(gn[1]);
            }
            else if(gn[0] != null) {
            	button1.setVisibility(View.VISIBLE);
            	button1.setText(gn[0]);
            }
            else if(value.equals("0")) {
            	button2.setVisibility(View.VISIBLE);
            	button2.setClickable(false);
            	button2.setText("비어있음.");
            	button2.setBackgroundColor(Color.parseColor("#00000000"));
            }
        }        

    }    
}
