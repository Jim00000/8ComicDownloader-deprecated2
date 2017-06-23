package com.comicdl.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DownloadThread implements Runnable {

	private final static Logger log = LogManager.getLogger(DownloadThread.class);
	private final static boolean detailedInfo = false;
	private String urlString;
	private String fileName;
	private long totalSize;
	private DownloadState state;

	public DownloadThread(String urlString, String fileName) {
		this.urlString = urlString;
		this.fileName = fileName;
	}

	public DownloadThread(String urlString, String fileName, DownloadState state) {
		this(urlString, fileName);
		this.state = state;
	}

	@Override
	public void run() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(urlString);
		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);
			// OK
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				totalSize = entity.getContentLength();
				// 更新DownloadState的檔案大小
				if (state != null)
					state.updateSize(totalSize);
				if (detailedInfo) {
					log.info("下載檔案:" + urlString);
					log.info("檔案大小:" + totalSize);
				}
				BufferedInputStream bis = new BufferedInputStream(entity.getContent());
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
				ByteBuffer buf = ByteBuffer.allocate(1024);
				long allwrited = 0L;
				while (true) {
					int readByte = bis.read(buf.array(), 0, 1024);
					if (readByte == -1)
						break;
					bos.write(buf.array(), 0, readByte);
					// 更新目前已下載檔案，單位:bytes
					allwrited += readByte;
					// 更新DownloadState的下載進度
					if (state != null)
						state.updateProgress(((double)allwrited / (double)totalSize) * 100.0d);
					if (detailedInfo) {
						log.info("目前已下載 :" + allwrited + " bytes");
					}
				}
				log.info("檔案" + urlString + "已完成下載，重新命名成" + fileName);
				bis.close();
				bos.close();
			}
			// 關閉HttpResponse
			response.close();
		} catch (ClientProtocolException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		} catch (IOException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}

		// 關閉HttpClient
		try {
			httpclient.close();
		} catch (IOException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
	}

}
