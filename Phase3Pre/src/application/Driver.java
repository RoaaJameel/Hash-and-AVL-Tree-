package application;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import javafx.scene.control.ButtonBar;
import java.util.Stack;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Driver extends Application {
	HashTable hashTable = new HashTable();
	private AVLTree MartyrsAVLTree;
	private int currentIndex = 0;
	Stack<String> stackForDistricts = new Stack<>();
	Stack<String> stackForLocationss = new Stack<>();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new Pane();
		pane = firstScreen(primaryStage);
		Scene scene = new Scene(pane, 900, 700);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Palestinian Martyrs");
		primaryStage.show();

	}

	public Pane firstScreen(Stage stage) {
		Pane pane = new Pane();
		pane.setPrefSize(901, 700);

		Text welcomeText = new Text("Welcome To The Palestinian Martyrs Statistics");
		welcomeText.setFont(new Font("Segoe Print", 20));
		welcomeText.setLayoutX(27);
		welcomeText.setLayoutY(49);
		welcomeText.setWrappingWidth(500);

		ImageView imageView = new ImageView(new Image("file:/C:/Users/ayham/OneDrive/سطح%20المكتب/ayham/martyr.jpg"));
		imageView.setFitHeight(516);
		imageView.setFitWidth(471);
		imageView.setLayoutX(21);
		imageView.setLayoutY(106);
		imageView.setPreserveRatio(true);
		imageView.setPickOnBounds(true);
		Image icon = new Image("file:/C:/Users/ayham/OneDrive/سطح المكتب/ayham/372e4627a5fc83a70a84ea0b4094bc09.jpg");
		stage.getIcons().add(icon);

		Button readFileButton = new Button("Read File");
		readFileButton.setLayoutX(657);
		readFileButton.setLayoutY(124);
		readFileButton.setPrefSize(166, 43);
		readFileButton.setOnAction(e -> {
			try {
				String successMessage = readfile(stage); // Call read file method
				if (successMessage != null && !successMessage.isEmpty()) {
					Text successText = new Text(successMessage);
					successText.setLayoutX(27);
					successText.setLayoutY(650);
					pane.getChildren().add(successText);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		Button goToDateScreenButton = new Button("Go To Date Screen");
		goToDateScreenButton.setLayoutX(657);
		goToDateScreenButton.setLayoutY(290);
		goToDateScreenButton.setPrefSize(166, 43);
		goToDateScreenButton.setOnAction(e -> {
			secondScreen(stage);
		});

		Button saveButton = new Button("Save");
		saveButton.setLayoutX(657);
		saveButton.setLayoutY(455);
		saveButton.setPrefSize(166, 43);
		saveButton.setOnAction(e -> {
			writeFile(stage);
			showAlert(AlertType.INFORMATION, "Success", "Data Saved To File Successfully!");
		});

		Button exitButton = new Button("Exit");
		exitButton.setLayoutX(657);
		exitButton.setLayoutY(620);
		exitButton.setPrefSize(166, 43);
		exitButton.setTextFill(Color.RED);
		exitButton.setFont(new Font(19));
		exitButton.setOnAction(e -> stage.close()); // Close the stage (exit the program)

		pane.getChildren().addAll(welcomeText, imageView, readFileButton, goToDateScreenButton, saveButton, exitButton);
		return pane;
	}

	public Pane secondScreen(Stage stage) {// Date Screen
		Pane pane = new Pane();
		pane.setPrefSize(900, 700);

		TextArea textArea1 = new TextArea();
		textArea1.setLayoutX(45);
		textArea1.setLayoutY(19);
		textArea1.setPrefWidth(420);
		textArea1.setPrefHeight(339);

		TextArea textArea2 = new TextArea();
		textArea2.setLayoutX(488);
		textArea2.setLayoutY(19);
		textArea2.setPrefWidth(434);
		textArea2.setPrefHeight(339);

		textArea1.setEditable(false);
		textArea1.setWrapText(true);

		textArea2.setEditable(false);
		textArea2.setWrapText(true);

		Button insertButton = new Button("Insert Date");
		insertButton.setLayoutX(558);
		insertButton.setLayoutY(399);
		insertButton.setPrefWidth(130);
		insertButton.setPrefHeight(40);

		Button updateButton = new Button("Update Date");
		updateButton.setLayoutX(685);
		updateButton.setLayoutY(546);
		updateButton.setPrefWidth(137);
		updateButton.setPrefHeight(40);

		Button deleteButton = new Button("Delete Date");
		deleteButton.setLayoutX(623);
		deleteButton.setLayoutY(478);
		deleteButton.setPrefWidth(130);
		deleteButton.setPrefHeight(40);

		Button printButton = new Button("Print Hash Table");
		printButton.setLayoutX(771);
		printButton.setLayoutY(614);
		printButton.setPrefWidth(137);
		printButton.setPrefHeight(40);

		Button homeButton = new Button("Home Screen");
		homeButton.setLayoutX(37);
		homeButton.setLayoutY(654);
		homeButton.setOnAction(e -> {
			// firstScreen(stage);
			Pane homePane = firstScreen(stage);
			Scene homeScene = new Scene(homePane, 900, 700);
			stage.setScene(homeScene);
			stage.setTitle("Main Screen");
		});

		Button martyrScreenBtn = new Button("Go To Martyr Screen");
		martyrScreenBtn.setLayoutX(163);
		martyrScreenBtn.setLayoutY(654);

		martyrScreenBtn.setOnAction(e -> {
			// Get the currently selected date from your hash table or wherever it's stored
			HashNode currentNode = hashTable.getTable()[currentIndex];

			// Check if the current node is not null and has a date
			if (currentNode != null && currentNode.date != null) {
				// Retrieve the AVL tree for the selected date
				AVLTree avlTree = hashTable.find(currentNode.date);

				// Pass the AVL tree to the method responsible for loading the third screen
				thirdScreen(stage, avlTree, currentNode.date);
			} else {
				// Handle the case where no date is found at the current index
				showAlert(AlertType.ERROR, "Error", "No AVL Tree for the selected date because it's Empty");
			}
		});

		Button upButton = new Button("Up");
		upButton.setLayoutX(64);
		upButton.setLayoutY(368);
		upButton.setPrefWidth(47);
		upButton.setPrefHeight(25);

		Button downButton = new Button("Down");
		downButton.setLayoutX(64);
		downButton.setLayoutY(406);
		downButton.setPrefWidth(47);
		downButton.setPrefHeight(25);

		DatePicker datePicker = new DatePicker();
		datePicker.setLayoutX(714);
		datePicker.setLayoutY(406);

		insertButton.setOnAction(e -> {
			// System.out.println(hashTable.countFilledSlots());
			LocalDate date = datePicker.getValue();
			if (date == null || !isValidDate(date)) {
				showAlert(AlertType.ERROR, "Error", "Please select a valid date.");
			} else {
				if (hashTable.findDate(date)) {
					showAlert(AlertType.ERROR, "Error", "The Date " + date + " is already exists!");
				} else {
					Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
					confirmAlert.setTitle("Confirmation");
					confirmAlert.setHeaderText("Confirm Date Addition");
					confirmAlert.setContentText("Are you sure you want to add the date '" + date + "'?");
					Optional<ButtonType> result = confirmAlert.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						hashTable.insertDate(date);
						// System.out.println(hashTable.countFilledSlots());
						showAlert(AlertType.INFORMATION, "Success",
								"The date: " + formatDate(date) + " has been added succeassfully! ");
					}
				}
			}
		});

		deleteButton.setOnAction(e -> {
			LocalDate date = datePicker.getValue();
			if (date == null || !isValidDate(date)) {
				showAlert(AlertType.ERROR, "Error", "Please select a valid date.");
			} else {
				if (hashTable.findDate(date)) {
					Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
					confirmAlert.setTitle("Confirmation");
					confirmAlert.setHeaderText("Confirm Date Deletion");
					confirmAlert.setContentText("Are you sure you want to delete the date '" + date + "'?");
					Optional<ButtonType> result = confirmAlert.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						hashTable.delete(date);
						// System.out.println(hashTable.countFilledSlots());
						showAlert(AlertType.INFORMATION, "Success",
								"The date: " + formatDate(date) + " has been deleted succeassfully! ");
					}
				} else {
					showAlert(AlertType.ERROR, "Error", "The date: " + formatDate(date) + " Not Found !! ");
				}
			}
		});
		updateButton.setOnAction(e -> {
			LocalDate date = datePicker.getValue();

			if (date == null || !isValidDate(date)) {
				showAlert(AlertType.ERROR, "Error", "Please select a valid date.");
			} else {
				if (hashTable.findDate(date)) {
					Dialog<LocalDate> dialog = new Dialog<>();
					dialog.setTitle("Enter the new Date");

					// Set the button types (OK and Cancel)
					dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

					// Create a grid pane to hold the controls
					GridPane grid = new GridPane();
					grid.setPadding(new Insets(20));
					grid.setVgap(10);
					grid.setHgap(10);

					// Add label for the prompt
					Label promptLabel = new Label("Enter the new Date:");
					grid.add(promptLabel, 0, 0, 2, 1); // Span 2 columns

					// Add labels, text field, and date picker
					Label newDateLabel = new Label("New Date:");
					DatePicker newDatePicker = new DatePicker();

					grid.add(newDateLabel, 0, 1);
					grid.add(newDatePicker, 1, 1);

					// Set the grid pane as the content of the dialog
					dialog.getDialogPane().setContent(grid);

					// Convert the result to a LocalDate when the OK button is clicked
					dialog.setResultConverter(dialogButton -> {
						if (dialogButton == ButtonType.OK) {
							return newDatePicker.getValue();
						}
						return null;
					});

					// Show the dialog and wait for the user response
					Optional<LocalDate> result = dialog.showAndWait();
					if (result.isPresent()) {
						LocalDate newDateToUpdate = result.get();
						if (newDateToUpdate == null || !isValidDate(newDateToUpdate)) {
							showAlert(AlertType.ERROR, "Error", "Please select a valid date.");
						} else {
							hashTable.update(date, newDateToUpdate);
							showAlert(AlertType.INFORMATION, "Success",
									"The Date " + date + " updated successfully to " + newDateToUpdate);

						}
					}
				} else {
					showAlert(AlertType.ERROR, "Error", "The date: " + formatDate(date) + " Not Found !!");
				}
			}

		});

		// Set up the action event handler for the printButton
		printButton.setOnAction(e -> {
			// Reset the text areas
			textArea1.clear();
			textArea2.clear();

			// Navigate through the hash table from top to bottom
			StringBuilder stringBuilder1 = new StringBuilder();
			StringBuilder stringBuilder2 = new StringBuilder();
			HashNode[] table = hashTable.getTable();

			if (currentIndex >= 0 && currentIndex < table.length) {
				stringBuilder1.append(currentIndex).append("  : ");
				HashNode node = table[currentIndex];
				if (node != null) {
					if (node.flag == 'F') {
						stringBuilder1.append(node.date).append("\n");
						// Display martyrs' information
						stringBuilder1.append(node.MartyrsAVLTree.preOrder()).append("\n");
						// Add more information if needed
					} else if (node.flag == 'D') {
						stringBuilder1.append("Deleted").append("\n");
					} else {
						stringBuilder1.append("Empty").append("\n");
					}
				} else {
					stringBuilder1.append("Empty").append("\n");
				}

				// Display the contents of the hash table in textArea1
				textArea1.setText(stringBuilder1.toString());

				stringBuilder2.append("Martyrs Summary:\n");
				// Check if there is a HashNode at the current index
				if (table[currentIndex] != null) {
					// Retrieve the date from the HashNode at the current index
					String currentDate = table[currentIndex].date;

					// Check if the date is not null
					if (currentDate != null) {
						// Get the number of martyrs for the current date
						int numberOfMartyrs = hashTable.getNumberOfMartyrs(LocalDate.parse(currentDate));
						double averageMartyrs = (double) numberOfMartyrs / countTotalMartyrs(hashTable);
						stringBuilder2.append("Total Martyrs: ").append(numberOfMartyrs).append("\n");
						stringBuilder2.append("Average Martyrs: ").append(averageMartyrs).append("\n");

						stringBuilder2.append("District with Maximum Martyrs: ")
								.append(hashTable.maxMartyrsDistrictOnDate(currentDate)).append("\n");
						stringBuilder2.append("Location with Maximum Martyrs: ")
								.append(hashTable.maxMartyrsLocationOnDate(currentDate)).append("\n");

					}
				} else {

					stringBuilder2.append("Total Martyrs: 0\n");
					stringBuilder2.append("Average Martyrs: 0\n");
					stringBuilder2.append("Location with Maximum Martyrs: null\n");
					stringBuilder2.append("District with Maximum Martyrs: null\n");

				}
				textArea2.setText(stringBuilder2.toString());
			}
		});

		// Set up the action event handlers for the up and down buttons
		upButton.setOnAction(e -> {
			currentIndex--; // Move to the previous index
			currentIndex = Math.max(currentIndex, 0); // Ensure index doesn't go below 0
			printButton.fire(); // Trigger the printButton action event
		});

		downButton.setOnAction(e -> {
			currentIndex++; // Move to the next index
			currentIndex = Math.min(currentIndex, hashTable.getSize() - 1); // Ensure index doesn't exceed table size
			printButton.fire(); // Trigger the printButton action event
		});

		pane.getChildren().addAll(textArea1, textArea2, insertButton, updateButton, deleteButton, printButton,
				homeButton, martyrScreenBtn, upButton, downButton, datePicker);

		Scene dateScene = new Scene(pane, 1000, 700);
		stage.setScene(dateScene);
		stage.setTitle("Date Screen");
		stage.show();

		return pane;
	}

	public Pane thirdScreen(Stage stage, AVLTree avlTree, String date) {// Martyr Screen
		Pane pane = new Pane();
		pane.setPrefSize(937, 678);

		// Create Buttons
		Button insertButton = new Button("Insert");
		insertButton.setLayoutX(74);
		insertButton.setLayoutY(65);
		insertButton.setPrefSize(130, 33);
		insertButton.setFont(new Font(18));

		insertButton.setOnAction(e -> {
			insertScreen(stage, avlTree, date);
		});

		Button updateButton = new Button("Update");
		updateButton.setLayoutX(74);
		updateButton.setLayoutY(158);
		updateButton.setPrefSize(130, 33);
		updateButton.setFont(new Font(18));

		updateButton.setOnAction(e -> {
			// Display list of martyrs to choose from
			List<Martyr> martyrList = avlTreeToList(avlTree.getRoot());

			// Create a ChoiceDialog to choose the martyr
			ChoiceDialog<Martyr> dialog = new ChoiceDialog<>(null, martyrList);
			dialog.setTitle("Choose Martyr to Update");
			dialog.setHeaderText("Select the martyr you want to update:");
			dialog.setContentText("Martyr:");

			// Show dialog and wait for user response
			Optional<Martyr> chosenMartyr = dialog.showAndWait();

			// Update information if martyr is chosen
			chosenMartyr.ifPresent(martyr -> {
				// Open another dialog to choose what information to update
				ChoiceDialog<String> updateDialog = new ChoiceDialog<>(null,
						Arrays.asList("Name", "Age", "Date of Death", "District / Location"));
				updateDialog.setTitle("Choose Information to Update");
				updateDialog.setHeaderText("Select the information you want to update:");
				updateDialog.setContentText("Update:");

				// Show update dialog and wait for user response
				Optional<String> chosenUpdate = updateDialog.showAndWait();

				// Update chosen information
				chosenUpdate.ifPresent(update -> {
					switch (update) {
					case "Name":
						// Open a TextInputDialog to get the new name
						TextInputDialog nameDialog = new TextInputDialog();
						nameDialog.setTitle("Enter New Name");
						nameDialog.setHeaderText("Enter the new name for the martyr:");
						nameDialog.setContentText("Name:");

						// Show dialog and wait for user response
						Optional<String> newName = nameDialog.showAndWait();

						// Check if a new name is provided
						if (newName.isPresent() && !newName.get().trim().isEmpty()) {
							String name = newName.get().trim();
							martyr.setfullName(name);
							// Show a success alert
							Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
							successAlert.setTitle("Update Successful");
							successAlert.setHeaderText(null);
							successAlert.setContentText("Martyr's name updated successfully!");
							successAlert.showAndWait();
							System.out.println(martyr);
						} else {
							// Show an error alert if no name is provided
							Alert errorAlert = new Alert(Alert.AlertType.ERROR);
							errorAlert.setTitle("Error");
							errorAlert.setHeaderText(null);
							errorAlert.setContentText("You must enter a new name!");
							errorAlert.showAndWait();
						}
						break;
					case "Age":
						// Open a TextInputDialog to get the new age
						TextInputDialog ageDialog = new TextInputDialog();
						ageDialog.setTitle("Enter New Age");
						ageDialog.setHeaderText("Enter the new age for the martyr:");
						ageDialog.setContentText("Age:");

						// Show dialog and wait for user response
						Optional<String> newAgeStr = ageDialog.showAndWait();

						// Parse the age string to an integer and update age if a valid integer is
						// provided
						newAgeStr.ifPresent(ageStr -> {
							try {
								int newAge = Integer.parseInt(ageStr);
								// Update age
								martyr.setAge(newAge);
								// Optionally, you can update your AVL tree here
							} catch (NumberFormatException x) {
								// Handle invalid input (non-integer)
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Invalid Input");
								alert.setHeaderText(null);
								alert.setContentText("Please enter a valid integer for age.");
								alert.showAndWait();
							}
						});
						break;
					case "Date of Death":
						// Create a custom dialog for choosing the date of death
						Dialog<LocalDate> dateDialog = new Dialog<>();
						dateDialog.setTitle("New Date of Death");
						dateDialog.setHeaderText("Enter the new date of death for the martyr:");

						// Set the button types
						ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
						dateDialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

						// Create a date picker and add it to the dialog
						DatePicker datePicker = new DatePicker();
						dateDialog.getDialogPane().setContent(datePicker);

						// Convert the result to a LocalDate when the OK button is clicked
						dateDialog.setResultConverter(dialogButton -> {
							if (dialogButton == okButtonType) {
								return datePicker.getValue();
							}
							return null;
						});

						// Show the dialog and wait for user response
						Optional<LocalDate> result = dateDialog.showAndWait();

						// Process the chosen date if present and valid
						result.ifPresent(newDate -> {
							// Validate the date (e.g., it should not be in the future)
							if (newDate != null && !newDate.isAfter(LocalDate.now())) {
								// Remove the martyr from the old AVL tree
								if (hashTable.findDate(LocalDate.parse(date))) {
									AVLTree oldTree = hashTable.find(LocalDate.parse(date));
									oldTree.delete(martyr);

									// If the old AVL tree is empty after deletion, remove the date from the hash
									// table
									if (oldTree.isEmpty()) { // Assuming AVLTree has an isEmpty method
										hashTable.delete(LocalDate.parse(date));
									}
								}

								// Update the date of death for the martyr
								martyr.setDateOfDeath(newDate.toString());

								// Check if the new date already exists in the hash table
								if (hashTable.findDate(newDate)) {
									// If the new date exists, add the martyr to the AVL tree for that date
									AVLTree existingTree = hashTable.find(newDate);
									existingTree.insert(martyr);
								} else {
									// If the new date doesn't exist, create a new AVL tree and insert the martyr
									AVLTree newTree = new AVLTree();
									newTree.insert(martyr);
									hashTable.insert(newDate, newTree);
								}

								// Show a success alert
								Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
								successAlert.setTitle("Update Successful");
								successAlert.setHeaderText(null);
								successAlert.setContentText("Martyr's date of death updated successfully!");
								successAlert.showAndWait();
							} else {
								// Show an error alert if the date is invalid
								Alert errorAlert = new Alert(Alert.AlertType.ERROR);
								errorAlert.setTitle("Invalid Date");
								errorAlert.setHeaderText(null);
								errorAlert.setContentText("The selected date is invalid. Please choose a valid date.");
								errorAlert.showAndWait();
							}
						});
						break;

					case "District / Location":
						// Show dialog and wait for user response
						chosenUpdate.ifPresent(Toupdate -> {
							// Update district
							ComboBox<String> districtComboBox = new ComboBox<>();
							ComboBox<String> locationComboBox = new ComboBox<>();

							districtComboBox.getItems().addAll(hashTable.getAllDistricts());
							districtComboBox.setPromptText("Select District");

							// Show district selection dialog
							Alert districtAlert = new Alert(Alert.AlertType.CONFIRMATION);
							districtAlert.setTitle("Choose District");
							districtAlert.setHeaderText("Select the district to update:");
							districtAlert.getDialogPane().setContent(districtComboBox);

							Optional<ButtonType> districtResult = districtAlert.showAndWait();
							if (districtResult.isPresent() && districtResult.get() == ButtonType.OK) {
								String selectedDistrict = districtComboBox.getValue();
								if (selectedDistrict != null) {
									List<String> locations = hashTable.getAllLocationsForDistrict(selectedDistrict);
									locationComboBox.getItems().addAll(locations);
									locationComboBox.setPromptText("Select Location");

									// Show location selection dialog
									Alert locationAlert = new Alert(Alert.AlertType.CONFIRMATION);
									locationAlert.setTitle("Choose Location");
									locationAlert.setHeaderText("Select the location to update:");
									locationAlert.getDialogPane().setContent(locationComboBox);

									Optional<ButtonType> locationResult = locationAlert.showAndWait();
									if (locationResult.isPresent() && locationResult.get() == ButtonType.OK) {
										String selectedLocation = locationComboBox.getValue();
										// Update martyr's district and location
										martyr.setDistrict(selectedDistrict);
										martyr.setLocation(selectedLocation);

										// Show success message
										Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
										successAlert.setTitle("Update Successful");
										successAlert.setHeaderText(null);
										successAlert
												.setContentText("Martyr's district and location updated successfully!");
										successAlert.showAndWait();
									}
								}
							}
						});
						break;

					default:
						break;
					}
				});
			});
		});

		Button deleteButton = new Button("Delete");
		deleteButton.setLayoutX(74);
		deleteButton.setLayoutY(264);
		deleteButton.setPrefSize(130, 33);
		deleteButton.setFont(new Font(18));

		deleteButton.setOnAction(e -> {
			List<Martyr> martyrList = avlTreeToList(avlTree.getRoot());

			// Create a ChoiceDialog to choose the martyr
			ChoiceDialog<Martyr> dialog = new ChoiceDialog<>(null, martyrList);
			dialog.setTitle("Choose Martyr to Delete");
			dialog.setHeaderText("Select the martyr you want to delete:");
			dialog.setContentText("Martyr:");

			// Show dialog and wait for user response
			Optional<Martyr> chosenMartyr = dialog.showAndWait();

			// Delete the chosen martyr if present
			chosenMartyr.ifPresent(martyr -> {
				avlTree.delete(martyr);
				// Show success alert
				Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
				successAlert.setTitle("Success");
				successAlert.setHeaderText(null);
				successAlert.setContentText("Martyr deleted successfully!");
				successAlert.showAndWait();
			});
		});

		Button printTreeButton = new Button("Print Tree");
		printTreeButton.setLayoutX(74);
		printTreeButton.setLayoutY(363);
		printTreeButton.setPrefSize(130, 33);
		printTreeButton.setFont(new Font(18));
		printTreeButton.setOnAction(e -> {
			display(avlTreeToList(avlTree.getRoot()));
		});

		Button homeButton = new Button("Home");
		homeButton.setLayoutX(25);
		homeButton.setLayoutY(621);
		homeButton.setPrefSize(61, 25);

		homeButton.setOnAction(e -> {
			Pane homePane = firstScreen(stage);
			Scene homeScene = new Scene(homePane, 900, 700);
			stage.setScene(homeScene);
			stage.setTitle("Main Screen");
		});

		Button dateScreenButton = new Button("Date Screen");
		dateScreenButton.setLayoutX(127);
		dateScreenButton.setLayoutY(621);
		dateScreenButton.setPrefSize(102, 25);
		dateScreenButton.setOnAction(e -> {
			secondScreen(stage);
		});

		// Create Labels
		Label treeSizeLabel = new Label("Tree Size: ");
		treeSizeLabel.setLayoutX(380);
		treeSizeLabel.setLayoutY(534);
		treeSizeLabel.setPrefSize(89, 25);
		treeSizeLabel.setFont(new Font("System Bold", 16));

		Label treeHeightLabel = new Label("Tree Height:");
		treeHeightLabel.setLayoutX(661);
		treeHeightLabel.setLayoutY(534);
		treeHeightLabel.setPrefSize(102, 25);
		treeHeightLabel.setFont(new Font("System Bold", 16));

		Button exitButton = new Button("Exit");
		exitButton.setLayoutX(837);
		exitButton.setLayoutY(634);
		exitButton.setPrefSize(78, 25);
		exitButton.setTextFill(javafx.scene.paint.Color.web("#dc0b0b"));
		exitButton.setOnAction(e -> System.exit(0));

		Label treeSizeValueLabel = new Label(avlTree.size() + "");
		treeSizeValueLabel.setLayoutX(458);
		treeSizeValueLabel.setLayoutY(534);
		treeSizeValueLabel.setPrefSize(61, 25);
		treeSizeValueLabel.setFont(new Font(16));

		Label treeHeightValueLabel = new Label(avlTree.height() + "");
		treeHeightValueLabel.setLayoutX(763);
		treeHeightValueLabel.setLayoutY(534);
		treeHeightValueLabel.setFont(new Font(16));

		// Create TextArea
		TextArea textArea = new TextArea();
		textArea.setLayoutX(333);
		textArea.setLayoutY(42);
		textArea.setPrefSize(571, 428);

		StringBuilder treeData = new StringBuilder();
		treeData.append("Martyrs AVL Tree Data (Level-by-Level, Right to Left):\n");

		int height = avlTree.height();
		for (int i = height; i >= 1; i--) {
			printLevel(avlTree.getRoot(), i, treeData);
		}

		textArea.setText(treeData.toString());
		pane.getChildren().addAll(insertButton, updateButton, deleteButton, printTreeButton, homeButton,
				dateScreenButton, exitButton, treeSizeLabel, treeHeightLabel, treeSizeValueLabel, treeHeightValueLabel,
				textArea);

		Scene martyrScene = new Scene(pane, 1000, 680);
		stage.setScene(martyrScene);
		stage.setTitle("Martyr Screen");
		stage.show();

		return pane;
	}

	public Pane insertScreen(Stage stage, AVLTree avlTree, String date) {
		Pane pane = new Pane();

		pane.setPrefSize(867, 586);

		// Create Labels
		Label insertMartyrLabel = new Label("Insert a Martyr");
		insertMartyrLabel.setLayoutX(14);
		insertMartyrLabel.setLayoutY(14);
		insertMartyrLabel.setPrefSize(205, 17);
		insertMartyrLabel.setFont(new Font("System Bold", 24));

		Label nameLabel = new Label("Name");
		nameLabel.setLayoutX(254);
		nameLabel.setLayoutY(78);
		nameLabel.setFont(new Font(18));

		Label ageLabel = new Label("Age");
		ageLabel.setLayoutX(262);
		ageLabel.setLayoutY(144);
		ageLabel.setFont(new Font(18));

		Label districtLabel = new Label("District");
		districtLabel.setLayoutX(249);
		districtLabel.setLayoutY(293);
		districtLabel.setFont(new Font(18));

		Label locationLabel = new Label("Location");
		locationLabel.setLayoutX(243);
		locationLabel.setLayoutY(373);
		locationLabel.setFont(new Font(18));

		Label genderLabel = new Label("Gender");
		genderLabel.setLayoutX(248);
		genderLabel.setLayoutY(211);
		genderLabel.setFont(new Font(18));

		// Create TextFields
		TextField nameTextField = new TextField();
		nameTextField.setLayoutX(341);
		nameTextField.setLayoutY(74);
		nameTextField.setPrefSize(225, 35);

		TextField ageTextField = new TextField();
		ageTextField.setLayoutX(342);
		ageTextField.setLayoutY(145);
		ageTextField.setPrefSize(48, 35);

		// Create ComboBoxes
		ComboBox<String> districtComboBox = new ComboBox<>();
		districtComboBox.setLayoutX(342);
		districtComboBox.setLayoutY(293);
		districtComboBox.setPrefSize(205, 27);

		ComboBox<String> locationComboBox = new ComboBox<>();
		locationComboBox.setLayoutX(342);
		locationComboBox.setLayoutY(373);
		locationComboBox.setPrefSize(205, 27);

		// Create RadioButtons
		ToggleGroup toggleGroup = new ToggleGroup();
		RadioButton femaleRadioButton = new RadioButton("F");
		femaleRadioButton.setLayoutX(349);
		femaleRadioButton.setLayoutY(216);
		femaleRadioButton.setFont(new Font(14));
		femaleRadioButton.setToggleGroup(toggleGroup);

		RadioButton maleRadioButton = new RadioButton("M");
		maleRadioButton.setLayoutX(453);
		maleRadioButton.setLayoutY(216);
		maleRadioButton.setFont(new Font(14));
		maleRadioButton.setToggleGroup(toggleGroup);

		// Create Buttons
		Button saveButton = new Button("Save");
		saveButton.setLayoutX(532);
		saveButton.setLayoutY(462);
		saveButton.setPrefSize(68, 25);
		saveButton.setTextFill(javafx.scene.paint.Color.GREEN);

		Button clearButton = new Button("Clear");
		clearButton.setLayoutX(638);
		clearButton.setLayoutY(462);
		clearButton.setPrefSize(70, 25);
		clearButton.setTextFill(javafx.scene.paint.Color.RED);
		clearButton.setOnAction(e -> {

			// clear the text fields
			nameTextField.clear();
			ageTextField.clear();

			// Clear radio button selection
			toggleGroup.getSelectedToggle().setSelected(false);

			// Clear combo box selections
			districtComboBox.getSelectionModel().clearSelection();
			locationComboBox.getSelectionModel().clearSelection();

		});

		Button exitButton = new Button("Exit");
		exitButton.setLayoutX(795);
		exitButton.setLayoutY(546);
		exitButton.setPrefSize(58, 25);

		// Add all components to the pane
		pane.getChildren().addAll(insertMartyrLabel, nameLabel, ageLabel, districtLabel, locationLabel, genderLabel,
				nameTextField, ageTextField, districtComboBox, locationComboBox, femaleRadioButton, maleRadioButton,
				saveButton, clearButton, exitButton);

		List<String> districts = hashTable.getAllDistricts();
		districtComboBox.getItems().addAll(districts);
		districtComboBox.setPromptText("Select District");

		districtComboBox.setOnAction(e -> {
			String selectedDistrict = districtComboBox.getValue();
			if (selectedDistrict != null) {
				List<String> locations = hashTable.getAllLocationsForDistrict(selectedDistrict);
				locationComboBox.getItems().clear(); // Clear previous items
				locationComboBox.getItems().addAll(locations);
				locationComboBox.setPromptText("Select Location");
			}
		});

		saveButton.setOnAction(e -> {
			// Validate name
			if (nameTextField.getText() == null || nameTextField.getText().trim().isEmpty()) {
				showAlert(Alert.AlertType.ERROR, "Empty Name", "You should enter a name !!");
				return;
			}
			String name = nameTextField.getText().trim();

			// Validate age
			if (ageTextField.getText() == null || ageTextField.getText().trim().isEmpty()) {
				showAlert(Alert.AlertType.ERROR, "Empty Age", "You should enter an age !!");
				return;
			}
			int age;
			try {
				age = Integer.parseInt(ageTextField.getText().trim());
			} catch (NumberFormatException ex) {
				showAlert(Alert.AlertType.ERROR, "Invalid Age", "Age must be a valid number !!");
				return;
			}

			// Validate gender
			char gender = ' ';
			if (toggleGroup.getSelectedToggle() != null) {
				RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
				gender = selectedRadioButton.getText().charAt(0);
			} else {
				showAlert(Alert.AlertType.ERROR, "Missing Gender", "You should choose a gender !!");
				return;
			}

			// Validate district
			if (districtComboBox.getValue() == null || districtComboBox.getValue().trim().isEmpty()) {
				showAlert(Alert.AlertType.ERROR, "Missing District", "You should choose a district !!");
				return;
			}
			String district = districtComboBox.getValue().trim();

			// Validate location
			if (locationComboBox.getValue() == null || locationComboBox.getValue().trim().isEmpty()) {
				showAlert(Alert.AlertType.ERROR, "Missing Location", "You should choose a location !!");
				return;
			}
			String location = locationComboBox.getValue().trim();

			// Create and insert the Martyr object
			Martyr martyr = new Martyr(name, date, age, district, location, gender);
			System.out.println(martyr);

			avlTree.insert(martyr);

			// Optionally, show confirmation message or update the UI
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("Martyr inserted successfully!");
			alert.showAndWait();
		});

		// Create "Martyr Screen" Button
		Button martyrScreenButton = new Button("Martyr Screen");
		martyrScreenButton.setLayoutX(14);
		martyrScreenButton.setLayoutY(546);
		martyrScreenButton.setPrefSize(120, 25);

		martyrScreenButton.setOnAction(e -> {
			thirdScreen(stage, avlTree, date);

		});

		// Add "Martyr Screen" Button to the pane
		pane.getChildren().add(martyrScreenButton);

		// Set up the scene and stage
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setTitle("Insert a Martyr");
		stage.show();
		return pane;
	}

	public String readfile(Stage primaryStage) throws ParseException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File ");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.txt", "*.*"));
		File file = fileChooser.showOpenDialog(primaryStage);

		if (file != null) {
			try (Scanner scanner = new Scanner(file)) {
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					if (line.isEmpty()) {
						continue; // Skip empty lines
					}

					String[] split = line.split(",");
					if (split.length < 6) {
						continue; // Skip invalid lines
					}

					String fullName = split[0];
					String date = split[1];
					LocalDate Date = parseDateFromFile(date);
					int age = 0;
					if (!split[2].trim().isEmpty()) {
						try {
							age = Integer.parseInt(split[2]);
						} catch (NumberFormatException e) {
							continue; // Skip lines with invalid age
						}
					}
					String districtName = split[4];
					if (!stackForDistricts.contains(districtName)) {
						stackForDistricts.push(districtName);
					}
					String locationName = split[3];

					char gender = split[5].charAt(0);

					Martyr martyr = new Martyr(fullName, date, age, districtName, locationName, gender);

					// Check if AVLTree for this date already exists in the hash table
					MartyrsAVLTree = hashTable.find(Date);
					if (MartyrsAVLTree == null) {
						// If AVLTree doesn't exist for this date, create a new one
						MartyrsAVLTree = new AVLTree();
						hashTable.insert(Date, MartyrsAVLTree);
					}
					// Inserting Martyr into the AVLTree
					MartyrsAVLTree.insert(martyr);
				}
				scanner.close();
				return "File read successfully.";
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return "Error reading file: " + e.getMessage();
			}
		} else {
			return "No file selected.";
		}
	}

	public LocalDate parseDateFromFile(String dateString) {
		DateTimeFormatter[] formatters = { DateTimeFormatter.ofPattern("M/d/yyyy"), // handles single-digit month and
																					// day
				DateTimeFormatter.ofPattern("MM/d/yyyy"), // handles two-digit month and single-digit day
				DateTimeFormatter.ofPattern("M/dd/yyyy"), // handles single-digit month and two-digit day
				DateTimeFormatter.ofPattern("MM/dd/yyyy") // handles two-digit month and day
		};

		for (DateTimeFormatter formatter : formatters) {
			try {
				return LocalDate.parse(dateString, formatter);
			} catch (DateTimeParseException e) {
			}
		}

		// If none of the formatters succeed, throw an exception
		throw new IllegalArgumentException("Invalid date format: " + dateString);
	}

	private void showAlert(AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private boolean isValidDate(LocalDate date) {
		// Validate if the date is not in the future
		if (date.isAfter(LocalDate.now())) {
			return false;
		}
		return true;
	}

	// method to convert LocalDate to a consistent string format
	public String formatDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd format
		return date.format(formatter);
	}

	public double averageMartyrs(AVLTree tree) {
		// Calculate the total number of martyrs
		int totalMartyrs = tree.size();

		// Calculate the total number of districts with martyrs
		int totalDistricts = countDistricts(tree.root);

		// Calculate the average number of martyrs per district
		if (totalDistricts != 0) {
			return (double) totalMartyrs / totalDistricts;
		} else {
			return 0.0; // Return 0 if there are no districts with martyrs
		}
	}

	// Helper method to count the number of districts with martyrs
	private int countDistricts(TNode node) {
		if (node == null) {
			return 0;
		}
		int count = 1;
		count += countDistricts(node.left);
		count += countDistricts(node.right);
		return count;
	}

	public int countTotalMartyrs(HashTable hashTable) {
		int totalCount = 0;

		// Get the hash table array
		HashNode[] table = hashTable.getTable();

		// Iterate over each entry in the hash table
		for (HashNode node : table) {
			if (node != null && node.flag == 'F') { // Check if the node is not null and contains valid data
				// Get the AVL tree for this date
				AVLTree martyrsAVLTree = node.MartyrsAVLTree;

				// If AVL tree exists, add its size to the total count
				if (martyrsAVLTree != null) {
					totalCount += martyrsAVLTree.size();
				}
			}
		}

		return totalCount;
	}

	private void printLevel(TNode root, int level, StringBuilder treeData) {
		if (root == null) {
			return;
		}
		if (level == 1) {
			treeData.append(root.getData()).append("\n");
		} else if (level > 1) {
			printLevel(root.getRight(), level - 1, treeData);
			printLevel(root.getLeft(), level - 1, treeData);
		}
	}

	public void display(List<Martyr> martyrs) {
		Stage stage = new Stage();

		// Convert list to array
		Martyr[] martyrArray = martyrs.toArray(new Martyr[0]);

		// Sort martyrs by age using heap sort
		HeapSort.heapSort(martyrArray);

		TableView<Martyr> table = new TableView<>();

		TableColumn<Martyr, String> fullNameCol = new TableColumn<>("Full Name");
		fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));

		TableColumn<Martyr, Integer> ageCol = new TableColumn<>("Age");
		ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

		table.getColumns().addAll(fullNameCol, ageCol);

		for (Martyr martyr : martyrArray) {
			table.getItems().add(martyr);
		}

		VBox layout = new VBox(10);
		layout.getChildren().add(table);

		Scene scene = new Scene(layout, 500, 500);
		stage.setScene(scene);
		stage.setTitle("Martyrs Sorted by Age");
		stage.show();
	}

	public List<Martyr> avlTreeToList(TNode root) {
		List<Martyr> martyrs = new ArrayList<>();
		// Traverse the AVL tree and add martyrs to the list
		addMartyrsToList(root, martyrs);
		return martyrs;
	}

	private void addMartyrsToList(TNode node, List<Martyr> martyrs) {
		if (node != null) {
			addMartyrsToList(node.getLeft(), martyrs);
			martyrs.add((Martyr) node.getData()); // Cast the node data to Martyr and add to the list
			addMartyrsToList(node.getRight(), martyrs);
		}
	}

	public String writeFile(Stage primaryStage) {
		// choose the file to write the new Sorted data inside it
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Resource File");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.txt", "*.*")); // show the file in
																									// stage
		File file = fileChooser.showSaveDialog(primaryStage); // Changed to showSaveDialog for saving

		if (file != null) { // check if a file was selected
			try (PrintWriter writer = new PrintWriter(file)) { // try-with-resources to ensure the writer is closed
				// Iterate over all the nodes in the hash table
				for (HashNode node : hashTable.getTable()) {
					if (node != null && node.flag == 'F') {
						// Get the AVL tree associated with the current node
						AVLTree avlTree = node.MartyrsAVLTree;
						// If AVL tree is not null, save martyrs' information to the file
						if (avlTree != null) {
							saveMartyrsInfo(avlTree.getRoot(), writer);
						}
					}
				}
				return "File Saved successfully";
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return "No file selected.";
	}

	private void saveMartyrsInfo(TNode node, PrintWriter writer) {
		if (node != null) {
			// Save information for the left subtree
			saveMartyrsInfo(node.getLeft(), writer);
			// Extract martyr's information
			String line = node.data.getFullName() + "," + node.data.getDateOfDeath() + "," + node.data.getAge() + ","
					+ node.data.getLocation() + "," + node.data.getDistrict() + "," + node.data.getGender();
			// Write the line to the file
			writer.println(line);
			// Save information for the right subtree
			saveMartyrsInfo(node.getRight(), writer);
		}
	}

}
