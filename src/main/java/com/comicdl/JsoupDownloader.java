package com.comicdl;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class JsoupDownloader extends ComicDownloader {

	private final static Logger log = LogManager.getLogger(JsoupDownloader.class);

	public JsoupDownloader(String urlString) throws IOException {
		super(urlString);
	}

	@Override
	public void download(int select) throws Throwable {
		// 檢查comicId
		if (getComicId() == null)
			throw new NullPointerException();
		log.debug("已檢查ComicId和Parser");
		// 取得下載(集/卷)數的上限
		int selectUpperBound = Integer.parseInt(StringUtils.split(getEpisodeRange(), '-')[1]);
		// 檢查選擇下載的(集/卷)數是合理的
		if (select <= 0 || select > selectUpperBound)
			throw new IllegalArgumentException();
		log.debug("已檢查選擇下載的(集/卷)數是合法的");
	}

}
