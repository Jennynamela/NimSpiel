import java.util.Arrays;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PGraphics;

public class MySketch extends PApplet {
  public static void main(String[] args) {
  PApplet.runSketch(new String[]{""}, new MySketch());
  }
  

  Nim nim = Nim.of(2,3,4);

  

  public void settings() {
    size(800, 800);

  }
 int numberOfSticks=1;
 int rowSelected=0;

  public void draw() {
    background(0);
    nim.draw(this.g);
    noFill();
  
      stroke(255);
    rect(90,90+100*rowSelected,61*(numberOfSticks*1.5f),60);

  }

  public void keyPressed(){

if(keyCode==UP){

  rowSelected = (rowSelected - 1+this.nim.rows.length)%this.nim.rows.length;
}
if(keyCode == DOWN){
   rowSelected = (rowSelected + 1)%this.nim.rows.length;

  }

  if(keyCode == LEFT && numberOfSticks > 1){

    numberOfSticks = (numberOfSticks - 1+this.nim.rows[rowSelected])%this.nim.rows[rowSelected];
  }



  if(keyCode == RIGHT  && numberOfSticks < this.nim.rows[rowSelected] && this.nim.rows[rowSelected] > 0){
    numberOfSticks = (numberOfSticks + 1);
  }

  if(key == ENTER){
    this.nim = this.nim.play(Move.of(rowSelected, numberOfSticks));
    if(!nim.isGameOver()){
      nim = nim.play(nim.bestMove());
    }

    else if(nim.isGameOver()){
      super.println("Game Over");
    }
  }
}

}

 class Move {
    final int row, number;
    static Move of(int row, int number) {
        return new Move(row, number);
    }
    private Move(int row, int number) {
        if (row < 0 || number < 1) throw new IllegalArgumentException();
        this.row = row;
        this.number = number;
    }
    public String toString() {
        return "(" + row + ", " + number + ")";
    }
}


interface NimGame {
    static boolean isWinning(int... numbers) {
        return Arrays.stream(numbers).reduce(0, (i,j) -> i ^ j) != 0;
        // klassische Variante:
        // int res = 0;
        // for(int number : numbers) res ^= number;
        // return res != 0;
    }
    NimGame play(Move... moves);
    Move randomMove();
    Move bestMove();
    boolean isGameOver();
    String toString();
}

class Nim implements NimGame {
    private Random r = new Random();
    int[] rows;
    public static Nim of(int... rows) {
        return new Nim(rows);
        
    }
    private Nim(int... rows) {
        assert rows.length >= 1; 
        assert Arrays.stream(rows).allMatch(n -> n >= 0);
        this.rows = Arrays.copyOf(rows, rows.length);
    }
    private Nim play(Move m) {
        assert !isGameOver();
        assert m.row < rows.length && m.number <= rows[m.row];
        Nim nim = Nim.of(rows);
        nim.rows[m.row] -= m.number;
        return nim;
    }
    public Nim play(Move... moves) {
        Nim nim = this;
        for(Move m : moves) nim = nim.play(m);
        return nim;
    }
    public Move randomMove() {
        assert !isGameOver();
        int row;
        do {
            row = r.nextInt(rows.length);
        } while (rows[row] == 0);
        int number = r.nextInt(rows[row]) + 1;
        return Move.of(row, number);
    }
    public Move bestMove() {
        assert !isGameOver();
        if (!NimGame.isWinning(rows)) return randomMove();
        Move m;
        do {
            m = randomMove();
        } while(NimGame.isWinning(play(m).rows));
        return m;
    }
    public boolean isGameOver() {
        return Arrays.stream(rows).allMatch(n -> n == 0);
    }
    public String toString() {
        String s = "";
        for(int n : rows) s += "\n" + "I ".repeat(n);
        return s;
    }

    public void draw(PGraphics g){

       g.background(0);

       for(int i = 0; i < rows.length; i++){
           for(int j = 0; j < rows[i]; j++){
            g.stroke(0);
              g.fill(124, 10, 2);
               g.ellipse(102+ 100 * j, 100 + 100 * i, 10, 10);
                g.fill(255, 255, 0);
               g.rect(100 + 100 * j, 100 + 100 * i, 4, 50);
               
               
          }
       }
    }

  }
  
/*Nim nim = Nim.of(2,3,4);
assert nim != nim.play(Move.of(1,2)) : "Return a new Nim instance";

int[] randomSetup(int... maxN) {
    Random r = new Random();
    int[] rows = new int[maxN.length];
    for(int i = 0; i < maxN.length; i++) {
        rows[i] = r.nextInt(maxN[i]) + 1;
    }
    return rows;
}

ArrayList<Move> autoplay(NimGame nim) {
    ArrayList<Move> moves = new ArrayList<>();
    while (!nim.isGameOver()) {
        Move m = nim.bestMove();
        moves.add(m);
        nim = nim.play(m);
    }
    return moves;
}

boolean simulateGame(int... maxN) {
    NimGame nim = Nim.of(randomSetup(maxN));
    // System.out.println(nim);
    // System.out.println((NimGame.isWinning(nim.rows) ? "first" : "second") + " to win"); 
    ArrayList<Move> moves = autoplay(nim);
    // System.out.println(moves);
    return (NimGame.isWinning(nim.rows) && (moves.size() % 2) == 1) ||
           (!NimGame.isWinning(nim.rows) && (moves.size() % 2) == 0); 
}

assert IntStream.range(0,100).allMatch(i -> simulateGame(3,4,5));
assert IntStream.range(0,100).allMatch(i -> simulateGame(3,4,6,8));



/* // Beispielhaftes Spiel über JShell
jshell> Nim n = Nim.of(2,3,4)
n ==>
I I
I I I
I I I I
jshell> n = n.play(n.bestMove())
n ==>
I I
I I I
I
jshell> n = n.play(Move.of(2,1))
n ==>
I I
I I I
jshell> n = n.play(n.bestMove())
n ==>
I I
I I
jshell> n = n.play(Move.of(1,1))
n ==>
I I
I
jshell> n = n.play(n.bestMove())
n ==>
I
I
jshell> n = n.play(Move.of(1,1))
n ==>
I
jshell> n = n.play(n.bestMove())
n ==>
jshell> n.isGameOver()
$25 ==> true
*/