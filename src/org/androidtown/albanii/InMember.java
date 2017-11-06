package org.androidtown.albanii;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.androidtown.albanii.LoginActivity.HttpTask;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class InMember extends Activity implements android.widget.RadioGroup.OnCheckedChangeListener  {
	String part = "";
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
	layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
	layoutParams.dimAmount= 0.7f;
	getWindow().setAttributes(layoutParams);
	setContentView(R.layout.in_member);
	
	RadioGroup radio = (RadioGroup) findViewById(R.id.radio);
	radio.setOnCheckedChangeListener(this);
	Button inBtn = (Button) findViewById(R.id.inBtn);
	inBtn.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			new HttpTask().execute();  
		}  
	});
	}
	
	@Override
	public void onCheckedChanged(RadioGroup radio, int checkedId) {
		// TODO Auto-generated method stub
		if(radio.getCheckedRadioButtonId() == R.id.option1) { 
			part = "true";
		}
		if(radio.getCheckedRadioButtonId() == R.id.option2) {
			part = "false";
		}
	}

	class HttpTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
		    try {
		    	EditText edit2 = (EditText) findViewById(R.id.edit2);
				EditText edit3 = (EditText) findViewById(R.id.edit3);
				EditText edit4 = (EditText) findViewById(R.id.edit4);
				EditText edit5 = (EditText) findViewById(R.id.edit5);

				String id = edit2.getText().toString();
				String pw = edit3.getText().toString();
				String name = edit4.getText().toString();
				String phone = edit5.getText().toString();
				
				if(part.equals("") || id.equals("") || pw.equals("") || name.equals("") || phone.equals("")) {
					return "empty";
				}
				else {
		        HttpPost request = new HttpPost(LoginActivity.url+"join.php");
                //������ ���ڵ�
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("part", part));
                nameValue.add(new BasicNameValuePair("id", id));
                nameValue.add(new BasicNameValuePair("pw", pw));
                nameValue.add(new BasicNameValuePair("name", name));
                nameValue.add(new BasicNameValuePair("phone", phone));
                
                //�� ���� - utf-8 �������
                HttpEntity enty = new UrlEncodedFormEntity(nameValue, HTTP.UTF_8);
                request.setEntity(enty);
                
                HttpClient client = new DefaultHttpClient();
                HttpResponse res = client.execute(request);
                //�� �������� ���ޱ�
                HttpEntity entityResponse = res.getEntity();
                InputStream im = entityResponse.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(im, HTTP.UTF_8));
                
                String total = "";
                String tmp = "";
                //���ۿ��ִ°� ���� �����ֱ�
                //readLine -> ���ϳ����� �� ������ �б�
                while((tmp = reader.readLine())!= null)
                {
                    if(tmp != null)
                    {
                        total += tmp;
                    }
                }
                im.close();
                //���â�ѷ��ֱ� - ui ����� ����
                //result.setText(total);
                return total;
				}
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		    }
		    
            //������ null ��ȯ
            return null;
		    
        }
        //asyonTask 3��° ���ڿ� ��ġ �Ű������� -> doInBackground ���ϰ��� ���޵�
        //AsynoTask �� preExcute - doInBackground - postExecute ������ �ڵ����� ����˴ϴ�.
        //ui�� ���⼭ ����
        protected void onPostExecute(String value){
            super.onPostExecute(value);
            if(value.equals("empty")) {
            	Toast.makeText(InMember.this, "�������� �Է����ּ���.", Toast.LENGTH_SHORT).show();
            }
            else if(value.equals("1")) {
            	Toast.makeText(InMember.this, "�̹� ������ ID�Դϴ�.", Toast.LENGTH_SHORT ).show();
            }
            else if(value.equals("0")) {
            	Toast.makeText(InMember.this, "ȸ������ ����.", Toast.LENGTH_SHORT ).show();
            	Intent myIntent = new Intent(InMember.this, LoginActivity.class);
            	startActivity(myIntent);
            }
        }        

    }       

}