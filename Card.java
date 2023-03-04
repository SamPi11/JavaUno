import java.util.Random;
public class Card {
    private int value;
    private String color;


    public Card() {                     //Represents an empty, unfilled card spot in hand
        this.value = -1;
        this.color = "";
    }
    public Card(int value, String color) {      //Actual Card Constructor
        this.value = value;
        this.color = color;
    }

    public static Card createCard() {           //"Draws" Cards for gameplay
        Random rand = new Random();
        int value = rand.nextInt(10);       //Randomly generates value for 0-9
        int colorChoice = rand.nextInt(85);     //Randomly generates color number. Much higher probability of colored number card than wild
        String color = "";
        if(0 <= colorChoice && colorChoice <= 19) {
            color = "red";
        }
        else if (20 <= colorChoice && colorChoice <= 39) {
            color = "blue";
        }
        else if (40 <= colorChoice && colorChoice <= 59) {
            color = "yellow";
        }
        else if (60 <= colorChoice && colorChoice <= 79) {
            color = "green";
        }
        else if (80 <= colorChoice && colorChoice <= 84) {
            color = "wild";
        }
        else {
            System.out.println("Invalid number");
        }
        
        Card card = new Card(value, color);             //Changes value of wild to -2 to overwrite initial value, since all wilds should be of value -2 for other method references.
        if (card.color.equals("wild")) {
            card.value = -2;
        }
        return card;
    }

    public String getColor() {          //Returns Card's color as a string
        return this.color;
    }
    
    public int getValue() {         //Returns Card's value as an int
        return this.value;
    }

    public void setColor(String color) {        //Sets a card's color. Not directly accessible by player. Used to change card in play to most recently played card and add/remove cards from hand.
        this.color = color;
    }
    
    public void setValue(int value) {       //Sets a card's value. Not directly accessible by player. Used to change card in play to most recently played card and add/remove cards from hand.
        this.value = value;
    }
    public String toString() {          //How a Card object is printed.
        if (this.value == -2) {         //Wild printed just as "wild"
            return this.color;
        }
        else if (this.value == -1) {        //Empty card not printed
            return "";
        }
        else {
            return this.color + " " + this.value;       //Other cards printed as "color value"
        }
    }

    public void displayCurrentCard() {              //Displays card to match to Player.
        System.out.print("The current card in play is a ");
        System.out.println(this);
        System.out.println();
    }

    public boolean checkCardMatch(Card other) {     //Checks that card that Player attempts to play matches card in play.
        boolean cardMatch = false;
        if ((this.color.equals(other.color)) || (this.value == other.value)) {
            cardMatch = true;
        }
        return cardMatch;
    }
}