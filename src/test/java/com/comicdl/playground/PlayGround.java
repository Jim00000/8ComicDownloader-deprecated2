package com.comicdl.playground;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.comicdl.download.DownloadState;
import com.comicdl.download.DownloadThread;
import com.comicdl.file.XMLManager;

@SuppressWarnings("unused")
public class PlayGround {

	public static void main(String[] args) throws DocumentException {
		XMLManager fm = new XMLManager();
		//fm.createDefaultConfig();
		Element root = fm.root();
	}

}
