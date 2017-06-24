package com.comicdl;

import java.util.List;
import java.util.concurrent.Future;

import com.comicdl.download.DownloadState;

public interface Downloadable {
	
	/**
	 * 下載某一(集/卷)漫畫
	 * @param chapter
	 */
	public List<Future<?>> download(int chapter,DownloadState state) throws Throwable;
	
}
