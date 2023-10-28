module com.example.lp3_g2_feira_office_ {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    exports Main;
    opens Main to javafx.fxml;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Controller.Fornecedor;
    opens Controller.Fornecedor to javafx.fxml;
    exports Controller.Login;
    opens Controller.Login to javafx.fxml;
    exports Controller.Administrador;
    opens Controller.Administrador to javafx.fxml;
    exports Controller.Operador;
    opens Controller.Operador to javafx.fxml;
}