package com.comicdl.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.comicdl.decode.ImageURLDecoder;

public class DownloadParser extends HTMLParser implements IDecodable, IPageCount {

	private final static Logger log = LogManager.getLogger(DownloadParser.class);
	private int ch;
	private int page;
	private int allPageCount = Integer.MIN_VALUE;

	public DownloadParser(int id, int chapter, int page) throws IOException {
		super("http://v.comicbus.com/online/comic-" + id + ".html?ch=" + chapter + "-" + page);
		this.ch = chapter;
		this.page = page;
	}

	@Override
	public String decode() {
		Elements scripts = super.getDocument().body().getElementsByTag("script");
		String chs = null, ti = null, cs = null;
		// 找到目標script，也就是var chs=...
		for (Element element : scripts) {
			String candidate = StringUtils.substring(element.html(), 0, 3);
			if (StringUtils.equals("var", candidate)) {
				String[] vars = StringUtils.split(element.html(), ';');
				chs = StringUtils.split(vars[0], '=')[1];
				ti = StringUtils.split(vars[1], '=')[1];
				cs = StringUtils.split(vars[2], '=')[1];
				// 移除single quote
				cs = StringUtils.remove(cs, '\'');
				break;
			}
		}

		log.debug("ch:" + ch);
		log.debug("chs:" + chs);
		log.debug("ti:" + ti);
		log.debug("cs:" + cs);
		// 解碼取得圖片的URL
		ImageURLDecoder decoder = new ImageURLDecoder(Integer.toString(ch), chs, ti, cs, page);
		String imageurl = decoder.decode();
		log.debug("解碼後圖片URL : " + imageurl);
		// 檢查是否已取得漫畫總頁數
		if (allPageCount == Integer.MIN_VALUE) {
			allPageCount = decoder.getAllPageCount();
		}
		
		return imageurl;
	}

	@Override
	public int getPageCount() {
		// 初始化漫畫頁數
		if (allPageCount == Integer.MIN_VALUE) {
			// 執行decode成員函式取得漫畫頁數
			decode();
		}
		log.debug("漫畫頁數:" + allPageCount);
		return allPageCount;
	}

}
