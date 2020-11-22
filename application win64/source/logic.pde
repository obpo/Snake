
// Function that moves the snake
void moveSnake() {
  
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
boolean gameOver() {
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
void genBerry() {
  // Generate a berry at a random set of coordinates
  berry[0] = int(random(width/gridSize)) * gridSize;
  berry[1] = int(random(height/gridSize)) * gridSize;
  
  // If the berry is inside the snake, generate a new one
  for (int i = 0; i < snake.size(); i++) {
    if (snake.get(i)[0] == berry[0] && snake.get(i)[1] == berry[1]) {
      genBerry();
    }
  }
}

// Function to eat the berry and grow the snake by one
void eatBerry() {
  // Initialize the new tail
  int[] newSegment = new int[2];
  
  // Set the new tails coordinates to the old ones
  newSegment[0] = snake.get(0)[0];
  newSegment[1] = snake.get(0)[1];
  
  // Add the new tail, and generate a new berry 
  snake.add(0, newSegment);
  genBerry();
}
