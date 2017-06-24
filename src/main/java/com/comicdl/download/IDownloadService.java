package com.comicdl.download;

import java.util.concurrent.Future;

public interface IDownloadService {

	public Future<?> download(int id, int chapter, int page,String dir);
		
}
