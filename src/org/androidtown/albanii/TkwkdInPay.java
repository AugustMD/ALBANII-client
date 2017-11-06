package org.androidtown.albanii;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

import org.androidtown.albanii.NewActivitycnfxhlrms.MyAdapter;
import org.androidtown.albanii.NewActivitycnfxhlrms.MyData;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TkwkdInPay extends Activity {

	String id;
	String name;
	String pay;
	String totaltext;
	int wlrmqdor;
	double totaltime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
		layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		layoutParams.dimAmount= 0.7f;
		getWindow().setAttributes(layoutParams);
		
		setContentView(R.layout.in_pay);

		Intent intent = getIntent();
		id = intent.getExtras().getString("id");
		name = intent.getExtras().getString("name");
		pay = intent.getExtras().getString("pay");
		
		new HttpTask().execute();
		
		Button payset = (Button)findViewById(R.id.payset);
		payset.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(TkwkdInPay.this, TkwkdPaySet.class);
				myIntent.addFlags(myIntent.FLAG_ACTIVITY_NO_HISTORY);
				myIntent.putExtra("id", id);
				myIntent.putExtra("name", name);
				myIntent.putExtra("pay", pay);
				startActivity(myIntent);
			}
		});
		
		Button wlrmq = (Button)findViewById(R.id.wlrmq);
		wlrmq.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(wlrmqdor == 0) {
					Toast.makeText(TkwkdInPay.this, "������ �ݾ��� �����ϴ�.", Toast.LENGTH_SHORT).show();
				}
				else {
					Intent myIntent = new Intent(TkwkdInPay.this, TkwkdPaywlrmq.class);
					myIntent.addFlags(myIntent.FLAG_ACTIVITY_NO_HISTORY);
					myIntent.putExtra("id", id);
					myIntent.putExtra("totaltime", totaltime);
					myIntent.putExtra("wlrmqdor", wlrmqdor);
					startActivity(myIntent);
				}
			}
		});
		
	}
	
	class HttpTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
        	try {        		
        		HttpPost request = new HttpPost(LoginActivity.url+"time.php");
        		//������ ���ڵ�
        		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
        		nameValue.add(new BasicNameValuePair("id", id));
        		nameValue.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
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
        		JSONArray jArr = new JSONArray(total);
        		
        		for(int i=0; i<jArr.length();i++) {
        			JSONObject json = jArr.getJSONObject(i);
        			totaltime += Double.parseDouble(json.getString("time"));
        		}
        		//�ð�
        		totaltext = String.format("�� %02d�ð� %02d��", (int)totaltime, (int)((totaltime-(int)totaltime)*60));
        		//�޿�
        		wlrmqdor = (int)(totaltime * Integer.parseInt(pay));
        		
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
            TextView setname = (TextView)findViewById(R.id.name);
            setname.setText(name);
            TextView setpay = (TextView)findViewById(R.id.pay);
            setpay.setText(pay);
            TextView settime = (TextView)findViewById(R.id.time);
            settime.setText(totaltext);
            TextView setwlrmqdor = (TextView)findViewById(R.id.wlrmqdor);
            setwlrmqdor.setText(String.valueOf(wlrmqdor));
        }
	}

}
