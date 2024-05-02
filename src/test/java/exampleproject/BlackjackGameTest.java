package exampleproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Blackjack.BlackjackGame;
import Blackjack.Card;
import Blackjack.player;

import static org.junit.jupiter.api.Assertions.*;

public class BlackjackGameTest {

    private BlackjackGame game;
    private player testPlayer;

    @BeforeEach
    public void setUp() {
        game = new BlackjackGame();
        testPlayer = new player("testuser", "testpassword", 100.0);
        game.setPlayer(testPlayer);
    }

    @Test
    public void testNewCardDeck() {
        game.newCardDeck();
        assertEquals(208, game.getDecksize());
    }

    @Test
    public void testHasWonFirstTry() {
        game.getPlayer().addCard(new Card('H', 1));
        game.getPlayer().addCard(new Card('S', 10));
        assertTrue(game.hasWonFirstTry(game.getPlayer()));
    }

    @Test
    public void testHasWon() {
        game.getPlayer().addCard(new Card('H', 10));
        game.getPlayer().addCard(new Card('S', 10));
        game.getDealer().addCard(new Card('H', 10));
        game.getDealer().addCard(new Card('S', 9));
        assertEquals(1, game.hasWon());
    }

    @Test
    public void testLogin() {
        game.Login("NewTestUser", "NewTestPassword", 100);
        assertFalse(game.Login("NewTestUser", "WRONGPassword", 100));
    }

    @Test 
    public void testSetInvalidBet1() throws Exception {
        game.getPlayer().setBalance(100);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            game.setBet(-10);
        });

        assertEquals("Provide a bet greater than 0", exception.getMessage());
    }

    @Test 
    public void testSetInvalidBet2() throws Exception {
        game.getPlayer().setBalance(100);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            game.setBet(110);
        });

        assertEquals("You can not afford this bet", exception.getMessage());
    }

}

