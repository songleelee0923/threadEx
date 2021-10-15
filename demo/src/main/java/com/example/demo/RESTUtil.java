package com.example.demo;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;


@Component
public class RESTUtil {
	
	 private int timeout = 20000;
	
	public  String request(String targetURL, String payload, String httpMethod, final String charset) {

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				public String handleResponse(final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					HttpEntity entity = response.getEntity();
					if ((status >= 200 && status < 300) || status==400) {
						return entity != null ? EntityUtils.toString(entity, charset) : null;
					} else {
						if(entity != null) {
							System.out.println("content : " + EntityUtils.toString(entity, charset));
						}
						
							throw new IOException();
					}
				}
			};

			String responseBody;

			if ("POST".equalsIgnoreCase(httpMethod)) { //create
				HttpPost http = new HttpPost(targetURL);
				//time out config
				RequestConfig requestConfig = RequestConfig.custom()
						  .setSocketTimeout(timeout)
						  .setConnectTimeout(timeout)
						  .setConnectionRequestTimeout(timeout)
						  .build();
				http.setConfig(requestConfig);
				if(payload != null) {
					StringEntity requestEntity = new StringEntity(payload, charset);
					requestEntity.setContentType(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
					http.setEntity(requestEntity);	
				}
				
				responseBody = httpclient.execute(http, responseHandler);
			}
			else if("PUT".equalsIgnoreCase(httpMethod)) { //update
				HttpPut http = new HttpPut(targetURL);
				//time out config
				RequestConfig requestConfig = RequestConfig.custom()
						  .setSocketTimeout(timeout)
						  .setConnectTimeout(timeout)
						  .setConnectionRequestTimeout(timeout)
						  .build();
				http.setConfig(requestConfig);
				http.setHeader("Accept", "application/json");
				if(payload != null) {
					StringEntity requestEntity = new StringEntity(payload, charset);
					requestEntity.setContentType(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
					http.setEntity(requestEntity);	
				}
				    responseBody = httpclient.execute(http, responseHandler);
			
			}
			else if("DELETE".equalsIgnoreCase(httpMethod)) { //delete
				HttpDelete http = new HttpDelete(targetURL);
				//time out config
				RequestConfig requestConfig = RequestConfig.custom()
						  .setSocketTimeout(timeout)
						  .setConnectTimeout(timeout)
						  .setConnectionRequestTimeout(timeout)
						  .build();
				http.setConfig(requestConfig);
				responseBody = httpclient.execute(http, responseHandler);
			}else { //get
				HttpGet http = new HttpGet(targetURL);
				//time out config
				RequestConfig requestConfig = RequestConfig.custom()
						  .setSocketTimeout(timeout)
						  .setConnectTimeout(timeout)
						  .setConnectionRequestTimeout(timeout)
						  .build();
				http.setConfig(requestConfig);
				responseBody = httpclient.execute(http, responseHandler);
			}
			//logger.debug("responseBody : " + responseBody );
			return responseBody;
		}catch(HttpHostConnectException e) {
			e.printStackTrace();
			System.out.println("responseBody fail :  \n"+ e.getMessage());
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("responseBody fail :  \n"+ e.getMessage());
			return null;
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
