package Blackjack;

import java.util.ArrayList;

public class player implements playerInterface {

    private String Username;
    private String password;
    private double balance;
    private CardHand hand = new CardHand();

    
    public player(String username, String password, double balance) {
        setUsername(username);
        setPassword(password);
        setBalance(balance);
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
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        if (password==null || password.equals("")) {
            throw new IllegalArgumentException("Passordet ditt er ikke gyldig");
        }
        this.password = password;
    }
    
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Venligst oppgi gyldig sum");
        }
        this.balance = balance;
    }

    public void changeBalance(double deposit) {
        this.balance += deposit ;
    }
    

    
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
