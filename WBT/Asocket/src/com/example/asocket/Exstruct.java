/*
 * http://www.eifr.com/article.php?id=1396
 * 可用来将固定的结构体数据做成想要的c结构体
 * 发送可以用如下方式sock.getOutputStream().write(new Exstruct("kingfish", 123456789, 8888.99f)
 * revised time 20161023, Author: DingWei Version 1.0 调试通过
 * rationale: 用到数组位数的高低转换，数组的合并的概念
 */
package com.example.asocket;



public class Exstruct {

//	struct Exstruct {
//		short f_start;
//		short mes_long;
//		String name;
//		byte f_stop;
//		};
	private byte[] buf = new byte[15]; //为说明问题，定死大小，事件中可以灵活处理，这里定死大小很重要，因为一旦错，直接抛出异常
	/**
	* 将int转为低字节在前，高字节在后的byte数组
	*/
	private static byte[] toLH(int n) {
	byte[] b = new byte[4];
	b[0] = (byte) (n & 0xff);
	b[1] = (byte) (n >> 8 & 0xff);
	b[2] = (byte) (n >> 16 & 0xff);
	b[3] = (byte) (n >> 24 & 0xff);
	return b;
	}
	/**
	* 将char转为低字节在前，高字节在后的byte数组
	*/
	private static byte[] CtoLH(short n) {
		byte[] bchar = new byte[2];
		bchar[0] = (byte) (n & 0xff);//&按位取操作，11111111表示8位
		bchar[1] = (byte) (n >> 8 & 0xff);
		return bchar;
		}
	/**
	* 将float转为低字节在前，高字节在后的byte数组
	*/
//	private static byte[] toLH(float f) {
//	return toLH(Float.floatToRawIntBits(f));
//	}
	/**
	* 构造并转换 ,可以将数量，名称，id, 经纬度等组合到一个byte[]中，然后就好玩了
	* eq_number, name, eq1_id, eq1_lat, eq2_lon
	* >>看是否需要int, 不行char也行
	*/
	public Exstruct(String name) {//short mes_long, 先不用，因为高地位反了，要求的是00 0b, 成了0b 00
		//byte[] temp = CtoLH(f_start);
		byte[] temp=new byte[4];
		byte[] f_stop= new byte[1];
		temp [0] =(byte)0x00;
		temp [1] =(byte)0x08;
		temp [2] =(byte)0x00;
		temp [3] =(byte)0x0b;
//		System.arraycopy(temp, 0, buf, 0, temp.length);
//		temp = CtoLH(mes_long);
		System.arraycopy(temp, 0, buf, 0, temp.length);
		temp = name.getBytes();
		System.arraycopy(temp, 0, buf, 4, temp.length);
		f_stop[0]=(byte)0x00;
		System.arraycopy(f_stop, 0, buf, 14, 1);
		//System.arraycopy(f_stop, 0, buf, temp.length, f_stop.length);
		}
//	以下为原程序所用的组合
//	public Exstruct(String name, int id, float salary) {
//	byte[] temp = name.getBytes();
//	System.arraycopy(temp, 0, buf, 0, temp.length);
//	temp = toLH(id);
//	System.arraycopy(temp, 0, buf, 20, temp.length);
//	temp = toLH(salary);
//	System.arraycopy(temp, 0, buf, 24, temp.length);
//	}
	/**
	* 返回要发送的数组
	*/
	public byte[] getBuf() {
	return buf;
	} 
}
