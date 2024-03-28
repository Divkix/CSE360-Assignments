// generic package name
package me.divkix.cse360.hw4;

// Import the necessary classes
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Main class
public class HealyDashboard extends Application {

    // Constants
    public static final String setStyleButtonString = "-fx-font-size: 16pt; -fx-background-color: rgb(54, 94, 187); -fx-text-fill: black;"; // Set the font size and background color
    public static final String layoutStyleString = "-fx-padding: 20; -fx-alignment: center;"; // Add padding and center the components

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        // Initial view
        VBox loginView = mainLoginView(primaryStage);

        // Set the scene with height and width
        Scene scene = new Scene(loginView, 600, 600);

        // Set the title of the window
        primaryStage.setScene(scene); // Set the scene
        primaryStage.show(); // Show the window
    }

    // Method to build main menu view
    private VBox mainLoginView(Stage primaryStage) {
        VBox loginLayout = new VBox(35); // Create a layout with vertical spacing of 35

        loginLayout.setAlignment(Pos.CENTER); // Center the components
        loginLayout.setStyle(layoutStyleString); // Add padding and center the components

        // create a label called "Healy Dashboard"
        Label titleLabel = new Label("Welcome to Heart Health Imaging and Recording System"); // Create a label
        titleLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size

        // Patient Intake button
        Button patientIntakeButton = new Button("Patient Intake"); // Create a button
        patientIntakeButton.setStyle(setStyleButtonString); // Set the font size and background color
        patientIntakeButton.setPrefWidth(250); // Set the button width
        patientIntakeButton.setOnAction(e -> switchToPatientIntake(primaryStage)); // Switch to patient intake form

        // CT Scan Tech View button
        Button cTScanTechViewButton = new Button("CT Scan Tech View"); // Create a button
        cTScanTechViewButton.setStyle(setStyleButtonString); // Set the font size
        cTScanTechViewButton.setPrefWidth(250); // Set the button width
        cTScanTechViewButton.setOnAction(e -> switchToCTScanTechView(primaryStage)); // Switch to CT Scan Tech view

        // Patient View Screen
        Button patientViewButton = new Button("Patient View"); // Create a button
        patientViewButton.setStyle(setStyleButtonString); // Set the font size
        patientViewButton.setPrefWidth(250); // Set the button width
        patientViewButton.setOnAction(e -> switchToPatientView(primaryStage)); // Switch to patient view

        // Add the components to the layout
        loginLayout.getChildren().addAll(titleLabel, patientIntakeButton, cTScanTechViewButton, patientViewButton);

        // Return the layout
        return loginLayout;
    }

    // Method to switch to patient login when the patient login button is clicked
    private void switchToPatientIntake(Stage primaryStage) {
        VBox patientLoginLayout = new VBox(10); // Create a layout with vertical spacing of 10
        patientLoginLayout.setAlignment(Pos.CENTER); // Center the components
        patientLoginLayout.setStyle("-fx-padding: 20; -fx-alignment: center;"); // Add padding and center the components

        // create a label called "Patient Intake Form"
        Label titleLabel = new Label("Patient Intake Form"); // Create a label
        titleLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size

        // First Name text field
        Label patientFirstNameLabel = new Label("First Name:"); // Create a label for patient name
        TextField patientFirstNameField = new TextField(); // Create a text field for patient name
        patientFirstNameField.prefWidth(10);
        HBox firstnameBox = new HBox(5);
        firstnameBox.getChildren().addAll(patientFirstNameLabel, patientFirstNameField);

        // Last Name text field
        Label LastNameLabel = new Label("Last Name:"); // Create a label for password
        TextField lastname_text = new PasswordField(); // Create a password field for password
        lastname_text.setPrefWidth(150); // Set the width of the text field
        HBox lastnameBox = new HBox(5);
        lastnameBox.getChildren().addAll(LastNameLabel, lastname_text);

        // email field
        Label emailLabel = new Label("Email:"); // Create a label for password
        TextField emailField = new TextField(); // Create a password field for password
        emailField.setPrefWidth(150); // Set the width of the text field
        HBox emailBox = new HBox(5);
        emailBox.getChildren().addAll(emailLabel, emailField);

        // phone number field
        Label phoneLabel = new Label("Phone Number:"); // Create a label for password
        TextField phoneField = new TextField(); // Create a password field for password
        phoneField.setPrefWidth(150); // Set the width of the text field
        HBox phoneBox = new HBox(5);
        phoneBox.getChildren().addAll(phoneLabel, phoneField);

        // Health History
        Label healthHistoryLabel = new Label("Health History:");
        TextField healthHistoryField = new TextField();
        healthHistoryField.setPrefWidth(150); // Set the width of the text field
        HBox healthHistoryBox = new HBox(5);
        healthHistoryBox.getChildren().addAll(healthHistoryLabel, healthHistoryField);

        // Insurance ID
        Label insuranceIDLabel = new Label("Insurance ID:");
        TextField insuranceIDField = new TextField();
        insuranceIDField.setPrefWidth(150); // Set the width of the text field
        HBox insuranceIDBox = new HBox(5);
        insuranceIDBox.getChildren().addAll(insuranceIDLabel, insuranceIDField);

        // Create a layout for the patient intake form fields
        VBox patientIntakeLayout = new VBox(5); // Create a layout with vertical spacing of 5
        patientIntakeLayout.getChildren().addAll(firstnameBox, lastnameBox, emailBox, phoneBox, healthHistoryBox, insuranceIDBox); // Add the components to the layout

        // Login Button
        Button loginButton = new Button("Save Patient Info"); // Create a button for login
        loginButton.setStyle(setStyleButtonString); // Set the font size

        // Add Event Handler for loginButton to handle the login logic
        loginButton.setOnAction(e -> {
            // TODO: Add the patient intake logic here
        });

        // Add a back button to the top left corner
        Button backButton = new Button("Back"); // Create a button
        backButton.setOnAction(e -> start(primaryStage)); // Switch to the initial view
        backButton.setStyle(setStyleButtonString); // Set the font size

        // Add the components to the layout
        patientLoginLayout.getChildren().addAll(titleLabel, patientIntakeLayout, loginButton, backButton);

        // Set the scene with height and width
        Scene patientLoginScene = new Scene(patientLoginLayout, 600, 600);
        primaryStage.setScene(patientLoginScene); // Set the scene
    }

    // Method to switch to employee login when the patient login button is clicked
    private void switchToCTScanTechView(Stage primaryStage) {
        VBox patientLoginLayout = new VBox(10); // Create a layout with vertical spacing of 10
        patientLoginLayout.setAlignment(Pos.CENTER); // Center the components
        patientLoginLayout.setStyle("-fx-padding: 20; -fx-alignment: center;"); // Add padding and center the components

        // Patient ID label and text field
        Label patientIdLabel = new Label("Patient ID: "); // Create a label for employee name
        TextField patientIdTextField = new TextField(); // Create a text field for employee name
        patientIdTextField.setPrefWidth(150); // Set the width of the text field
        HBox patientIdBox = new HBox(5);
        patientIdBox.getChildren().addAll(patientIdLabel, patientIdTextField);

        // Total Agatston CAC Score label and text field
        Label totalCACScoreLabel = new Label("Total Agatston CAC Score: "); // Create a label for password
        TextField totalCACScoreTextField = new TextField(); // Create a password field for total Agatston CAC Score
        totalCACScoreTextField.setPrefWidth(150); // Set the width of the text field
        HBox totalCACScore = new HBox(5);
        totalCACScore.getChildren().addAll(totalCACScoreLabel, totalCACScoreTextField);

        // Vesel label Agaston CAC Score label
        Label vesselLevelAgastonCacScore = new Label("Vessel level Agaston CAC Score"); // Create a label for vessel level Agaston CAC Score

        // Label for LM and text field
        Label lmLabel = new Label("LM: "); // Create a label for LM
        TextField lmTextField = new TextField(); // Create a text field for LM
        lmTextField.setPrefWidth(150); // Set the width of the text field
        HBox lmBox = new HBox(5);
        lmBox.getChildren().addAll(lmLabel, lmTextField);

        // Label for LAD and text field
        Label ladLabel = new Label("LAD: "); // Create a label for LAD
        TextField ladTextField = new TextField(); // Create a text field for LAD
        ladTextField.setPrefWidth(150); // Set the width of the text field
        HBox ladBox = new HBox(5);
        ladBox.getChildren().addAll(ladLabel, ladTextField);

        // Label for LCX and text field
        Label lcxLabel = new Label("LCX: "); // Create a label for LCX
        TextField lcxTextField = new TextField(); // Create a text field for LCX
        lcxTextField.setPrefWidth(150); // Set the width of the text field
        HBox lcxBox = new HBox(5);
        lcxBox.getChildren().addAll(lcxLabel, lcxTextField);

        // Label for RCA and text field
        Label rcaLabel = new Label("RCA: "); // Create a label for RCA
        TextField rcaTextField = new TextField(); // Create a text field for RCA
        rcaTextField.setPrefWidth(150); // Set the width of the text field
        HBox rcaBox = new HBox(5);
        rcaBox.getChildren().addAll(rcaLabel, rcaTextField);

        // Label for PDA and text field
        Label pdaLabel = new Label("PDA: "); // Create a label for PDA
        TextField pdaTextField = new TextField(); // Create a text field for PDA
        pdaTextField.setPrefWidth(150); // Set the width of the text field
        HBox pdaBox = new HBox(5);
        pdaBox.getChildren().addAll(pdaLabel, pdaTextField);

        // Login Button
        Button saveButton = new Button("Save Information"); // Create a button for saving scores information
        saveButton.setStyle(setStyleButtonString); // Set the font size

        // Add Event Handler for saveButton to handle the login logic
        saveButton.setOnAction(e -> {
            // TODO: Add the save information for CT Scan Tech logic here
        });

        // Add a back button to the top left corner
        Button backButton = new Button("Back"); // Create a button
        backButton.setOnAction(e -> start(primaryStage)); // Switch to the initial view
        backButton.setStyle(setStyleButtonString); // Set the font size

        // Add the components to the layout
        patientLoginLayout.getChildren().addAll(patientIdBox, totalCACScore, vesselLevelAgastonCacScore, lmBox, ladBox, lcxBox, rcaBox, pdaBox, saveButton, backButton);

        // Set the scene with height and width
        Scene patientLoginScene = new Scene(patientLoginLayout, 600, 600);
        primaryStage.setScene(patientLoginScene); // Set the scene
    }

    // Method to switch to patient information view
    private void switchToPatientView(Stage primaryStage) {
        VBox patientLoginLayout = new VBox(10); // Create a layout with vertical spacing of 10
        patientLoginLayout.setAlignment(Pos.CENTER); // Center the components
        patientLoginLayout.setStyle("-fx-padding: 20; -fx-alignment: center;"); // Add padding and center the components

        // Components for patient login
        Label enterPatientIdLabel = new Label("Enter the Patient ID: "); // Create a label for employee name
        TextField patientIdTextField = new TextField(); // Create a text field for patient id

        // Login Button
        Button patientReloadInformationButton = new Button("Load Patient Information"); // Create a button for login
        patientReloadInformationButton.setStyle(setStyleButtonString); // Set the font size

        // Add Event Handler for patientReloadInformationButton to handle the login logic
        patientReloadInformationButton.setOnAction(e -> {
            int intInsuranceOrPatientId = Integer.parseInt(patientIdTextField.getText()); // Convert the patient id to an integer
            loadPatientResults(primaryStage, intInsuranceOrPatientId);
        });

        // Add a back button to the top left corner
        Button backButton = new Button("Back"); // Create a back button
        backButton.setOnAction(e -> start(primaryStage)); // Switch to the initial view
        backButton.setStyle(setStyleButtonString); // Set the font size

        // Add the components to the layout
        patientLoginLayout.getChildren().addAll(enterPatientIdLabel, patientIdTextField, patientReloadInformationButton, backButton);

        // Set the scene with height and width
        Scene patientLoginScene = new Scene(patientLoginLayout, 600, 600);
        primaryStage.setScene(patientLoginScene); // Set the scene
    }

    private void loadPatientResults(Stage primaryStage, int insuranceOrPatientId) {
        VBox loadPatientResults = new VBox(10); // Create a layout with vertical spacing of 10
        loadPatientResults.setAlignment(Pos.CENTER); // Center the components
        loadPatientResults.setStyle("-fx-padding: 20; -fx-alignment: center;"); // Add padding and center the components

        // todo: fetch patient name from the database
        String patientName = "";
        // Components for patient login
        Label patientResultsLabel = new Label(String.format("Hello %s, Patient Results:",patientName)); // Create a label for employee name

        // Add a back button to the top left corner
        Button backButton = new Button("Back"); // Create a button
        backButton.setOnAction(e -> start(primaryStage)); // Switch to the initial view
        backButton.setStyle(setStyleButtonString); // Set the font size

        // Add the components to the layout
        loadPatientResults.getChildren().addAll(patientResultsLabel, backButton);

        // Set the scene with height and width
        Scene loadPatientResultsScene = new Scene(loadPatientResults, 600, 600);
        primaryStage.setScene(loadPatientResultsScene); // Set the scene
    }
}
