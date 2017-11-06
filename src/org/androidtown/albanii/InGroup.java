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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InGroup extends Activity  {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
		layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		layoutParams.dimAmount= 0.7f;
		getWindow().setAttributes(layoutParams);
		
		setContentView(R.layout.in_group);
		Button inBtn = (Button) findViewById(R.id.inBtn);
		
		if(LoginActivity.memberPart.equals("true")) {
			inBtn.setText("그룹 생성");
		}
		else if(LoginActivity.memberPart.equals("false")) {
			inBtn.setText("그룹 참여");
		}		
		if(AlbaMenuActivity.gn[2] != null) {
			inBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(InGroup.this, "더이상 생성/참여할 수 없습니다.", Toast.LENGTH_SHORT).show();
				}
			});
		}
		
		else {
			inBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new HttpTask().execute();
			}
			
		});
		}
		
	}
	
	class HttpTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
		    try {
		    EditText edit1 = (EditText) findViewById(R.id.edit1);
			EditText edit2 = (EditText) findViewById(R.id.edit2);

			String groupname = edit1.getText().toString();
			String groupcode = edit2.getText().toString();
			
			if(groupname.equals("") || groupcode.equals("")) {
				return "empty";
				
			}
			else {
				if(LoginActivity.memberPart.equals("true")) {
					HttpPost request = new HttpPost(LoginActivity.url+"makegroup.php");
	                //전달할 인자들
	                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
	                nameValue.add(new BasicNameValuePair("groupname", groupname));
	                nameValue.add(new BasicNameValuePair("groupcode", groupcode));
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
	                    if(tmp != null)
	                    {
	                        total += tmp;
	                    }
	                }
	                im.close();
	                //결과창뿌려주기 - ui 변경시 에러
	                //result.setText(total);
	                return total;
				}
				else if(LoginActivity.memberPart.equals("false")) {
					HttpPost request = new HttpPost(LoginActivity.url+"ingroup.php");
	                //전달할 인자들
	                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
	                nameValue.add(new BasicNameValuePair("groupname", groupname));
	                nameValue.add(new BasicNameValuePair("groupcode", groupcode));
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
	                    if(tmp != null)
	                    {
	                        total += tmp;
	                    }
	                }
	                im.close();
	                //결과창뿌려주기 - ui 변경시 에러
	                //result.setText(total);
	                return total;
				}                
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
            	Toast.makeText(InGroup.this, "빠짐없이 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
            else if(value.equals("1")) {
            	Toast.makeText(InGroup.this, "이미 있는 그룹명입니다.", Toast.LENGTH_SHORT ).show();
            }
            else if(value.equals("0")) {
            	Toast.makeText(InGroup.this, "그룹 생성 성공.", Toast.LENGTH_SHORT ).show();
            	Intent myIntent = new Intent(InGroup.this, AlbaMenuActivity.class);
            	startActivity(myIntent);
            }
            else if(value.equals("-1")) {
            	Toast.makeText(InGroup.this, "그룹 참여 성공.", Toast.LENGTH_SHORT ).show();
            	Intent myIntent = new Intent(InGroup.this, AlbaMenuActivity.class);
            	startActivity(myIntent);
            }
            else if(value.equals("-2")) {
            	Toast.makeText(InGroup.this, "그룹명 또는 그룹코드를 확인해주세요.", Toast.LENGTH_SHORT ).show();
            }
            else if(value.equals("-3")) {
            	Toast.makeText(InGroup.this, "이미 참여하고 있는 그룹명입니다.", Toast.LENGTH_SHORT ).show();
            }
        }        

    }    

	
}