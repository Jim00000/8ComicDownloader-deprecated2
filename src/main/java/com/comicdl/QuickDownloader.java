package com.comicdl;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.comicdl.parser.DownloadParser;
import com.comicdl.parser.JsoupDownloadParser;

public class QuickDownloader extends ComicDownloader {

	private final static Logger log = LogManager.getLogger(QuickDownloader.class);

	public QuickDownloader(String urlString) throws IOException {
		super(urlString);
	}

	@Override
	public void download(int chapter) throws Throwable {
		// 檢查comicId
		if (getComicId() == null)
			throw new NullPointerException();
		log.debug("已檢查ComicId和Parser");
		// 取得下載(集/卷)數的上限
		int selectUpperBound = Integer.parseInt(StringUtils.split(getEpisodeRange(), '-')[1]);
		// 檢查選擇下載的(集/卷)數是合理的
		if (chapter <= 0 || chapter > selectUpperBound)
			throw new IllegalArgumentException();
		log.debug("已檢查選擇下載的(集/卷)數是合法的");
		int comicid = Integer.parseInt(getComicId());
		DownloadParser downloadParser = new JsoupDownloadParser(comicid, chapter, 1);
		// 取得這集漫畫的頁數
		int pageCount = downloadParser.getPageCount();
		log.info("id = " + comicid + "集數 = " + chapter + "，總共需要下載" + pageCount + "頁");
		// 執行漫畫下載
		downloadProcess(comicid,chapter,pageCount);
	}

	private void downloadProcess(int id, int chapter, int pageCount) {
		for (int page = 1; page <= pageCount; page++) {
			try {
				DownloadParser downloadParser = new JsoupDownloadParser(id, chapter, page);
				String imageurl = downloadParser.decode();
				// 將圖片抓下來
				downloadManager.download(imageurl, chapter + "_" + page + ".jpg");
			} catch (IOException e) {
				log.error(ExceptionUtils.getStackTrace(e));
			}
		}
	}

}
