//import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class Hand {
    private int maxHandSize;
    private Card[] cards;
    private String[] colors = {"red", "blue", "yellow", "green"};

    public Hand() {
        maxHandSize = 100;              //A game probably won't get to the Player and/or AI having 100 cards in hand, but you never know...
        cards = new Card[maxHandSize];      //Initially creates array to hold 100 card objects.
    }

    Scanner input = new Scanner(System.in);     //Scanner and Random for the other methods.
    Random rand = new Random();

    public void initialDraw() {             //Draw at the start of game to give both Player and AI 7 cards in hand.
        for (int i = 0; i < 7; i++) {           //Creates random cards for first 7 indicies of hand array.
            this.cards[i] = Card.createCard();
        }
        for (int i = 7; i < this.maxHandSize; i++) {        //Populates remaining spots in hand array with empty cards.
            this.cards[i] = new Card();
        }
    }

    public void drawCard() {                //How one "draws" a card.
        Card drawnCard = Card.createCard();     //Creates a new random card.
        for (Card card : this.cards) {
            if (card.getValue() == -1) {            //Overwrites color and value of the first "empty" card slot it comes across with drawn card's attributes.
                card.setColor(drawnCard.getColor());
                card.setValue(drawnCard.getValue());
                break;
            }
        }
    }

    public void displayHand() {                     //Displays Player's Hand.
        System.out.print("Your current hand is: ");     
        for (Card card : this.cards) {                      //Possibly sort Hand by color and number? - For Creator
            if (card == null){
                break;
            }
            else if (card.getValue() == -1) {
                System.out.print(card);
            }
            else{
                                                    //Find a way to print last card without comma. Store first card and print last? - For Creator
                System.out.print(card + ", ");
            }
        }
        System.out.println();
        System.out.println();
    }
    
    public int playCardComputer(Card cardInPlay, int cardCount) {       //How AI plays its turn.
        boolean foundCard = false;
        boolean drewCard = false;
        Card playedCard = new Card();
        String newColor = "";
        for (Card card1 : this.cards) {                 //First, AI looks for a macthing color card by cycling through its hand array. Plays first one it finds.
            if (card1.getColor().equals(cardInPlay.getColor())) {
                cardInPlay.setColor(card1.getColor());
                cardInPlay.setValue(card1.getValue());
                playedCard.setColor(card1.getColor());
                playedCard.setValue(card1.getValue());
                card1.setColor("");
                card1.setValue(-1);
                foundCard = true;
                cardCount--;
                break;
            }
        }
        if (!foundCard) { 
            for (Card card2 : this.cards) {             //If no color match found, AI cycles through hand array again to search for value match. Plays first one it finds.
                if (card2.getValue() == cardInPlay.getValue()) {
                    cardInPlay.setColor(card2.getColor());
                    cardInPlay.setValue(card2.getValue());
                    playedCard.setColor(card2.getColor());
                    playedCard.setValue(card2.getValue());
                    card2.setColor("");
                    card2.setValue(-1);
                    foundCard = true;
                    cardCount--;
                    break;
                }
            }
        }
        if (!foundCard) {
            for (Card card3 : this.cards) {         //If no color match nor value match found, AI cycles through hand array once more to search for a wild card. Plays first one it finds.
                    if (card3.getColor().equals("wild")) {      
                        int newcolorChoice = rand.nextInt(4);
                        newColor = colors[newcolorChoice];
                        playedCard.setColor(card3.getColor());  // (jude) I think this is the source of the computer 'wild' issue
                                                                // (jude) may need to set cardInPlay's color to the randomly chosen color
                        playedCard.setValue(card3.getValue());
                        //playedCard.setColor(newColor);        // (jude) unsure why this was commented out - uncommented to set playedCard color to new color 
                                        // (jude) nevermind - I see that it's unnecessary here w/ the previous setColor
                        card3.setColor("");
                        card3.setValue(-1);
                        foundCard = true;
                        cardCount--;
                        break;
                    }
                }
            }
        if (!foundCard) {           //If no valid card to match is found through the three cycles, AI draws a card.
            this.drawCard();
            drewCard = true;
            cardCount++;
        }
        if (foundCard) {        //Prints out what card the AI played if it played one.
            System.out.print("The Computer played a " + playedCard + " - ");
            if (playedCard.getColor().equals("wild")) {     //Special print statements for wild to announce new color.
                playedCard.setColor(newColor);
                System.out.print("The next card to play must be ");
                System.out.println(playedCard.getColor());
                System.out.println();
            }
           
        }
        if (drewCard) {     //Announcement that the AI drew a card if it drew one.
            System.out.print("The Computer drew a card! " + " - ");
        }
        if (cardCount == 1) {       //Special print statement for one card so it doesn't say "cards".
            System.out.println("The Computer has " + cardCount + " card in hand.");
        }
        else {      //Display to Player how many playable cards the AI has in its hand array.
            System.out.println("The Computer has " + cardCount + " cards in hand.");
        }
        System.out.println();
        return cardCount;
        }
    

    public int playCardPlayer(Card cardInPlay, int cardCount) {             //Player Turn
       boolean validCard = false;           //Ensures a valid card or the option to draw was selected.
        Card playedCard = new Card();       //Holds card played this turn.
       do {
        System.out.print("Which color card would you like to play? red, blue, yellow, green, or wild? Or would you like to draw? ");        //Starts with asking Player for color of card.
        String colorChoice = input.nextLine();
        String colorChoiceLower = colorChoice.toLowerCase();
        if (colorChoiceLower.equals("wild")) {      //If Player plays a wild, prompts for new color to play on.
            for (Card card : this.cards) {
                if (card.getColor().equals("wild")) {
                    System.out.print("What's the new color? ");
                    String newColor = input.nextLine();
                    String newColorLower = newColor.toLowerCase();
                    //if (checkCardColor(newColorLower)) {              //I'm currently implementing a check that the Player inputs a valid color here.
                    // (jude) this check is definitely important - method could return true if the color matches any of the card colors
                        playedCard.setColor(card.getColor());
                        playedCard.setValue(card.getValue());
                        playedCard.setColor(newColorLower);
                        cardInPlay.setColor(newColorLower);         //Sets card color to selected color.
                        cardInPlay.setValue(card.getValue());       //Sets card value to that of wild card, -2.
                        card.setColor("");                  //Sets card played from hand to "empty" card slot in hand.
                        card.setValue(-1);
                        cardCount--;
                        System.out.println();
                        validCard = true;
                        break;
                    //}
                    //else {
                                        // (jude) else could just give an 'invalid input' message then prompt again
                    //}
                }
            }
        }
        else if (colorChoiceLower.equals("draw")) {     //Draws for player.
            this.drawCard();
            cardCount++;
            validCard = true;
            System.out.println();
        }
        else {                      //If color other than "wild" is selected, prompts for value 0-9.
            boolean validNumber = true;
            int valueChoice = -1;
            do {
            System.out.print("Which value of card would you like to play? 0 through 9? ");
            try {
                valueChoice = input.nextInt();
                validNumber = true;
            } catch (java.util.InputMismatchException exception) {          //Just in case user inputs a String when the scanner is expecting an int
                System.out.println("Try again. Input must be an integer 0-9");
                input.nextLine();
                validNumber = false;
            }
            } while(validNumber == false);      //Will reprompt until valid number is input.
            input.nextLine();
            for (Card card : this.cards) {
                if (card.getColor().equals(colorChoiceLower)) {
                    if (card.getValue() == valueChoice) {
                        playedCard.setColor(card.getColor());       //Sets to attributes of card Player is attempting to play.
                        playedCard.setValue(card.getValue());
                        if (playedCard.checkCardMatch(cardInPlay)) {      //If card played is a valid match, changes card in play and empties slot in hand.    
                            cardInPlay.setColor(card.getColor());
                            cardInPlay.setValue(card.getValue());
                            card.setColor("");
                            card.setValue(-1);
                            cardCount--;
                            validCard = true;
                            System.out.println();
                            break;
                        }
                        else {
                            playedCard.setColor("");        //If card selected is not a match, resets played card so it will reprompt user for another move.
                            playedCard.setValue(-1);
                            break;
                        }
                    } 
                }
            }
        }
        if (validCard == true) {        //These run if a valid move was played.
            if (colorChoiceLower.equals("draw")) {      //Print statements for Player Drawing a card.
                System.out.println("You drew a card!");
                System.out.println();
            }
            else {     
                if (playedCard.getValue() == -2) {      //Print statements for if Player played a wild.
                    System.out.println("You played a wild!");
                    System.out.print("The next card to play must be ");
                    System.out.println(playedCard.getColor());
                    System.out.println();
                }
                else {          //Print statements for if Player played a colored card 0-9.
                System.out.print("You played a ");
                System.out.println(playedCard);
                System.out.println();
                }
            }
        }
        else {          //Triggered if 1.Selected color/value doesn't match a card in hand 2.Card selected is in hand, but doesn't match card in play 3.Invalid inputs for color, value, or draw
            if (playedCard.getValue() == -1) {
                System.out.println("Invalid play/draw attempt. Please select a card from your hand or choose to draw.");
            }
        }
        } while (validCard == false);
        return cardCount;
    }

    public static boolean checkCardColor(String color) {        //Trying to use for within Player's color selection after playing a wild. Still needs some work.
        boolean colorOK = false;
        switch (color) {
            case "red":
            case "blue":
            case "green":
            case "yellow":
                colorOK = true;
                break;
            default:
                System.out.println("Invalid color");
                break;
        }
        return colorOK;
    }

    public static boolean checkCardValue(int value) {       //Wrote early on to check for a valid value of card. Not currently in use, but I kept it in case it would be useful. 
        boolean valueOK = false;                                //Will reevaluate usefulness as I get closer to finishing.
        switch (value) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                valueOK = true;
                break;
            default:
                System.out.println("Invalid value");
                break;
        }
        return valueOK;
    }

    public void firstCardWild(Card cardInPlay) {        //Method for if first card in play generates as a wild. Player gets to choose color.
        System.out.print("Lucky for you, the first card's a wild. What color are we playing? Your choice. ");
        String newColor = input.nextLine();
        String newColorLower = newColor.toLowerCase();
        cardInPlay.setColor(newColorLower);
        System.out.print("The next card to play must be ");
        System.out.println(cardInPlay.getColor());
        System.out.println();
    }

    public int unoCallPlayer(int cardCount) {       //Prompts Player for calling uno.
        System.out.print("Uh oh, you only have one card left. Don't you have something to say? ");
        String response = input.nextLine();
        String responseLower = response.toLowerCase();
        if (responseLower.substring(0, 3).equals("uno")) {      //Will accept "uno" in any form of capitalization and with any punctuation afterwards, as long as first three characters are "uno"
            System.out.println("Nice catch");
            System.out.println();
        }
        else {          //If Player doesn't type something that begins with "uno"
            System.out.println("Haha, caught you! Draw 2!");
            System.out.println();
            this.drawCard();
            this.drawCard();
            cardCount = cardCount + 2;
        }
        return cardCount;
    }

    public int unoCallComputer(int cardCount) {     //AI's chance to call uno
        int callChance = rand.nextInt(10);
        if (0 <= callChance && callChance <= 8) {       //90% of the time, it will successfully call uno.
            System.out.println("Uno!!!!");
            System.out.println();
        }
        else if(callChance == 9) {          //10% of the time, it will "forget" to call uno, at which time, the player can call it out and force it to draw two cards.
            System.out.println("...");
            System.out.print("(Quick! Type 'call' to catch the computer. It didn't call uno!) ");
            String response = input.nextLine();
            String responseLower = response.toLowerCase();
            if (responseLower.substring(0, 4).equals("call")) {
                System.out.println("Dang, you got me.");
                this.drawCard();
                this.drawCard();
                cardCount = cardCount + 2;
                if (cardCount == 1) {       //Prints AI's remaining cards
                    System.out.println("The Computer has " + cardCount + " card in hand.");
                }
                else {       //Prints AI's remaining cards
                    System.out.println("The Computer has " + cardCount + " cards in hand.");
                }
                System.out.println();
            }
        }
        return cardCount;
    }
}
