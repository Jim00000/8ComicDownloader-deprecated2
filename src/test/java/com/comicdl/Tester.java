package com.comicdl;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.comicdl.decode.ImageURLDecoder;
import com.comicdl.download.DownloadManager;
import com.comicdl.download.DownloadThread;
import com.comicdl.parser.ComicParser;
import com.comicdl.parser.DownloadParser;
import com.comicdl.parser.JsoupDownloadParser;
import com.comicdl.parser.JsoupDownloadParser;

@SuppressWarnings("unused")
public class Tester {

	public static void main(String[] args) throws Throwable {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/log4j.properties");
		Logger.getLogger("org.apache.http").setLevel(org.apache.log4j.Level.OFF);
		
		ComicDownloader cd = new QuickDownloader("http://www.comicbus.com/html/103.html");
		cd.download(472,null);
		cd.close();

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
				
	}

}
