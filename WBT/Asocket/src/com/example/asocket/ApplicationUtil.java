/*
 * http://blog.csdn.net/qingzi635533/article/details/8971422
 * 用实例化使socket在多个activity中传递
 */
package com.example.asocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.app.Application;

public class ApplicationUtil extends Application{
	private Socket socket;  
    private OutputStream out = null;  
    private InputStream in = null;  
  
  
    public void init(String str) throws IOException, Exception{  
        this.socket = new Socket(str,2001);  
        this.out = socket.getOutputStream();  
        this.in = socket.getInputStream();  
    }  
      
    public Socket getSocket() {  
        return socket;  
    }  
  
    public void setSocket(Socket socket) {  
        this.socket = socket;  
    }  
  
    public OutputStream getOut() {  
        return out;  
    }  
  
    public void setOut(OutputStream out) {  
        this.out = out;  
    }  
  
    public InputStream getIn() {  
        return in;  
    }  
  
    public void setIn(InputStream in) {  
        this.in = in;  
    }  
      
}  


