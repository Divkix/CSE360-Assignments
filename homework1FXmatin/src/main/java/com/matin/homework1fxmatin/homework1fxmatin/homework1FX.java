package com.matin.homework1fxmatin.homework1fxmatin;

// import javafx packages
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// Main class
public class homework1FX extends Application {

    // Bill text area
    TextArea billTextArea = new TextArea();

    // Prices
    // Prices are in dollars
    double priceEggSandwich = 7.99; // price for egg sandwich
    double priceChickenSandwich = 9.99; // price for chicken sandwich
    double priceBagel = 2.50; // price for bagel
    double pricePotatoSalad = 4.49; // price for potato salad
    double priceCoffee = 1.99; // price for coffee
    double priceGreenTea = 0.99; // price for green tea
    double priceBlackTea = 1.25; // price for black tea
    double priceOrangeJuice = 2.25; // price for orange juice

    // Menu items
    // we need to use CheckBox for food items as the user can select multiple items
    CheckBox eggSandwich = new CheckBox("Egg Sandwich"); // egg sandwich
    CheckBox chickenSandwich = new CheckBox("Chicken Sandwich"); // chicken sandwich
    CheckBox bagel = new CheckBox("Bagel"); // bagel
    CheckBox potatoSalad = new CheckBox("Potato Salad"); // potato salad

    // we need to use RadioButton for drinks as the user can select only one drink
    // for the meal
    RadioButton blackTea = new RadioButton("Black Tea"); // black tea
    RadioButton greenTea = new RadioButton("Green Tea"); // green tea
    RadioButton coffee = new RadioButton("Coffee"); // coffee
    RadioButton orangeJuice = new RadioButton("Orange Juice"); // orange juice

    // Buttons
    // we need to use Button for the order, cancel, and confirm buttons
    Button orderBtn = new Button("Order"); // order button
    Button cancelBtn = new Button("Cancel"); // cancel button
    Button confirmBtn = new Button("Confirm"); // confirm button

    // Toggle group for drinks so that only one drink can be selected
    ToggleGroup drinksToggleGroup = new ToggleGroup(); // toggle group for drinks

    @Override
    // Override the start method in the Application class
    // This is where we set up the UI
    public void start(Stage mainStage) {

        // Set up the grid pane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // Add the UI components to the grid pane
        gridPane.add(eggSandwich, 0, 0);
        gridPane.add(chickenSandwich, 0, 1);
        gridPane.add(bagel, 0, 2);
        gridPane.add(potatoSalad, 0, 3);
        gridPane.add(blackTea, 1, 0);
        gridPane.add(greenTea, 1, 1);
        gridPane.add(coffee, 1, 2);
        gridPane.add(orangeJuice, 1, 3);
        gridPane.add(billTextArea, 2, 0, 1, 4);
        gridPane.add(orderBtn, 0, 4);
        gridPane.add(cancelBtn, 1, 4);
        gridPane.add(confirmBtn, 2, 4);

        // Set the toggle group for radio buttons
        blackTea.setToggleGroup(drinksToggleGroup);
        greenTea.setToggleGroup(drinksToggleGroup);
        coffee.setToggleGroup(drinksToggleGroup);
        orangeJuice.setToggleGroup(drinksToggleGroup);

        // Event handlers
        orderBtn.setOnAction(e -> handleOrder());
        cancelBtn.setOnAction(e -> handleCancel());
        confirmBtn.setOnAction(e -> handleConfirm());

        // Set up the scene and stage
        Scene scene = new Scene(gridPane);
        mainStage.setTitle("Joe's Deli");
        mainStage.setScene(scene);
        mainStage.show();
    }

    private void handleOrder() {
        StringBuilder orderBill = new StringBuilder("Bill:\n\n");
        double total = 0;

        // Check which items are selected and add to bill
        if (eggSandwich.isSelected()) {
            orderBill.append(eggSandwich.getText()).append(": $").append(priceEggSandwich).append("\n");
            total += priceEggSandwich;
        }
        if (chickenSandwich.isSelected()) {
            orderBill.append(chickenSandwich.getText()).append(": $").append(priceChickenSandwich).append("\n");
            total += priceChickenSandwich;
        }
        if (bagel.isSelected()) {
            orderBill.append(bagel.getText()).append(": $").append(priceBagel).append("\n");
            total += priceBagel;
        }
        if (potatoSalad.isSelected()) {
            orderBill.append(potatoSalad.getText()).append(": $").append(pricePotatoSalad).append("\n");
            total += pricePotatoSalad;
        }

        // Check which drink is selected
        RadioButton selectedDrink = (RadioButton) drinksToggleGroup.getSelectedToggle();
        if (selectedDrink != null) {
            double priceDrink = getPriceForDrink(selectedDrink);
            orderBill.append(selectedDrink.getText()).append(": $").append(priceDrink).append("\n");
            total += priceDrink;
        }

        // Calculate and add tax, the tax rate is 7%
        double tax = total * 0.07;
        total += tax;

        // Add total to bill
        orderBill.append("\nTax: ").append(String.format("$%.2f", tax));
        orderBill.append("\nTotal: ").append(String.format("$%.2f", total));

        billTextArea.setText(orderBill.toString());
    }

    private void handleCancel() {
        // Clear all selections and the bill text area
        eggSandwich.setSelected(false); // clear selection
        chickenSandwich.setSelected(false); // clear selection
        bagel.setSelected(false); // clear selection
        potatoSalad.setSelected(false); // clear selection
        if (drinksToggleGroup.getSelectedToggle() != null) {
            drinksToggleGroup.getSelectedToggle().setSelected(false); // clear selection
        }
        billTextArea.clear(); // clear text area
    }

    private void handleConfirm() {
        // For simplicity, this will just clear the selections
        handleCancel(); // clear selections and bill text area, we use this method to avoid code
                        // duplication
        billTextArea.setText("Order confirmed!"); // set text to order confirmed
    }

    private double getPriceForDrink(RadioButton drink) {
        // Return the price based on the drink selected
        if (drink.equals(blackTea)) {
            return priceBlackTea; // return price for black tea
        } else if (drink.equals(greenTea)) {
            return priceGreenTea; // return price for green tea
        } else if (drink.equals(coffee)) {
            return priceCoffee; // return price for coffee
        } else if (drink.equals(orangeJuice)) {
            return priceOrangeJuice; // return price for orange juice
        }
        return 0; // return 0 if no drink is selected
    }

    public static void main(String[] args) {
        launch(args);
    }
}
