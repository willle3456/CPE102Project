import pygame

UP = -1
DOWN = 1
LEFT = -1
RIGHT = 1
NEUTRAL = 0

class Ball:
   def __init__(self, x, y, radius, color, dx, dy):
      self.x = x
      self.y = y
      self.radius = radius
      self.color = color
      self.dx = dx
      self.dy = dy

def draw_ball(ball, screen):
   pygame.draw.ellipse(screen, ball.color,
      pygame.Rect(ball.x - ball.radius, ball.y - ball.radius,
         ball.radius * 2, ball.radius * 2)) 

