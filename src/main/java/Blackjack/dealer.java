package Blackjack;

import java.util.ArrayList;

public class dealer implements playerInterface{

    private String Username;
    private CardHand hand = new CardHand();


    public dealer(String username) {
        setUsername(username);
    }

    @Override
    public void setUsername(String Username) {
        if (Username==null || Username.equals("") || !Username.matches("^[a-zA-Z]*$")) {
            throw new IllegalArgumentException("Navnet ditt er ikke gyldig");
        }
        this.Username = Username;
    }
        

    @Override
    public String getUsername() {
        return Username;    
    }

    @Override
    public void addCard(Card card) {
        this.hand.addCard(card);
    }

    @Override
    public int getHandSum() {
        int sum=0;
        ArrayList<Integer> sumList=new ArrayList<>();
        for (Card card:this.hand) {
            sumList.add(card.getFace());
            if (sumList.contains(1) && sumList.stream().mapToInt(a->a).sum()+10<=21) {
                sum = sumList.stream().mapToInt(a->a).sum()+10;
            }
            else {
                sum = sumList.stream().mapToInt(a->a).sum();
            }
        }

        return sum;
    }
    @Override
    public CardHand getHand() {
        return this.hand;   
    }
    

}
