package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


public class RestTask implements Runnable ,OnNetworkListener{
	
    private static final Logger LOGGER = LogManager.getLogger(RestTask.class);
	
	@Autowired
	private RESTUtil restUtil;
	
	public String url;
	
	public RestTask(String request) {
		url = request ;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		String result = "";
		try {
			result = restUtil.request(url, "", "POST", "UTF-8");
			onNetworkParserComplet(url,result);
		}catch(Exception e) {
			e.printStackTrace();
			onNetworkParserError(url,null);
		}
	
		
	}


	@Override
	public void onNetworkParserComplet(String url, Object object) {
		// TODO Auto-generated method stub
		/* 리턴받은 데이터 가공 */
	}


	@Override
	public void onNetworkParserError(String url, Object object) {
		// TODO Auto-generated method stub
		/* 에러 로그  */
		LOGGER.error("onNetworkParserError     :   "   + url)  ;
	}

}
