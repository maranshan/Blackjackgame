package Blackjack;


public class Card {
    private char kortfarge;
    private int tallverdi;

    public Card(char kortfarge,int tallverdi) {
        if ((kortfarge=='S' || kortfarge=='H' || kortfarge=='D' || kortfarge=='C') && tallverdi<14 && tallverdi>0) {
            this.kortfarge=kortfarge;
            this.tallverdi=tallverdi;
        }
        else {throw new IllegalArgumentException("Card objektet er ikke mulig å lage!");}
        
    }

    public char getSuit() {
        return kortfarge;
    }

    public int getFace() {
        if (tallverdi>10) {return 10;}
        return tallverdi;
    }

    @Override
    public String toString() {
        String s=String.valueOf(kortfarge)+String.valueOf(tallverdi); 
        return s;
    }
    
    
}
