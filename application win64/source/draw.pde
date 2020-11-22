
// Function that draws the grid
void drawGrid() {
  // Draw horizontal lines every interval of the gridsize
  for (int i = 0; i < height; i+=gridSize)
    line(i, 0, i, height);
   
  // Draw vertical lines every interval of the gridsize
  for (int i = 0; i < width; i+=gridSize)
    line(0, i, width, i);
}

// Function that draws the sanke
void drawSnake() {
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
void drawBerry() {
  // Draw a red square as the berry
  fill(255, 0, 0);
  rect(berry[0], berry[1], gridSize, gridSize);
  fill(0);
}
