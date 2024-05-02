package Blackjack;
import java.util.*;

public class CardHand implements Iterable<Card> {
    private ArrayList<Card> hand = new ArrayList<Card>(Arrays.asList());

    public int getCardCount() {
        return hand.size();
    }

    public Card getCard(int n) {
        if (hand.size() > n) {
            return hand.get(n);
        }
        else {
            throw new IllegalArgumentException("Du er skada");
        }
    }

    public void addCard(Card card) {
        hand.add(card);
    }

	@Override
	public Iterator<Card> iterator() {
		return hand.iterator();
	}
    
}
