module me.divkix.cse360.hw4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens me.divkix.cse360.hw4 to javafx.fxml;
    exports me.divkix.cse360.hw4;
}