package com.comicdl.ui;

import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javafx.application.Application;
import javafx.stage.Stage;

public class DownloaderUIPreview extends Application {

	private final static Logger log = LogManager.getLogger(DownloaderUIPreview.class);
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		MainScene mainScene = new MainScene();
		primaryStage.setScene(mainScene.getScene());
		primaryStage.setTitle("8ComicDownloader Preview");
		primaryStage.setResizable(false);
		primaryStage.show();
		setUserAgentStylesheet(Application.STYLESHEET_MODENA);
		primaryStage.setOnCloseRequest((event)->{
			try {
				mainScene.close();
			} catch (IOException e) {
				log.error(ExceptionUtils.getStackTrace(e));
			}
		});
	}
	
	public static void main(String[] args) {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/log4j.properties");
		Logger.getLogger("org.apache.http").setLevel(org.apache.log4j.Level.OFF);
		Application.launch(args);
	}

}
