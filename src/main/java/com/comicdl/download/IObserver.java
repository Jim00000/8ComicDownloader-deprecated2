package com.comicdl.download;

public interface IObserver {

	public void updateSize(long size);
	
	public void updateProgress(double progress);
	
}
