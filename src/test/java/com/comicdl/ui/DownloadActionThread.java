package com.comicdl.ui;

import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.comicdl.ComicDownloader;
import com.comicdl.download.DownloadState;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class DownloadActionThread extends Thread {

	private final static Logger log = LogManager.getLogger(DownloadActionThread.class);
	private int chapter;
	private Timeline timer;
	private ProgressBar progressBar;
	private ComicDownloader downloader;
	private ComicTableRow row;
	
	public DownloadActionThread(int chapter,ComicDownloader downloader,ComicTableRow row) {
		super();
		this.chapter = chapter;
		this.downloader = downloader;
		this.row = row;
		this.progressBar = row.getProgress();
	}

	@Override
	public void run() {
		DownloadState state = new DownloadState();
		List<Future<?>> futures = null;
		try {
			futures = downloader.download(chapter, state);
		} catch (Throwable e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
		row.setPage(Integer.toString(state.getPage()));

		int total = Integer.parseInt(row.getPage());
		final List<Future<?>> ffutures = futures;
		timer = new Timeline(new KeyFrame(Duration.millis(200), (e) -> {
			double count = 0;
			for (Future<?> future : ffutures) {
				if (future.isDone()) {
					count += 1;
				}
				double prog = (double) count / (double) total;
				progressBar.setProgress(prog);
				if (prog >= 1.0) {
					e.consume();
					timer.stop();
					log.debug("STOP IT");
				}
			}
		}));
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();
	}

	
	
}
