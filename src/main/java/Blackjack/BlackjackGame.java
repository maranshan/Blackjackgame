package Blackjack;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BlackjackGame {

    private ArrayList<Card> BlackjackDeck = new ArrayList<Card>();
    private double bet;
    private player player;
    private dealer dealer = new dealer("dealer");
    private static ArrayList<List<String>> SaveToFile = new ArrayList<>();
    private fileHelper fileHelper = new fileHelper();

    public void newCardDeck() {
        BlackjackDeck.removeAll(BlackjackDeck);
        BlackjackDeck.addAll(new CardDeck(13).getDeck());
        BlackjackDeck.addAll(new CardDeck(13).getDeck());
        BlackjackDeck.addAll(new CardDeck(13).getDeck());
        BlackjackDeck.addAll(new CardDeck(13).getDeck());
        randomShuffle();
    }

    public int getDecksize() {
        return BlackjackDeck.size();
    }

    public void randomShuffle() {
        Collections.shuffle(BlackjackDeck);
    }

    public boolean hasWonFirstTry(playerInterface player) {
        int sum = 0;
        int antallS = 0;
        for (Card card : player.getHand()) {
            if (card.getFace() == 1) {
                if (antallS == 0) {
                    sum += 11;
                    antallS++;
                } else {
                    sum += 1;
                }
            } else {
                sum += card.getFace();
            }
        }
        return sum == 21;

    }

    public int hasWon() { // dette er for standmetoden
        if (dealer.getHandSum() > 21) {
            return 1;
        } else if (player.getHandSum() > dealer.getHandSum()) {
            return 1;
        } else if (player.getHandSum() == dealer.getHandSum()) {
            return 0;
        } else if (player.getHandSum() < dealer.getHandSum()) {
            return -1;
        } else {
            throw new IllegalStateException("Det har blitt noe feil med hasWon metoden");
        }
    }

    public void balanceChangeHelper(double balance) {
        player.setBalance(balance);
        for (List<String> list : SaveToFile) {
            if (list.get(0).equals(player.getUsername())) {
                SaveToFile.remove(list);
                SaveToFile.add(new ArrayList<>(Arrays.asList(player.getUsername(), player.getPassword(),
                        Double.toString(player.getBalance()))));
            }
        }
        fileHelper.writeStateToFile(SaveToFile, "UserInfo.txt");
    }

    public boolean Login(String username, String password, double balance) {
        boolean newUser = true;
        for (List<String> list : SaveToFile) {
            if (list.get(0).equals(username)) {
                newUser = false;
                if (list.get(1).equals(password)) {
                    SaveToFile.remove(list);
                    this.player = new player(username, password, balance);
                    SaveToFile.add(new ArrayList<>(Arrays.asList(username, password, Double.toString(balance))));
                    try (PrintWriter writer = new PrintWriter(new FileWriter("UserInfo.txt", true))) {
                        writer.println(username + "," + password + "," + balance);
                    } catch (IOException e) {
                        System.err.println("Feil ved skriving til filen: " + e.getMessage());
                    }
                    return true;
                } else {
                    return false;
                }
            }
        }
        if (newUser == true) {
            SaveToFile.add(new ArrayList<>(Arrays.asList(username, password, Double.toString(balance))));
            this.player = new player(username, password, balance);
            try (PrintWriter writer = new PrintWriter(new FileWriter("UserInfo.txt", true))) {
                writer.println(username + "," + password + "," + balance);
            } catch (IOException e) {
                System.err.println("Feil ved skriving til filen: " + e.getMessage());
            }
            return true;
        }
        return false;
    }

    public void setBet(double bet) throws Exception{
        if (bet < 0) {
            throw new IllegalArgumentException("Provide a bet greater than 0");
        } 
        if (bet > player.getBalance()) {
            throw new IllegalStateException("You can not afford this bet");
        }
        this.bet = bet;
    }

    public double getBet() {
        return this.bet;
    }

    public player getPlayer() {
        return this.player;
    }
    
    public void setPlayer(player player) {
		this.player = player;
	}

    public dealer getDealer() {
        return this.dealer;
    }

    public ArrayList<Card> getBlackjackDeck() {
        return this.BlackjackDeck;
    }

    public fileHelper getFileHelper() {
        return this.fileHelper;
    }

    

}
