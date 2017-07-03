package com.comicdl.ui;

import java.io.Closeable;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.comicdl.ComicDownloader;
import com.comicdl.QuickDownloader;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ComicListField extends Field implements Closeable {

	private final static Logger log = LogManager.getLogger(ComicListField.class);

	private HBox hbox;
	private TextField urlTextInput;
	private Button btnEnterURL;
	private ListView<UIComicComponent> comiclist;
	private ObservableList<UIComicComponent> comicData;

	private UpdateField updateField;

	public synchronized final UpdateField getUpdateField() {
		return updateField;
	}

	public synchronized final void setUpdateField(UpdateField updateField) {
		this.updateField = updateField;
	}

	public ComicListField(Pane root) {
		super(root);
	}

	@Override
	protected void initialize(Pane root) {
		double width = MainScene.WIDTH;
		hbox = new HBox();
		hbox.setPadding(new Insets(5, 10, 5, 10));
		hbox.setSpacing(10);
		urlTextInput = new TextField();
		urlTextInput.setPrefWidth(width * 0.7);
		urlTextInput.setPromptText("http://www.comicbus.com/html/<漫畫id>.html");
		urlTextInput.setText("http://www.comicbus.com/html/103.html");
		btnEnterURL = new Button("確定");
		btnEnterURL.setPrefWidth(width - urlTextInput.getPrefWidth());
		comicData = FXCollections.observableArrayList();
		comiclist = new ListView<UIComicComponent>();
		comiclist.setItems(comicData);
	}

	@Override
	protected void combine(Pane root) {
		hbox.getChildren().addAll(urlTextInput, btnEnterURL);
		root.getChildren().add(hbox);
		root.getChildren().add(comiclist);
	}

	@Override
	protected void setOnEvent() {
		btnEnterURL.setOnAction((ActionEvent event) -> {
			String urlString = urlTextInput.getText();
			// 檢查輸入的網址不為空，而且符合http://www.comicbus.com/html/<漫畫id>.html的形式
			if (StringUtils.isNotEmpty(urlString) && urlString.matches("http://www.comicbus.com/html/\\d+.html")) {
				log.debug("接受輸入網址:" + urlString);
				new Thread(() -> {
					// 從urlString找出漫畫id
					Pattern pattern = Pattern.compile("http://www.comicbus.com/html/(\\d+).html");
					Matcher matcher = pattern.matcher(urlString);
					String id = (matcher.find()) ? matcher.group(1) : null;
					// 假設不含此id
					boolean isContained = false;
					// 檢查comicData是否含此漫畫
					for (UIComicComponent item : comicData) {
						// 候選人id
						String candId = item.getDownloader().getComicId();
						if (StringUtils.equals(candId, id)) {
							isContained = true;
							break;
						}
					}

					try {
						// 如果comicData不含此id
						if (isContained == false) {
							ComicDownloader cd = new QuickDownloader(urlString);
							UIComicComponent component = new UIComicComponent(cd);
							comicData.add(component);
						}
					} catch (IOException e) {
						log.error(ExceptionUtils.getStackTrace(e));
					}
				}).start();
			}
		}); // end of btnEnterURL.setOnAction

		comiclist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UIComicComponent>() {
			@Override
			public void changed(ObservableValue<? extends UIComicComponent> observable, UIComicComponent oldValue,
					UIComicComponent newValue) {
				log.debug("分析漫畫列表:" + newValue.getDownloader().getComicName());
				updateField.update(newValue);
			}
		});

	}

	@Override
	public void close() throws IOException {
		for (UIComicComponent component : comicData) {
			ComicDownloader downloader = component.getDownloader();
			if (downloader != null)
				downloader.close();
		}
	}

}
