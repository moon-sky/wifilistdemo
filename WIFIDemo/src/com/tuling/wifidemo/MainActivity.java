package com.tuling.wifidemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	WifiManager mWifiManager;
	List<ScanResult> mResults;
	ListView lv_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mWifiManager=(WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		mWifiManager.startScan();
		mResults=mWifiManager.getScanResults();
		lv_content=(ListView)findViewById(R.id.lv_content);
		lv_content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long id) {
				if(!mWifiManager.isWifiEnabled())
					mWifiManager.setWifiEnabled(true);
				int wifID=mWifiManager.addNetwork(CreateWifiInfo(mResults.get(pos).SSID, "guojiaguojia", 3));
				mWifiManager.enableNetwork(wifID, true);
				
//				configuration.
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onResume() {
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.item_list, R.id.tv_info, getScanResult());/*(this, R.id.tv_info, getScanResult())*/;
		lv_content.setAdapter(adapter);
		super.onResume();
	}
	private List<String> getScanResult(){
		List<String> wifi_arrayList=new ArrayList<String>();
		for(ScanResult m:mResults){
			StringBuilder sb=new StringBuilder();
			sb.append("BSSID:"+m.BSSID+"|SSID:"+m.SSID+"|capabilities:"+m.capabilities);
			wifi_arrayList.add(sb.toString());
		}
		return wifi_arrayList;
	}
	 public WifiConfiguration CreateWifiInfo(String SSID, String Password, int Type)   
	    {   
	          WifiConfiguration config = new WifiConfiguration();     
	           config.allowedAuthAlgorithms.clear();   
	           config.allowedGroupCiphers.clear();   
	           config.allowedKeyManagement.clear();   
	           config.allowedPairwiseCiphers.clear();   
	           config.allowedProtocols.clear();   
	          config.SSID = "\"" + SSID + "\"";     
	            
	            
	          if(Type == 1) //WIFICIPHER_NOPASS  
	          {   
	               config.wepKeys[0] = "";   
	               config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);   
	               config.wepTxKeyIndex = 0;   
	          }   
	          if(Type == 2) //WIFICIPHER_WEP  
	          {   
	              config.hiddenSSID = true;  
	              config.wepKeys[0]= "\""+Password+"\"";   
	              config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);   
	              config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);   
	              config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);   
	              config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);   
	              config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);   
	              config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);   
	              config.wepTxKeyIndex = 0;   
	          }   
	          if(Type == 3) //WIFICIPHER_WPA  
	          {   
	          config.preSharedKey = "\""+Password+"\"";   
	          config.hiddenSSID = true;     
	          config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);     
	          config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);                           
	          config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);                           
	          config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);                      
	          //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);    
	          config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);  
	          config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);  
	          config.status = WifiConfiguration.Status.ENABLED;     
	          }  
	           return config;   
	    }  

}
