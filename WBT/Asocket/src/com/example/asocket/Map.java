package com.example.asocket;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.os.Bundle;

public class Map extends Activity {
	MapView mMapView = null;
	BaiduMap mBaiduMap;
	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
		
		super.onCreate(savedInstanceState);   
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext());  
        setContentView(R.layout.map); 
      //获取地图控件引用  
        mMapView = (MapView) findViewById(R.id.mymapview); 
        mBaiduMap = mMapView.getMap();
        /*
         * baidu map 中心点设置
         */
        LatLng cenpt = new LatLng(32.0,114.0); 
        MapStatus mMapStatus = new MapStatus.Builder()
      		  .target(cenpt)
      		  .zoom(11)
      		  .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
      //定义Maker坐标点  
        LatLng point = new LatLng(32.0000, 114.0000);  
        //构建Marker图标  
        BitmapDescriptor bitmap = BitmapDescriptorFactory  
            .fromResource(R.drawable.icon_marka3);  
        //构建MarkerOption，用于在地图上添加Marker  
        OverlayOptions option = new MarkerOptions()  
            .position(point)  
            .icon(bitmap);  
        //在地图上添加Marker，并显示  
        mBaiduMap.addOverlay(option);
    }  
	@Override 
	protected void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mMapView.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
        }  
}
