package com.example.asocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import com.example.asocket.MainActivity.SendThread;
import com.example.asocket.ApplicationUtil;
import com.example.asocket.GroupByte;
import com.example.asocket.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MemsStatus extends Activity{
	private Button mems_set;
    private Button con_map;
    private Button download;
    private TextView memsid;
    private TextView latlon;
    private TextView hint;
    private EditText para1;
    private EditText para2;
    private Socket socket;
    private OutputStream output;
    private InputStream input;
    private Handler handler;
    
    int count=60;//拟发送的16进制数 来自http://blog.csdn.net/roserose0002/article/details/8768591
    byte[] b1 = new byte[count]; //接收用数组
    int readCount = 0; // 已经成功读取的字节的个数
	
   
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mems_set);
        memsid=(TextView)findViewById(R.id.memsid);
        latlon=(TextView)findViewById(R.id.latlon);
        hint=(TextView)findViewById(R.id.hint);
        mems_set=(Button)findViewById(R.id.mems_set);
        con_map=(Button)findViewById(R.id.con_map);
        getIntentdata();
        /*
         * 调用百度地图API
         */ 
        con_map.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        	Intent it= new Intent();
        	it.setClass(MemsStatus.this, Map.class);
        	startActivity(it);
        	MemsStatus.this.finish();
        	}
        });
        	
        
        /*
         * 用共用的socket来进行数据读取，这里还用到了句柄对消息线程进行处理
         */
        
        mems_set.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		
//                try {
//					output.write(ex);
//					output.flush();
//					Log.v("hao", Arrays.toString(CommonUnit.bytesToHexStrings(bq)));
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
        		
            	myThread();
