module me.divkix.cse360.hw4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens me.divkix.cse360.hw4 to javafx.fxml;
    exports me.divkix.cse360.hw4;
}