import entities
import worldmodel
import pygame
import math
import random
import point
import image_store

BLOB_RATE_SCALE = 4
BLOB_ANIMATION_RATE_SCALE = 50
BLOB_ANIMATION_MIN = 1
BLOB_ANIMATION_MAX = 3

ORE_CORRUPT_MIN = 20000
ORE_CORRUPT_MAX = 30000

QUAKE_STEPS = 10
QUAKE_DURATION = 1100
QUAKE_ANIMATION_RATE = 100

VEIN_SPAWN_DELAY = 500
VEIN_RATE_MIN = 8000
VEIN_RATE_MAX = 17000


def sign(x):
   if x < 0:
      return -1
   elif x > 0:
      return 1
   else:
      return 0









def create_ore_blob_action(world, entity, i_store):
   def action(current_ticks):
      entity.remove_pending_action(action)

      entity_pt = entity.get_position()
      vein = world.find_nearest(entity_pt, entities.Vein)
      (tiles, found) = blob_to_vein(world, entity, vein)

      next_time = current_ticks + entity.get_rate()
      if found:
         quake = create_quake(world, tiles[0], current_ticks, i_store)
         world.add_entity(quake)
         next_time = current_ticks + entity.get_rate() * 2

      schedule_action(world, entity,
         create_ore_blob_action(world, entity, i_store),
         next_time)

      return tiles
   return action







def remove_entity(world, entity):
   for action in entity.get_pending_actions():
      world.unschedule_action(action)
   entity.clear_pending_actions()
   world.remove_entity(entity)




def schedule_blob(world, blob, ticks, i_store):
   schedule_action(world, blob, create_ore_blob_action(world, blob, i_store),
      ticks + blob.get_rate())
   schedule_animation(world, blob)











def schedule_animation(world, entity, repeat_count=0):
   schedule_action(world, entity,
      create_animation_action(world, entity, repeat_count),
      entity.get_animation_rate())


def clear_pending_actions(world, entity):
   for action in entity.get_pending_actions():
      world.unschedule_action(action)
   entity.clear_pending_actions()
