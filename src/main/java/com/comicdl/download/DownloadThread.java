package com.comicdl.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.comicdl.parser.DownloadParser;
import com.comicdl.parser.JsoupDownloadParser;

public class DownloadThread implements Runnable {

	private final static Logger log = LogManager.getLogger(DownloadThread.class);
	private long totalSize;
	private int id, chapter, page;
	private String dir;

	public DownloadThread(int id, int chapter, int page, String dir) {
		this.id = id;
		this.chapter = chapter;
		this.page = page;
		this.dir = dir;
	}

	@Override
	public void run() {
		DownloadParser downloadParser = null;
		try {
			downloadParser = new JsoupDownloadParser(id, chapter, page);
		} catch (IOException e1) {
			log.error(ExceptionUtils.getStackTrace(e1));
		}
		String imageurl = downloadParser.decode();
		// 檢查是否已建立目標漫畫的資料夾
		File comicDir = FileUtils.getFile(dir);
		if (comicDir.exists() == false) {
			comicDir.mkdirs();
		}
		// 將圖片抓下來
		String fileName = dir + "/" + Integer.toString(page) + ".jpg";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(imageurl);
		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);
			// OK
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				totalSize = entity.getContentLength();
				log.info("下載檔案:" + imageurl);
				log.info("檔案大小:" + totalSize);
				BufferedInputStream bis = new BufferedInputStream(entity.getContent());
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
				ByteBuffer buf = ByteBuffer.allocate(4096);
				while (true) {
					int readByte = bis.read(buf.array(), 0, 4096);
					if (readByte == -1)
						break;
					bos.write(buf.array(), 0, readByte);
				}
				log.info("檔案" + imageurl + "已完成下載，重新命名成" + fileName);
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
