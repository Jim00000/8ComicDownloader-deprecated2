package com.comicdl;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;

import com.comicdl.download.DownloadManager;
import com.comicdl.parser.ComicParser;
import com.comicdl.parser.DownloadParser;
import com.comicdl.parser.JsoupDownloadParser;

public abstract class ComicDownloader implements Downloadable,Closeable {

	private String comicId;
	private String comicName;
	private String comicUpdate;
	private String author;
	private String episodeRange;
	private String urlString;
	private List<Element> episodes,volumes;
	protected ComicParser parser;
	protected DownloadManager downloadManager;
		
	public synchronized final String getComicName() {
		return comicName;
	}

	public synchronized final String getComicUpdate() {
		return comicUpdate;
	}

	public synchronized final String getAuthor() {
		return author;
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
	
	public synchronized final Integer getPageCount(int chapter) {
		DownloadParser downloadParser = null;
		try {
			downloadParser = new JsoupDownloadParser(comicId, chapter, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return downloadParser.getPageCount();
	}

	public synchronized final List<Element> getEpisodes() {
		return episodes;
	}

	public synchronized final List<Element> getVolumes() {
		return volumes;
	}

	public ComicDownloader(String urlString) throws IOException {
		this.urlString = urlString;
		this.parser = new ComicParser(urlString);
		this.comicName = parser.getComicName();
		this.comicId = parser.getComicId();
		this.author = parser.getAuthor();
		this.comicUpdate = parser.getUpdate();
		this.episodeRange = parser.getEpisode();
		this.episodes = parser.episodeAsList();
		this.volumes = parser.volumeAsList();
		this.downloadManager = new DownloadManager();
	}

	@Override
	public void close() throws IOException {
		downloadManager.close();
	}

}
