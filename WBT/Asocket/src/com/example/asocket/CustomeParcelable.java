/*
 * http://blog.chinaunix.net/uid-24406894-id-2608856.html
 * 用来尝试用parcelabel的方法传递string, int
 * Author: Ding Wei, Revised time 2016/10/31 Success
 */
package com.example.asocket;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomeParcelable implements Parcelable { 
    
    private String name; 
    private String id; 
    private int age; 
    private String sex;
//    private byte[] data;
  
    public String getName() { 
        return name; 
    } 
  
    public void setName(String name) { 
        this.name = name; 
    } 
  
    public String getId() { 
        return id; 
    } 
  
    public void setId(String id) { 
        this.id = id; 
    } 
  
    public int getAge() { 
        return age; 
    } 
  
    public void setAge(int age) { 
        this.age = age; 
    } 
  
    public String getSex() { 
        return sex; 
    } 
  
    public void setSex(String sex) { 
        this.sex = sex; 
    }
//    /*
//     * 增加byte,仿写，byte的读写部分参考http://blog.csdn.net/chf1142152101/article/details/51016643
//     * 现在问题是，如果parcelabel本身就是转成byte[]传递，怎么拼接byte[],暂时注释掉
//     */
//    public byte[] getData(){
//    	return data;
//    }
//    
//    public void setData(byte[] data){
//    	this.data =data;
//    }
       
    public static final Parcelable.Creator<CustomeParcelable> CREATOR = new Creator<CustomeParcelable>(){ 
  
        public CustomeParcelable createFromParcel(Parcel source) { 
            // TODO Auto-generated method stub 

            CustomeParcelable cus = new CustomeParcelable(); 
            cus.name = source.readString(); 
            cus.id = source.readString(); 
            cus.age = source.readInt(); 
            cus.sex = source.readString();
//            byte[] data = new byte[source.readInt()];
//            source.readByteArray(data);
            return cus; 
        } 
  
        public CustomeParcelable[] newArray(int size) { 
            // TODO Auto-generated method stub 

            return new CustomeParcelable[size]; 
        } 
           
    }; 
  
    public int describeContents() { 
        // TODO Auto-generated method stub 

        return 0; 
    } 
  
    public void writeToParcel(Parcel dest, int flags) { 
        // TODO Auto-generated method stub 

        dest.writeString(name); 
        dest.writeString(id); 
        dest.writeInt(age); 
        dest.writeString(sex);
 //       dest.writeInt(bytes.length);
//        dest.writeByteArray(data);
    }
} 
