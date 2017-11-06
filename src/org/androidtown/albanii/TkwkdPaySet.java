package org.androidtown.albanii;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class TkwkdPaySet extends Activity {

	String id;
	String name;
	String pay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
		layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		layoutParams.dimAmount= 0.7f;
		getWindow().setAttributes(layoutParams);
		
		setContentView(R.layout.in_pay_set);

		Intent intent = getIntent();
		id = intent.getExtras().getString("id");
		name = intent.getExtras().getString("name");
		pay = intent.getExtras().getString("pay");
		
		EditText editpay = (EditText)findViewById(R.id.editpay);
		editpay.setHint(pay);

		Button set = (Button)findViewById(R.id.set);
		set.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new HttpTask().execute();
				Intent myIntent = new Intent(TkwkdPaySet.this, Tkwkdrmqdu.class);
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
        		EditText editpay = (EditText)findViewById(R.id.editpay);
        		String edit = editpay.getText().toString();
        		HttpPost request = new HttpPost(LoginActivity.url+"payset.php");
        		//전달할 인자들
        		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
        		nameValue.add(new BasicNameValuePair("id", id));
        		nameValue.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
        		nameValue.add(new BasicNameValuePair("pay", edit));
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

        }
	}

}
