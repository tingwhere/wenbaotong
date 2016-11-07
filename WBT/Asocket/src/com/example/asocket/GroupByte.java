/*
 * 试图用结构化的信息来发送想要的字节
 * http://bbs.csdn.net/topics/390906438
 */
package com.example.asocket;

import java.nio.ByteBuffer;

public class GroupByte {

	public byte[] register(){
	
	ByteBuffer buffer = ByteBuffer.allocate(15); //初始化30个长度的空间
    //添加一个byte,,后面都一样，，，，，
   
    buffer.put((byte)0x00);
    buffer.put((byte)0x08);
    buffer.put((byte)0x0b);
    buffer.put((byte)0x77);
    buffer.put((byte)0x75);
    buffer.put((byte)0x68);
    buffer.put((byte)0x6e);
    buffer.put((byte)0x5f);
    buffer.put((byte)0x63);
    buffer.put((byte)0x30);
    buffer.put((byte)0x30);
    buffer.put((byte)0x30);
    buffer.put((byte)0x31);
    buffer.put((byte)0x00);

    buffer.flip();//必须执行，为读取数据做准备
    byte[] data = buffer.array();// 获取所有数据 就是30个字节的数组了
	
    return data;
	
	
}
	public static byte[] register1(){
		 byte[] b = new byte[15];

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
         
         return b;
	}
	
	public byte[] check(byte[] b){
		byte[] bq = new byte[8];
//      
  	  bq[0] = (byte) 0x10;
  	
      bq[1] = (byte) 0x08;
  	
      bq[2] = (byte) 0x04;
  	
      bq[3] = (byte) 0x00;
  	//bq[4] = getMemsIDbyte()[0];
     // bq[4] = (byte) 0x7E;
      bq[4] = b[0];
  	//bq[5] = getMemsIDbyte()[1];
     // bq[5] = (byte) 0x01;
      bq[5] = b[1];
      //bq[6] = getMemsIDbyte()[2];
      bq[6] = (byte) 0x00;
      //bq[7] = getMemsIDbyte()[3];
      bq[7] = (byte) 0x00;
        
        return bq;
	}
}
