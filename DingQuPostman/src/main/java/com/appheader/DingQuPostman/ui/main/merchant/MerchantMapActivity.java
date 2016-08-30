package com.appheader.DingQuPostman.ui.main.merchant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnCameraChangeListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds.Builder;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.entities.Merchant;


import java.util.ArrayList;
import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;


/**
 *
 * @author QZP
 *
 */
public class MerchantMapActivity extends BaseActivity implements
		LocationSource, AMapLocationListener, OnCameraChangeListener,
		OnMarkerClickListener {
	@Bind(R.id.mapview)
	MapView mMapView;
	private AMap mAMap;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	// 是否已经定位过，初次定位成功后将该值设为true
	private boolean mHasLocated;
	private List<Merchant> list = new ArrayList<>();



	Merchant mMerchant = new Merchant();
	private AnimationDrawable scanAnimation;// loading动画
	String location = "";
	View mShowRootView;
	TextView mMarkerNum;
	AMapLocation myLocation;// 我的位置

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_map);
		ButterKnife.bind(this);
		title.setText("附近的商户");
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mMapView.onCreate(savedInstanceState);

		if (mAMap == null) {
			mAMap = mMapView.getMap();
			setUpMap();
		}

		mAMap.setOnCameraChangeListener(this);
		mAMap.setOnMarkerClickListener(this);

	}

	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		mAMap.setLocationSource(this);// 设置定位监听
		mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
	}



	@Override
	public void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
		mHasLocated = false;
	}


	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation aLocation) {

		if (mListener != null && aLocation != null) {
			if (!mHasLocated) {
				LatLng position = new LatLng(aLocation.getLatitude(),
						aLocation.getLongitude());
				mAMap.moveCamera(CameraUpdateFactory.changeLatLng(position));
				mAMap.moveCamera(CameraUpdateFactory.zoomTo(16));
				mHasLocated = true;
				mAMapLocationManager.removeUpdates(this);
				myLocation = aLocation;
			}
			location = aLocation.getLongitude() + "," + aLocation.getLatitude();
			//getMerchants(location);

			//setMarker(list);
			mListener.onLocationChanged(aLocation);// 显示系统小蓝点
		}

	}

	@Override
	public void activate(OnLocationChangedListener listener) {

		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
			mAMapLocationManager.requestLocationData(
					LocationProviderProxy.AMapNetwork, -1, 1, this);
		}

	}

	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;
	}



	public void setMarker(List<Merchant> list) {
		Builder bulder = new Builder();
		for (int i = 0; i < list.size(); i++) {
			MarkerOptions markerOptions = new MarkerOptions();

			//getViewBitmap(mShowRootView);
			 
			// 设置Marker的坐标，为我们点击地图的经纬度坐标
			LatLng position = new LatLng(list.get(i).getLocation().get(1), list
					.get(i).getLocation().get(0));
			BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(getViewBitmap(mShowRootView));
		
			markerOptions.position(position);
			// 设置Marker的可见性
			markerOptions.visible(true);
			// 设置Marker是否可以被拖拽，这里先设置为false，之后会演示Marker的拖拽功能
			markerOptions.draggable(false);
			markerOptions.icon(bitmap);
			// 将Marker添加到地图上去
			Marker marker = mAMap.addMarker(markerOptions);
			
			bulder.include(marker.getPosition());
		}
//		LatLngBounds bounds = bulder.build();
		//if(isFirst){
			// 移动地图，所有marker自适应显示。LatLngBounds与地图边缘10像素的填充区域
		
			//mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
		//	isFirst = false;
		//}

	}
	
	//boolean isFirst = true;

	public void getMerchants(String aLocation) {
//		RequestHelper.sendAsyncRequest(this,
//				UrlConstants.SEARCH_MERCHANTS_LIST,
//				ParamBuilder.buildParam("location", aLocation)
//						.append("cityCode", CITY_CODE).append("pageNum", "1")
//						.append("pageSize", "10").append("carModel", carModel).append("sort", "distance")
//						.toHashMap(), new RequestCallback() {
//
//					@Override
//					public void onSuccess(ResponseEntity response) {
//						if (response.isSuccess()) {
//							JSONObject data = response.getDataObject();
//							JSONArray array = data.optJSONArray("list");
//
//							list = JSONUtil.jsonArrayToList(array,
//									new ItemConvertor<Merchant>() {
//										@Override
//										public Merchant convert(
//												JSONObject jsonObject) {
//											return JSONUtil.fromJSON(
//													jsonObject, Merchant.class);
//										}
//									});
//							if(list.size()>0){
//								showMerchant(list.get(0));
//								mBarbottom.setVisibility(View.VISIBLE);
//								setMarker(list);
//							}
//
//						}
//
//					}
//
//					@Override
//					public void onFailure(Exception ex) {
//
//					}
//
//					@Override
//					public void onComplete() {
//
//					}
//
//				});
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getLocation().get(0)
					.equals(marker.getPosition().longitude)
					&& list.get(i).getLocation().get(1)
							.equals(marker.getPosition().latitude)) {
//				showMerchant(list.get(i));
				
			}

		}
		return true;
	}

	@Override
	public void onCameraChange(CameraPosition position) {

	}

	@Override
	public void onCameraChangeFinish(CameraPosition position) {


		mAMap.clear();
		LatLng target = position.target;
		String location = target.longitude + "," + target.latitude;
		getMerchants(location);

	}

	 private Bitmap getViewBitmap(View addViewContent) {

	        addViewContent.setDrawingCacheEnabled(true );

	        addViewContent.measure(
	                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
	                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
	        addViewContent.layout(0, 0,
	                addViewContent.getMeasuredWidth(),
	                addViewContent.getMeasuredHeight());

	        addViewContent.buildDrawingCache();
	        Bitmap cacheBitmap = addViewContent.getDrawingCache();
	        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
	        addViewContent.destroyDrawingCache();
	        return bitmap;
	    }


}
