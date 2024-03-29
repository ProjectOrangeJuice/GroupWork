package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.MyList;
import model.ReadingList;
import model.Resource;
import model.User;

/**
 * The Reading list controller (FXML).
 * 
 * @author Oliver Harris
 */
public class ReadingListController {

	/** The Constant RES_IMG_WIDTH. */
	private static final int RES_IMG_WIDTH = 150;

	/** The Constant RES_IMG_HEIGHT. */
	private static final int RES_IMG_HEIGHT = 200;

	/** The constant for the size of a box. */
	private static final int MIN_WIDTH = 300;
	
	/** The constant for the height of a box */
	private static final int MIN_HEIGHT = 100;
	
	/** The constant for spacing */
	private static final int SPACING = 10;
	
	/** The number of items to display before new line */
	private static final int DISPLAY_AS = 3;

	/** The left list. */
	@FXML
	private VBox yourList;

	/** The right list. */
	@FXML
	private VBox otherList;

	/** The search box. */
	@FXML
	private TextField searchBox;

	/**
	 * Initialize.
	 */
	@FXML
	public void initialize() {
		setupReadingList("");
		setupMyList();

	}

	/**
	 * Search action.
	 *
	 * @param e the key press.
	 */
	@FXML
	public void doSearch(KeyEvent e) {
		setupReadingList(searchBox.getText());

	}

