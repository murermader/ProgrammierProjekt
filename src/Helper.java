import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

class Helper {

    //Windows
    private static final Path appDirectoryLog = Paths.get(System.getenv("LOCALAPPDATA"), "flashcards", "Log");
    private static final Path appDirectoryRoot = Paths.get(System.getenv("LOCALAPPDATA"), "flashcards");
    private Path osXDirectory = Paths.get(System.getenv("user.home"), "Library", "Application Support", "flashcards");

    //Gibt eine Liste mit den Dateinamen der Decks zurück (ohne Dateiendung).
    //TODO: Auch für Mac / Unix kompatibel machen! (vlt mit Argumenten)
    List<String> getDeckNames() {

        try {

            List<String> fileNames = new ArrayList<>();
            File directory = new File(appDirectoryRoot.toString());
            File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
            if (files != null) {
                for (File file : files) {
                    fileNames.add(file.getName());
                }
            }
            return fileNames;

        } catch (Exception ex) {
            LogHelper.writeToLog(Level.INFO, "fehler");
        }
        return null;
    }

    //TODO: Testen auf "False Positives", "mögliche Fehler"
    //4 Mögliche Returns: "windows", "osx", "unix", "undertermined".
    public String getOperationSystemNameLowerCase() {

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            //Betriebssystem ist Windows-basiert
            return "windows";
        } else if (os.contains("osx")) {
            //Betriebssystem ist Apple OSX
            return "osx";
        } else if (os.contains("nix") || os.contains("aix") || os.contains("nux")) {
            //Betriebssystem ist Linux/Unix basiert
            return "unix";
        }
        return "undertermined";
    }

    @SuppressWarnings("unchecked")
    public List<String> getUsersFromFile(){

        List<String> users = new ArrayList<>();
        try{
            FileInputStream fileStreamIn = new FileInputStream(Paths.get(appDirectoryRoot.toString(),
                    "Users.txt").toString());
            ObjectInputStream objectStream = new ObjectInputStream(fileStreamIn);
            users = (List<String>) objectStream.readObject();
            objectStream.close();

        } catch (Exception ex){
            LogHelper.writeToLog(Level.INFO,"Fehler beim Einlesen der Users Datei" +ex);
        }
        return users;
    }

    public void saveUsersToFile(List<String> users){

        try{
            if(users.size() > 0){
                FileOutputStream fileStreamOut = new FileOutputStream(Paths.get(appDirectoryRoot.toString(),
                        "Users.txt").toString());
                ObjectOutputStream objectStream = new ObjectOutputStream(fileStreamOut);
                objectStream.writeObject(users);
                objectStream.close();
            }

        } catch(Exception ex){
            LogHelper.writeToLog(Level.INFO, "Fehler beim Speichern der User" +ex);
        }
    }

    //Um ein Deck abzuspeichern.
    //TODO: Auch für Mac / Unix kompatibel machen! (vlt mit Argumenten)
    void saveDeckToFile(Deck deck, String deckName) {

        if (deck != null && deckName != null) {
            try {

                FileOutputStream fileStreamOut = new FileOutputStream(
                        Paths.get(appDirectoryRoot.toString(), deckName + ".txt").toString());
                ObjectOutputStream objectStream = new ObjectOutputStream(fileStreamOut);
                objectStream.writeObject(deck.getCards());
                objectStream.close();

            } catch (Exception ex) {
                LogHelper.writeToLog(Level.INFO, "Fehler beim Erstellen des Ordners: " + ex);
            }
        } else {
            LogHelper.writeToLog(Level.INFO, "Deck nicht gespeichert. Entweder war das Deck leer, "
                    + "oder das Deck hatte keinen Namen. (Deck: " + (deck == null) + ") / (deckName: " + deckName + ")");
        }
    }

    //readObject gibt eine Warnung, da nicht sichergestellt werden kann, ob das zurückgegebene Objekt
    //tatsächlich vom Typ List<Flashcard> ist. Kann unterdrückt werden, da das Objekt nur von diesem Typ sein kann.
    //TODO: Auch für Mac / Unix kompatibel machen! (vlt mit Argumenten)
    @SuppressWarnings("unchecked")
    Deck getDeckFromFile(String deckName) {

        List<Flashcard> list = new ArrayList<>();
        Deck deck = new Deck(deckName, list);

        try {

            FileInputStream fileStreamIn = new FileInputStream(
                    Paths.get(appDirectoryRoot.toString(), deckName).toString());
            ObjectInputStream objectStream = new ObjectInputStream(fileStreamIn);
            list = (List<Flashcard>) objectStream.readObject();
            objectStream.close();

        } catch (Exception ex) {
            LogHelper.writeToLog(Level.INFO, "Fehler beim Einlesen der Speicherdatei: " + ex);
        }
        deck.setCards(list);
        return deck;
    }

    //TODO: Auch für Mac / Unix kompatibel machen! (vlt mit Argumenten)
    void createDirectories() {

        try {

            File directory;
            if (getOperationSystemNameLowerCase().equals("windows")) {
                directory = appDirectoryLog.toFile();
            } else { //UNIX || OSX
                directory = osXDirectory.toFile();
            }

            if (directory != null) {
                if (!directory.exists()) {
                    boolean isDirectoryCreated = directory.mkdirs();
                    if (isDirectoryCreated) {
                        LogHelper.writeToLog(Level.INFO, "Ordner erstellt!");
                    } else {
                        LogHelper.writeToLog(Level.INFO, "Ordner konnte nicht erstellt werden. (Helper)");
                    }
                }
            }

        } catch (Exception ex) {
            LogHelper.writeToLog(Level.INFO, "Fehler beim Erstellen des Ordners: " + ex);
        }
    }

    void createSampleDeck(String deckName, int length) {

        try {

            List<Flashcard> list = new ArrayList<>();
            Deck deck = new Deck(deckName, list);

            for (int i = 0; i < length; i++) {

                int random1 = (int) (Math.random() * 50 + 1);
                int random2 = (int) (Math.random() * 100 + 1);
                //list.add(new Flashcard(Integer.toString(random1), Integer.toString(random2)));
                deck.addCard(new Flashcard(Integer.toString(random1), Integer.toString(random2)));
            }
            saveDeckToFile(deck, deckName);

        } catch (Exception ex) {
            LogHelper.writeToLog(Level.INFO, "Fehler beim Erstellen von SampleData" + ex);
        }
    }

}


