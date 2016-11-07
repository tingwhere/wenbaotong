/*
 * 
 * http://www.cnblogs.com/freeliver54/archive/2012/07/30/2615134.html
 */

package com.example.asocket;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class Byte2Char {

	public byte[] getBytes (char[] chars) {
		   Charset cs = Charset.forName ("UTF-8");
		   CharBuffer cb = CharBuffer.allocate (chars.length);
		   cb.put (chars);
		                 cb.flip ();
		   ByteBuffer bb = cs.encode (cb);
		  
		   return bb.array();

		 }

		// byte转char

		public char[] getChars (byte[] bytes) {
		      Charset cs = Charset.forName ("UTF-8");
		      ByteBuffer bb = ByteBuffer.allocate (bytes.length);
		      bb.put (bytes);
		                 bb.flip ();
		       CharBuffer cb = cs.decode (bb);
		  
		   return cb.array();
		}
}
