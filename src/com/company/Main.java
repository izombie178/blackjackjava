package com.company;

import java.util.*;

class Card
{
    private String rank;
    private String suit;

    public Card(String card_rank, String card_suit)
    {
        rank=card_rank;
        suit=card_suit;
    }

    public String card_representation()
    {
        String str = ""+this.rank+" Of "+this.suit;
        return str;
    }

    public int return_card_rank()
    {
        Map<String, Integer> card_ranks = new HashMap<>();
        card_ranks.put("Ace",1);card_ranks.put("2",2);card_ranks.put("3",3);card_ranks.put("4",4);card_ranks.put("5",5);
        card_ranks.put("6",6);card_ranks.put("7",7);card_ranks.put("8",8);card_ranks.put("9",9);card_ranks.put("10",10);
        card_ranks.put("Jack",10);card_ranks.put("Queen",10);card_ranks.put("King",10);
        int card_value=card_ranks.get(this.rank);
        return card_value;
    }
}

class Deck
{
    private String suits[]={"Clubs","Diamonds","Hearts","Spades"};
    private String ranks[]={"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};

    public Deck()
    {

    }
    public LinkedList<Card> generate_deck()
    {
        LinkedList<Card> deck = new LinkedList<Card>();
        for(String suit:suits)
        {
            for(String rank:ranks)
            {
                deck.add(new Card(rank,suit));
            }
        }
        return deck;
    }
    public void shuffle_array(Object[] arr)
    {
        Random rand = new Random();
        for (int i = 0; i < arr.length - 1; i++) {
            int index = rand.nextInt(i + 1);
            Object g = arr[index];
            arr[index] = arr[i];
            arr[i] = g;
        }
    }
    public void list_data_populate(Object[] arr, LinkedList<Card> list)
    {
        ListIterator<Card> it = list.listIterator();
        for (Object e : arr) {
            it.next();
            it.set((Card) e);
        }
    }

    public void shuffle_deck(LinkedList<Card> deck)
    {
        Object[] array = deck.toArray();
        shuffle_array(array);
        list_data_populate(array, deck);
    }

    public Map<String, Integer> init_black_jack_game(LinkedList<Card> deck,LinkedList<Card> dealers_cards,LinkedList<Card> players_cards)
    {
        // This is dealing for the dealer ie the first two cards
        dealers_cards.add(deck.get(0));
        deck.remove(0);
        dealers_cards.add(deck.get(1));
        deck.remove(1);
        // This is dealing for the player ie another two cards to the player's side
        players_cards.add(deck.get(2));
        deck.remove(2);
        players_cards.add(deck.get(3));
        deck.remove(3);
        int player_blackjack_score=0;
        int dealer_blackjack_score=0;

        System.out.println("Player Side");
        System.out.println("-----------------------------");

        for(Card card:players_cards)
        {
            player_blackjack_score+=card.return_card_rank();
            System.out.println(card.card_representation());
        }

        for(Card card:dealers_cards)
        {
            dealer_blackjack_score+=card.return_card_rank();
        }

        System.out.println("The total points are: "+player_blackjack_score);

        Map<String, Integer> player_dealer_scores = new HashMap<>();
        player_dealer_scores.put("Player",player_blackjack_score);
        player_dealer_scores.put("Dealer",dealer_blackjack_score);

        return player_dealer_scores;
    }
}

public class Main {

    public static void main(String[] args) {
        Deck deck = new Deck();
        Scanner scanner = new Scanner(System.in);

        String playing_option="y",card_option;
        while(Objects.equals(playing_option, "y"))
        {
            LinkedList<Card> dealers_cards= new LinkedList<>();
            LinkedList<Card> players_cards= new LinkedList<>();
            LinkedList<Card> card_deck = deck.generate_deck();
            deck.shuffle_deck(card_deck); // This shuffles the cards
            System.out.println("The deck of cards has been shuffled and it contains "+card_deck.size()+" cards");
            Map<String, Integer> player_dealer_scores;
            player_dealer_scores=deck.init_black_jack_game(card_deck,dealers_cards,players_cards);
            System.out.println("Do you want to get another card? (y/n)");
            card_option=scanner.next();

            while(Objects.equals(card_option, "y"))
            {
                int player_scores=0;
                players_cards.add(card_deck.get(0));
                card_deck.remove(0);
                for(Card card:players_cards)
                {
                    player_scores+=card.return_card_rank();
                    System.out.println(card.card_representation());
                }
                player_dealer_scores.put("Player",player_scores);
                if(player_dealer_scores.get("Player")>21){
                    System.out.println("The Total points are: "+player_dealer_scores.get("Player"));
                    System.out.println("Burst!: You lose!");
                    System.out.println("Do you want to play again? (y/n)");
                    card_option="n";
                    playing_option=scanner.next();
                }
                else if(player_dealer_scores.get("Player")==21)
                {
                    System.out.println("The Total points are: "+player_dealer_scores.get("Player"));
                    System.out.println("Congratulations!: You win!");
                    System.out.println("Do you want to play again? (y/n)");
                    card_option="n";
                    playing_option=scanner.next();
                }
                else{
                    System.out.println("The Total points are: "+player_dealer_scores.get("Player"));
                    System.out.println("Do you want to get another card(y/n)");
                    card_option=scanner.next();
                }

            }
            if(player_dealer_scores.get("Player")<21)
            {
                System.out.println("Dealer Side");
                System.out.println("-----------------------------");
                for(Card card:dealers_cards)
                {
                    System.out.println(card.card_representation());
                }
                System.out.println("The Total points are: "+player_dealer_scores.get("Dealer"));
                while(player_dealer_scores.get("Dealer")<=16)
                {
                    int dealer_scores=0;
                    dealers_cards.add(card_deck.get(0));
                    card_deck.remove(0);
                    for(Card card:dealers_cards)
                    {
                        dealer_scores+=card.return_card_rank();
                        System.out.println(card.card_representation());
                    }
                    player_dealer_scores.put("Dealer",dealer_scores);
                    System.out.println("The Total points are: "+player_dealer_scores.get("Dealer"));
                }
                if(Math.abs(player_dealer_scores.get("Dealer")-21)<Math.abs(player_dealer_scores.get("Player")-21))
                {
                    System.out.println("Dealer: "+player_dealer_scores.get("Dealer")+" You: "+player_dealer_scores.get("Player"));
                    System.out.println("Dealer Black Jack!: You lose!");
                    System.out.println("Do you want to play again? (y/n)");
                    playing_option=scanner.next();
                }
                else if(Math.abs(player_dealer_scores.get("Dealer")-21)>Math.abs(player_dealer_scores.get("Player")-21))
                {
                    System.out.println("Dealer: "+player_dealer_scores.get("Dealer")+" You: "+player_dealer_scores.get("Player"));
                    System.out.println("Congratulations!: You win!");
                    System.out.println("Do you want to play again? (y/n)");
                    playing_option=scanner.next();
                }
                else if(Objects.equals(Math.abs(player_dealer_scores.get("Dealer")-21), Math.abs(player_dealer_scores.get("Player")-21)))
                {
                    System.out.println("Dealer: "+player_dealer_scores.get("Dealer")+" You: "+player_dealer_scores.get("Player"));
                    System.out.println("Its a draw!");
                    System.out.println("Do you want to play again? (y/n)");
                    playing_option=scanner.next();
                }
            }
        }
        System.out.println("Game Ended");
    }
}
