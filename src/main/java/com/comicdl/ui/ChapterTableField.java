package com.comicdl.ui;

import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Element;

import com.comicdl.Config;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ChapterTableField extends Field implements UpdateField {

	private TableView<TableRow> chapterlist;
	private TableColumn<TableRow, ?> chapterColumn;
	private TableColumn<TableRow, ?> pageColumn;
	private TableColumn<TableRow, ?> downloadColumn;
	private TableColumn<TableRow, ?> btnColumn;
	private ObservableList<TableRow> chapterData;

	private Timeline timer;

	public ChapterTableField(Pane root) {
		super(root);
		this.timer = new Timeline(new KeyFrame(Duration.millis(1000), (e) -> {
			chapterlist.refresh();
		}));
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();
	}

	@Override
	protected void initialize(Pane root) {
		chapterlist = new TableView<TableRow>();
		this.chapterColumn = new TableColumn<>("章節(卷/集)");
		this.pageColumn = new TableColumn<>("頁數");
		this.downloadColumn = new TableColumn<>("下載進度");
		this.btnColumn = new TableColumn<>("執行動作");
		this.chapterData = FXCollections.observableArrayList();
	}

	@Override
	protected void combine(Pane root) {
		chapterlist.getColumns().add(chapterColumn);
		chapterlist.getColumns().add(pageColumn);
		chapterlist.getColumns().add(downloadColumn);
		chapterlist.getColumns().add(btnColumn);
		chapterlist.setItems(chapterData);
		root.getChildren().add(chapterlist);
	}

	@Override
	protected void setOnEvent() {
		chapterColumn.setCellValueFactory(new PropertyValueFactory<>("chapter"));
		pageColumn.setCellValueFactory(new PropertyValueFactory<>("page"));
		downloadColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
		btnColumn.setCellValueFactory(new PropertyValueFactory<>("downloadAction"));
	}

	@Override
	public void update(UIComicComponent component) {
		chapterData.clear();
		List<Element> chapters = component.getDownloader().getVolumes();
		chapters.addAll(component.getDownloader().getEpisodes());
		for (Element chapter : chapters) {
			String chapterText = chapter.text();
			String comicName = component.getDownloader().getComicName();
			int chapterInt = Integer.parseInt(chapter.attr("id").substring(1));
			String checkedDir = Config.DOWNLOAD_DIR + "/" + comicName + "/" + chapterText;
			ComicTableRow row = null;
			if (FileUtils.getFile(checkedDir).exists()) {
				int fileCount = FileUtils.getFile(checkedDir).listFiles().length;
				row = new ComicTableRow(chapterText, chapterInt, fileCount, component.getDownloader(), true);
			} else
				row = new ComicTableRow(chapterText, chapterInt, -1, component.getDownloader());
			chapterData.add(row);
		}
	}

}
