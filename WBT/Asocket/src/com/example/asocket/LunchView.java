package com.example.asocket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LunchView extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        Handler x = new Handler();
        x.postDelayed(new lunchhandler(), 2000);//延时2秒
        
    }
    
    class lunchhandler implements Runnable{

        public void run() {
            startActivity(new Intent(getApplication(),MainActivity.class));
            LunchView.this.finish();
        }
        
    }
}
