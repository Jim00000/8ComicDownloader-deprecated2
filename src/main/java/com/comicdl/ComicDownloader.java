package com.comicdl;

import java.io.Closeable;
import java.io.IOException;

import com.comicdl.download.DownloadManager;
import com.comicdl.parser.ComicParser;

public abstract class ComicDownloader implements Downloadable,Closeable {

	private String comicId;
	private String comicName;
	private String episodeRange;
	private String urlString;
	protected ComicParser parser;
	protected DownloadManager downloadManager;
		
	public synchronized final String getComicName() {
		return comicName;
	}

	public synchronized final String getUrlString() {
		return urlString;
	}

	public synchronized final String getComicId() {
		return comicId;
	}

	public synchronized final String getEpisodeRange() {
		return episodeRange;
	}

	public ComicDownloader(String urlString) throws IOException {
		this.urlString = urlString;
		this.parser = new ComicParser(urlString);
		this.comicName = parser.getComicName();
		this.comicId = parser.getComicId();
		this.episodeRange = parser.getEpisode();
		this.downloadManager = new DownloadManager();
	}

	@Override
	public void close() throws IOException {
		downloadManager.close();
	}
	
	

}
