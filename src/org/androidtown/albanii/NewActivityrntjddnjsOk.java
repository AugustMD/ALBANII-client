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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewActivityrntjddnjsOk extends Activity {
	
	String name;
	String phone;
	String type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
		layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		layoutParams.dimAmount= 0.7f;
		getWindow().setAttributes(layoutParams);
		
		setContentView(R.layout.list_rntjddnjs_ok);

		Intent intent = getIntent();
		name = intent.getExtras().getString("name");
		phone = intent.getExtras().getString("phone");
		type = intent.getExtras().getString("type");
		
		TextView text = (TextView)findViewById(R.id.text);
		
		Button cancel = (Button)findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		
		Button ok = (Button)findViewById(R.id.ok);
		
		if(type.equals("tel")) {
			text.setText(name+"님에게 전화하시겠습니까? ("+phone+")");
			ok.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
				Intent myIntent = new Intent(Intent.ACTION_DIAL);
				myIntent.setData( Uri.parse(type+":"+phone) );
				startActivity(myIntent);
				finish();
				}
			});
		}
		else if(type.equals("sms")) {
			text.setText(name+"님에게 문자하시겠습니까? ("+phone+")");
			ok.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
				Intent myIntent = new Intent(Intent.ACTION_VIEW);
				myIntent.setData( Uri.parse(type+":"+phone) );
				startActivity(myIntent);
				finish();
				}
			});
		}
		
		
	}
} 
