package me.divkix.homework1fx.homework1fx;

// Import the necessary packages
import javafx.application.Application;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// Create the main class
public class homework1FX extends Application {

    // Text area for the bill
    TextArea billTextArea = new TextArea();

    // Menu items
    CheckBox foodEggSandwich = new CheckBox("Egg Sandwich"); // Menu item for egg sandwich
    CheckBox foodChickenSandwich = new CheckBox("Chicken Sandwich"); // Menu item for chicken sandwich
    CheckBox foodBagel = new CheckBox("Bagel"); // Menu item for bagel
    CheckBox foodPotatoSalad = new CheckBox("Potato Salad"); // Menu item for potato salad

    RadioButton drinkBlackTea = new RadioButton("Black Tea"); // Menu item for black tea
    RadioButton drinkGreenTea = new RadioButton("Green Tea"); // Menu item for green tea
    RadioButton drinkCoffee = new RadioButton("Coffee"); // Menu item for coffee
    RadioButton drinkOrangeJuice = new RadioButton("Orange Juice"); // Menu item for orange juice

    Button placeOrderButton = new Button("Order"); // Order button
    Button cancelOrderButton = new Button("Cancel"); // Cancel button
    Button confirmOrderButton = new Button("Confirm"); // Confirm button

    // make a toggle group for the drinks, because only one drink can be selected
    ToggleGroup drinksGroup = new ToggleGroup();

    @Override
    // Set up the UI
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane(); // Create and setup a new grid pane
        gridPane.setPadding(new Insets(10, 10, 10, 10)); // Set padding
        gridPane.setVgap(5); // Set vertical gap
        gridPane.setHgap(5); // Set horizontal gap

        // ----- Add the UI components to the grid pane -----
        gridPane.add(foodEggSandwich, 0, 0); // Add 'egg sandwich' to the grid pane
        gridPane.add(foodChickenSandwich, 0, 1); // Add 'chicken sandwich' to the grid pane
        gridPane.add(foodBagel, 0, 2); // Add 'bagel' to the grid pane
        gridPane.add(foodPotatoSalad, 0, 3); // Add 'potato salad' to the grid pane
        gridPane.add(drinkBlackTea, 1, 0); // Add 'black tea' to the grid pane
        gridPane.add(drinkGreenTea, 1, 1); // Add 'green tea' to the grid pane
        gridPane.add(drinkCoffee, 1, 2); // Add 'coffee' to the grid pane
        gridPane.add(drinkOrangeJuice, 1, 3); // Add 'orange juice' to the grid pane
        gridPane.add(billTextArea, 2, 0, 1, 4); // Add the bill text area to the grid pane
        gridPane.add(placeOrderButton, 0, 4); // Add the 'order' button to the grid pane
        gridPane.add(cancelOrderButton, 1, 4); // Add the 'cancel' button to the grid pane
        gridPane.add(confirmOrderButton, 2, 4); // Add the 'confirm' button to the grid pane

        // Set the toggle group for radio buttons
        drinkBlackTea.setToggleGroup(drinksGroup); // Add 'black tea' to the toggle group
        drinkGreenTea.setToggleGroup(drinksGroup); // Add 'green tea' to the toggle group
        drinkCoffee.setToggleGroup(drinksGroup); // Add 'coffee' to the toggle group
        drinkOrangeJuice.setToggleGroup(drinksGroup); // Add 'orange juice' to the toggle group

        // Event handlers for the buttons
        placeOrderButton.setOnAction(e -> handleOrder()); // Handle 'order' button click
        cancelOrderButton.setOnAction(e -> handleCancel()); // Handle 'cancel' button click
        confirmOrderButton.setOnAction(e -> handleConfirm()); // Handle 'confirm' button click

