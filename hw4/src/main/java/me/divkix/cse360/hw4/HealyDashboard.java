// generic package name
package me.divkix.cse360.hw4;

// Import the necessary classes
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Main class
public class HealyDashboard extends Application {

    // Constants
    private static final String setStyleButtonString = "-fx-font-size: 16pt; -fx-background-color: rgb(54, 94, 187); -fx-text-fill: black;"; // Set the font size and background color
    private static final String layoutStyleString = "-fx-padding: 20; -fx-alignment: center;"; // Add padding and center the components
    private static final String patient_intake_db_table = "patient_intake";
    private static final String patient_results_db_table = "patient_results";

    // Helper function to save data to the database using prepared statements
    // The function takes in a variable number of arguments
    // The arguments are expected to be in pairs of column name and value
    // example: saveData("first_name", "John", "last_name", "Doe", "email", "john.doe@example.com")
    public static void saveData(String tableName, Object... args) {
        // Get the database connection
        Connection conn = DatabaseConnection.getConnection();

        // Build the SQL query using StringBuilder
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
        List<Object> values = new ArrayList<>(); // Create a list to store the values

        // create a loop to iterate over the arguments and build the SQL query and values list by pairs
        // add them to the prepared statement
        for (int i = 0; i < args.length; i += 2) {
            sql.append((String) args[i]).append(", ");
            values.add(args[i + 1]);
        }

        sql.setLength(sql.length() - 2); // Remove the trailing ", "
        sql.append(") VALUES ("); // Add the VALUES, close and open parenthesis

        // Add the required number of "?" placeholders
        sql.append("?, ".repeat(Math.max(0, values.size())));

        // Remove the trailing ", "
        sql.setLength(sql.length() - 2);
        sql.append(")"); // add the closing parenthesis

        try {
            PreparedStatement statement = conn.prepareStatement(sql.toString());
            //print the sql query
            System.out.println(sql);
            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data saved successfully!");
            } else {
                System.out.println("Failed to save data.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // class to get data from the database
    // The function takes in a table name and patient ID
    public static ResultSet getData(String tableName, String patientId) {
        // Get the database connection
        Connection conn = DatabaseConnection.getConnection();

        // Create the SQL query
        String sql = "SELECT * FROM " + tableName + " WHERE patient_id = ?";

        try {
            // Prepare the SQL query
            PreparedStatement statement = conn.prepareStatement(sql);

            // Set the patient ID parameter
            statement.setString(1, patientId);

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery();

            // Return the result set
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

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
        patientLoginLayout.setStyle(layoutStyleString); // Add padding and center the components

        // create a label called "Patient Intake Form"
        Label titleLabel = new Label("Patient Intake Form"); // Create a label
        titleLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size

        // First Name text field
        Label patientFirstNameLabel = new Label("First Name:"); // Create a label for patient name
        TextField patientFirstNameField = new TextField(); // Create a text field for patient name
        patientFirstNameField.prefWidth(10);
        HBox firstnameBox = new HBox(5);
        firstnameBox.getChildren().addAll(patientFirstNameLabel, patientFirstNameField);

        // Middle Name text field
        Label patientMiddleNameLabel = new Label("Middle Name:"); // Create a label for patient name
        TextField patientMiddleNameField = new TextField(); // Create a text field for patient name
        patientMiddleNameField.prefWidth(10);
        HBox middlenameBox = new HBox(5);
        middlenameBox.getChildren().addAll(patientMiddleNameLabel, patientMiddleNameField);

        // Last Name text field
        Label patientLastNameLabel = new Label("Last Name:"); // Create a label for last name
        TextField patientLastNameField = new TextField(); // Create a text field for last name
        patientLastNameField.setPrefWidth(150); // Set the width of the text field
        HBox lastnameBox = new HBox(5);
        lastnameBox.getChildren().addAll(patientLastNameLabel, patientLastNameField);

        // email field
        Label emailLabel = new Label("Email:"); // Create a label for email
        TextField emailField = new TextField(); // Create a text field for email
        emailField.setPrefWidth(150); // Set the width of the text field
        HBox emailBox = new HBox(5);
        emailBox.getChildren().addAll(emailLabel, emailField);

        // phone number field
        Label phoneLabel = new Label("Phone Number:"); // Create a label for phone number
        TextField phoneField = new TextField(); // Create a text field for phone number
        phoneField.setPrefWidth(150); // Set the width of the text field
        HBox phoneBox = new HBox(5);
        phoneBox.getChildren().addAll(phoneLabel, phoneField);

        // Health History
        Label healthHistoryLabel = new Label("Health History:");
        TextField healthHistoryField = new TextField();
        healthHistoryField.setPrefWidth(150); // Set the width of the text field
        HBox healthHistoryBox = new HBox(5);
        healthHistoryBox.getChildren().addAll(healthHistoryLabel, healthHistoryField);

        // Insurance Provider Box
        Label insuranceProviderLabel = new Label("Insurance Provider:");
        TextField insuranceProviderField = new TextField();
        insuranceProviderField.setPrefWidth(150); // Set the width of the text field
        HBox insuranceProviderBox = new HBox(5);
        insuranceProviderBox.getChildren().addAll(insuranceProviderLabel, insuranceProviderField);

        // Insurance ID
        Label insuranceIDLabel = new Label("Insurance ID:");
        TextField insuranceIDField = new TextField();
        insuranceIDField.setPrefWidth(150); // Set the width of the text field
        HBox insuranceIDBox = new HBox(5);
        insuranceIDBox.getChildren().addAll(insuranceIDLabel, insuranceIDField);

        // Create a layout for the patient intake form fields
        VBox patientIntakeLayout = new VBox(5); // Create a layout with vertical spacing of 5
        patientIntakeLayout.getChildren().addAll(firstnameBox, middlenameBox, lastnameBox, emailBox, phoneBox, healthHistoryBox, insuranceProviderBox, insuranceIDBox); // Add the components to the layout

        // Login Button
        Button loginButton = new Button("Save Patient Info"); // Create a button for login
        loginButton.setStyle(setStyleButtonString); // Set the font size

        // Add Event Handler for loginButton to handle the login logic
        loginButton.setOnAction(e -> {
            String patientId = patientLastNameField.getText() + "_" + patientFirstNameField.getText() + "_" + phoneField.getText();
            saveData(
                    patient_intake_db_table,
                    "first_name", patientFirstNameField.getText(),
                    "middle_name", patientMiddleNameField.getText(),
                    "last_name", patientLastNameField.getText(),
                    "email", emailField.getText(),
                    "phone_number", phoneField.getText(),
                    "insurance_provider", insuranceProviderField.getText(),
                    "patient_id", patientId
            );
        });

        // Add a back button to the top left corner
        Button backButton = new Button("Back"); // Create a back button
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
        patientLoginLayout.setStyle(layoutStyleString); // Add padding and center the components

        // Patient ID label and text field
        Label patientIdLabel = new Label("Patient ID: "); // Create a label for employee name
        TextField patientIdTextField = new TextField(); // Create a text field for employee name
        patientIdTextField.setPrefWidth(150); // Set the width of the text field
        HBox patientIdBox = new HBox(5);
        patientIdBox.getChildren().addAll(patientIdLabel, patientIdTextField);

        // Total Agatston CAC Score label and text field
        Label totalCACScoreLabel = new Label("Total Agatston CAC Score: "); // Create a label for cac score
        TextField totalCACScoreTextField = new TextField(); // Create a text field for total Agatston CAC Score
        totalCACScoreTextField.setPrefWidth(150); // Set the width of the text field
        HBox totalCACScore = new HBox(5); // make a hbox to put them horizontally in order
        totalCACScore.getChildren().addAll(totalCACScoreLabel, totalCACScoreTextField); // add to hbox

        // Vesel label Agaston CAC Score label
        Label vesselLevelAgastonCacScore = new Label("Vessel level Agaston CAC Score"); // Create a label for vessel level Agaston CAC Score

        // Label for LM and text field
        Label lmLabel = new Label("LM: "); // Create a label for LM
        TextField lmTextField = new TextField(); // Create a text field for LM
        lmTextField.setPrefWidth(150); // Set the width of the text field
        HBox lmBox = new HBox(5); // make a hbox to put them horizontally in order
        lmBox.getChildren().addAll(lmLabel, lmTextField); // add to hbox

        // Label for LAD and text field
        Label ladLabel = new Label("LAD: "); // Create a label for LAD
        TextField ladTextField = new TextField(); // Create a text field for LAD
        ladTextField.setPrefWidth(150); // Set the width of the text field
        HBox ladBox = new HBox(5); // make a hbox to put them horizontally in order
        ladBox.getChildren().addAll(ladLabel, ladTextField); // add to hbox

        // Label for LCX and text field
        Label lcxLabel = new Label("LCX: "); // Create a label for LCX
        TextField lcxTextField = new TextField(); // Create a text field for LCX
        lcxTextField.setPrefWidth(150); // Set the width of the text field
        HBox lcxBox = new HBox(5); // make a hbox to put them horizontally in order
        lcxBox.getChildren().addAll(lcxLabel, lcxTextField); // add to hbox

        // Label for RCA and text field
        Label rcaLabel = new Label("RCA: "); // Create a label for RCA
        TextField rcaTextField = new TextField(); // Create a text field for RCA
        rcaTextField.setPrefWidth(150); // Set the width of the text field
        HBox rcaBox = new HBox(5); // make a hbox to put them horizontally in order
        rcaBox.getChildren().addAll(rcaLabel, rcaTextField); // add to hbox

        // Label for PDA and text field
        Label pdaLabel = new Label("PDA: "); // Create a label for PDA
        TextField pdaTextField = new TextField(); // Create a text field for PDA
        pdaTextField.setPrefWidth(150); // Set the width of the text field
        HBox pdaBox = new HBox(5); // make a hbox to put them horizontally in order
        pdaBox.getChildren().addAll(pdaLabel, pdaTextField); // add to hbox

        // Login Button
        Button saveButton = new Button("Save Information"); // Create a button for saving scores information
        saveButton.setStyle(setStyleButtonString); // Set the font size

        // Add Event Handler for saveButton to handle the login logic
        saveButton.setOnAction(e -> {
            saveData(
                    patient_results_db_table,
                    "patient_id", patientIdTextField.getText(),
                    "agaston_cac_score", totalCACScoreTextField.getText(),
                    "lm_score", lmTextField.getText(),
                    "lad_score", ladTextField.getText(),
                    "lcx_score", lcxTextField.getText(),
                    "rca_score", rcaTextField.getText(),
                    "pda_score", pdaTextField.getText()
            );
        });

        // Add a back button to the top left corner
        Button backButton = new Button("Back"); // Create a back button
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
        patientLoginLayout.setStyle(layoutStyleString); // Add padding and center the components

        // Components for patient login
        Label enterPatientIdLabel = new Label("Enter the Patient ID: "); // Create a label for employee name
        TextField patientIdTextField = new TextField(); // Create a text field for patient id

        // patient info reload button
        Button patientReloadInformationButton = new Button("Load Patient Information"); // Create a button for login
        patientReloadInformationButton.setStyle(setStyleButtonString); // Set the font size

        // Add Event Handler for patientReloadInformationButton to handle the login logic
        patientReloadInformationButton.setOnAction(e -> { // Convert the patient id to an integer
            String patientId = patientIdTextField.getText();
            loadPatientResults(primaryStage, patientId);
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

    private void loadPatientResults(Stage primaryStage, String insuranceOrPatientId) {
        VBox loadPatientResults = new VBox(10); // Create a layout with vertical spacing of 10
        loadPatientResults.setAlignment(Pos.CENTER); // Center the components
        loadPatientResults.setStyle(layoutStyleString); // Add padding and center the components

        // put base variables here to be used in the try catch block
        String firstName = "";
        double totalAgatstonCACScore = 0.0;
        double lmScore = 0.0;
        double ladScore = 0.0;
        double lcxScore = 0.0;
        double rcaScore = 0.0;
        double pdaScore = 0.0;

        // retrieve first_name from patient_info table
        try {
            ResultSet patientResults = getData(patient_intake_db_table, insuranceOrPatientId);
            if (patientResults.next()) { // Move the cursor to the first row
                firstName = patientResults.getString("first_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // retrieve patient information from the database
        try {
            ResultSet patientResults = getData(patient_results_db_table, String.valueOf(insuranceOrPatientId));
            if (patientResults.next()) { // Move the cursor to the first row
                totalAgatstonCACScore = patientResults.getDouble("agaston_cac_score");
                lmScore = patientResults.getDouble("lm_score");
                ladScore = patientResults.getDouble("lad_score");
                lcxScore = patientResults.getDouble("lcx_score");
                rcaScore = patientResults.getDouble("rca_score");
                pdaScore = patientResults.getDouble("pda_score");
                System.out.println("First Name: " + firstName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Components for patient login
        Label patientResultsLabel = new Label(String.format("Hello %s, Patient Results:", firstName)); // Create a label showcasing patient name

        // create a label for each of the risk score and put them in two separate vboxes side by side to show them horizontally
        VBox riskScores = new VBox(10);
        riskScores.setAlignment(Pos.CENTER);
        riskScores.setStyle(layoutStyleString);
        Label totalAgatstonCACScoreLabel = new Label("Total Agatston CAC Score: " + totalAgatstonCACScore); // Create a label for total Agatston CAC Score
        Label lmScoreLabel = new Label("LM Score: " + lmScore); // Create a label for LM Score
        Label ladScoreLabel = new Label("LAD Score: " + ladScore); // Create a label for LAD Score
        Label lcxScoreLabel = new Label("LCX Score: " + lcxScore); // Create a label for LCX Score
        Label rcaScoreLabel = new Label("RCA Score: " + rcaScore); // Create a label for RCA Score
        Label pdaScoreLabel = new Label("PDA Score: " + pdaScore); // Create a label for PDA Score
        riskScores.getChildren().addAll(totalAgatstonCACScoreLabel, lmScoreLabel, ladScoreLabel, lcxScoreLabel, rcaScoreLabel, pdaScoreLabel); // Add the components to the layout

        // Add a back button to the top left corner
        Button backButton = new Button("Back"); // Create a back button
        backButton.setOnAction(e -> start(primaryStage)); // Switch to the initial view
        backButton.setStyle(setStyleButtonString); // Set the font size

        // Add the components to the layout
        loadPatientResults.getChildren().addAll(patientResultsLabel, riskScores, backButton);

        // Set the scene with height and width
        Scene loadPatientResultsScene = new Scene(loadPatientResults, 600, 600);
        primaryStage.setScene(loadPatientResultsScene); // Set the scene
    }

    // Database Connection class
    // Helper class to connect to the local mysql database
    // The mysql database is called healy_health_system and is hosted on localhost (using docker)
    public static class DatabaseConnection {
        private static final String db_name = "healy_health_system";
        private static final String DB_URL = "jdbc:mysql://localhost:3306/" + db_name;
        private static final String DB_USER = "root";
        private static final String DB_PASSWORD = "password";

        public static Connection getConnection() {
            Connection conn = null;
            try {
                // Register the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Open a connection
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    }
}
