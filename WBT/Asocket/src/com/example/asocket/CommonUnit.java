/*
 * 在0-100的范围内java会自动转换成十进制的数字出来，但如果包含abc这些字符自动转换就有问题了，可能变成负数了
 * 为防止16进制数直接转成字符显示出现负数的转换http://jingyan.baidu.com/article/2fb0ba4052e85b00f3ec5f75.html
 * http://blog.csdn.net/zhao_liwei/article/details/51985391
 * Author: Wei Ding, Date: 20161101 v0.2
 */
package com.example.asocket;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class CommonUnit {
    /** 
     * byte数组转换成16进制字符数组
     * @param src 
     * @return 
     */  
    public static String[] bytesToHexStrings(byte[] src){       
           if (src == null || src.length <= 0) {       
               return null;       
           }
           String[] str = new String[src.length];
           
           for (int i = 0; i < src.length; i++) {       
               int v = src[i] & 0xFF;       
               String hv = Integer.toHexString(v);       
               if (hv.length() < 2) {       
                   str[i] = "0";       
               }       
               str[i] = hv;        
           }       
           return str;       
       }
    
    /** 
     * 十六进制转字符串 
     *  
     * @param hexString 
     *            十六进制字符串 
     * @param encodeType 
     *            编码类型4：Unicode，2：普通编码 
     * @return 字符串 
     */  
    public static String hexStringToString(String hexString, int encodeType) {  
        String result = "";  
        int max = hexString.length() / encodeType;  
        for (int i = 0; i < max; i++) {  
            char c = (char) CommonUnit.hexStringToAlgorism(hexString  
                    .substring(i * encodeType, (i + 1) * encodeType));  
            result += c;  
        }  
        return result;  
    }
    
    /** 
     * 十六进制字符串装十进制 
     *  
     * @param hex 
     *            十六进制字符串 
     * @return 十进制数值 
     */  
    public static int hexStringToAlgorism(String hex) {  
        hex = hex.toUpperCase();  
        int max = hex.length();  
        int result = 0;  
        for (int i = max; i > 0; i--) {  
            char c = hex.charAt(i - 1);  
            int algorism = 0;  
            if (c >= '0' && c <= '9') {  
                algorism = c - '0';  
            } else {  
                algorism = c - 55;  
            }  
            result += Math.pow(16, max - i) * algorism;  
        }  
        return result;  
    }
    /** 
     * 字节数组转换为十六进制字符串 
     *  
     * @param b 
     *            byte[] 需要转换的字节数组 
     * @return String 十六进制字符串 
     */  


	public static final String byte2hex(byte b[]) {  
        if (b == null) {  
            throw new IllegalArgumentException(  
                    "Argument b ( byte array ) is null! ");  
        }  
        String hs = "";  
        String stmp = "";  
        for (int n = 0; n < b.length; n++) {  
            stmp = Integer.toHexString(b[n] & 0xff);  
            if (stmp.length() == 1) {  
                hs = hs + "0" + stmp;  
            } else {  
                hs = hs + stmp;  
            }  
        }  
        return hs.toUpperCase();  
    } 
   
	public static String bytes2HexString(byte[] b) {

		  String ret = "";

		  for (int i = 0; i < b.length; i++) {

		   String hex = Integer.toHexString(b[i] & 0xFF);

		   if (hex.length() == 1) {

		    hex = '0' + hex;

		   }

		   ret += hex.toUpperCase();

		  }
        return ret;
  }
}   