        // Set up the scene and stage
        Scene scene = new Scene(gridPane);
        primaryStage.setTitle("Joe's Deli"); // Set the title of the window to 'Joe's Deli'
        primaryStage.setScene(scene); // Set the scene
        primaryStage.show(); // Show the window
    }

    // function to handle the order button
    private void handleOrder() {
        StringBuilder billString = new StringBuilder("Bill:\n\n"); // Create a string builder for the bill
        String foodItem = ""; // Create a variable for the item and set it to an empty string
        double totalBill = 0; // Create a variable for the total bill and set it to 0
        MenuPrices menuPrices = new MenuPrices(); // Create a new menu prices object

        // Check which items are selected and add to bill
        if (foodEggSandwich.isSelected()) {
            foodItem = foodEggSandwich.getText();
            Double price = menuPrices.getPrice(foodItem);
            double priceFoodItem = price.doubleValue();
            totalBill += priceFoodItem;
            billString.append(foodItem).append(": $").append(priceFoodItem).append("\n"); // Add the food item to the
                                                                                          // bill
        }
        if (foodChickenSandwich.isSelected()) {
            foodItem = foodChickenSandwich.getText();
            Double price = menuPrices.getPrice(foodItem);
            double priceFoodItem = price.doubleValue();
            totalBill += priceFoodItem;
            billString.append(foodItem).append(": $").append(priceFoodItem).append("\n"); // Add the food item to the
                                                                                          // bill
        }
        if (foodBagel.isSelected()) {
            foodItem = foodBagel.getText();
            Double price = menuPrices.getPrice(foodItem);
            double priceFoodItem = price.doubleValue();
            totalBill += priceFoodItem;
            billString.append(foodItem).append(": $").append(priceFoodItem).append("\n"); // Add the food item to the
                                                                                          // bill
        }
        if (foodPotatoSalad.isSelected()) {
            foodItem = foodPotatoSalad.getText();
            Double price = menuPrices.getPrice(foodItem);
            double priceFoodItem = price.doubleValue();
            totalBill += priceFoodItem;
            billString.append(foodItem).append(": $").append(priceFoodItem).append("\n"); // Add the food item to the
                                                                                          // bill
        }

        // Check which drink is selected
        RadioButton selectedDrink = (RadioButton) drinksGroup.getSelectedToggle(); // Get the selected drink
        if (selectedDrink != null) {
            double priceDrink = getPriceForDrink(selectedDrink); // Get the price of the drink
            billString.append(selectedDrink.getText()).append(": $").append(priceDrink).append("\n"); // Add the drink
                                                                                                      // to the bill
                                                                                                      // text
            totalBill += priceDrink; // Add the price of the drink to the total bill
        } else {
            billString.append("No drink selected\n"); // Add a message to the bill text if no drink is selected
        }

        // Calculate and add tax, the tax rate is 7%
        double tax = totalBill * 0.07; // Calculate the tax
        totalBill += tax; // Add the tax to the total bill

        // Add total to bill
        billString.append("\nTax: ").append(String.format("$%.2f", tax)); // Add the tax to the bill text
        billString.append("\nTotal: ").append(String.format("$%.2f", totalBill)); // Add the total to the bill text

        billTextArea.setText(billString.toString()); // Set the bill text area to the bill string
    }

    private void handleCancel() {
        // Clear all selections and the bill text area
        foodEggSandwich.setSelected(false);
        foodChickenSandwich.setSelected(false);
        foodBagel.setSelected(false);
        foodPotatoSalad.setSelected(false);
        if (drinksGroup.getSelectedToggle() != null) {
            drinksGroup.getSelectedToggle().setSelected(false);
        }

        // Clear the bill text area
        billTextArea.clear();
    }

    private void handleConfirm() {
        // For simplicity, this will just clear the selections
        handleCancel();
        billTextArea.setText("Order confirmed!"); // Set the bill text area to the confirmation message
    }

    private double getPriceForDrink(RadioButton drink) {
        MenuPrices prices = new MenuPrices();
        // Return the price based on the drink selected
        if (drink.equals(drinkBlackTea)) {
            return prices.getPrice("Black Tea");
        } else if (drink.equals(drinkGreenTea)) {
            return prices.getPrice("Green Tea");
        } else if (drink.equals(drinkCoffee)) {
            return prices.getPrice("Coffee");
        } else if (drink.equals(drinkOrangeJuice)) {
            return prices.getPrice("Orange Juice");
        }
        return 0;
    }

    private static class MenuPrices {
        // Create a hash map for the menu prices
        private final Map<String, Double> menuPrices;

        // Constructor for the menu prices
        private MenuPrices() {
            menuPrices = new HashMap<>(); // Create a new hash map for the menu prices
            menuPrices.put("Egg Sandwich", 7.99);
            menuPrices.put("Chicken Sandwich", 9.99);
            menuPrices.put("Bagel", 2.50);
            menuPrices.put("Potato Salad", 4.49);
            menuPrices.put("Coffee", 1.99);
            menuPrices.put("Green Tea", 0.99);
            menuPrices.put("Black Tea", 1.25);
            menuPrices.put("Orange Juice", 2.25);
        }

        // Get the price for an item
        private Double getPrice(String item) {
            return menuPrices.get(item); // Return the price for the item
        }
    }

    // Launch the application
    public static void main(String[] args) {
        launch(args);
    }
}
