package com.comicdl.download;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.comicdl.download.DownloadThread;

public class DownloadManager implements IDownloadService,Closeable {

	private ExecutorService executor;
	
	public DownloadManager() {
		executor = Executors.newCachedThreadPool();
	}

	@Override
	public Future<?> download(int id, int chapter, int page,String dir) {
		return executor.submit(new DownloadThread(id,chapter,page,dir));
	}
	
	@Override
	public void close() throws IOException {
		executor.shutdown();
	}

}
