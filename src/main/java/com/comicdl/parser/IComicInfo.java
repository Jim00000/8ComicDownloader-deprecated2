package com.comicdl.parser;

import java.util.List;

import org.jsoup.nodes.Element;

public interface IComicInfo {

	/**
	 * 從http://www.comicbus.com/html/%d.html裡分析取出漫畫id--->%d
	 * 
	 * @return 漫畫id
	 */
	public String getComicId();

	/**
	 * 取得漫畫的名稱
	 * 
	 * @return 漫畫名
	 */
	public String getComicName();

	/**
	 * 取得漫畫的類別
	 * 
	 * @return 類別
	 */
	public String getCategory();

	/**
	 * 取得漫畫的作者
	 * 
	 * @return 作者名
	 */
	public String getAuthor();

	/**
	 * 取得漫畫的集數
	 * 
	 * @return 格式為%d-%d的集數字串
	 */
	public String getEpisode();

	/**
	 * 取得目前漫畫的更新日期
	 * 
	 * @return 格式為yyyy-mm-dd日期字串
	 */
	public String getUpdate();

	/**
	 * 取得漫畫的介紹
	 * 
	 * @return 漫畫的介紹
	 */
	public String getComicDescription();

	/**
	 * 取得漫畫列表所有漫畫卷數
	 * 
	 * @return
	 */
	public List<Element> volumeAsList();

	/**
	 * 取得所有漫畫集數
	 * 
	 * @return
	 */
	public List<Element> episodeAsList();

}
