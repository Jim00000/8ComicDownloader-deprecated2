package com.comicdl.playground;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.comicdl.download.DownloadState;
import com.comicdl.download.DownloadThread;

public class PlayGround {

	public static void main(String[] args) {
		PropertyConfigurator.configure(System.getProperty("user.dir")+ "/src/log4j.properties");
		Logger.getLogger("org.apache.http").setLevel(org.apache.log4j.Level.OFF);
		DownloadState ds = new DownloadState();
		DownloadThread dth = new DownloadThread("http://img3.6comic.com:99/2/103/471/001_rwq.jpg","471_001.jpg",ds);
		Thread th = new Thread(dth);
		th.start();
	}

}
