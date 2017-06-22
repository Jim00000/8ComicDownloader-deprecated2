package com.comicdl;

public interface IComicInfo {

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
	 * @return 格式為yyyy-mm-dd日期字串
	 */
	public String getUpdate();
	
	/**
	 * 取得漫畫的介紹
	 * @return 漫畫的介紹
	 */
	public String getComicDescription();

}
