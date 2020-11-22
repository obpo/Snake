import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class snake extends PApplet {


// The following variables can be customized

// Size of all cells, the second parameter can be changed
// Default value is 4, should not exceed 6, must not exceed 8
final int gridSize = PApplet.parseInt(pow(2, 4));

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


public void setup() {
  // Set size of canvas to 512x512, Lock the fps to 60 and
  // set the default fill colour to black
  
  frameRate(60);
  fill(0);
  
  // Set variables to initial values
  init();
}

public void draw() {
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
public void keyPressed() {
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
public void init() {
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

// Function that draws the grid
public void drawGrid() {
  // Draw horizontal lines every interval of the gridsize
  for (int i = 0; i < height; i+=gridSize)
    line(i, 0, i, height);
   
  // Draw vertical lines every interval of the gridsize
  for (int i = 0; i < width; i+=gridSize)
    line(0, i, width, i);
}

// Function that draws the sanke
public void drawSnake() {
  // Draw the snake body as a light green, except the
  fill(0, 255, 0);
  for (int i = snake.size()-2; i > 0 ; i--) {
    rect(snake.get(i)[0], snake.get(i)[1], gridSize, gridSize);
  }
  
  // Make the head a darker green
  fill(0, 200, 0);
  rect(snake.get(snake.size()-1)[0], snake.get(snake.size()-1)[1], gridSize, gridSize);
  fill(0);
  
  /* NOTE:
  The tail is not drawn due to a bug that makes the player able to travel through the tail
  To fix the bug, an invisble "True Tail" was added in the initialization, which is not drawn
  */
}

// Function that draws the berry
public void drawBerry() {
  // Draw a red square as the berry
  fill(255, 0, 0);
  rect(berry[0], berry[1], gridSize, gridSize);
  fill(0);
}

// Function that moves the snake
public void moveSnake() {
  
  // Create a new head, with initial coordinates of the old head
  int[] newHead = new int[2];
  newHead[0] = snake.get(snake.size()-1)[0];
  newHead[1] = snake.get(snake.size()-1)[1];
  
  // Move head 1, cell in the snakes current direction
  switch (direction) {
    case "up":
      newHead[1] -= gridSize;
      break;
    case "down":
      newHead[1] += gridSize;
      break;
    case "left":
      newHead[0] -= gridSize;
      break;
    case "right":
      newHead[0] += gridSize;
      break;
  }
  
  // Add the new head to the snake, and remove the tail
  snake.add(newHead);
  snake.remove(0);
  
  // Eat the berry if the head is on the same spot
  if (newHead[0] == berry[0] && newHead[1] == berry[1])
    eatBerry();
}

// Function that returns true if the snake is in a dead position
// or false if the snake is not in a dead position
public boolean gameOver() {
  // Initializes snakes state to alive
  boolean result = false;
  
  // Gets the position of the snakes head
  int[] head = snake.get(snake.size()-1);
  
  // Test if the snake is out of bounds
  if (head[0] < 0 || head[0] >= width || head[1] < 0 || head[1] >= height)
    result = true;
  
  // Tests if the snake hit itself
  for (int i = snake.size()-2; i > 0; i--)
    if (snake.get(i)[0] == snake.get(snake.size()-1)[0] && snake.get(i)[1] == snake.get(snake.size()-1)[1])
      result = true;

  // Returns the snakes state
  return result;
}

// Function that generates a new berry that is
// not ontop of the snake
public void genBerry() {
  // Generate a berry at a random set of coordinates
  berry[0] = PApplet.parseInt(random(width/gridSize)) * gridSize;
  berry[1] = PApplet.parseInt(random(height/gridSize)) * gridSize;
  
  // If the berry is inside the snake, generate a new one
  for (int i = 0; i < snake.size(); i++) {
    if (snake.get(i)[0] == berry[0] && snake.get(i)[1] == berry[1]) {
      genBerry();
    }
  }
}

// Function to eat the berry and grow the snake by one
public void eatBerry() {
  // Initialize the new tail
  int[] newSegment = new int[2];
  
  // Set the new tails coordinates to the old ones
  newSegment[0] = snake.get(0)[0];
  newSegment[1] = snake.get(0)[1];
  
  // Add the new tail, and generate a new berry 
  snake.add(0, newSegment);
  genBerry();
}
  public void settings() {  size(512, 512); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "snake" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
