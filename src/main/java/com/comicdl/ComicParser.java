package com.comicdl;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.select.Elements;

public class ComicParser extends HTMLParser implements IComicInfo {

	private final static Logger log = LogManager.getLogger(ComicParser.class);
	
	public ComicParser(String urlString) throws IOException {
		super(urlString);
	}

	@Override
	public String getCategory() {
		Elements tables = super.getDocument().select("[style='border-bottom:1px dotted #e6e6e6']");
		Elements tds = tables.get(0).getElementsByTag("td");
		String category = tds.get(1).text();
		log.debug("取得類別：" + category);
		return category;
	}

	@Override
	public String getAuthor() {
		Elements tables = super.getDocument().select("[style='border-bottom:1px dotted #e6e6e6']");
		Elements tds = tables.get(1).getElementsByTag("td");
		String author = tds.get(1).text();
		log.debug("取得作者：" + author);
		return author;
	}

	@Override
	public String getEpisode() {
		Elements tables = super.getDocument().select("[style='border-bottom:1px dotted #e6e6e6']");
		Elements tds = tables.get(3).getElementsByTag("td");
		String episode = tds.get(1).text();
		// 使用regex找出%d-%d的字串模式並取出
		Pattern pattern = Pattern.compile("([0-9-]+).*");
		Matcher matcher = pattern.matcher(episode);
		episode = (matcher.find())? matcher.group(1) : "搜尋錯誤";
		log.debug("取得漫畫集數：" + episode);
		return episode;
	}

	@Override
	public String getUpdate() {
		Elements tables = super.getDocument().select("[style='border-bottom:1px dotted #e6e6e6']");
		Elements tds = tables.get(4).getElementsByTag("td");
		String update = tds.get(1).text();
		log.debug("取得更新：" + update);
		return update;
	}

	@Override
	public String getComicDescription() {
		Elements tables = super.getDocument().select("[style='padding:10px; border-top:1px dotted #cccccc']");
		Elements td = tables.get(0).getElementsByTag("td");
		String description = td.get(0).text();
		log.debug("取得漫畫描述：" + description);
		return description;
	}
	
}
