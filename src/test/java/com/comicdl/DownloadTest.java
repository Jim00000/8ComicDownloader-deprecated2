package com.comicdl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@SuppressWarnings("unused")
public class DownloadTest {

	public static void main(String args[]) throws Exception, IOException {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/log4j.properties");
		// Ref:https://stackoverflow.com/questions/12935923/how-to-disable-log4j-logging-in-http-client-4-1-to-log-to-fileappender
		Logger.getLogger("org.apache.http").setLevel(org.apache.log4j.Level.OFF);
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://img3.6comic.com:99/2/103/471/001_rwq.jpg");
		CloseableHttpResponse response = httpclient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			System.out.println("download bytes : " + entity.getContentLength());
			BufferedInputStream bis = new BufferedInputStream(entity.getContent());
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("sample.jpg")));
			ByteBuffer buf = ByteBuffer.allocate(1024);
			long allwritedBytes = 0L;
			while(true){
				int readByte = bis.read(buf.array(),0,1024);
				if(readByte == -1)
					break;
				bos.write(buf.array(), 0, readByte);
				allwritedBytes += readByte;
				System.out.println("Total downloaded : " + allwritedBytes);
			}
			bis.close();
			bos.close();
		}
		response.close();
		httpclient.close();
	}

}
