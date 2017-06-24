package com.comicdl.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

public class MenuField extends Field {

	private MenuBar menubar;
	private Menu file;
	private MenuItem file_close;

	public synchronized final MenuBar getMenubar() {
		return menubar;
	}

	public MenuField(Pane root) {
		super(root);
	}

	@Override
	protected void initialize(Pane root) {
		// 初始化MenuBar
		menubar = new MenuBar();
		// 初始化Menu
		file = new Menu("檔案");
		// 初始化MenuItem
		file_close = new MenuItem("關閉");
	}

	@Override
	protected void combine(Pane root) {
		file.getItems().addAll(file_close);
		menubar.getMenus().addAll(file);
		// 加入root
		root.getChildren().add(menubar);
	}
	
	@Override
	protected void setOnEvent(){
		/**
		 * 檔案->關閉
		 */
		file_close.setOnAction((ActionEvent e)->{
			Platform.exit();
		});
	}

}
