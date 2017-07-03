package com.comicdl.ui;

import javafx.scene.layout.Pane;

public abstract class Field {

	protected Field(Pane root) {
		// 執行元件初始化
		initialize(root);
		// 執行元件組合
		combine(root);
		// 設定事件
		setOnEvent();
	}

	protected abstract void initialize(Pane root);

	protected abstract void combine(Pane root);

	protected abstract void setOnEvent();

}
