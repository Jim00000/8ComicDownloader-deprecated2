package com.comicdl.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.comicdl.Config;

public class XMLManager implements IXMLManager {

	private final static Logger log = LogManager.getLogger(XMLManager.class);

	private Document doc;
	
	public XMLManager() throws DocumentException {
		SAXReader reader = new SAXReader();
		this.doc = reader.read(Config.CONFIG_FILE);
	}

	@Override
	public void createDefaultConfig() {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("comicdl");
		Element conf = root.addElement("conf");
		conf.addElement("download").addAttribute("dir", "comic");
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(FileUtils.getFile(Config.CONFIG_FILE_PATH));
			writer = new XMLWriter(fos, format);
			writer.write(doc);
		} catch (IOException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		} finally {
			try {
				writer.flush();
			} catch (IOException e) {
				log.error(ExceptionUtils.getStackTrace(e));
			}
			try {
				writer.close();
			} catch (IOException e) {
				log.error(ExceptionUtils.getStackTrace(e));
			}
			try {
				fos.close();
			} catch (IOException e) {
				log.error(ExceptionUtils.getStackTrace(e));
			}
		}
	}

	@Override
	public Element root(){
		return this.doc.getRootElement();
	}

}
