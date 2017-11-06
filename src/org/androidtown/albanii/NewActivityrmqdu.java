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
import android.widget.Toast;

public class NewActivityrmqdu extends Activity {

	ListView listView;
	ArrayList<MyData> dataArr;
	MyAdapter mAdapter;
	String totaltext = "�� 0�ð� 0��";
	String won;
	int monthlypay = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity_rmqdu);
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText(AlbaMenuActivity.groupname);
		
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivityrmqdu.this, MainActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(LoginActivity.memberPart.equals("true")) {
					Intent myIntent = new Intent(NewActivityrmqdu.this, Tkwkdcnfxhlrms.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
				else if(LoginActivity.memberPart.equals("false")) {
					Intent myIntent = new Intent(NewActivityrmqdu.this, NewActivitycnfxhlrms.class);
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
					Intent myIntent = new Intent(NewActivityrmqdu.this, Tkwkdrmqdu.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
				else if(LoginActivity.memberPart.equals("false")) {
					Intent myIntent = new Intent(NewActivityrmqdu.this, NewActivityrmqdu.class);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(myIntent);
					finish();
				}
			}
		});
		
		Button button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivityrmqdu.this, NewActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		
		Button button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(NewActivityrmqdu.this, NewActivityrntjddnjs.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				finish();
			}
		});
		new HttpTask().execute();
		
	}
	
	//�ð�,�޿�,�ñ޻Ѹ���
	class HttpTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
        	try {        		
        		//�ñ޻Ѹ���
        		HttpPost request = new HttpPost(LoginActivity.url+"timepay.php");
        		//������ ���ڵ�
        		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
        		nameValue.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
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
        			total += tmp;
        		}
        		won = total;
        		im.close();
        		
        		//�ð� �Ѹ���
        		HttpPost request2 = new HttpPost(LoginActivity.url+"time.php");
        		//������ ���ڵ�
        		Vector<NameValuePair> nameValue2 = new Vector<NameValuePair>();
        		nameValue2.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
        		nameValue2.add(new BasicNameValuePair("id", LoginActivity.memberId));

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
        		double totaltime = 0;
        		
        		for(int i=0; i<jArr2.length();i++) {
        			JSONObject json2 = jArr2.getJSONObject(i);
        			if(!json2.getString("time").equals("0")) {
        				totaltime += Double.parseDouble(json2.getString("time"));
        			}
        		}
       			im2.close();
        		
       			//�޿� �Ѹ���
       			totaltext = String.format("�� %02d�ð� %02d��", (int)totaltime, (int)((totaltime-(int)totaltime)*60));
       			monthlypay = (int)(totaltime * Integer.parseInt(won));
        		
        		//�����޿�
        		HttpPost request3 = new HttpPost(LoginActivity.url+"lastwage.php");
        		//������ ���ڵ�
        		Vector<NameValuePair> nameValue3 = new Vector<NameValuePair>();
        		nameValue3.add(new BasicNameValuePair("groupname", AlbaMenuActivity.groupname));
        		nameValue3.add(new BasicNameValuePair("id", LoginActivity.memberId));
        		//�� ���� - utf-8 �������
        		HttpEntity enty3 = new UrlEncodedFormEntity(nameValue3, HTTP.UTF_8);
        		request3.setEntity(enty3);
        		
        		HttpClient client3 = new DefaultHttpClient();
        		HttpResponse res3 = client3.execute(request3);

        		//�� �������� ���ޱ�
        		HttpEntity entityResponse3 = res3.getEntity();
        		InputStream im3 = entityResponse3.getContent();
        		BufferedReader reader3 = new BufferedReader(new InputStreamReader(im3, HTTP.UTF_8));
        		String total3 = "";
        		String tmp3 = "";	
        		//���ۿ��ִ°� ���� �����ֱ�
        		//readLine -> ���ϳ����� �� ������ �б�
        		while((tmp3 = reader3.readLine())!= null)
        		{
        			total3 += tmp3;
        		}
        		JSONArray jArr3 = new JSONArray(total3);
        		
        		dataArr = new ArrayList<MyData>();
        		
        		for(int i=0; i<jArr3.length();i++) {
        			JSONObject json3 = jArr3.getJSONObject(i);
        			dataArr.add(new MyData( json3.getString("date"), json3.getString("time"), json3.getString("mpay")));
        		}
        		im3.close();

        		//���â�ѷ��ֱ� - ui ����� ����
        		//result.setText(total);
        		return totaltext;
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
            TextView timepay = (TextView) findViewById(R.id.timepay);
            timepay.setText(won+"��");
    		TextView time = (TextView) findViewById(R.id.time);
    		time.setText(totaltext);
    		TextView wage = (TextView) findViewById(R.id.wage);
    		wage.setText("�� "+String.valueOf(monthlypay)+"��");

            listView = (ListView) findViewById(R.id.listView);
            mAdapter = new MyAdapter(NewActivityrmqdu.this, R.layout.list_lastwage, dataArr);
    		listView.setAdapter(mAdapter);
        }
	}
	
	class MyData{
		String date;
		String time;
		String mpay;

		MyData(String date, String time, String mpay){
		    this.date = date;
		    this.time = time;
		    this.mpay = mpay;
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
			return String.valueOf(myDataArr.get(position).date);
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
			TextView ldate = (TextView)convertView.findViewById(R.id.ldate);
			ldate.setText(myDataArr.get(position).date);
			TextView ltime = (TextView)convertView.findViewById(R.id.ltime);
			double dtime = Double.parseDouble(myDataArr.get(position).time);
			ltime.setText(String.format("�� %02d�ð� %02d��", (int)dtime, (int)((dtime-(int)dtime)*60)));
			TextView lwage = (TextView)convertView.findViewById(R.id.lwage);
			lwage.setText(myDataArr.get(position).mpay+"��");
		
			return convertView;
		}
	}



}
