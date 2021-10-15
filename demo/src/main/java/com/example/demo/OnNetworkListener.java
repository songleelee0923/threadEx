package com.example.demo;


public interface OnNetworkListener {

	void onNetworkParserComplet(String url, Object object);
		
	void onNetworkParserError(String url, Object object);


}
