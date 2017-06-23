package com.comicdl.download;

import java.util.concurrent.Future;

public interface IDownloadService {

	public Future<?> download(String urlString,String fileName);
	
}
