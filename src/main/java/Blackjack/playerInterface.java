package Blackjack;

public interface playerInterface {
    public void setUsername(String Username);
    public String getUsername();
    public void addCard (Card card);
    public int getHandSum();
    public CardHand getHand();

}