	/**
	 * Display reading list.
	 *
	 * @param list the reading list.
	 */
	private void displayReadingList(ReadingList list) {
		
		otherList.getChildren().removeAll();
		otherList.getChildren().clear();

		Text listText = new Text(list.getName());
		listText.setFont(Font.font("Verdana", 20));

		Button close = new Button("Close");
		close.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				setupReadingList("");
			}

		});
		Button follow = new Button();
		if (ReadingList.follows(ScreenManager.getCurrentUser().getUsername(),
				list.getName())) {
			follow.setText("Unfollow");
		} else {
			follow.setText("Follow");
		}

		follow.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				String username = ScreenManager.getCurrentUser().getUsername();
				ReadingList.changeFollow(username, list.getName());
				if (ReadingList.follows(username, list.getName())) {
					follow.setText("Unfollow");
				} else {
					follow.setText("Follow");
				}
				setupMyList();
			}

		});

		HBox h = new HBox();
		h.setSpacing(SPACING);
		h.getChildren().addAll(close, follow);

		otherList.setSpacing(SPACING);
		otherList.getChildren().addAll(listText, h);

		ArrayList<Resource> resources = list.getResources();
		HBox hbox = new HBox();
		for (int i = 0; i < resources.size(); i++) {
			Resource r = resources.get(i);
			VBox item = new VBox();
			ImageView img = generateImageView(r.getThumbnail());
			img.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					ScreenManager.setCurrentResource(r);
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(
								getClass().getResource("/fxml/copyScene.fxml"));
						Parent root1 = (Parent) fxmlLoader.load();
						Stage stage = new Stage();
						stage.initModality(Modality.APPLICATION_MODAL);
						// stage.initStyle(StageStyle.UNDECORATED);
						stage.setTitle("Resource Information");
						stage.setScene(new Scene(root1));
						stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

			});
			Text name = new Text(r.getTitle());
			item.getChildren().addAll(img, name);
			if (!(ScreenManager.getCurrentUser() instanceof User)) {
				Button remove = new Button("Remove");
				remove.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						ReadingList.removeFromList(list.getName(), r.getUniqueID());
						setupReadingList(list.getName());
					}

				});
				item.getChildren().add(remove);
			}

			if (i != 0 && i % DISPLAY_AS == 0) {
				System.out.println("Adding to main");
				otherList.getChildren().add(hbox);
				hbox = new HBox();
				hbox.getChildren().add(item);
			} else {
				System.out.println("Adding to inner");
				hbox.getChildren().add(item);
			}

		}
		otherList.getChildren().add(hbox);

	}

	/**
	 * Sets up the reading list.
	 *
	 * @param search The search word.
	 */
	private void setupReadingList(String search) {
		ArrayList<ReadingList> lists = ReadingList.readReadingLists();
		otherList.getChildren().removeAll();
		otherList.getChildren().clear();
		Text listText = new Text("Reading lists");
		listText.setFont(Font.font("Verdana", 20));
		otherList.setSpacing(2);
		otherList.getChildren().add(listText);
		ArrayList<VBox> vboxs = new ArrayList<VBox>();
		for (ReadingList l : lists) {
			if (search == "" || l.contains(search)) {

				VBox vbox = new VBox();
				vbox.setMinWidth(MIN_WIDTH);
				vbox.setSpacing(SPACING);
				ImageView i = generateImageView(l.getImage());

				Text t = new Text(l.getName());
				t.setFont(Font.font(15));

				TextArea desc = new TextArea(l.getDescription());
				desc.setWrapText(true);

				desc.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(
							final ObservableValue<? extends String> observable,
							final String oldValue,
							final String newValue) {
						l.setDescription(newValue);
					}
				});

				desc.setMinHeight(MIN_HEIGHT);

				if (ScreenManager.getCurrentUser() instanceof User) {
					desc.setEditable(false);
					desc.setMouseTransparent(true);
					desc.setFocusTraversable(false);
					vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent arg0) {
							displayReadingList(l);

						}

					});
				} else {
					desc.setEditable(true);

					i.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent arg0) {
							FileChooser fileChooser = new FileChooser();
							fileChooser.setTitle("Open image File");
							fileChooser.getExtensionFilters()
							.addAll(new ExtensionFilter(
									"Image Files", "*.png", "*.jpg", "*.gif"));

							File selectedFile = fileChooser.showOpenDialog(
									new Stage());
							if (selectedFile != null) {

								l.setImage("/graphics/" + selectedFile.getName());
								setupReadingList("");
							}
						}

					});

					t.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent arg0) {
							displayReadingList(l);

						}

					});

				}
				vbox.getChildren().addAll(i, t, desc);

				vboxs.add(vbox);
				System.out.println("Value created");
			}
		}
		HBox hbox = new HBox();
		for (int i = 0; i < vboxs.size(); i++) {
			if (i != 0 && i % DISPLAY_AS == 0) {
				System.out.println("Adding to main");
				otherList.getChildren().add(hbox);
				hbox = new HBox();
				hbox.getChildren().add(vboxs.get(i));
			} else {
				System.out.println("Adding to inner");
				hbox.getChildren().add(vboxs.get(i));
			}

		}
		otherList.getChildren().add(hbox);

	}

	/**
	 * Setup the users list.
	 */
	private void setupMyList() {
		yourList.getChildren().removeAll();
		yourList.getChildren().clear();
		Text yours = new Text("Your list");
		yours.setFont(Font.font("Verdana", 20));

		yourList.getChildren().add(yours);
		yourList.setSpacing(2);

		String username = ScreenManager.getCurrentUser().getUsername();
		MyList myList = MyList.getMyList(username);
		if (myList != null) {
			for (Resource r : myList.getResources()) {
				VBox vbox = new VBox();
				vbox.setSpacing(2);
				String[] borrowed = r.hasBorrowed(username);
				Button remove = new Button("Remove");
				vbox.getChildren().add(remove);
				if (borrowed != null) {
					for (String b : borrowed) {
						vbox.getChildren().add(new Text("Borrowed: " + b));
					}

				} else {
					vbox.getChildren().add(new Text("Not borrowed before"));
				}

				ImageView image = generateImageView(r.getThumbnail());
				image.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						ScreenManager.setCurrentResource(r);
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(
									getClass().getResource("/fxml/copyScene.fxml"));
							Parent root1 = (Parent) fxmlLoader.load();
							Stage stage = new Stage();
							stage.initModality(Modality.APPLICATION_MODAL);
							// stage.initStyle(StageStyle.UNDECORATED);
							stage.setTitle("Resource Information");
							stage.setScene(new Scene(root1));
							stage.show();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}

				});

				Text text = new Text(r.getTitle());

				remove.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						MyList.removeFromMyList(
								ScreenManager.getCurrentUser().getUsername(),
								r.getUniqueID());

						setupMyList();
					}
				});

				HBox box = new HBox();
				box.setMinWidth(MIN_WIDTH);
				box.getChildren().addAll(image, vbox);

				yourList.getChildren().addAll(box, text);

			}
		}

		// Add their followed readinglists
		ArrayList<ReadingList> followed = ReadingList.followedList(
				ScreenManager.getCurrentUser().getUsername());
		for (ReadingList l : followed) {
			VBox vbox = new VBox();
			vbox.setSpacing(2);
			Button remove = new Button("Remove");
			vbox.getChildren().add(remove);
			TextArea desc = new TextArea(l.getDescription());
			desc.setWrapText(true);
			desc.setEditable(false);
			desc.setMouseTransparent(true);
			desc.setFocusTraversable(false);

			desc.setMinHeight(MIN_HEIGHT);

			vbox.getChildren().add(desc);

			ImageView image = generateImageView(l.getImage());
			image.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					displayReadingList(l);

				}

			});

			Text text = new Text(l.getName());

			remove.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					ReadingList.changeFollow(
							ScreenManager.getCurrentUser().getUsername(),
							l.getName());

					setupMyList();
				}
			});

			HBox box = new HBox();
			box.setMinWidth(MIN_WIDTH);
			box.getChildren().addAll(image, vbox);

			yourList.getChildren().addAll(box, text);

		}

	}

	/**
	 * Generate image view.
	 *
	 * @param image the image
	 * @return the image view
	 */
	private ImageView generateImageView(Image image) {
		ImageView resourceimage = new ImageView();
		resourceimage.setFitWidth(RES_IMG_WIDTH);
		resourceimage.setFitHeight(RES_IMG_HEIGHT);

		resourceimage.setImage(image);
		return resourceimage;
	}

}
