package com.comicdl.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.beans.property.SimpleObjectProperty;

public class TableRow {

	private SimpleStringProperty chapter;
	private SimpleStringProperty page;
	private final SimpleObjectProperty<ProgressBar> progress;
	private final SimpleObjectProperty<Button> downloadAction;

	public synchronized final String getChapter() {
		return chapter.get();
	}

	public synchronized final String getPage() {
		return page.get();
	}
	
	public synchronized final void setPage(String page) {
		this.page.set(page);
	}
	
	public synchronized final void setPage(Integer page) {
		this.page.set(page.toString());
	}

	public synchronized final ProgressBar getProgress() {
		return progress.get();
	}
	
	public synchronized final Button getDownloadAction() {
		return downloadAction.get();
	}

	public TableRow(String chapter,String page) {
		this.chapter = new SimpleStringProperty(chapter);
		this.page = new SimpleStringProperty(page);
		ProgressBar progress = new ProgressBar();
		progress.setProgress(0.0d);
		progress.setPrefWidth(MainScene.WIDTH * 0.5d);
		this.progress = new SimpleObjectProperty<>(progress);
		Button downloadAct = new Button("下載");
		downloadAct.setPrefWidth(MainScene.WIDTH * 0.1d);
		this.downloadAction = new SimpleObjectProperty<>(downloadAct);
	}
	
	

}
