package com.comicdl;

import java.io.Closeable;
import java.io.IOException;

import com.comicdl.download.DownloadManager;
import com.comicdl.parser.ComicParser;

public abstract class ComicDownloader implements Downloadable,Closeable {

	private String comicId;
	private String episodeRange;
	private String urlString;
	private ComicParser parser;
	protected DownloadManager downloadManager;
		
	public synchronized final String getUrlString() {
		return urlString;
	}

	protected synchronized final String getComicId() {
		return comicId;
	}

	protected synchronized final String getEpisodeRange() {
		return episodeRange;
	}

	public ComicDownloader(String urlString) throws IOException {
		this.urlString = urlString;
		this.parser = new ComicParser(urlString);
		this.comicId = parser.getComicId();
		this.episodeRange = parser.getEpisode();
		this.downloadManager = new DownloadManager();
	}

	@Override
	public void close() throws IOException {
		downloadManager.close();
	}
	
	

}
