package org.androidtown.albanii;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class IntroActivity extends Activity  {
	
	Handler h;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		h= new Handler(); //�����̸� �ֱ� ���� �ڵ鷯 ����
	    h.postDelayed(mrun, 3000);

	}
	    Runnable mrun = new Runnable(){
	        @Override
	        public void run(){ 
	        Intent i = new Intent(IntroActivity.this, LoginActivity.class); //����Ʈ ����(�� ��Ƽ��Ƽ, ���� ������ ��Ƽ��Ƽ)
	        startActivity(i);
	        finish();
	        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); 
	        //overridePendingTransition �̶� �Լ��� �̿��Ͽ� fade in,out ȿ������. ������ �߿�
	        }
	    };
	    
	    //��Ʈ�� �߿� �ڷΰ��⸦ ���� ��� �ڵ鷯�� ������� �ƹ��� ���� ����� �κ�
	    //�� ������ ��Ʈ�� �� �ڷΰ��⸦ ������ ��Ʈ�� �Ŀ� Ȩȭ���� ����.
	    @Override
	    public void onBackPressed(){
	        super.onBackPressed();
	        h.removeCallbacks(mrun);
	    }
	 
	}