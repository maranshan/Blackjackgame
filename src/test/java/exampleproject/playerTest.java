package exampleproject;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Blackjack.Card;

public class playerTest {

    private Blackjack.player player;

    @BeforeEach
    void setUp() {
        player = new Blackjack.player("TestUser", "testpassword", 100.0);
    }

    @Test
    void testSetAndGetUsername() {
        player.setUsername("NewUsername");
        Assertions.assertEquals("NewUsername", player.getUsername());
    }

    @Test
    void testSetUsernameWithInvalidInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            player.setUsername("");
        });
    }

    @Test
    void testSetAndGetPassword() {
        player.setPassword("newpassword");
        Assertions.assertEquals("newpassword", player.getPassword());
    }
    @Test
    void testSetPasswordWithInvalidInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            player.setPassword("");
        });
    }

    @Test
    public void testGetHandSumDifferentFaceValues() {
        player.addCard(new Card('S', 10));
        player.addCard(new Card('D', 4));
        assertEquals(14, player.getHandSum());
    }

    @Test
    public void testGetHandSumAceAndFaceCard() {
        player.addCard(new Card('C', 1)); 
        player.addCard(new Card('H', 11)); 
        assertEquals(21, player.getHandSum());
    }

    @Test
    public void testGetHandSumTwoAces() {
        player.addCard(new Card('D', 1)); 
        player.addCard(new Card('S', 1)); 
        assertEquals(12, player.getHandSum());
    }

    @Test
    void testSetBalanceWithNegativeInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            player.setBalance(-100.0);
        });
    }

    @Test
    void testChangeBalance() {
        player.changeBalance(50.0);
        Assertions.assertEquals(150.0, player.getBalance());
    }

    

}