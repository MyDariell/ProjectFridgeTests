package virtual.fridge.test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Stack;

//classes
import client.FridgeModel;
import client.Controller;
import org.w3c.dom.css.Rect;

public class Main extends Application{

    FridgeModel fridge;
    boolean[] clicked1 = {false};
    boolean[] clicked2 = {false};
    boolean[] clicked3 = {false};
    boolean[] clicked4 = {false};
    boolean[] clicked5 = {false};
    boolean[] clicked6 = {false};
    boolean[] clicked7 = {false};
    boolean[] clicked8 = {false};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        //initialize Fridge
        this.fridge = new FridgeModel();

        Image icon = new Image("Logo.png");
        stage.getIcons().add(icon);
        stage.setTitle("Virtual Fridge");
        stage.setWidth(500);
        stage.setHeight(800);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("ESC"));

        //start of log in screen
        OnboardingScene(stage);

        stage.show();
    }

    //    log in scene to be designed fully later
    public void OnboardingScene(Stage stage) {

        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("vbox-onboarding");
        Scene onboardingScene = new Scene(vbox, 500, 800);
        onboardingScene.getStylesheets().add(getClass().getResource("/OnboardingScene.css").toExternalForm());

        // Logo image
        Image logo = new Image("Logo.png");
        ImageView logoView = new ImageView(logo);
        logoView.getStyleClass().add("logo");
        logoView.setFitWidth(150);
        logoView.setFitHeight(150);
        Rectangle logoClip = new Rectangle(150,150);
        logoClip.setArcHeight(70);
        logoClip.setArcWidth(70);
        logoView.setClip(logoClip);

        // Welcome text
        Label title1 = new Label("Hi, welcome to\nVirtual Fridge!");
        title1.getStyleClass().add("title-onboard");
        title1.setPadding(new Insets(30,0,0,0)); //top,right,bottom,left

        //text
        Label text1 = new Label("What's your name?");
        text1.getStyleClass().add("text-onboard");

        Label text2 = new Label("Any dietary preference?");
        text2.getStyleClass().add("text-onboard");

        // Name field
        Rectangle textClip = new Rectangle(450, 65);
        textClip.setArcWidth(50);
        textClip.setArcHeight(50);
        TextField nameField = new TextField();
        nameField.setPromptText("Type in name...");
        nameField.getStyleClass().add("text-field-name");
        nameField.setPrefWidth(450);
        nameField.setPrefHeight(65);
        nameField.setClip(textClip);

        // dietary buttons
        Button dietaryButton1 = createDietaryButton("Vegan");
        Button dietaryButton2 = createDietaryButton("Vegetarian");
        Button dietaryButton3 = createDietaryButton("Pescetarian");
        Button dietaryButton4 = createDietaryButton("Halal");
        Button dietaryButton5 = createDietaryButton("Kosher");
        Button dietaryButton6 = createDietaryButton("Keto");

        // Error message label
        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label-onboard");
        errorLabel.setVisible(false);

        dietaryButton1.setOnMouseClicked(e -> toggleButtonState(dietaryButton1, clicked1));
        dietaryButton2.setOnMouseClicked(e -> toggleButtonState(dietaryButton2, clicked2));
        dietaryButton3.setOnMouseClicked(e -> toggleButtonState(dietaryButton3, clicked3));
        dietaryButton4.setOnMouseClicked(e -> toggleButtonState(dietaryButton4, clicked4));
        dietaryButton5.setOnMouseClicked(e -> toggleButtonState(dietaryButton5, clicked5));
        dietaryButton6.setOnMouseClicked(e -> toggleButtonState(dietaryButton6, clicked6));

        GridPane buttonGrid = new GridPane();
        buttonGrid.getStyleClass().add("button-box-style");
        buttonGrid.setPadding(new Insets(0, 0, 0, 0));
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);
        buttonGrid.add(dietaryButton1, 0, 0);
        buttonGrid.add(dietaryButton2, 1, 0);
        buttonGrid.add(dietaryButton3, 2, 0);
        buttonGrid.add(dietaryButton4, 0, 1);
        buttonGrid.add(dietaryButton5, 1, 1);
        buttonGrid.add(dietaryButton6, 2, 1);

        Rectangle nextButtonClip = new Rectangle(450, 65);
        nextButtonClip.setArcWidth(50);
        nextButtonClip.setArcHeight(50);
        Button nextButton = new Button("Next →");
        nextButton.getStyleClass().add("button-default");
        nextButton.setPrefWidth(450);
        nextButton.setPrefHeight(65);
        nextButton.setClip(nextButtonClip);

        nextButton.setOnMousePressed(e -> toggleButtonState(nextButton, clicked7));
        nextButton.setOnMouseClicked(e -> OnboardScene2(stage));

        HBox textBox1 = new HBox(text1);
        textBox1.setPadding(new Insets(30,0,0,20)); //top,right,bottom,left
        textBox1.setAlignment(Pos.CENTER_LEFT);

        HBox textTypeBox = new HBox(nameField);
        textTypeBox.setPadding(new Insets(0,0,0,0));
        textTypeBox.setAlignment(Pos.CENTER);

        HBox textBox2 = new HBox(text2);
        textBox2.setPadding(new Insets(20,0,0,20));
        textBox2.setAlignment(Pos.CENTER_LEFT);

        vbox.getChildren().addAll(logoView, title1, textBox1, textTypeBox, textBox2, buttonGrid, errorLabel, nextButton);
        stage.setScene(onboardingScene);
    }

    public void OnboardScene2(Stage stage){
        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("vbox-onboarding");
        Scene onboardingScene = new Scene(vbox, 500, 800);
        onboardingScene.getStylesheets().add(getClass().getResource("/OnboardingScene.css").toExternalForm());

        // Logo image
        Image logo = new Image("Logo.png");
        ImageView logoView = new ImageView(logo);
        logoView.getStyleClass().add("logo");
        logoView.setFitWidth(150);
        logoView.setFitHeight(150);
        Rectangle logoClip = new Rectangle(150,150);
        logoClip.setArcHeight(70);
        logoClip.setArcWidth(70);
        logoView.setClip(logoClip);

        // Welcome text
        Label title1 = new Label("Almost there!");
        title1.getStyleClass().add("title-onboard");
        title1.setPadding(new Insets(30,0,0,0)); //top,right,bottom,left

        //text
        Label text1 = new Label("How many days do you want to be\nnotified before something expires?");
        text1.getStyleClass().add("text-onboard");

        //text
        Label text2 = new Label("Hold and drag or click the slider!");
        text2.getStyleClass().add("text-onboard");

        //slider
        Slider slider = new Slider(0, 7, 0); // Min = 0, Max = 7, Initial = 0
        slider.setMajorTickUnit(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.getStyleClass().add("slider-style");
        slider.setMaxWidth(450);
        Rectangle sliderClip = new Rectangle(450, 65);
        sliderClip.setArcWidth(50);
        sliderClip.setArcHeight(50);
        slider.setClip(sliderClip);
        slider.applyCss();
        slider.layout();

        // Label to display the slider's value
        Label valueLabel = new Label(String.valueOf(slider.getValue()) + " days");
        valueLabel.getStyleClass().add("slider-text");
        // Add a listener to update the label when the slider value changes
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            valueLabel.setText(newValue.intValue() + " days");
        });

        // Name field
        Rectangle textClip = new Rectangle(450, 65);
        textClip.setArcWidth(50);
        textClip.setArcHeight(50);
        TextField nameField = new TextField();
        nameField.setPromptText("Type in name...");
        nameField.getStyleClass().add("text-field-name");
        nameField.setPrefWidth(450);
        nameField.setPrefHeight(65);
        nameField.setClip(textClip);

        // Error message label
        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label-onboard");
        errorLabel.setVisible(false);

        Rectangle doneButtonClip = new Rectangle(450, 65);
        doneButtonClip.setArcWidth(50);
        doneButtonClip.setArcHeight(50);
        Button doneButton = new Button("Next →");
        doneButton.getStyleClass().add("button-default");
        doneButton.setPrefWidth(450);
        doneButton.setPrefHeight(65);
        doneButton.setClip(doneButtonClip);

        doneButton.setOnMousePressed(e -> toggleButtonState(doneButton, clicked8));
        doneButton.setOnMouseClicked(e -> MainScene(stage));

        HBox textBox1 = new HBox(text1);
        textBox1.setPadding(new Insets(30,0,0,0)); //top,right,bottom,left
        textBox1.setAlignment(Pos.CENTER);

        HBox textBox2 = new HBox(text2);
        textBox2.setPadding(new Insets(50,0,0,0));
        textBox2.setAlignment(Pos.CENTER);

        StackPane sliderPane = new StackPane(slider, valueLabel);
        sliderPane.setPadding(new Insets(10,0,120,0));
        sliderPane.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(logoView, title1, textBox1, textBox2, sliderPane, doneButton);
        stage.setScene(onboardingScene);
    }

    public void MainScene(Stage stage) {

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("vbox-main");
        vbox.setMinSize(465, 740);
        Scene mainScene = new Scene(vbox, 500, 800);
        mainScene.getStylesheets().add(getClass().getResource("/MainStyle.css").toExternalForm());

        ListView listView = new ListView();
        listView.getStyleClass().add("list-view-default");
        listView.setPrefSize(400, 546);
        listView.setEditable(true);

        // Configure Search Field
        TextField searchField = new TextField();
        searchField.setPromptText("Add Items to Fridge");
        searchField.getStyleClass().add("text-field-default");

        HBox searchBox = new HBox(10, searchField);
        searchBox.setAlignment(Pos.CENTER);

        ListView<String> suggestionList = new ListView<>();
        suggestionList.setVisible(false);
        suggestionList.setPrefHeight(150);

        // Update suggestions on text input
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                suggestionList.setVisible(false);
            } else {
                //Get the filtered results from the controller
                ArrayList<String> results = Controller.getSearchResults(newValue);
                //Converter results to observable list
                ObservableList<String> filteredItems = FXCollections.observableArrayList(results);
                suggestionList.setItems(filteredItems);
                suggestionList.setVisible(!filteredItems.isEmpty());
            }
        });

        // Add components to the VBox
        vbox.getChildren().addAll(searchField, searchBox, suggestionList, listView);

        stage.setScene(mainScene);
    }

    private void toggleButtonState(Button button, boolean[] clickedState) {
        if (clickedState[0]) {
            button.getStyleClass().remove("button-pressed");
            button.getStyleClass().add("button-default");
        } else {
            button.getStyleClass().remove("button-default");
            button.getStyleClass().add("button-pressed");
        }
        //toggle between clicked and not clicked
        //literally just for button colouring
        clickedState[0] = !clickedState[0];
    }

    private Button createDietaryButton(String text) {
        Button button = new Button(text);

        button.getStyleClass().add("button-default");
        button.setPrefWidth(140);
        button.setPrefHeight(65);

        Rectangle clip = new Rectangle(140, 65);
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        button.setClip(clip);

        return button;
    }
}
