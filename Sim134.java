import java.awt.Color;

public class Sim134{
    private final int N = 80;
    private boolean[][] cells;
    private Picture pic;
    final int multiplier = 10; //use for scale



  public Sim134(String pattern){

    this.cells = new boolean[N][N];
    pic = new Picture(N*multiplier,N*multiplier); // using a scale since regular output is very small
    if ("R".equals(pattern)){
      randomGenerator();
    }                                // if statements calling the pattern wanted in the command line
    else if ("D".equals(pattern)){
      DartGenerator();
    }
    else if ("J".equals(pattern)){
      jamGenerator();

    }

  }
  public void randomGenerator(){     //generates random alive and dead cells with 50% chance
    for (int i = 0; i<N;i++){
      for(int j=0;j<N;j++){
        cells[i][j] = Math.random() < 0.5;
      }
    }
  }
  public void DartGenerator(){    //initially generating the dart patter
    for (int i = 0; i<N; i++){
      for (int j = 0; j<N;j++){
        cells[i][j]= false;
      }
    }
    cells[8][1] = true;
    cells[7][2] = true;
    cells[9][2] = true;
    cells[6][3] = true;
    cells[10][3] = true;
    cells[7][4] = true;
    cells[8][4] = true;
    cells[9][4] = true;
    cells[5][6] = true;
    cells[6][6] = true;
    cells[10][6] = true;
    cells[11][6] = true;
    cells[3][7] = true;
    cells[7][7] = true;
    cells[9][7] = true;
    cells[13][7] = true;
    cells[2][8] = true;
    cells[3][8] = true;
    cells[7][8] = true;
    cells[9][8] = true;
    cells[13][8] = true;
    cells[14][8] = true;
    cells[1][9] = true;
    cells[7][9] = true;
    cells[9][9] = true;
    cells[15][9] = true;
    cells[2][10] = true;
    cells[4][10] = true;
    cells[5][10] = true;
    cells[7][10] = true;
    cells[9][10] = true;
    cells[11][10] = true;
    cells[12][10] = true;
    cells[14][10] = true;




  }
  public void jamGenerator(){
    for (int i = 0; i<N; i++){
      for (int j = 0; j<N;j++){
        cells[i][j]= false;
      }
    }
    cells[6][3] = true;
    cells[7][3] = true;
    cells[5][4] = true;
    cells[8][4] = true;
    cells[3][5] = true;
    cells[6][5] = true;
    cells[8][5] = true;
    cells[3][6] = true;
    cells[7][6] = true;
    cells[3][7] = true;
    cells[6][8] = true;
    cells[4][9] = true;
    cells[5][9] = true;

  }
  public int aliveNeighborhood(int r, int c){  // checks the number of alive neighborhood cells for each cell
    int aliveCount = 0;
    int[] rHood = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] cHood = {-1, 0, 1, -1, 1, -1, 0, 1};
    for (int i = 0; i<8; i++){
      int currRow = (r + rHood[i] + N) % N; // topo rows neighborhood
      int currCol = (c + cHood[i] + N) % N; // topo columns neighborhood
      if (cells[currRow][currCol]){
        aliveCount+=1;
      }
    }
    return aliveCount;
  }
  public void update(){                                 // updates each cell to next generation using game of life rules
    boolean[][] children = new boolean[N][N];
    for(int i = 0;i<N;i++){
      for(int j=0;j<N;j++){
        int aliveNeighbors = aliveNeighborhood(i, j);

        if (cells[i][j]) {
            if (aliveNeighbors == 2 || aliveNeighbors == 3) {
                children[i][j] = true; // Survival
            }
            else {
                children[i][j] = false; // Die
            }
        }
        else {
            if (aliveNeighbors == 3) {
                children[i][j] = true; // revive
            }
            else {
                children[i][j] = false; // Stay dead
            }
        }


      }
    }
    cells = children; // updating to the next gen
  }
  public void draw() {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            boolean alive = cells[i][j]; // Get the state of the current cell

            // using the scale to magnify each cell but number of cells is still 80x80
            for (int p = 0; p < multiplier; p++) {
                for (int q = 0; q < multiplier; q++) {
                    int x = i * multiplier + p;
                    int y = j * multiplier + q;

                    // Set color for grid base on alive or dead cells
                    if (alive) {
                        pic.set(x, y, Color.BLACK);
                    } else {
                        pic.set(x, y, Color.WHITE);
                    }
                }
            }
        }
    }
    pic.show(); // Display the scaled grid
}

  public static void main(String[] args) {
      int looper = Integer.parseInt(args[0]);
      String pattern = args[1].toUpperCase();
      Sim134 game = new Sim134(pattern);
      game.draw();

      for (int i = 0; i < looper; i++) {

          try { Thread.sleep(300); } // sets speed/delay!
          catch (Exception ex) { /* ignore */ }

          game.update();
          game.draw();
      }
  }
}
