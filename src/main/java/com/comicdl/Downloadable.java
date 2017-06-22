package com.comicdl;

public interface Downloadable {
	
	/**
	 * 下載某一(集/卷)漫畫
	 * @param select
	 */
	public void download(int select) throws Throwable;
	
}
