package ViewModel;

import Model.Data;
import Model.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class CardAddController {


    public ListView CardList = new ListView();

    Helper helper = new Helper();
    Data data = new Data();

    @FXML
    public void initialize() {

    }

    @FXML
    public void handlerBack(ActionEvent event) throws IOException {
        helper.switchScene(event, "MainWindow.fxml");
    }

    @FXML
    public void handlerAdd(ActionEvent event) throws IOException {
        System.out.println(Data.getCurrentDeckName());
        //TODO: Hier fertigmachen
    }
}
