package com.comicdl;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.comicdl.file.XMLManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class Config {

	private final static Logger log = LogManager.getLogger(Config.class);
	/**
	 * Config設定檔
	 * <p>請注意不要更動CONFIG_FILE_PATH</p>
	 */
	public final static String CONFIG_FILE_PATH = "src/config/conf.xml";
	public final static File CONFIG_FILE = FileUtils.getFile(CONFIG_FILE_PATH);

	/**
	 * 漫畫下載資料夾
	 */
	public final static File DOWNLOAD_DIR;
	public final static String DOWNLOAD_DIR_PATH;

	static {
		// 初始化log4j
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/log4j.properties");
		Logger.getLogger("org.apache.http").setLevel(org.apache.log4j.Level.OFF);
		// 取得conf.xml的root
		Element root = null;
		try {
			root = new XMLManager().root();
		} catch (DocumentException e) {
			log.error("讀取不到conf.xml設定檔");
			log.error(ExceptionUtils.getStackTrace(e));
			System.exit(1);
		}
		// 初始化Config變數
		DOWNLOAD_DIR_PATH = root.element("conf").element("download").attributeValue("dir");
		DOWNLOAD_DIR = FileUtils.getFile(DOWNLOAD_DIR_PATH);
		// 檢查DOWNLOAD_DIR是否存在，不存在就建立資料夾
		if (DOWNLOAD_DIR.exists() == false) {
			DOWNLOAD_DIR.mkdirs();
			log.debug("建立" + DOWNLOAD_DIR + "資料夾");
		}
	}

}
