package com.comicdl.ui;

import java.io.Closeable;
import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class MainScene implements Closeable {

	static final double WIDTH = 600,HEIGHT = 450;
	private Scene scene;
	private VBox root;
	
	private MenuField menuField;
	private ComicListField comicListField;
	private ChapterTableField chapterTableField;
	
	public synchronized final Scene getScene() {
		return scene;
	}

	public MainScene() {
		root = new VBox();
		// 加入Menu Bar
		menuField = new MenuField(root);
		menuField.toString();
		// 加入漫畫網址列表
		comicListField = new ComicListField(root);
		// 加入漫畫章節列表
		chapterTableField = new ChapterTableField(root);
		comicListField.setUpdateField(chapterTableField);
		// 產生Scene
		this.scene = new Scene(root,WIDTH,HEIGHT);
	}

	@Override
	public void close() throws IOException {
		comicListField.close();
	}

}
