package com.comicdl.download;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DownloadState implements IObserver {

	private final static Logger log = LogManager.getLogger(DownloadState.class);
	private long size = 0L;
	private double progress = 0.0;

	public synchronized final long getSize() {
		return size;
	}

	public synchronized final double getProgress() {
		return progress;
	}

	@Override
	public void updateSize(long size) {
		this.size = size;
	}

	@Override
	public void updateProgress(double progress) {
		this.progress = progress;
		log.info("progress : " + progress + "%");
	}

}
