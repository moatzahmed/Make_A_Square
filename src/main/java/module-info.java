module com.example.makeasquarefinal {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.makeasquarefinal to javafx.fxml;
    exports com.example.makeasquarefinal;
}