package org.androidtown.albanii;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class TkwkdPaywlrmq extends Activity {

	String id;
	double totaltime;
	int wlrmqdor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
		layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		layoutParams.dimAmount= 0.7f;
		getWindow().setAttributes(layoutParams);
		
		setContentView(R.layout.in_pay_wlrmq);

		Intent intent = getIntent();
		id = intent.getExtras().getString("id");
		totaltime = intent.getExtras().getDouble("totaltime");
		wlrmqdor = intent.getExtras().getInt("wlrmqdor");
		
		Button cancel = (Button)findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		Button ok = (Button)findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new HttpTask().execute();
				Intent myIntent = new Intent(TkwkdPaywlrmq.this, Tkwkdrmqdu.class);
				myIntent.addFlags(myIntent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(myIntent);
				finish();
			}
		});
		
	}
	
	class HttpTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
        	try {
        		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ("yyyy.MM.dd HH:mm", Locale.KOREA);
    			Date currentTime = new Date();
    			String date = mSimpleDateFormat.format(currentTime);
    			
        		HttpPost request = new HttpPost(LoginActivity.url+"wlrmq.php");
        		//������ ���ڵ�
        		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
        		nameValue.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
        		nameValue.add(new BasicNameValuePair("id", id));
        		nameValue.add(new BasicNameValuePair("date", date));
        		nameValue.add(new BasicNameValuePair("time", String.valueOf(totaltime)));
        		nameValue.add(new BasicNameValuePair("mpay", String.valueOf(wlrmqdor)));
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
        			total += tmp;
        		}
        	
          		im.close();

        		//���â�ѷ��ֱ� - ui ����� ����
        		//result.setText(total);
        		return total;
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
            //������ null ��ȯ
            return null;
        }
        //asyonTask 3��° ���ڿ� ��ġ �Ű������� -> doInBackground ���ϰ��� ���޵�
        //AsynoTask �� preExcute - doInBackground - postExecute ������ �ڵ����� ����˴ϴ�.
        //ui�� ���⼭ ����
        protected void onPostExecute(String value){
            super.onPostExecute(value);

        }
	}

}
