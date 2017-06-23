package com.comicdl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;

import com.comicdl.parser.DownloadParser;
import com.comicdl.parser.JsoupDownloadParser;

public class QuickDownloader extends ComicDownloader {

	private final static Logger log = LogManager.getLogger(QuickDownloader.class);

	public QuickDownloader(String urlString) throws IOException {
		super(urlString);
	}

	@Override
	public void download(int chapter) throws Throwable {
		// 檢查comicId
		if (getComicId() == null)
			throw new NullPointerException();
		log.debug("已檢查ComicId和Parser");
		// 取得下載(集/卷)數的上限
		int selectUpperBound = Integer.parseInt(StringUtils.split(getEpisodeRange(), '-')[1]);
		// 檢查選擇下載的(集/卷)數是合理的
		if (chapter <= 0 || chapter > selectUpperBound)
			throw new IllegalArgumentException();
		log.debug("已檢查選擇下載的(集/卷)數是合法的");
		int comicid = Integer.parseInt(getComicId());
		DownloadParser downloadParser = new JsoupDownloadParser(comicid, chapter, 1);
		// 取得這集漫畫的頁數
		int pageCount = downloadParser.getPageCount();
		log.info("id = " + comicid + "集數 = " + chapter + "，總共需要下載" + pageCount + "頁");
		// 執行漫畫下載
		downloadProcess(comicid, chapter, pageCount);
	}

	private void downloadProcess(int id, int chapter, int pageCount) {
		for (int page = 1; page <= pageCount; page++) {
			try {
				DownloadParser downloadParser = new JsoupDownloadParser(id, chapter, page);
				String imageurl = downloadParser.decode();
				String download_dir = Config.DOWNLOAD_DIR.getPath();
				// 檢查是否已建立目標漫畫的資料夾
				File comicDir = FileUtils.getFile(download_dir + "/" + super.getComicName() + "/" + episodeName(chapter));
				if (comicDir.exists() == false) {
					comicDir.mkdirs();
				}
				// 將圖片抓下來
				downloadManager.download(imageurl, comicDir + "/" + page + ".jpg");
			} catch (IOException e) {
				log.error(ExceptionUtils.getStackTrace(e));
			}
		}
	}

	private String episodeName(int chapter) {
		List<Element> episodes = parser.episodeAsList();
		List<Element> volumes = parser.volumeAsList();

		for (Element episode : episodes) {
			if (StringUtils.equals(episode.attr("id"), "c" + chapter)) {
				return episode.html();
			}
		}

		for (Element volume : volumes) {
			if (StringUtils.equals(volume.attr("id"), "c" + chapter)) {
				return volume.html();
			}
		}

		return Integer.toString(chapter);
	}

}
