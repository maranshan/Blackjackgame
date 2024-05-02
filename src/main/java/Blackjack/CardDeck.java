package Blackjack;
import java.util.*;

public class CardDeck {
    private ArrayList<Card> Kortstokk = new ArrayList<Card>(Arrays.asList());
    
    
    public CardDeck(int n) {
        if (n<0) {throw new IllegalArgumentException("Feil.");}
        else {
        char a=0;
        for (int i = 0; i < 4; i++) {
            if (i==0) {a='S';}
            else if (i==1) {a='H';}
            else if (i==2) {a='D';}
            else if (i==3) {a='C';}
            for (int j = 1; j < n+1; j++) {
                Kortstokk.add(new Card(a, j));
            }
            }
        }    
        }
    
    public int getCardCount() {
        return this.Kortstokk.size();
    }

    public ArrayList<Card> getDeck() {
        return this.Kortstokk;
    }
    
    public Card getCard(int n) {
        if (Kortstokk.size() > n) {
            return Kortstokk.get(n);
        }
        else {
            throw new IllegalArgumentException("Går ikke");
        }
    }

}
