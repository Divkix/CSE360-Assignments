package me.divkix.homework1fx.homework1fx;

import javafx.application.Application;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
        // Set up the grid pane
        GridPane gridPane = new GridPane();
        // Set padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        // Set vertical and horizontal gaps
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // Add the UI components to the grid pane
        gridPane.add(foodEggSandwich, 0, 0); // Add egg sandwich to the grid pane
        gridPane.add(foodChickenSandwich, 0, 1); // Add chicken sandwich to the grid pane
        gridPane.add(foodBagel, 0, 2); // Add bagel to the grid pane
        gridPane.add(foodPotatoSalad, 0, 3); // Add potato salad to the grid pane
        gridPane.add(drinkBlackTea, 1, 0); // Add black tea to the grid pane
        gridPane.add(drinkGreenTea, 1, 1); // Add green tea to the grid pane
        gridPane.add(drinkCoffee, 1, 2); // Add coffee to the grid pane
        gridPane.add(drinkOrangeJuice, 1, 3); // Add orange juice to the grid pane
        gridPane.add(billTextArea, 2, 0, 1, 4); // Add the bill text area to the grid pane
        gridPane.add(placeOrderButton, 0, 4); // Add the order button to the grid pane
        gridPane.add(cancelOrderButton, 1, 4); // Add the cancel button to the grid pane
        gridPane.add(confirmOrderButton, 2, 4); // Add the confirm button to the grid pane

        // Set the toggle group for radio buttons
        drinkBlackTea.setToggleGroup(drinksGroup); // Add black tea to the toggle group
        drinkGreenTea.setToggleGroup(drinksGroup); // Add green tea to the toggle group
        drinkCoffee.setToggleGroup(drinksGroup); // Add coffee to the toggle group
        drinkOrangeJuice.setToggleGroup(drinksGroup); // Add orange juice to the toggle group

        // Event handlers
        placeOrderButton.setOnAction(e -> handleOrder()); // Handle order button click
        cancelOrderButton.setOnAction(e -> handleCancel()); // Handle cancel button click
        confirmOrderButton.setOnAction(e -> handleConfirm()); // Handle confirm button click

        // Set up the scene and stage
        Scene scene = new Scene(gridPane);
        primaryStage.setTitle("Joe's Deli");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleOrder() {
        StringBuilder billString = new StringBuilder("Bill:\n\n");
        double totalBill = 0;
        MenuPrices menuPrices = new MenuPrices();

        // Check which items are selected and add to bill
        if (foodEggSandwich.isSelected()) {
            double priceEggSandwich = menuPrices.getPrice("Egg Sandwich");
            billString.append(foodEggSandwich.getText()).append(": $").append(priceEggSandwich).append("\n");
            totalBill += priceEggSandwich;
        }
        if (foodChickenSandwich.isSelected()) {
            double priceChickenSandwich = menuPrices.getPrice("Chicken Sandwich");
            billString.append(foodChickenSandwich.getText()).append(": $").append(priceChickenSandwich).append("\n");
            totalBill += priceChickenSandwich;
        }
        if (foodBagel.isSelected()) {
            double priceBagel = menuPrices.getPrice("Bagel");
            billString.append(foodBagel.getText()).append(": $").append(priceBagel).append("\n");
            totalBill += priceBagel;
        }
        if (foodPotatoSalad.isSelected()) {
            double pricePotatoSalad = menuPrices.getPrice("Potato Salad");
            billString.append(foodPotatoSalad.getText()).append(": $").append(pricePotatoSalad).append("\n");
            totalBill += pricePotatoSalad;
        }

        // Check which drink is selected
        RadioButton selectedDrink = (RadioButton) drinksGroup.getSelectedToggle();
        if (selectedDrink != null) {
            double priceDrink = getPriceForDrink(selectedDrink);
            billString.append(selectedDrink.getText()).append(": $").append(priceDrink).append("\n");
            totalBill += priceDrink;
        }

        // Calculate and add tax, the tax rate is 7%
        double tax = totalBill * 0.07;
        totalBill += tax;

        // Add total to bill
        billString.append("\nTax (7%): ").append(String.format("$%.2f", tax));
        billString.append("\nTotal: ").append(String.format("$%.2f", totalBill));

        billTextArea.setText(billString.toString());
    }

    private void handleCancel() {
        // Clear all selections and the bill text area
        foodEggSandwich.setSelected(false);
        foodChickenSandwich.setSelected(false);
        foodBagel.setSelected(false);
        foodPotatoSalad.setSelected(false);
        drinksGroup.getSelectedToggle().setSelected(false);
        billTextArea.clear();
    }

    private void handleConfirm() {
        // For simplicity, this will just clear the selections
        handleCancel();
        billTextArea.setText("Order confirmed!");
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
        private final Map<String, Double> menuPrices;

        private MenuPrices() {
            menuPrices = new HashMap<>();
            menuPrices.put("Egg Sandwich", 7.99);
            menuPrices.put("Chicken Sandwich", 9.99);
            menuPrices.put("Bagel", 2.50);
            menuPrices.put("Potato Salad", 4.49);
            menuPrices.put("Coffee", 1.99);
            menuPrices.put("Green Tea", 0.99);
            menuPrices.put("Black Tea", 1.25);
            menuPrices.put("Orange Juice", 2.25);
        }

        private Double getPrice(String item) {
            return menuPrices.get(item);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
