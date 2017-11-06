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
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Tkwkdrmqdu extends Activity {

	ListView listView;
	ArrayList<MyData> dataArr;
	MyAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tkwkd_rmqdu);
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText(AlbaMenuActivity.groupname);
		
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(Tkwkdrmqdu.this, MainActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(LoginActivity.memberPart.equals("true")) {
					Intent myIntent = new Intent(Tkwkdrmqdu.this, Tkwkdcnfxhlrms.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
				else if(LoginActivity.memberPart.equals("false")) {
					Intent myIntent = new Intent(Tkwkdrmqdu.this, NewActivitycnfxhlrms.class);
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
					Intent myIntent = new Intent(Tkwkdrmqdu.this, Tkwkdrmqdu.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
				else if(LoginActivity.memberPart.equals("false")) {
					Intent myIntent = new Intent(Tkwkdrmqdu.this, NewActivityrmqdu.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
			}
		});
		
		Button button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(Tkwkdrmqdu.this, NewActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		
		Button button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(Tkwkdrmqdu.this, NewActivityrntjddnjs.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
			new HttpTask().execute();
		
	}
	
	// ��� �Ѹ���
	class HttpTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
        	try {        		
        		HttpPost request = new HttpPost(LoginActivity.url+"membername.php");
        		//������ ���ڵ�
        		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
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

        		dataArr = new ArrayList<MyData>();
        		
        		for(int i=0; i<jArr.length();i++) {
        			JSONObject json = jArr.getJSONObject(i);
            		
        			// pay
            		HttpPost request2 = new HttpPost(LoginActivity.url+"memberpay.php");
            		//������ ���ڵ�
            		Vector<NameValuePair> nameValue2 = new Vector<NameValuePair>();
            		nameValue2.add(new BasicNameValuePair("id", json.getString("id")));
            		nameValue2.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
            		//�� ���� - utf-8 �������
            		HttpEntity enty2 = new UrlEncodedFormEntity(nameValue2, HTTP.UTF_8);
            		request2.setEntity(enty2);
            		
            		HttpClient client2 = new DefaultHttpClient();
            		HttpResponse res2 = client2.execute(request2);

            		//�� �������� ���ޱ�
            		HttpEntity entityResponse2 = res2.getEntity();
            		InputStream im2 = entityResponse2.getContent();
            		BufferedReader reader2 = new BufferedReader(new InputStreamReader(im2, HTTP.UTF_8));
            		String total2 = "";
            		String tmp2 = "";	
            		//���ۿ��ִ°� ���� �����ֱ�
            		//readLine -> ���ϳ����� �� ������ �б�
            		while((tmp2 = reader2.readLine())!= null)
            		{
            			total2 += tmp2;
            		}
            		JSONArray jArr2 = new JSONArray(total2);
            		JSONObject json2 = jArr2.getJSONObject(0);
            		
        			dataArr.add(new MyData( json.getString("id"), json.getString("name"), json2.getString("pay") ));
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
            listView = (ListView) findViewById(R.id.listView);
            mAdapter = new MyAdapter(Tkwkdrmqdu.this, R.layout.tkwkd_rmqdu_list, dataArr);
    		listView.setAdapter(mAdapter);
        }
	}
	
	class MyData{
		String id;
		String name;
		String pay;

		MyData(String id, String name, String pay){
		    this.id = id;
		    this.name = name;
		    this.pay = pay;
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
			// 1�� ����
			if (convertView == null)  {
				convertView = Inflater.inflate(layoutId, parent, false);
			}
			Button member = (Button)convertView.findViewById(R.id.member);
			member.setText(myDataArr.get(position).name);
			TextView pay = (TextView)convertView.findViewById(R.id.pay);
			pay.setText("�ñ� : " + myDataArr.get(position).pay + " ��");
			
			member.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent myIntent = new Intent(Tkwkdrmqdu.this, TkwkdInPay.class);
					myIntent.addFlags(myIntent.FLAG_ACTIVITY_NO_HISTORY);
					myIntent.putExtra("id", myDataArr.get(pos).id);
					myIntent.putExtra("name", myDataArr.get(pos).name);
					myIntent.putExtra("pay", myDataArr.get(pos).pay);
					startActivity(myIntent);
				}
			});
			return convertView;
		}
	}



}
