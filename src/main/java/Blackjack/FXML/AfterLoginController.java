package Blackjack.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Blackjack.BlackjackGame;
import Blackjack.Card;
import Blackjack.dealer;
import Blackjack.player;

public class AfterLoginController implements Initializable {

    @FXML
    private Button logout;
    @FXML
    private Button startGame;
    @FXML
    private Button hit;
    @FXML
    private Button stand;
    @FXML
    private Button doubleDown;
    @FXML
    private Button resetGame;

    @FXML
    private Label validBet; // rød feilmelding
    @FXML
    private TextField insertBet;
    @FXML
    private Label currentBet;
    @FXML
    private Label playerHandSum;
    @FXML
    private Label dealerHandSum;
    @FXML
    private Label winpopup;
    @FXML
    private Label playerBalance;

    @FXML
    private ImageView dealer1;
    @FXML
    private ImageView dealer2;
    @FXML
    private ImageView dealer3;
    @FXML
    private ImageView dealer4;
    @FXML
    private ImageView dealer5;

    @FXML
    private ImageView player1;
    @FXML
    private ImageView player2;
    @FXML
    private ImageView player3;
    @FXML
    private ImageView player4;
    @FXML
    private ImageView player5;

    private int pictureCounterPlayer = 3;
    private int pictureCounterDealer = 3;
    private Card hiddenCard;

