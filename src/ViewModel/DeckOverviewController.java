package ViewModel;

import Model.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class DeckOverviewController {

    public ListView list = new ListView<String>();
    private Data data = new Data();
    private ObservableList<String> deckNames = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        if (!data.isEmpty) {

            for (Deck deck : data.getListOfDecks()) {
                deckNames.add(deck.getName() + " (" + deck.getOwner() + ")");
                System.out.println(deck.getName());
            }
            //noinspection unchecked
            list.setItems(deckNames);
        }
    }

    public void handlerDeckSelect(ActionEvent event) throws IOException {
        String selectedItem = (String) list.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            for (Deck deck : data.getListOfDecks()) {
                if (selectedItem.contains(deck.getName()) && selectedItem.contains(deck.getOwner())) {
                    Data.setCurrentDeckName(deck.getName());
                    LogHelper.writeToLog(Level.INFO, "setCurrentDeckname: " + selectedItem);
                }
            }
        }
        Parent practiceViewParent = FXMLLoader.load(getClass().getClassLoader().getResource("View/PracticeWindow.fxml"));
        Scene practiceViewScene = new Scene(practiceViewParent);
        System.out.println(data.getCurrentDeck());

        //This line gets the Stage information
        Stage window1 = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window1.setScene(practiceViewScene);
        window1.show();
    }

    public void handlerBack(ActionEvent event) throws IOException {
        list.getItems().clear();
        deckNames.clear();
        Parent mainViewParent = FXMLLoader.load(getClass().getClassLoader().getResource("View/MainWindow.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }

    public void handlerDeckAdd(ActionEvent event) throws IOException {
        Parent CardAddViewParent = FXMLLoader.load(getClass().getClassLoader().getResource("View/DeckAdd.fxml"));
        Scene CardAddViewScene = new Scene(CardAddViewParent);

        //This line gets the Stage information
        Stage window1 = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window1.setScene(CardAddViewScene);
        window1.show();
        //Model.Deck hinzufügen
        //Fenster popup: Textfeld für Namen
    }

    //Kartenansicht
    public void handlerCardAdd(ActionEvent event) throws IOException {

        String selectedItem = (String) list.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            for (Deck deck : data.getListOfDecks()) {
                if (selectedItem.contains(deck.getName()) && selectedItem.contains(deck.getOwner())) {
                    Data.setCurrentDeckName(deck.getName());
                    LogHelper.writeToLog(Level.INFO, "setCurrentDeckname: " + selectedItem);
                }
            }
        }
        System.out.println(Data.getCurrentDeckName());

        Parent CardAddViewParent = FXMLLoader.load(getClass().getClassLoader().getResource("View/CardOverview.fxml"));
        Scene CardAddViewScene = new Scene(CardAddViewParent);

        //This line gets the Stage information
        Stage window1 = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window1.setScene(CardAddViewScene);
        window1.show();
    }

    public void handlerDeleteDeck(ActionEvent event) throws IOException {

        String selectedItem = (String) list.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            for (Deck deck : data.getListOfDecks()) {
                if (selectedItem.contains(deck.getName()) && selectedItem.contains(deck.getOwner())) {
                    Data.setCurrentDeckName(deck.getName());
                    LogHelper.writeToLog(Level.INFO, "setCurrentDeckname: " + selectedItem);
                }
            }
        }
        System.out.println(Data.getCurrentDeckName());

        File deck = new File(Paths.get(System.getenv("LOCALAPPDATA"), "flashcards", Data.getCurrentDeckName()).toString() + ".txt");
        System.out.println(deck);
        System.out.println("Attempting to delete " + deck.getAbsolutePath());

        if (!deck.exists()) {
            System.out.println("  Doesn't exist");

        } else if (!deck.canWrite()) {
            System.out.println("  No write permission");

        } else {
            deck = deck.getCanonicalFile();
            boolean Isremoved = deck.delete();

            if (Isremoved) {

                System.out.println("  Deleted!");
                Parent CardAddViewParent = FXMLLoader.load(getClass().getClassLoader().getResource("View/DeckOverview.fxml"));
                Scene CardAddViewScene = new Scene(CardAddViewParent);
                Stage window1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window1.setScene(CardAddViewScene);
                window1.show();

            } else {
                System.out.println("  Delete failed - reason unknown");

            }
        }
    }
}