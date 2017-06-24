package com.comicdl.ui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.comicdl.ComicDownloader;

import javafx.event.ActionEvent;

public class ComicTableRow extends TableRow {

	private final static Logger log = LogManager.getLogger(ComicTableRow.class);
	
	public ComicTableRow(String chapterText, Integer chapter,Integer page, ComicDownloader downloader) {
		super(chapterText, page.toString());
		super.getDownloadAction().setOnAction((ActionEvent event) -> {
			DownloadActionThread thread = new DownloadActionThread(chapter, downloader, this);
			thread.start();
		});
	}

	public ComicTableRow(String chapterText, Integer chapter,Integer page, ComicDownloader downloader, boolean complete) {
		this(chapterText, chapter, page, downloader);
		if (complete == true)
			super.getProgress().setProgress(1.0d);
	}

}
