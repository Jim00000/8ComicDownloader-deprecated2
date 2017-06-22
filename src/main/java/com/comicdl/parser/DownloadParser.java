package com.comicdl.parser;

import java.io.IOException;

public abstract class DownloadParser extends HTMLParser implements IDecodable, IPageCount {

	protected int ch;
	protected int page;
	protected int allPageCount = Integer.MIN_VALUE;

	public DownloadParser(int id, int chapter, int page) throws IOException {
		super("http://v.comicbus.com/online/comic-" + id + ".html?ch=" + chapter + "-" + page);
		this.ch = chapter;
		this.page = page;
	}

}
