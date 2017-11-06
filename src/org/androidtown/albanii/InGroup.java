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
			inBtn.setText("�׷� ����");
		}
		else if(LoginActivity.memberPart.equals("false")) {
			inBtn.setText("�׷� ����");
		}		
		if(AlbaMenuActivity.gn[2] != null) {
			inBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(InGroup.this, "���̻� ����/������ �� �����ϴ�.", Toast.LENGTH_SHORT).show();
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
	                //������ ���ڵ�
	                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
	                nameValue.add(new BasicNameValuePair("groupname", groupname));
	                nameValue.add(new BasicNameValuePair("groupcode", groupcode));
	                nameValue.add(new BasicNameValuePair("id", LoginActivity.memberId));				
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
				else if(LoginActivity.memberPart.equals("false")) {
					HttpPost request = new HttpPost(LoginActivity.url+"ingroup.php");
	                //������ ���ڵ�
	                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
	                nameValue.add(new BasicNameValuePair("groupname", groupname));
	                nameValue.add(new BasicNameValuePair("groupcode", groupcode));
	                nameValue.add(new BasicNameValuePair("id", LoginActivity.memberId));				
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
            	Toast.makeText(InGroup.this, "�������� �Է����ּ���.", Toast.LENGTH_SHORT).show();
            }
            else if(value.equals("1")) {
            	Toast.makeText(InGroup.this, "�̹� �ִ� �׷���Դϴ�.", Toast.LENGTH_SHORT ).show();
            }
            else if(value.equals("0")) {
            	Toast.makeText(InGroup.this, "�׷� ���� ����.", Toast.LENGTH_SHORT ).show();
            	Intent myIntent = new Intent(InGroup.this, AlbaMenuActivity.class);
            	startActivity(myIntent);
            }
            else if(value.equals("-1")) {
            	Toast.makeText(InGroup.this, "�׷� ���� ����.", Toast.LENGTH_SHORT ).show();
            	Intent myIntent = new Intent(InGroup.this, AlbaMenuActivity.class);
            	startActivity(myIntent);
            }
            else if(value.equals("-2")) {
            	Toast.makeText(InGroup.this, "�׷�� �Ǵ� �׷��ڵ带 Ȯ�����ּ���.", Toast.LENGTH_SHORT ).show();
            }
            else if(value.equals("-3")) {
            	Toast.makeText(InGroup.this, "�̹� �����ϰ� �ִ� �׷���Դϴ�.", Toast.LENGTH_SHORT ).show();
            }
        }        

    }    

	
}