//Run this class to play the game.
import java.util.Scanner;

public class Game {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String playerInput = "";
        do {
        System.out.print("So, are you ready to play? [y] or [n]? ");   
        playerInput = input.nextLine();
            } while(!(playerInput.equals("y")) && !(playerInput.equals("n")));      //If player does not input "y" or "n", will not proceed. Plays game if "y" - Exits program if "n"
        while (playerInput.equals("y")){
        Hand Player = new Hand();                       //Creating AI and Player Hands
        Hand Computer = new Hand();
        Card cardInPlay = Card.createCard();            //Creates random Card to match to start. Is the equivalent of the last card played to match throughout the game.
        //Sass sass = new Sass();                       //Creates Sassy lines for the AI (Still work in progress)
        System.out.println("You dare to challenge me? Very well. Let's begin.");
        System.out.println();
        int playerCardCount = 7;                        //Keeps track of player and AI card count, as whomever reaches 0 cards first wins.
        int computerCardCount = 7;
        Player.initialDraw();                           //Populates both hands with 7 cards
        Computer.initialDraw();
        if (cardInPlay.getColor().equals("wild")) {     //If the first card in play is wild, lets the player choose the color to start.
            cardInPlay.displayCurrentCard();
            Player.displayHand();
            Player.firstCardWild(cardInPlay);
        }
        do {
        cardInPlay.displayCurrentCard();                //Displays card to play on.
        Player.displayHand();                       //Displays Player's hand.
        playerCardCount = Player.playCardPlayer(cardInPlay, playerCardCount);       //Player's turn. Takes in card to match and player's current card count. Returns player's card count after turn.
        if (playerCardCount == 1) {
            playerCardCount = Player.unoCallPlayer(playerCardCount);            //If player has one card after their turn, prompts for "uno"
        }
        if (playerCardCount > 0) {    
            computerCardCount = Computer.playCardComputer(cardInPlay, computerCardCount);       //If player didn't win this turn, lets AI play. Takes in card to match and AI card count. Returns AI's Card Count.
            if (computerCardCount == 1) {
                computerCardCount = Computer.unoCallComputer(computerCardCount);            //If AI has one card after its turn, prompts its uno call, with 10% chance it misses, when player can call it out.
            }
        }
        ///sass.throwShade();
        } while (playerCardCount > 0 && computerCardCount > 0);         //Continues playing if neither player nor AI reached 0 cards in hand.
        if (computerCardCount == 0) {                               //If AI wins
            System.out.println("Too bad, you lose ;-;");
            System.out.println();
        }
        else if (playerCardCount == 0) {                            //If Player wins
            System.out.println("Fine, I guess you win.");
            System.out.println();
        }
        else {                                                  //Error Statement
            System.out.println("Hmm? It seems something went wrong. Play again?");
            System.out.println();
        }
        do {
            System.out.print("Good game. Would you like to play again? [y] or [n]? ");      //Choice to play again without closing program. "y" plays again. "n" closes program.
            playerInput = input.nextLine();
            } while(!(playerInput.equals("y")) && !(playerInput.equals("n")));
        }
    }
}
