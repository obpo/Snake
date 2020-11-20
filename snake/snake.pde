
// The following variables can be customized

// Size of all cells, the second parameter can be changed
// Default value is 4, should not exceed 6, must not exceed 8
final int gridSize = int(pow(2, 5));

// Constant that declares how often the snake is updated
// It is inversly propotional to the speed of the snake (Higher value = Slower Snake)
// Default value is 6, must not be 0
final int speed = 6;

// Set default direction to right
// Possible directions [up, down, left, right]
final String defaultDirection = "right";

// Controls
final char uKey = 'w';  // Move up    | defaul is w
final char dKey = 's';  // Move down  | defaul is s
final char lKey = 'a';  // Move left  | defaul is a
final char rKey = 'd';  // Move right | defaul is d

final char reset = 'r'; // Reset game | dealut is r

// No variables from this point should be changed
// ----------------------------------------------

// Initialize snake and its direction
ArrayList<int[]> snake = new ArrayList<int[]>();
String direction;

// Initialize the berry
int[] berry = new int[2];


void setup() {
  // Set size of canvas to 512x512, Lock the fps to 60 and
  // set the default fill colour to black
  size(512, 512);
  frameRate(60);
  fill(0);
  
  // Set variables to initial values
  init();
}

void draw() {
  // Update the snake every at the set speed interval
  // but only if the blayer is not dead
  if (frameCount % speed  == 0 && !gameOver()){
    moveSnake();
    
    // check if the snake died during movement
    if (!gameOver())
      // Delete the old board and draw the new
      background(180);
      drawGrid();
      drawSnake();
      drawBerry();
  }
}

// Function that activates on all keypresses
void keyPressed() {
  // Reset the snake if the reset button is pressed
  // Moves the snake in the corresponding direction as pressed
  // except if the snake is moving in the opposite direction
  switch (key) {
    case reset:
      init();
      break;
    case uKey:
      if (direction != "down")
        direction = "up";
      break;
    case dKey:
      if (direction != "up")
        direction = "down";
      break;
    case lKey:
      if (direction != "right")
        direction = "left";
      break;
    case rKey:
      if (direction != "left")
        direction = "right";
      break;
  }
}

// Function that initializes the snake
void init() {
  // Reset snake and set the starting segment to the middle
  snake = new ArrayList<int[]>();
  int[] newSnake = {width/(gridSize*2)*gridSize-gridSize, height/(gridSize*2)*gridSize-gridSize};
  
  // Add the starting sement twice to make the player unable to collide with the tail
  snake.add(newSnake);
  snake.add(newSnake);
  
 // Sets the default direction the specified one
  direction = defaultDirection;
  
  // Generate the first berry
  genBerry();
}
