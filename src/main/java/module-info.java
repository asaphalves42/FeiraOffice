module com.example.lp3_g2_feira_office_ {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.base;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires junit;
    requires java.desktop;
    requires org.mockito;

    exports Model;
    opens Model to javafx.base;

    exports Utilidades;
    opens Utilidades to javafx.base;

    exports TestesUnitarios to javafx.graphics, javafx.fxml, junit, org.mockito; // Adicionei javafx.graphics aqui
    opens TestesUnitarios to javafx.graphics, javafx.fxml, junit, org.mockito; // Adicionei javafx.graphics aqui
    exports TestesUnitarios.Login;
    opens TestesUnitarios.Login to org.mockito;

    exports TestesUnitarios.Fornecedores to junit, org.mockito;

    exports Main;
    opens Main to javafx.fxml;

    exports Controller.Fornecedor;
    opens Controller.Fornecedor to javafx.fxml;
    opens Controller.DAL to org.mockito;


    exports Controller.Login;
    opens Controller.Login to javafx.fxml;

    exports Controller.Administrador;
    opens Controller.Administrador to javafx.fxml;

    exports Controller.Operador;
    opens Controller.Operador to javafx.fxml;

    exports Controller.Produtos;
    opens Controller.Produtos to javafx.fxml;

    exports Controller.MensagensDeErro;
    opens Controller.MensagensDeErro to javafx.fxml;
}
