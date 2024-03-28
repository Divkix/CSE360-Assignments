// generic package name
package me.divkix.cse360.hw4;

// Import the necessary classes
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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

        // use try catch block to handle the prepared statement
        try {
            PreparedStatement statement = conn.prepareStatement(sql.toString());

            // loop over the values list and set the values in the prepared statement
            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }
            int rowsInserted = statement.executeUpdate(); // execute the prepared statement
            if (rowsInserted > 0) {
                // print success message if data is saved
                System.out.println("Data saved successfully!");
            } else {
                // print error message if data is not saved
                System.out.println("Failed to save data.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // print the stack trace
        } finally {
            try {
                conn.close(); // close the connection
            } catch (SQLException e) {
                e.printStackTrace(); // print the stack trace
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
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }

        // Return null if the result set is empty
        // this means that the patient ID was not found in the database
        return null;
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }

    // Start method to build the initial view
    @Override
    public void start(Stage primaryStage) {
        // Initial view
        VBox mainView = mainScreen(primaryStage);

        // Set the scene with height and width
        Scene scene = new Scene(mainView, 600, 600);

        // Set the title of the window
        primaryStage.setScene(scene); // Set the scene
        primaryStage.show(); // Show the window
    }

    // Method to build main menu view
    private VBox mainScreen(Stage primaryStage) {
        VBox mainScreenLayout = new VBox(35); // Create a layout with vertical spacing of 35
        mainScreenLayout.setAlignment(Pos.CENTER); // Center the components
        mainScreenLayout.setStyle(layoutStyleString); // Add padding and center the components

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
        mainScreenLayout.getChildren().addAll(titleLabel, patientIntakeButton, cTScanTechViewButton, patientViewButton);

        // Return the layout
        return mainScreenLayout;
    }

    // Method to switch to patient intake form when the patient intake button is clicked
    private void switchToPatientIntake(Stage primaryStage) {
        GridPane patientIntakeLayout = new GridPane(); // Create a GridPane layout
        patientIntakeLayout.setHgap(10); // Set horizontal gap
        patientIntakeLayout.setVgap(10); // Set vertical gap
        patientIntakeLayout.setAlignment(Pos.CENTER); // Center the components

        // create a label called "Patient Intake Form"
        Label titleLabel = new Label("Patient Intake Form"); // Create a label
        titleLabel.setStyle("-fx-font-size: 16pt;"); // Set the font size
        patientIntakeLayout.add(titleLabel, 0, 0, 2, 1); // Add to GridPane at column 0, row 0, span 2 columns

        // First Name text field
        Label patientFirstNameLabel = new Label("First Name:"); // Create a label for patient name
        TextField patientFirstNameField = new TextField(); // Create a text field for patient name
        patientFirstNameField.prefWidth(10);
        patientIntakeLayout.add(patientFirstNameLabel, 0, 1); // Add to GridPane at column 0, row 1
        patientIntakeLayout.add(patientFirstNameField, 1, 1); // Add to GridPane at column 1, row 1

        // Middle Name text field
        Label patientMiddleNameLabel = new Label("Middle Name:"); // Create a label for patient name
        TextField patientMiddleNameField = new TextField(); // Create a text field for patient name
        patientMiddleNameField.prefWidth(10);
        patientIntakeLayout.add(patientMiddleNameLabel, 0, 2); // Add to GridPane at column 0, row 2
        patientIntakeLayout.add(patientMiddleNameField, 1, 2); // Add to GridPane at column 1, row 2

        // Last Name text field
        Label patientLastNameLabel = new Label("Last Name:"); // Create a label for last name
        TextField patientLastNameField = new TextField(); // Create a text field for last name
        patientLastNameField.setPrefWidth(150); // Set the width of the text field
        patientIntakeLayout.add(patientLastNameLabel, 0, 3); // Add to GridPane at column 0, row 3
        patientIntakeLayout.add(patientLastNameField, 1, 3); // Add to GridPane at column 1, row 3

        // email field
        Label emailLabel = new Label("Email:"); // Create a label for email
        TextField emailField = new TextField(); // Create a text field for email
        emailField.setPrefWidth(150); // Set the width of the text field
        patientIntakeLayout.add(emailLabel, 0, 4); // Add to GridPane at column 0, row 4
        patientIntakeLayout.add(emailField, 1, 4); // Add to GridPane at column 1, row 4

        // phone number field
        Label phoneLabel = new Label("Phone Number:"); // Create a label for phone number
        TextField phoneField = new TextField(); // Create a text field for phone number
        phoneField.setPrefWidth(150); // Set the width of the text field
        patientIntakeLayout.add(phoneLabel, 0, 5); // Add to GridPane at column 0, row 5
        patientIntakeLayout.add(phoneField, 1, 5); // Add to GridPane at column 1, row 5

        // Health History
        Label healthHistoryLabel = new Label("Health History:");
        TextField healthHistoryField = new TextField();
        healthHistoryField.setPrefWidth(150); // Set the width of the text field
        patientIntakeLayout.add(healthHistoryLabel, 0, 6); // Add to GridPane at column 0, row 6
        patientIntakeLayout.add(healthHistoryField, 1, 6); // Add to GridPane at column 1, row 6

        // Insurance Provider Box
        Label insuranceProviderLabel = new Label("Insurance Provider:");
        TextField insuranceProviderField = new TextField();
        insuranceProviderField.setPrefWidth(150); // Set the width of the text field
        patientIntakeLayout.add(insuranceProviderLabel, 0, 7); // Add to GridPane at column 0, row 7
        patientIntakeLayout.add(insuranceProviderField, 1, 7); // Add to GridPane at column 1, row 7

        // Insurance ID
        Label insuranceIDLabel = new Label("Insurance ID:");
        TextField insuranceIDField = new TextField();
        insuranceIDField.setPrefWidth(150); // Set the width of the text field
        patientIntakeLayout.add(insuranceIDLabel, 0, 8); // Add to GridPane at column 0, row 8
        patientIntakeLayout.add(insuranceIDField, 1, 8); // Add to GridPane at column 1, row 8

        // Save Patient Info Button
        Button savePatientInfoButton = new Button("Save Patient Info"); // Create a button for save patient info
        savePatientInfoButton.setStyle(setStyleButtonString); // Set the font size
        patientIntakeLayout.add(savePatientInfoButton, 0, 9, 2, 1); // Add to GridPane at column 0, row 9, span 2 columns

        // Add Event Handler for savePatientInfoButton to handle the save info logic
        savePatientInfoButton.setOnAction(e -> {
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
        patientIntakeLayout.add(backButton, 0, 10, 2, 1); // Add to GridPane at column 0, row 10, span 2 columns

        // Set the scene with height and width
        Scene patientIntakeScene = new Scene(patientIntakeLayout, 600, 600);
        primaryStage.setScene(patientIntakeScene); // Set the scene

    }

    // Method to switch to ct scan tech view
    private void switchToCTScanTechView(Stage primaryStage) {

        // Create a layout for the CT Scan Tech View
        GridPane ctScanTechViewLayout = new GridPane(); // Create a GridPane layout
        ctScanTechViewLayout.setHgap(10); // Set horizontal gap
        ctScanTechViewLayout.setVgap(10); // Set vertical gap
        ctScanTechViewLayout.setAlignment(Pos.CENTER); // Center the components

        // Patient ID label and text field
        Label patientIdLabel = new Label("Patient ID: "); // Create a label for patient id
        TextField patientIdTextField = new TextField(); // Create a text field for id
        patientIdTextField.setPrefWidth(150); // Set the width of the text field
        ctScanTechViewLayout.add(patientIdLabel, 0, 0); // Add to GridPane at column 0, row 0
        ctScanTechViewLayout.add(patientIdTextField, 1, 0); // Add to GridPane at column 1, row 0

        // Total Agatston CAC Score label and text field
        Label totalCACScoreLabel = new Label("Total Agatston CAC Score: "); // Create a label for cac score
        TextField totalCACScoreTextField = new TextField(); // Create a text field for total Agatston CAC Score
        totalCACScoreTextField.setPrefWidth(150); // Set the width of the text field
        ctScanTechViewLayout.add(totalCACScoreLabel, 0, 1); // Add to GridPane at column 0, row 1
        ctScanTechViewLayout.add(totalCACScoreTextField, 1, 1); // Add to GridPane at column 1, row 1

        // Vesel label Agaston CAC Score label
        Label vesselLevelAgastonCacScore = new Label("Vessel level Agaston CAC Score"); // Create a label for vessel level Agaston CAC Score
        ctScanTechViewLayout.add(vesselLevelAgastonCacScore, 0, 2); // Add to GridPane at column 0, row 2

        // Label for LM and text field
        Label lmLabel = new Label("LM: "); // Create a label for LM
        TextField lmTextField = new TextField(); // Create a text field for LM
        lmTextField.setPrefWidth(150); // Set the width of the text field
        ctScanTechViewLayout.add(lmLabel, 0, 3); // Add to GridPane at column 0, row 3
        ctScanTechViewLayout.add(lmTextField, 1, 3); // Add to GridPane at column 1, row 3

        // Label for LAD and text field
        Label ladLabel = new Label("LAD: "); // Create a label for LAD
        TextField ladTextField = new TextField(); // Create a text field for LAD
        ladTextField.setPrefWidth(150); // Set the width of the text field
        ctScanTechViewLayout.add(ladLabel, 0, 4); // Add to GridPane at column 0, row 4
        ctScanTechViewLayout.add(ladTextField, 1, 4); // Add to GridPane at column 1, row 4

        // Label for LCX and text field
        Label lcxLabel = new Label("LCX: "); // Create a label for LCX
        TextField lcxTextField = new TextField(); // Create a text field for LCX
        lcxTextField.setPrefWidth(150); // Set the width of the text field
        ctScanTechViewLayout.add(lcxLabel, 0, 5); // Add to GridPane at column 0, row 5
        ctScanTechViewLayout.add(lcxTextField, 1, 5); // Add to GridPane at column 1, row 5

        // Label for RCA and text field
        Label rcaLabel = new Label("RCA: "); // Create a label for RCA
        TextField rcaTextField = new TextField(); // Create a text field for RCA
        rcaTextField.setPrefWidth(150); // Set the width of the text field
        ctScanTechViewLayout.add(rcaLabel, 0, 6); // Add to GridPane at column 0, row 6
        ctScanTechViewLayout.add(rcaTextField, 1, 6); // Add to GridPane at column 1, row 6

        // Label for PDA and text field
        Label pdaLabel = new Label("PDA: "); // Create a label for PDA
        TextField pdaTextField = new TextField(); // Create a text field for PDA
        pdaTextField.setPrefWidth(150); // Set the width of the text field
        ctScanTechViewLayout.add(pdaLabel, 0, 7); // Add to GridPane at column 0, row 7
        ctScanTechViewLayout.add(pdaTextField, 1, 7); // Add to GridPane at column 1, row 7

        // save Button
        Button saveButton = new Button("Save Information"); // Create a button for saving scores information
        saveButton.setStyle(setStyleButtonString); // Set the font size

        // Add Event Handler for saveButton to handle the save logic
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

        ctScanTechViewLayout.add(saveButton, 0, 8); // Add to GridPane at column 0, row 8
        ctScanTechViewLayout.add(backButton, 1, 8); // Add to GridPane at column 1, row 8

        // Set the scene with height and width
        Scene ctScanTechViewScene = new Scene(ctScanTechViewLayout, 600, 600);
        primaryStage.setScene(ctScanTechViewScene); // Set the scene
    }

    // Method to switch to patient information view
    private void switchToPatientView(Stage primaryStage) {
        VBox patientViewLayout = new VBox(10); // Create a layout with vertical spacing of 10
        patientViewLayout.setAlignment(Pos.CENTER); // Center the components
        patientViewLayout.setStyle(layoutStyleString); // Add padding and center the components

        // patient id label and text field
        Label enterPatientIdLabel = new Label("Enter the Patient ID: "); // Create a label for patient id
        TextField patientIdTextField = new TextField(); // Create a text field for patient id

        // patient info reload button
        Button patientReloadInformationButton = new Button("Load Patient Information"); // Create a button for load information
        patientReloadInformationButton.setStyle(setStyleButtonString); // Set the font size

        // Add Event Handler for patientReloadInformationButton to handle the loading information logic
        patientReloadInformationButton.setOnAction(e -> { // Convert the patient id to an integer
            String patientId = patientIdTextField.getText();
            loadPatientResults(primaryStage, patientId);
        });

        // Add a back button to the top left corner
        Button backButton = new Button("Back"); // Create a back button
        backButton.setOnAction(e -> start(primaryStage)); // Switch to the initial view
        backButton.setStyle(setStyleButtonString); // Set the font size

        // Add the components to the layout
        patientViewLayout.getChildren().addAll(enterPatientIdLabel, patientIdTextField, patientReloadInformationButton, backButton);

        // Set the scene with height and width
        Scene patientViewScene = new Scene(patientViewLayout, 600, 600);
        primaryStage.setScene(patientViewScene); // Set the scene
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // add label for patient name
        Label patientResultsLabel = new Label(String.format("Hello %s, Patient Results:", firstName)); // Create a label showcasing patient name

        // Create a GridPane with horizontal and vertical gaps of 10
        GridPane riskScores = new GridPane();
        riskScores.setHgap(10);
        riskScores.setVgap(10);

        // Create labels for each of the risk scores
        Label totalAgatstonCACScoreLabel = new Label("Total Agatston CAC Score: ");
        Label lmScoreLabel = new Label("LM Score: ");
        Label ladScoreLabel = new Label("LAD Score: ");
        Label lcxScoreLabel = new Label("LCX Score: ");
        Label rcaScoreLabel = new Label("RCA Score: ");
        Label pdaScoreLabel = new Label("PDA Score: ");

        // Create labels for each of the risk score values
        Label totalAgatstonCACScoreValue = new Label(String.valueOf(totalAgatstonCACScore));
        Label lmScoreValue = new Label(String.valueOf(lmScore));
        Label ladScoreValue = new Label(String.valueOf(ladScore));
        Label lcxScoreValue = new Label(String.valueOf(lcxScore));
        Label rcaScoreValue = new Label(String.valueOf(rcaScore));
        Label pdaScoreValue = new Label(String.valueOf(pdaScore));

        // Add the labels to the GridPane
        riskScores.add(totalAgatstonCACScoreLabel, 0, 0);
        riskScores.add(totalAgatstonCACScoreValue, 1, 0);
        riskScores.add(lmScoreLabel, 0, 1);
        riskScores.add(lmScoreValue, 1, 1);
        riskScores.add(ladScoreLabel, 0, 2);
        riskScores.add(ladScoreValue, 1, 2);
        riskScores.add(lcxScoreLabel, 0, 3);
        riskScores.add(lcxScoreValue, 1, 3);
        riskScores.add(rcaScoreLabel, 0, 4);
        riskScores.add(rcaScoreValue, 1, 4);
        riskScores.add(pdaScoreLabel, 0, 5);
        riskScores.add(pdaScoreValue, 1, 5);

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
