/*
 * http://blog.chinaunix.net/uid-24406894-id-2608856.html
 * 用来尝试用parcelabel的方法传递string, int
 * Author: Ding Wei, Revised time 2016/11/02 Success
 */
package com.example.asocket;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DataParcelabel implements Parcelable{
	private int total; 
    private String id;
    private String netid;
    private String serverid;
    private int lon;
    private int lat;
    private String height;
    private String sample;
    
    public int getTotal() { 
        return total; 
    } 
  
    public void setTotal(int total) { 
        this.total = total; 
    } 
    
    public String getId() { 
        return id; 
    } 
  
    public void setId(String id) { 
        this.id = id; 
    } 
    
    public String getNetid() { 
        return netid; 
    } 
  
    public void setNetid(String netid) { 
        this.id = netid; 
    }
    
    public String getServeid() { 
        return serverid; 
    } 
  
    public void setServerid(String serverid) { 
        this.serverid = serverid; 
    }
    
    public int getLon() { 
        return lon; 
    } 
  
    public void setLon(int lon) { 
        this.lon = lon; 
    } 
    
    public int getLat() { 
        return lat; 
    } 
  
    public void setLat(int lat) { 
        this.lat = lat; 
    } 
    
    public String getHeight() { 
        return height; 
    } 
  
    public void setHeight(String height) { 
        this.height = height; 
    }
    
    public String getSample() { 
        return sample; 
    } 
  
    public void setSample(String sample) { 
        this.sample = sample; 
    }
    
    public static final Parcelable.Creator<DataParcelabel> CREATOR = new Creator<DataParcelabel>(){ 
    	  
        public DataParcelabel createFromParcel(Parcel source) { 
            // TODO Auto-generated method stub 

        	DataParcelabel cus = new DataParcelabel(); 
            cus.total = source.readInt(); 
            cus.id = source.readString(); 
            cus.netid = source.readString();
            cus.serverid = source.readString();
            cus.lon = source.readInt(); 
            cus.lat = source.readInt(); 
            cus.height = source.readString();
            cus.sample = source.readString();
//            byte[] data = new byte[source.readInt()];
//            source.readByteArray(data);
            return cus; 
        } 
  
        public DataParcelabel[] newArray(int size) { 
            // TODO Auto-generated method stub 

            return new DataParcelabel[size]; 
        } 
           
    }; 
  
    public int describeContents() { 
        // TODO Auto-generated method stub 

        return 0; 
    } 
  
    public void writeToParcel(Parcel dest, int flags) { 
        // TODO Auto-generated method stub 

        dest.writeInt(total); 
        dest.writeString(id);
        dest.writeString(netid);
        dest.writeString(serverid);
        dest.writeInt(lon);
        dest.writeInt(lat);
        dest.writeString(height);
        dest.writeString(sample);
 //       dest.writeInt(bytes.length);
//        dest.writeByteArray(data);
    }
}