//        		String str="111111";
//        		new MyThread2(str).start();
//            	String strshow = new String(b1);//在android中将byte[]转换为String源自：http://www.iteye.com/problems/95470
//                hint.setText(strshow);
        	}
        });

        //传到下一个页面，测试socket
        
        handler = new Handler(){  
            @Override  
            public void handleMessage(Message msg) {  
                //如果来自子线程  
                if(msg.what == 0x123){  
                    hint.append(msg.obj.toString()+"\n");  
                    //消息通知  
                   // mynoti();  
                }  
            }  
        };  
        //调用线程  
       // myThread();
       }
    
    //JAVA可以设置读写缓冲区的大小-setReceiveBufferSize(int size), setSendBufferSize(int size)。  
    public void myThread(){  
        Thread oneThread = new Thread(new Runnable(){  
             public boolean isRunning = true;  
            public void run() {    
            	ApplicationUtil appUtil =  (ApplicationUtil) MemsStatus.this.getApplication(); 
            	try{

                     appUtil.init("61.183.113.230");  
                     Socket socket = appUtil.getSocket();  
                     output = appUtil.getOut();  
                     input = appUtil.getIn();	

             } catch (IOException e1) {  
                 e1.printStackTrace();  
             } catch (Exception e1) {  
                 e1.printStackTrace();  
             }
                byte[] ex= GroupByte.register1();
   //             int ex1=MainActivity.getEQnumber(ex);
                try {  
                    //发送数据  
                    output.write(ex);  
                    output.flush();  
                    System.out.println("str ===> "+Arrays.toString(ex));  

                } catch (UnknownHostException e) {  
                    e.printStackTrace();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                } 
            	while(isRunning){  
                try {  
                  Thread.sleep(1000);  
                     //创建一个缓冲字节数  
                    int r = input.available();  
                    while(r==0){    
                         r = input.available();    
                        }  
                    byte[] b = new byte[r];  
                    
                    input.read(b);
                   
                    String content = new String(b,"utf-8");
//                    HexUtil.decode(content);//16进制字符串转字符串
                        //每当读到来自服务器的数据后，发送消息通知程序页面显示数据    
                        Message msg = new Message();  
                        msg.what = 0x123;  
                        msg.obj = content;
                        //msg.obj=HexUtil.decode(content);
                        Log.v("ho", content); 
                        //Log.v("ho", HexUtil.decode(content));
                        handler.sendMessage(msg);  
                                     
                } catch (IOException e) {  
                    e.printStackTrace();  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }   
            }  
            }  
        });  
        oneThread.start();  
    } 
    
    
    public void myThread1(){  
        Thread oneThread = new Thread(new Runnable(){  
 
            public void run() {    
            	byte[] bq = new byte[8];
                
            	bq[0] = (byte) 0x10;
            	
                bq[1] = (byte) 0x08;
            	
                bq[2] = (byte) 0x04;
            	
                bq[3] = (byte) 0x00;
            	//bq[4] = getMemsIDbyte()[0];
                bq[4] = (byte) 0x7E;
            	//bq[5] = getMemsIDbyte()[1];
                bq[5] = (byte) 0x01;
                //bq[6] = getMemsIDbyte()[2];
                bq[6] = (byte) 0x00;
                //bq[7] = getMemsIDbyte()[3];
                bq[7] = (byte) 0x00;
                byte[] ex =new Exstruct("wuhn_c0001").getBuf(); 	
            	 
                try {  
                	ApplicationUtil appUtil = (ApplicationUtil) MemsStatus.this.getApplication();  
                    socket = appUtil.getSocket();
                    input = appUtil.getIn();  
                    output= appUtil.getOut();
                	output.write(ex);
					output.flush();
             if(input!=null) {
                
                    
                while (readCount < count) {
                        readCount += input.read(b1, readCount, count - readCount);
                       }
                System.out.println("MemsStatus成功接收服务器返回数据：" + input.read());//为什么要read一下？否则梗死
                    //Log.d("成功接收服务器返回数据：" + input.read());//为什么要read一下？否则梗死
                Log.v("nnn", Arrays.toString(CommonUnit.bytesToHexStrings(b1)));
                System.out.println(Arrays.toString(CommonUnit.bytesToHexStrings(b1)));//防止负数问题，采用可以识别的16位标准读数
                    //System.out.println("已连接仪器台数为"+getEQnumber(getEQnumberbyte())+"台,"+"台网ID为"+getNetID()+","+"服务器ID为"+getServerID()+".");
                     }
          else
                    	 System.out.println("服务器没消息：" /*+ is.read()*/);                      
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    
     }
        });
        oneThread.start();
    }
    
    public class MyThread2 extends Thread implements Runnable {

		String msg;		
		
		public MyThread2(String msg) {
			super();
			this.msg = msg;
		}

		@Override
		public void run() {
        	byte[] bq = new byte[8];
            
        	bq[0] = (byte) 0x10;
        	
            bq[1] = (byte) 0x08;
        	
            bq[2] = (byte) 0x04;
        	
            bq[3] = (byte) 0x00;
        	//bq[4] = getMemsIDbyte()[0];
            bq[4] = (byte) 0x7E;
        	//bq[5] = getMemsIDbyte()[1];
            bq[5] = (byte) 0x01;
            //bq[6] = getMemsIDbyte()[2];
            bq[6] = (byte) 0x00;
            //bq[7] = getMemsIDbyte()[3];
            bq[7] = (byte) 0x00;
            byte[] ex =new Exstruct("wuhn_c0001").getBuf(); 	
        	 
            try {  
            	ApplicationUtil appUtil = (ApplicationUtil) MemsStatus.this.getApplication();  
                socket = appUtil.getSocket();
                input = appUtil.getIn();  
                output= appUtil.getOut();
            	output.write(bq);
				output.flush();
//				if(!socket.isClosed()){
//				Log.v("ddd", "socket开的");
//				}
//				else
//					Log.v("ddd", "socket关的");	
         if(input!=null) {
            
                
            while (readCount < count) {
                    readCount += input.read(b1, readCount, count - readCount);
                   }
            System.out.println("MemsStatus成功接收服务器返回数据：" + input.read());//为什么要read一下？否则梗死
                //Log.d("成功接收服务器返回数据：" + input.read());//为什么要read一下？否则梗死
            Log.v("nnn", Arrays.toString(CommonUnit.bytesToHexStrings(b1)));
            System.out.println(Arrays.toString(CommonUnit.bytesToHexStrings(b1)));//防止负数问题，采用可以识别的16位标准读数
                //System.out.println("已连接仪器台数为"+getEQnumber(getEQnumberbyte())+"台,"+"台网ID为"+getNetID()+","+"服务器ID为"+getServerID()+".");
                 }
      else
                	 System.out.println("服务器没消息：" /*+ is.read()*/);                      
    } catch (IOException e) {  
        e.printStackTrace();  
    }  

 }
		}
    /*
     * 接收Intent传来的数据,测试用，看能否有效
     * http://blog.chinaunix.net/uid-24406894-id-2608856.html
     * Author: Ding Wei, Revised time 2016/10/31 Success
     */
    public void getIntentdata(){
    	Intent intent = getIntent(); 
    	DataParcelabel cc = intent.getParcelableExtra("PERSON_INFO"); 
    	String sid=new String(cc.getId());
    	//int sage = Integer.valueOf(cc.getAge());//传递int型整数形式
    	//String sname=new String(cc.getName());
    	int rec_lat=cc.getLat();
    	int rec_lon=cc.getLon();
    	String sheight=new String(cc.getHeight());
    	String ssample=new String(cc.getSample());
    	memsid.setText("本仪器ID是"+sid);
    	latlon.setText("仪器经度是"+rec_lon+"仪器纬度是"+rec_lat+"\n"+"海拔高度为"+sheight+"采样率为"+ssample);
//    	setTextView(R.id.memsid, cc.getId()); 
//    	setTextView(R.id.name, cc.getName()); 
//    	setTextView(R.id.sex, cc.getSex()); 
//    	setTextView(R.id.age, String.valueOf(cc.getAge()));
    }
}
