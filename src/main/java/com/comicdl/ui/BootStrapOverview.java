package com.comicdl.ui;

import org.apache.commons.io.FileUtils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class BootStrapOverview extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(FileUtils.getFile("src/resources/bootstrap3overview.fxml").toURI().toURL());
		loader.load();

		primaryStage.setScene(new Scene(loader.<ScrollPane>getRoot(), 800, 600));

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
