package com.comicdl;

import org.apache.log4j.PropertyConfigurator;

public class Tester {

	public static void main(String[] args) throws Exception {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/log4j.properties");
		ComicParser cp = new ComicParser("http://www.comicbus.com/html/103.html");
		cp.getCategory();
		cp.getAuthor();
		cp.getEpisode();
		cp.getUpdate();
		cp.getComicDescription();
	}

}
