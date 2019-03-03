package Model;

import java.io.Serializable;
import java.util.logging.Level;

public class User implements Serializable {

  private String name;
  private int numberOfDecks;
  private int numberOfCards;
  private int cardsLearned;
  private long timeSpentLearning; //Zeiteinheit?

  public User(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCardsLearned() {
    return cardsLearned;
  }

  public void setCardsLearned(int cardsLearned) {
    this.cardsLearned = cardsLearned;
  }

  public long getTimeSpentLearning() {
    return timeSpentLearning;
  }

  public void setTimeSpentLearning(long timeSpentLearning) {
    this.timeSpentLearning = timeSpentLearning;
  }

  public int getNumberOfDecks() {
    return numberOfDecks;
  }

  public void setNumberOfDecks(int numberOfDecks) {
    this.numberOfDecks = numberOfDecks;
  }

  public int getNumberOfCards() {
//    this.numberOfCards = Model.Deck.getLength();
    LogHelper.writeToLog(Level.INFO, "Anzahl Karten: " + numberOfCards);
    return numberOfCards;
  }

  public void setNumberOfCards(int numberOfCards) {
    this.numberOfCards = numberOfCards;
  }

  public void getData(int numberOfCards, int numberOfDecks, int cardsLearned, int timeSpentLearning) {
  }

  public void resetTime() {
    setTimeSpentLearning(0);
  }

}