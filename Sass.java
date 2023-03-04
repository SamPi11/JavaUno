import java.util.Random;

public class Sass {                 //Meant to funnily trash talk during game. Work in progress.
    private String[] sass = {"Hmm, that's your move?", "Are you sure?"};
    
    public Sass() {
    //this.sass = sass;
    }

    public void throwShade(){
    Random sassLord = new Random();
    int sassLine = sassLord.nextInt(1);
    System.out.println(this.sass[sassLine]);
    }
}
