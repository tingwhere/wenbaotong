package com.example.asocket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


 

//@SuppressLint("ShowToast")
public class MainActivity extends Activity {
        private EditText ed_tex;
        private Button btn_sent;
        private Button mems_check;
        private Button btn_connect;
        private TextView display;
        private TextView mems_display;
        private int SERVER_POINT = 2001;
        //private String SERVER_ADDRESS = "61.183.113.230";
        private String SERVER_ADDRESS = "";
        private Socket socket;
        private OutputStream output;
        private InputStream input;
        private Context context;
        int count=60;//拟发送的16进制数 来自http://blog.csdn.net/roserose0002/article/details/8768591
        int count1=50;//查询用计数器
        byte[] b1 = new byte[count]; //接收用数组
        byte[] breq = new byte[count1];
        int readCount = 0; // 已经成功读取的字节的个数
        int readCountq=0;//查询已成功读取的字节个数
        int socket_flag= 0;//socket连接标志位
        
        /*
         * 初始化ListView用
         * 来源：http://www.jb51.net/article/72085.htm
         * 
         */
        private ListView list = null;

       
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                
                    init();
                    //initList();
        }
 
        private void init() {
                // TODO Auto-generated method stub
        	display = (TextView)findViewById(R.id.display);   
        	ed_tex = (EditText) findViewById(R.id.ed_text);
        	mems_display=(TextView)findViewById(R.id.mems_display); 
                btn_sent = (Button) findViewById(R.id.btn_sent);
                mems_check = (Button) findViewById(R.id.mems_check);
                btn_connect=(Button) findViewById(R.id.btn_connect);
                btn_sent.setEnabled(false);
                mems_check.setEnabled(false);
                
                btn_connect.setOnClickListener(new OnClickListener() {
                	 
                    @Override
                    public void onClick(View v) {
        				String str = ed_tex.getText().
                		toString().trim();
				        SERVER_ADDRESS = str;
                    	System.out.println("准备发送....");
                        Toast.makeText(getApplicationContext(), "准备连接服务器", Toast.LENGTH_SHORT).show();
                        
                        new ConnectThread1(str).start();
                        Toast.makeText(getApplicationContext(), "服务器已连接", Toast.LENGTH_SHORT).show();
                      //连接按钮不能再用
                        btn_connect.setEnabled(false);
                         //发送按钮使能
                        btn_sent.setEnabled(true);
                        mems_check.setEnabled(true);
                        
                    }
                });
                
               
                btn_sent.setOnClickListener(new OnClickListener() {
 
                        @Override
                        public void onClick(View v) {
                                // TODO Auto-generated method stub
                                String str = ed_tex.getText().
                                		toString().trim();
                               // byte[] str= ed_tex.getText().toString().getBytes();//截取输入字符串中的数值变成二进制
                                if (v != null && !str.equals("")) {
                                	    SERVER_ADDRESS = str;
                                	    //System.out.println("准备发送....");
                                            //Toast.makeText(getApplicationContext(), "准备发送注册信息", Toast.LENGTH_SHORT).show();
                                        //new SendThred(str).start();
                                        for (int i=0;i<2;i++){//不知道为什么，需要注册两次才出数据，那就注册两次吧
                                        	new SendThread(str).start();
                                        
                                             //Toast.makeText(getApplicationContext(), "发送注册信息结束", Toast.LENGTH_SHORT).show();
                                        
                                        //display.setText(Arrays.toString(b1));
                                        /*
                                         * 因为java数组和android不同，范围为-128-127，少的那一位用来作为符号位，因此，所有数组必须移位后处理
                                         * 以下为调试用方法
                                         * Author: Wei Ding, Revised time: 2016/11/01
                                         */
                                        String sresult=CommonUnit.bytes2HexString(getMemslatByte());
                                        System.out.println(sresult);
                                        System.out.print(Integer.parseInt(sresult,16));//进制测试用Integer.parseInt(String s, int radix)方法实现，radix是进制，可以是2（二进制），10（十进制），16（十六进制）等
                                        /*调试数组显示结束*/
                                        
                                        String strshow = new String(b1);//在android中将byte[]转换为String源自：http://www.iteye.com/problems/95470
                                        display.setText(strshow);
                                        String strshowEQnumber = new String("已连接仪器台数为"+getEQnumber(getEQnumberbyte())+"台,"+"台网ID为"+getNetID()+"，"+
                                        "服务器ID为"+getServerID()+"经度为"+Integer.parseInt(CommonUnit.bytes2HexString(getMemslonByte()),16)+"纬度为"+Integer.parseInt(CommonUnit.bytes2HexString(getMemslatByte()),16)+".");
                                        mems_display.setText(strshowEQnumber);
                                        }
                                      //  initList();
                                }
                        }
                });
                
                mems_check.setOnClickListener(new OnClickListener() {
                	 
                    @Override
                    public void onClick(View v) {
                            // TODO Auto-generated method stub
                            String str = ed_tex.getText().toString().trim();
      //                      if (v != null && !str.equals("")) {
                            	    SERVER_ADDRESS = str;
                            	   // System.out.println("准备发送....");
                                    Toast.makeText(getApplicationContext(), "准备发送查询信息", Toast.LENGTH_SHORT).show();
                                    new CheckThread(str).start();
                                    Toast.makeText(getApplicationContext(), "发送查询信息结束", Toast.LENGTH_SHORT).show();
                                    
                                    String strshow = new String(breq);//在android中将byte[]转换为String源自：http://www.iteye.com/problems/95470
                                    display.setText(strshow);
        //                            String strshowEQnumber = new String("已连接仪器台数为"+getEQnumber(getEQnumberbyte())+"台,"+"台网ID为"+getNetID()+"，"+"服务器ID为"+getMEMSID()+".");
        //                            mems_display.setText(strshowEQnumber);
   //                         }
                                    initList();
                    }
            });
        }
 
        class SendThred extends Thread implements Runnable {
                String Str_message;
                //byte[] Str_message;
 
                public SendThred(String str) {
                  //public SendThred(byte[] str){
                        this.Str_message = str;//自定义线程发送输入字节
                }
 
                public void run() {
                        try {
                        	byte[] b = new byte[1024];

                            b[0] = (byte) 0x00;

                            b[1] = (byte) 0x08;

                            b[2] = (byte) 0x00;

                            b[3] = (byte) 0x0b;

                            b[4] = (byte) 0x77;

                            b[5] = (byte) 0x75;

                            b[6] = (byte) 0x68;

                            b[7] = (byte) 0x6e;

                            b[8] = (byte) 0x5f;

                            b[9] = (byte) 0x63;

                            b[10] = (byte) 0x30;

                            b[11] = (byte) 0x30;

                            b[12] = (byte) 0x30;

                            b[13] = (byte) 0x31;

                            b[14] = (byte) 0x00;
/*
 * 测试结构体发送数据  失败
 */
                            //ExREG exreg= new ExREG((byte)0x08, (byte)0x08,(byte)0x00, (byte)0x0b,"wuhn_C0003",(byte)0x00); 
                            //for(int i=0;i<100;i++)
                            	//exreg = new ExREG(0x08, 0x0b, "wuhn_C0003", 0);
//                            byte[] bex = new byte[1024];
//                            System.arraycopy(temp, 0, buf, 0, temp.length);
//                            
                                System.out.println("正在连接服务器...");
                                Socket s = new Socket(SERVER_ADDRESS, SERVER_POINT);
                                socket_flag=1;
                                System.out.println("成功连接服务器...");
                                //System.out.println("准备发送数据...");
                                Log.v("hao", "准备发送数据");
                                //DataOutputStream out = new DataOutputStream(s.getOutputStream());
                               // out.writeUTF(Str_message);
                               //可用程序，测试通过
                                OutputStream out = s.getOutputStream();//用OutputStream试一下 http://xue08161981.blog.163.com/blog/static/3249967720101258451390/
                               // out.write(b);
                                //采用结构体发送数据，测试中,成功
                               byte[] ex =new Exstruct("wuhn_c0001").getBuf();
                                out.write(ex);
                                System.out.println(Arrays.toString(ex));//就按16进制字符输出全部b1值
                                //out.write(exreg);
                                out.flush();//强制刷缓冲区，取数
                               // out.write(0x00);直接写？多位用数组
                               // out.write(Str_message);
                                System.out.println("成功发送数据...");
                                //System.out.println("准备接收服务器返回结果...");
                                Log.v("hao", "准备接收服务器返回结果...");
                                //Toast toast = Toast.makeText(getApplicationContext(), "准备接收服务器返回结果..",Toast.LENGTH_SHORT);
                                //Toast.makeText(MainActivity.this, "正在连接服务器", Toast.LENGTH_SHORT).show();
                               // DataInputStream is = new DataInputStream(s.getInputStream()); 
                                InputStream is = s.getInputStream();
                               
								//InputStream in = new ByteArrayInputStream( imgByte );//读文件相关的数据
                               // System.out.println("成功接收服务器返回数据：" + is.readUTF());
                               
                               /*
                                * 用判断固定长度读出的方法来进行输入流读取
                                * 20161018，dingwei 调试成功
                                * 
                                */
                               if(is!=null) {
                               
                                while (readCount < count) {
                                    readCount += is.read(b1, readCount, count - readCount);
                                   }
                                //code ending*/
                                
                                
                                /*
                                 * 用判断输入流总长度方法读取
                                 * 20161021，dingwei 没调通
                                 */
//                                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//                                byte[] buffer = new byte[1024];
//                                int len = 0;  
//                                while( (len=is.read(buffer)) != -1 ){  
//                                    outStream.write(buffer, 0, len);  
//                                }  

                                System.out.println("成功接收服务器返回数据：" + is.read());//为什么要read一下？否则梗死
          //                      System.out.println(Arrays.toString(getEQnumber ()));
         //                       System.out.println(getString(getNetID ()));
                                System.out.println(Arrays.toString(CommonUnit.bytesToHexStrings(b1)));//防止负数问题，采用可以识别的16位标准读数
                                //System.out.println(Arrays.toString(b1));//就按16进制字符输出全部b1值
                                System.out.println("已连接仪器台数为"+getEQnumber(getEQnumberbyte())+"台,"+"台网ID为"+getNetID()+","+"服务器ID为"+getServerID()+".");
                                //System.out.print(b1[7]);
                                //System.out.println(b1);//就按16进制输出
//                                System.out.println(buffer);
                                //System.out.println((byte[])b1);
                                /*Byte2Char b2c = new Byte2Char();
                                 * b2c.getChars(b1);
                                 *  System.out.println(b2c);
                                 */
                                                             
                                }
                                else
                                	 System.out.println("服务器没消息：" /*+ is.read()*/);	
                                //Toast.makeText(MainActivity.this, "成功接收服务器返回数据："/*+ is.readUTF()*/, Toast.LENGTH_SHORT).show();
                                 
                                is.close();
                                out.close();
  //                              if(socket_flag==0){
                                s.close();
  //                              }
                        } catch (UnknownHostException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                }
        }
 
        
//        class QueryThred extends Thread implements Runnable {
//            String Str_message;
//            //byte[] Str_message;
//
//            public QueryThred(String str) {
//                         this.Str_message = str;//自定义线程发送输入字节
//            }
//
//            public void run() {
//                    try {
//                    	byte[] bq = new byte[1024];
//
//                        bq[0] = (byte) 0x10;
//
//                        bq[1] = (byte) 0x08;
//
//                        bq[2] = (byte) 0x00;
//
//                        bq[3] = (byte) 0x04;
//
//                        bq[4] = (byte) 0x30;
//
//                        bq[5] = (byte) 0x01;
//
//                        bq[6] = (byte) 0x00;
//
//                        bq[7] = (byte) 0x00;
//
//                       //                        
////                            if(socket_flag==0){
//                            System.out.println("正在查询服务器...");
//                            Socket s = new Socket(SERVER_ADDRESS, SERVER_POINT);
//                            
//                            System.out.println("成功查询服务器...");
//                            System.out.println("准备发送查询请求数据...");
//  //                          }
//  //                          else{
//                           //可用程序，测试通过
//                            OutputStream out = s.getOutputStream();//用OutputStream试一下 http://xue08161981.blog.163.com/blog/static/3249967720101258451390/
//                            out.write(bq);
//    //                        }
//                            //采用结构体发送数据，测试中,成功
////                           byte[] ex =new Exstruct("wuhn_c0001").getBuf();
////                            out.write(ex);
////                            System.out.println(Arrays.toString(ex));//就按16进制字符输出全部b1值
//                            //out.write(exreg);
//                            out.flush();//强制刷缓冲区，取数
//                            System.out.println("成功发送查询请求数据...");
//                            System.out.println("准备接收服务器返回结果...");
//                            InputStream is = s.getInputStream();
//                           
//                           /*
//                            * 用判断固定长度读出的方法来进行输入流读取
//                            * 20161018，dingwei 调试成功
//                            * 
//                            */
//                           if(is!=null) {
//                           
//                            while (readCountq < count1) {
//                                readCountq += is.read(b1, readCountq, count1 - readCountq);
//                               }
//                            //code ending*/
//                            
//                            
//                             System.out.println("成功接收服务器返回数据：" + is.read());//为什么要read一下？否则梗死
//                            System.out.println(Arrays.toString(CommonUnit.bytesToHexStrings(breq)));//防止负数问题，采用可以识别的16位标准读数
//                            //System.out.println(Arrays.toString(b1));//就按16进制字符输出全部b1值
// //                           System.out.println("已连接仪器台数为"+getEQnumber(getEQnumberbyte())+"台,"+"台网ID为"+getNetID()+","+"服务器ID为"+getMEMSID()+".");
//
//                                                         
//                            }
//                            else
//                            	 System.out.println("服务器没消息：" /*+ is.read()*/);	
//                             
//                            is.close();
//                            out.close();
//                            s.close();
//                    } catch (UnknownHostException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                    } catch (IOException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                    }
//            }
//    }
  

        
        /* 连接socket线程
         * 不知道为什么system.println返回会很慢（0:02:40），但是实际软件上的显示倒还没错，是因为socket长连接的问题吗？ 待解决
         */
    	public class ConnectThread extends Thread implements Runnable {
    		String Str_message;
            //byte[] Str_message;

            public ConnectThread(String str) {
              //public SendThred(byte[] str){
                    this.Str_message = str;//自定义线程发送输入字节
            }
    		@Override
    		public void run() {
    			// TODO Auto-generated method stub
    			//Message msg = Message.obtain();
    			try {
//    				String str = ed_tex.getText().
//                    		toString().trim();
//    				SERVER_ADDRESS = str;
    				if (null == socket || socket.isClosed()) {
    					//socket = new Socket();
    					//socket.connect(new InetSocketAddress(host_ip,host_port),5000);
    					//socket.connect(new InetSocketAddress(SERVER_ADDRESS,2001),5000);//之前的连接方式
    					Socket socket = new Socket(SERVER_ADDRESS, SERVER_POINT);
    					//output = new PrintStream(socket.getOutputStream(), true,"utf-8");
    					output = socket.getOutputStream();
//    					output.flush();
    					input =socket.getInputStream();
    				}
//    				msg.what = 0x123;
//    				handler.sendMessage(msg);
    				//closeSocket();
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	
    	/* 连接socket线程测试，用Util
         * 
         */
    	public class ConnectThread1 extends Thread implements Runnable {
    		String Str_message;
            //byte[] Str_message;

            public ConnectThread1(String str) {
              //public SendThred(byte[] str){
                    this.Str_message = str;//自定义线程发送输入字节
            }
    		@Override
    		public void run() {
    			// TODO Auto-generated method stub
    			//Message msg = Message.obtain();
    			
    				ApplicationUtil appUtil =  (ApplicationUtil) MainActivity.this.getApplication();  
                    try {  
                        appUtil.init(SERVER_ADDRESS);  
                        Socket socket = appUtil.getSocket();  
                        output = appUtil.getOut();  
                        input = appUtil.getIn();  
                     
                    } catch (IOException e1) {  
                        e1.printStackTrace();  
                    } catch (Exception e1) {  
                        e1.printStackTrace();  
                    
    				}
    			
    		}
    	}
    	
    	/*发送信息线程*/
    	public class SendThread extends Thread implements Runnable {

    		String msg;		
    		
    		public SendThread(String msg) {
    			super();
    			this.msg = msg;
    		}

    		@Override
    		public void run() {
    			// TODO Auto-generated method stub
    			try {
    				
    				byte[] ex =new Exstruct("wuhn_c0001").getBuf();
                    output.write(ex);
                    System.out.println(Arrays.toString(ex));//就按16进制字符输出全部b1值
                    output.flush();//强制刷缓冲区，取数
                    System.out.println("成功发送注册数据...");
                    System.out.println("准备接收服务器返回结果...");
  //                  InputStream is = socket.getInputStream();
                   				
                   if(input!=null) {
                   
                    while (readCount < count) {
                        readCount += input.read(b1, readCount, count - readCount);
                       }
                    System.out.println("成功接收服务器返回数据：" + input.read());//为什么要read一下？否则梗死
                    //Log.d("成功接收服务器返回数据：" + input.read());//为什么要read一下？否则梗死
                    System.out.println(Arrays.toString(CommonUnit.bytesToHexStrings(b1)));//防止负数问题，采用可以识别的16位标准读数
                    System.out.println("已连接仪器台数为"+getEQnumber(getEQnumberbyte())+"台,"+"台网ID为"+getNetID()+","+"服务器ID为"+getServerID()+".");
                     }
                    else
                    	 System.out.println("服务器没消息：" /*+ is.read()*/);	
      			 } catch (UnknownHostException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
             } catch (IOException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
             }
     }
    	}
    		/*查询信息线程*/
        	public class CheckThread extends Thread implements Runnable {

        		String msg;		
        		
        		public CheckThread(String msg) {
        			super();
        			this.msg = msg;
        		}

        		@Override
        		public void run() {
        			// TODO Auto-generated method stub
        			try {
        				byte[] bq = new byte[8];
        			    
        				bq[0] = (byte) 0x10;
        				
        			    bq[1] = (byte) 0x08;
        				
        		        bq[2] = (byte) 0x04;
        				
        			    bq[3] = (byte) 0x00;
        				bq[4] = getMemsIDbyte()[0];
        			    //bq[4] = (byte) 0x3d;
        				bq[5] = getMemsIDbyte()[1];
        			    //bq[5] = (byte) 0x01;
        			    bq[6] = getMemsIDbyte()[2];
        			    //bq[6] = (byte) 0x00;
        			    bq[7] = getMemsIDbyte()[3];
        			    //bq[7] = (byte) 0x00;
        				
                        output.write(bq);
                        System.out.println(Arrays.toString(bq));//就按16进制字符输出全部b1值
       //                 output.flush();//强制刷缓冲区，取数
                        System.out.println("成功发送单台查询请求...");
                        System.out.println("准备接收仪器返回结果...");
      //                  InputStream is = socket.getInputStream();
                       				
                       if(input!=null) {
                       
                        while (readCountq < count1) {
                            readCountq += input.read(breq, readCountq, count1 - readCountq);
                           }
                        System.out.println("成功接收服务器返回数据：" + input.read());//为什么要read一下？否则梗死
                        //Log.d("成功接收服务器返回数据：" + input.read());//为什么要read一下？否则梗死
                        System.out.println(Arrays.toString(CommonUnit.bytesToHexStrings(breq)));//防止负数问题，采用可以识别的16位标准读数
  //                      System.out.println("已连接仪器台数为"+getEQnumber(getEQnumberbyte())+"台,"+"台网ID为"+getNetID()+","+"服务器ID为"+getMEMSID()+".");
                         }
                        else
                        	 System.out.println("服务器没消息：" /*+ is.read()*/);	
          			 } catch (UnknownHostException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                 } catch (IOException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                 }
         }
}
    	
    	/*
    	 * 显示吐司
    	 */
    	private void toastText(String message) {
    		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    	}
    	
    	/*
    	 * 关闭socket
    	 */
    	
    	private void closeSocket() {
    		try {
    			input.close();
    			output.close();
    			socket.close();
    		} catch (IOException e) {
    			//handleException(e, "close exception: ");
    			e.printStackTrace();
    					
    		}
    	}
    	
    	public void handleException(Exception e, String prefix) {
    		e.printStackTrace();
    		toastText(prefix + e.toString());
    	}
        /**
         * http://blog.csdn.net/menghnhhuan/article/details/6956016
         * 将byte数组转换为int数据
         * @param b 字节数组
         * @return 生成的int数据
         */
        public static int getEQnumber(byte[] b) {//这里参数写死，如此一来可以不用再找个参数,暂时保留
        	//int n= 0;
        	     		  return (((int)b[0]) << 24) + (((int)b[1]) << 16) + (((int)b[2]) << 8) + b[3];
        		 }
        
        public static int getEQnumberHtoL(byte[] b) {//高位和低位互换
        	//int n= 0;
        	     		  return (((int)b[3]) << 24) + (((int)b[2]) << 16) + (((int)b[1]) << 8) + b[0];
        		 }
        /**
         * 记录仪器个数
         * 将byte数组
         */ 
        public byte[] getEQnumberbyte() {
        	byte [] number = new byte[4];
        	number [0]= b1 [7];
        	number [1]= b1 [6];
        	number [2]= b1 [5];
        	number [3]= b1 [4];
        	 return number;
        }
 
        /**
         * 记录台网ID
         * 将byte数组转换为String数据
         */ 
        public String getNetID(){
        	byte [] NetID = new byte [4];//长度要写死，否则一堆多余数组
        	NetID[0] = b1[12];
        	NetID[1] = b1[13];
        	NetID[2] = b1[14];
        	NetID[3] = b1[15];
        	String str = new String(NetID);
        	return str;
        }
        
        /**
         * 记录服务器ID
         * 将byte数组转换为String数据
         */ 
        public String getServerID(){
        	byte [] MemsID = new byte [5];
        	MemsID[0] = b1[17];
        	MemsID[1] = b1[18];
        	MemsID[2] = b1[19];
        	MemsID[3] = b1[20];
        	MemsID[4]=  b1[21];
        	String str = new String(MemsID);
        	return str;
        }
        
        /**
         * 记录客户端注册ID
         * 将byte数组
         */ 
        public byte[] getMemsIDbyte(){
        	byte [] MemsID = new byte [4];
        	MemsID[0] = b1[8];
        	MemsID[1] = b1[9];
        	MemsID[2] = b1[10];
        	MemsID[3] = b1[11];
        	return MemsID;
        }
        
        /**
         * 记录仪器经度，并转换为int
         * 将byte数组
         */     
        public int getMemslon(){
        	byte [] temp = new byte[4];
        	temp[0] = b1[32];
        	temp[1] = b1[33];
        	temp[2] = b1[34];
        	temp[3] = b1[35];
        	int templon;
        	templon=getEQnumberHtoL(temp);
        	return templon;
            }
        /**
         * 记录仪器经度，并转换为Byte
         * 将byte数组
         */     
        public byte[] getMemslonByte(){
        	byte [] temp = new byte[4];
        	temp[0] = b1[35];
        	temp[1] = b1[34];
        	temp[2] = b1[33];
        	temp[3] = b1[32];
        	return temp;
            }
        /**
         * 记录仪器纬度，并转换为int
         * 将byte数组
         */     
        public int getMemslat(){
        	byte [] temp = new byte[4];//暂时倒过来
        	temp[0] = b1[39];
        	temp[1] = b1[38];
        	temp[2] = b1[37];
        	temp[3] = b1[36];
        	int templat;
        	templat=getEQnumberHtoL(temp);
        	return templat;
         }
        
        /**
         * 记录仪器经度，并转换为Byte
         * 将byte数组
         */     
        public byte[] getMemslatByte(){
        	byte [] temp = new byte[4];
        	temp[0] = b1[39];
        	temp[1] = b1[38];
        	temp[2] = b1[37];
        	temp[3] = b1[36];
        	return temp;
            }
        /**
         * 记录仪器高程，并转换为int
         * 将byte数组
         */     
        public int getMemsHeight(){
        	byte [] temp = new byte[4];
        	temp[0] = b1[40];
        	temp[1] = b1[41];
        	temp[2] = b1[42];
        	temp[3] = b1[43];
        	int tempHeight;
        	tempHeight=getEQnumberHtoL(temp);
        	return tempHeight;
         }
        
        /**
         * 记录仪器采样率，并转换为int
         * 将byte数组
         */     
        public int getMemsSample(){
        	byte [] temp = new byte[4];
        	temp[0] = b1[44];
        	temp[1] = b1[45];
        	temp[2] = b1[46];
        	temp[3] = b1[47];
        	int tempSample;
        	tempSample=getEQnumberHtoL(temp);
        	return tempSample;
         }
        
        /*
         * 初始化ListVies用函数
         */
//        public void  initListView(){
//        	 ListView mListView = null;
//             ArrayList<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();;
//             mListView = getListView();        	
//             int lengh = mListTitle.length;
//        	for(int i =0; i < lengh; i++) {
//        	    Map<String,Object> item = new HashMap<String,Object>();
//        	    item.put("image", R.drawable.imagetri);
//        	    item.put("title", mListTitle[i]);
//        	    item.put("text", mListStr[i]);
//        	    mData.add(item); 
//        	}
//        	SimpleAdapter adapter = new SimpleAdapter(this,mData,R.layout.iconlist,
//        		new String[]{"image","title","text"},new int[]{R.id.image,R.id.title,R.id.text});
//                mListView.setListAdapter(adapter);
//        	mListView.setOnItemClickListener(new OnItemClickListener() {
//        	    @Override
//        	    public void onItemClick(AdapterView<?> adapterView, View view, int position,
//        		    long id) {
//        		Toast.makeText(MainActivity.this,"您选择了标题：" + mListTitle[position] + "内容："+mListStr[position], Toast.LENGTH_LONG).show();
//        	    }
//        	});
//        }
        /*
         * 初始化ListVies用函数,先建立一个adapter，然后将布局设定，，再然后显示布局
         * 来源：http://www.jb51.net/article/72085.htm
         * Author:Wei Ding, Revised time: 2016/10/29
         */
        public void initList() {
        	list = (ListView) findViewById(R.id.mylist);
            //组织数据源
            int m=getEQnumber(getEQnumberbyte());//取仪器总个数，用来生成list
            int mmems=getEQnumberHtoL(getMemsIDbyte());
            String smmems=String.valueOf(mmems); //将int转换成字符串
        	List<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
            for(int i=0;i<m;i++) {
            	String s = String.valueOf(i+1);
            	HashMap<String, String> map = new HashMap<String, String>();
              map.put("itemTitle", "第"+s+"台仪器");
              map.put("itemText", "仪器ID为"+smmems);
              mylist.add(map);
            }
            //配置适配器
            SimpleAdapter adapter = new SimpleAdapter(this, 
             mylist,//数据源 
             R.layout.iconlist,//显示布局
             new String[] {"itemTitle", "itemText"}, //数据源的属性字段
             new int[] {R.id.itemTitle,R.id.itemText}); //布局里的控件id
            //添加并且显示
            list.setAdapter(adapter);
            //添加点击监听事件
            list.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                        long arg3) {
//                        //点击后在标题上显示点击了第几行    
//                	int argtemp=arg2+1;
////                	setTitle("你点击了第"+argtemp+"行");
//                	 Toast.makeText(getApplicationContext(), "你点击了第"+argtemp+"台仪器", Toast.LENGTH_SHORT).show();
                	/*
                	 * 增加输入Parcelabel的方法，包括putExtra
                	 * Author: Ding Wei, Revised time 2016/10/31 Success
                	 */
                	//CustomeParcelable cc = new CustomeParcelable(); 
                	DataParcelabel cc = new DataParcelabel();
                	String slat=CommonUnit.bytes2HexString(getMemslatByte());
                	String slon=CommonUnit.bytes2HexString(getMemslonByte());
//                	String sid=CommonUnit.bytes2HexString(getMemsIDbyte());
                	String sid=String.valueOf(getEQnumberHtoL(getMemsIDbyte()));
                	int m=getEQnumber(getEQnumberbyte());
                    //System.out.print(Integer.parseInt(sresult,16));
                	//int sage = Integer.valueOf(getMemslon());
                	cc.setTotal(m);
                	cc.setId(sid);
                	cc.setLon(Integer.parseInt(slon,16)); 
                	cc.setLat(Integer.parseInt(slat,16));
                	cc.setHeight(String.valueOf(getMemsHeight()));
                	cc.setSample(String.valueOf(getMemsSample()));
                	//cc.setName();
                	//cc.setSex(String.valueOf(getEQnumberHtoL(getMemslonByte())));
                	//cc.setSex(String.valueOf(getMemsSample()));
                	//源自http://lvlayster.iteye.com/blog/1333343
                	Intent intent = new Intent(getApplicationContext(), MemsStatus.class);
                	intent.putExtra("PERSON_INFO", cc);
                    MainActivity.this.startActivity(intent); 
                }
            });
        }

  
       
 
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.main, menu);
                return true;
        }
 
}
