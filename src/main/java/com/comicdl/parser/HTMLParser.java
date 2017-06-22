package com.comicdl.parser;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLParser{

	private final static Logger log = LogManager.getLogger(HTMLParser.class);
	private String urlString;
	private Document document;

	protected synchronized final String getUrlString() {
		return urlString;
	}

	protected synchronized final Document getDocument() {
		return document;
	}

	public HTMLParser(URL url) throws IOException {
		this(url.toString());
	}

	public HTMLParser(String urlString) throws IOException {
		this.urlString = urlString;
		initHtmlParsing(urlString);
	}

	/**
	 * 分析HTML網址得到Document
	 * @param urlString
	 */
	private void initHtmlParsing(String urlString){
		try {
			log.info("分析網址" + urlString);
			this.document = Jsoup.connect(urlString).get();
			log.info("分析網址完成");
		} catch (IOException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
	}

}