    private BlackjackGame blackjackGame;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.blackjackGame = new BlackjackGame();
        String lastLine = blackjackGame.getFileHelper().readLastLine("UserInfo.txt");
        blackjackGame.setPlayer(new player(lastLine.split(",")[0], lastLine.split(",")[1],
                Double.parseDouble(lastLine.split(",")[2])));
        playerBalance.setText(String.valueOf(blackjackGame.getPlayer().getBalance())); 
        winpopup.setText("Please insert a bet to start the game");
        hit.setDisable(true);
        stand.setDisable(true);
        doubleDown.setDisable(true);
        resetGame.setDisable(true);
        startGame.setDisable(true);
        insertBet.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DIGIT0 || event.getCode() == KeyCode.DIGIT1
                    || event.getCode() == KeyCode.DIGIT2 ||
                    event.getCode() == KeyCode.DIGIT3 || event.getCode() == KeyCode.DIGIT4
                    || event.getCode() == KeyCode.DIGIT5 ||
                    event.getCode() == KeyCode.DIGIT6 || event.getCode() == KeyCode.DIGIT7
                    || event.getCode() == KeyCode.DIGIT8 ||
                    event.getCode() == KeyCode.DIGIT9) {
                validBet.setText(null);
                startGame.setDisable(false);
                winpopup.setText(null);
            }
        });
    }

    public void userLogOut(ActionEvent event) throws IOException {
        LoginApp m = new LoginApp();
        m.changeScene("/Login.fxml");
    }

    public void userStartGame(ActionEvent event) throws IOException {
        hit.setDisable(false);
        stand.setDisable(false);
        doubleDown.setDisable(false);
        resetGame.setDisable(false);
        startGame.setDisable(true);
        winpopup.setText("");
        this.blackjackGame = new BlackjackGame();
        String lastLine = blackjackGame.getFileHelper().readLastLine("UserInfo.txt");
        blackjackGame.setPlayer(new player(lastLine.split(",")[0], lastLine.split(",")[1],
                Double.parseDouble(lastLine.split(",")[2])));
        blackjackGame.newCardDeck();
        double bettt = Double.parseDouble(insertBet.getText());
        try {
            blackjackGame.setBet(bettt);
            blackjackGame.getPlayer().changeBalance(-bettt);
            blackjackGame.balanceChangeHelper(blackjackGame.getPlayer().getBalance());
            playerBalance.setText(String.valueOf(blackjackGame.getPlayer().getBalance()));
            currentBet.setText(String.valueOf(blackjackGame.getBet()));
            firstCardHandout();
            playerHasWonFirstTry();
            dealerHasWonFirstTry();
            validBet.setText("");
        } catch (Exception e) {
            hit.setDisable(true);
            stand.setDisable(true);
            doubleDown.setDisable(true);
            resetGame.setDisable(true);
            startGame.setDisable(true);
            winpopup.setText("Please insert a valid bet");
            e.printStackTrace();
        }
    }

    public void firstCardHandout() {
        Card playercard1 = blackjackGame.getBlackjackDeck().remove(0);
        blackjackGame.getPlayer().addCard(playercard1);
        playerHandSum.setText(String.valueOf(blackjackGame.getPlayer().getHandSum()));
        player1.setImage(new Image("Carddeck/" + playercard1.toString() + ".png"));
        Card dealercard1 = blackjackGame.getBlackjackDeck().remove(0);
        blackjackGame.getDealer().addCard(dealercard1);
        dealerHandSum.setText(String.valueOf(blackjackGame.getDealer().getHandSum()));
        dealer1.setImage(new Image("Carddeck/" + dealercard1.toString() + ".png"));
        Card playercard2 = blackjackGame.getBlackjackDeck().remove(0);
        blackjackGame.getPlayer().addCard(playercard2);
        playerHandSum.setText(String.valueOf(blackjackGame.getPlayer().getHandSum()));
        player2.setImage(new Image("Carddeck/" + playercard2.toString() + ".png"));
        Card dealercard2 = blackjackGame.getBlackjackDeck().remove(0);
        blackjackGame.getDealer().addCard(dealercard2);
        this.hiddenCard = dealercard2;
        dealer2.setImage(new Image("Carddeck/BS.png"));

    }

    public void changeBalance(double amount) {
        blackjackGame.getPlayer().changeBalance(amount);
        blackjackGame.balanceChangeHelper(blackjackGame.getPlayer().getBalance());
        playerBalance.setText(String.valueOf(blackjackGame.getPlayer().getBalance()));

    }

    public void playerHasWonFirstTry() {
        if (blackjackGame.getPlayer().getHandSum() == 21) {
            changeBalance(2.5 * Double.parseDouble(insertBet.getText()));
            winpopup.setText("You won " + 2.5 * Double.parseDouble(insertBet.getText()));
            resetGame.setDisable(false);
            turnOffButtons();
        }
    }

    public void dealerHasWonFirstTry() {
        if (blackjackGame.getDealer().getHand().getCard(0).getFace() == 10
                || blackjackGame.getDealer().getHand().getCard(0).getFace() == 1) {
            if (blackjackGame.getDealer().getHandSum() == 21) {
                dealer2.setImage(new Image("Carddeck/" + this.hiddenCard.toString() + ".png"));
                dealerHandSum.setText(String.valueOf(blackjackGame.getDealer().getHandSum()));
                winpopup.setText("You lost " + Double.parseDouble(insertBet.getText()));
                resetGame.setDisable(false);
                turnOffButtons();
            }
        }
    }

    public void doubleDown(ActionEvent event) throws IOException {
        dealer dealer = blackjackGame.getDealer();
        player player = blackjackGame.getPlayer();
        player.changeBalance(-blackjackGame.getBet());
        blackjackGame.balanceChangeHelper(player.getBalance());
        Card cardPic = blackjackGame.getBlackjackDeck().remove(0);
        blackjackGame.getPlayer().addCard(cardPic);
        switch (this.pictureCounterPlayer) {
            case 3:
                player3.setImage(new Image("Carddeck/" + cardPic.toString() + ".png"));
                break;
            case 4:
                player4.setImage(new Image("Carddeck/" + cardPic.toString() + ".png"));
                break;
            case 5:
                player5.setImage(new Image("Carddeck/" + cardPic.toString() + ".png"));
                break;
            default:
                break;
        }
        this.pictureCounterPlayer++;

        playerHandSum.setText(String.valueOf(player.getHandSum()));
        playerBalance.setText(String.valueOf(player.getBalance()));
        currentBet.setText(String.valueOf(2 * blackjackGame.getBet()));
        if (player.getHandSum() > 21) {
            winpopup.setText("You lost " + Double.parseDouble(insertBet.getText()));
            resetGame.setDisable(false);
            turnOffButtons();
        } else {
            dealer2.setImage(new Image("Carddeck/" + this.hiddenCard.toString() + ".png"));
            while (dealer.getHandSum() < 17) {
                Card cardPicDealer = blackjackGame.getBlackjackDeck().remove(0);
                blackjackGame.getDealer().addCard(cardPicDealer);
                switch (this.pictureCounterDealer) {
                    case 3:
                        dealer3.setImage(new Image("Carddeck/" + cardPicDealer.toString() + ".png"));
                        break;
                    case 4:
                        dealer4.setImage(new Image("Carddeck/" + cardPicDealer.toString() + ".png"));
                        break;
                    case 5:
                        dealer5.setImage(new Image("Carddeck/" + cardPicDealer.toString() + ".png"));
                        break;
                    default:

                        break;
                }
                this.pictureCounterDealer++;
                dealerHandSum.setText(String.valueOf(dealer.getHandSum()));
                if (dealer.getHandSum() > 21) {
                    player.changeBalance(4 * blackjackGame.getBet());
                    blackjackGame.balanceChangeHelper(player.getBalance());
                    winpopup.setText("You won " + 4 * Double.parseDouble(insertBet.getText()));
                    resetGame.setDisable(false);
                    turnOffButtons();
                }
            }
            if (dealer.getHandSum() >= 17) {
                if (blackjackGame.hasWon() == 1) {
                    player.changeBalance(4 * blackjackGame.getBet());
                    blackjackGame.balanceChangeHelper(player.getBalance());
                    winpopup.setText("You won " + 4 * Double.parseDouble(insertBet.getText()));
                    resetGame.setDisable(false);
                    turnOffButtons();
                } else if (blackjackGame.hasWon() == -1) {
                    winpopup.setText("You lost " + Double.parseDouble(insertBet.getText()));
                    resetGame.setDisable(false);
                    turnOffButtons();
                } else if (blackjackGame.hasWon() == 0) {
                    player.changeBalance(2 * blackjackGame.getBet());
                    blackjackGame.balanceChangeHelper(player.getBalance());
                    winpopup.setText("You drew. Returning your initital bet " + 2 * blackjackGame.getBet());
                    resetGame.setDisable(false);
                    turnOffButtons();
                }
            }
        }
    }

    public void hit(ActionEvent event) throws IOException {
        Card cardPic = blackjackGame.getBlackjackDeck().remove(0);
        blackjackGame.getPlayer().addCard(cardPic);
        switch (this.pictureCounterPlayer) {
            case 3:
                player3.setImage(new Image("Carddeck/" + cardPic.toString() + ".png"));
                break;
            case 4:
                player4.setImage(new Image("Carddeck/" + cardPic.toString() + ".png"));
                break;
            case 5:
                player5.setImage(new Image("Carddeck/" + cardPic.toString() + ".png"));
                break;
            default:
                break;
        }
        playerHandSum.setText(String.valueOf(blackjackGame.getPlayer().getHandSum()));
        if (blackjackGame.getPlayer().getHandSum() > 21) {
            winpopup.setText("You lost " + Double.parseDouble(insertBet.getText()));
            resetGame.setDisable(false);
            turnOffButtons();
        }
        this.pictureCounterPlayer++;
    }

    public void stand(ActionEvent event) throws IOException {
        dealer2.setImage(new Image("Carddeck/" + this.hiddenCard.toString() + ".png"));
        dealer dealer = blackjackGame.getDealer();
        player player = blackjackGame.getPlayer();
        dealerHandSum.setText(String.valueOf(dealer.getHandSum()));
        while (dealer.getHandSum() < 17) {
            Card cardPicDealer = blackjackGame.getBlackjackDeck().remove(0);
            blackjackGame.getDealer().addCard(cardPicDealer);
            switch (this.pictureCounterDealer) {
                case 3:
                    dealer3.setImage(new Image("Carddeck/" + cardPicDealer.toString() + ".png"));
                    break;
                case 4:
                    dealer4.setImage(new Image("Carddeck/" + cardPicDealer.toString() + ".png"));
                    break;
                case 5:
                    dealer5.setImage(new Image("Carddeck/" + cardPicDealer.toString() + ".png"));
                    break;
                default:

                    break;
            }
            this.pictureCounterDealer++;
            dealerHandSum.setText(String.valueOf(dealer.getHandSum()));
            if (dealer.getHandSum() > 21) {
                player.changeBalance(2 * blackjackGame.getBet());
                blackjackGame.balanceChangeHelper(player.getBalance());
                winpopup.setText("You won " + 2 * Double.parseDouble(insertBet.getText()));
                resetGame.setDisable(false);
                turnOffButtons();
            }
        }
        if (dealer.getHandSum() >= 17) {
            if (blackjackGame.hasWon() == 1) {
                player.changeBalance(2 * blackjackGame.getBet());
                dealerHandSum.setText(String.valueOf(dealer.getHandSum()));
                blackjackGame.balanceChangeHelper(player.getBalance());
                winpopup.setText("You won " + 2 * Double.parseDouble(insertBet.getText()));
                resetGame.setDisable(false);
                turnOffButtons();
            } else if (blackjackGame.hasWon() == -1) {
                winpopup.setText("You lost " + Double.parseDouble(insertBet.getText()));
                resetGame.setDisable(false);
                turnOffButtons();
            } else if (blackjackGame.hasWon() == 0) {
                player.changeBalance(blackjackGame.getBet());
                blackjackGame.balanceChangeHelper(player.getBalance());
                winpopup.setText("You drew. Returning your initital bet ");
                resetGame.setDisable(false);
                turnOffButtons();
            }
        }
    }

    public void resetGame(ActionEvent event) throws IOException {
        dealer1.setImage(null);
        dealer2.setImage(null);
        dealer3.setImage(null);
        dealer4.setImage(null);
        dealer5.setImage(null);
        player1.setImage(null);
        player2.setImage(null);
        player3.setImage(null);
        player4.setImage(null);
        player5.setImage(null);
        insertBet.setText(null);
        turnOffButtons();
        resetGame.setDisable(true);
        winpopup.setText("");
        currentBet.setText("");
        playerHandSum.setText("");
        dealerHandSum.setText("");
        startGame.setDisable(true);
        validBet.setText("Please insert bet");
        startGame.setDisable(true);
        insertBet.setOnKeyPressed(babe -> {
            if (babe.getCode() == KeyCode.DIGIT0 || babe.getCode() == KeyCode.DIGIT1
                    || babe.getCode() == KeyCode.DIGIT2 ||
                    babe.getCode() == KeyCode.DIGIT3 || babe.getCode() == KeyCode.DIGIT4
                    || babe.getCode() == KeyCode.DIGIT5 ||
                    babe.getCode() == KeyCode.DIGIT6 || babe.getCode() == KeyCode.DIGIT7
                    || babe.getCode() == KeyCode.DIGIT8 ||
                    babe.getCode() == KeyCode.DIGIT9) {
                validBet.setText("");
                startGame.setDisable(false);
            }
        });

        this.pictureCounterDealer = 3;
        this.pictureCounterPlayer = 3;
    }

    public void turnOffButtons() {
        playerBalance.setText(String.valueOf(blackjackGame.getPlayer().getBalance()));
        stand.setDisable(true);
        hit.setDisable(true);
        doubleDown.setDisable(true);
    }

    public void turnOnButtons() {
        stand.setDisable(false);
        hit.setDisable(false);
        doubleDown.setDisable(false);
    }

}
