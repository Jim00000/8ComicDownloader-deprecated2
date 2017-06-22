package com.comicdl;

public interface Downloadable {
	
	/**
	 * 下載某一(集/卷)漫畫
	 * @param chapter
	 */
	public void download(int chapter) throws Throwable;
	
}
