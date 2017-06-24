package com.comicdl.ui;

import com.comicdl.ComicDownloader;

public class UIComicComponent {

	private ComicDownloader downloader;
	
	public synchronized final ComicDownloader getDownloader() {
		return downloader;
	}

	public UIComicComponent(ComicDownloader downloader){
		this.downloader = downloader;
	}

	@Override
	public String toString() {
		return String.format("[%s][%s][%s]", downloader.getComicName(),downloader.getAuthor(),downloader.getComicUpdate());
	}
	
	
	
}
