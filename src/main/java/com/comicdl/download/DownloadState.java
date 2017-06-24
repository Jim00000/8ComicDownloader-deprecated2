package com.comicdl.download;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DownloadState implements IObserver {

	private final static Logger log = LogManager.getLogger(DownloadState.class);
	private long size = 0L;
	private int page = 0;

	public synchronized final int getPage() {
		return page;
	}

	public synchronized final void setPage(int page) {
		this.page = page;
	}

	public synchronized final long getSize() {
		return size;
	}

	@Override
	public void updateSize(long size) {
		this.size = size;
	}

	@Override
	public void updateProgress(double progress) {
		log.info("progress : " + progress + "%");
	}

}
