package org.androidtown.albanii;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends Activity  {
	static String url = "http://192.168.0.3/";
	static String memberId=""; // id로 로그인 유지
	static String memberPart="";
	private InputMethodManager ipm;
	private LinearLayout lin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.login);
				
		setupUI(findViewById(R.id.lin));
		
		Button loginBtn = (Button) findViewById(R.id.loginBtn);
		Button inBtn = (Button) findViewById(R.id.inBtn);

		inBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(LoginActivity.this, InMember.class);
				myIntent.addFlags(myIntent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(myIntent);
			}
		});	
		
		loginBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new HttpTask().execute();    
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
                    hideSoftKeyboard(LoginActivity.this);
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
		class HttpTask extends AsyncTask<Void, Void, String>{

	        @Override
	        protected String doInBackground(Void... voids) {
	            // TODO Auto-generated method stub
	            try{
					EditText idEdit = (EditText) findViewById(R.id.idEdit);
					EditText pwEdit = (EditText) findViewById(R.id.pwEdit);
					String id = idEdit.getText().toString();
					String pw = pwEdit.getText().toString();
	                HttpPost request = new HttpPost(url+"login.php");
	                //전달할 인자들
	                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
	                nameValue.add(new BasicNameValuePair("id", id));
	                nameValue.add(new BasicNameValuePair("pw", pw));
	                
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
	                while((tmp = reader.readLine())!= null) {
	                	if(tmp.equals("false") || tmp.equals("true")) {
	                		memberPart = tmp;
	                	}
	                	else {
	                		memberId = tmp;
	                	}
	                	total += tmp;
	                }
	                /*while((tmp = reader.readLine())!= null)
	                {
	                    if(tmp != null)
	                    {
	                        total += tmp;
	                    }
	                }*/
	                im.close();
	                //결과창뿌려주기 - ui 변경시 에러
	                //result.setText(total);
	                return total;                
	            }catch(UnsupportedEncodingException e){
	                e.printStackTrace();
	            }catch(IOException e){
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
	            /*if(value.equals("1")) {
	            	Intent myIntent = new Intent(LoginActivity.this, AlbaMenuActivity.class);
	            	startActivity(myIntent);
	            }*/
	            if(value.equals("0")) {
	            	Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 확인해주세요.", Toast.LENGTH_LONG ).show();
	            }
	            else {
					EditText idEdit = (EditText) findViewById(R.id.idEdit);
					EditText pwEdit = (EditText) findViewById(R.id.pwEdit);
					idEdit.setText("");
					pwEdit.setText("");
	            	//memberId = value;
	            	Intent myIntent = new Intent(LoginActivity.this, AlbaMenuActivity.class);
	            	startActivity(myIntent);
	            }
	            
	        }        
	    }       
	}
