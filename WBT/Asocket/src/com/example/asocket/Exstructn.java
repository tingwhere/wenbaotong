package com.example.asocket;
import struct.StructClass;
import struct.StructField;

@StructClass
public class Exstructn {

	@StructField(order = 0)
	public int magic;    //4

	@StructField(order = 1)
	public byte[] DST_MAC = new byte[6];//6

	@StructField(order = 2)
	public byte[] SRC_MAC = new byte[6];;//6
	    
	@StructField(order = 3)
	public long AUTH; //8
}

/*
 * http://blog.csdn.net/yjyxlfy/article/details/40620905
 * 传输：
 * ControlStruct      structSendByte = new ControlStruct ();
      byte[] b;
b = JavaStruct.pack(structSendByte);
 * 
 * 接收JavaStruct.unpack(recStruct,b);
 * 
 * 
 */

