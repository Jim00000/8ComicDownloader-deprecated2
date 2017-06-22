package com.comicdl;

import org.apache.log4j.PropertyConfigurator;

import com.comicdl.decode.ImageURLDecoder;
import com.comicdl.parser.ComicParser;
import com.comicdl.parser.DownloadParser;

@SuppressWarnings("unused")
public class Tester {

	public static void main(String[] args) throws Throwable {
		PropertyConfigurator.configure(System.getProperty("user.dir")+ "/src/log4j.properties");
//		ComicDownloader cd = new JsoupDownloader("http://www.comicbus.com/html/103.html");
//		cd.download(471);

//		ComicParser cp = new ComicParser("http://www.comicbus.com/html/103.html");
//		cp.getComicId();
//		cp.getComicName();
//		cp.getCategory();
//		cp.getAuthor();
//		cp.getEpisode();
//		cp.getUpdate();
//		cp.getComicDescription();
//		cp.volumeAsList();
//		cp.episodeAsList();
		
		DownloadParser dlp = new DownloadParser(103,471,18);
		dlp.getPageCount();
		
	}

}
