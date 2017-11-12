package com.ngs.games.battleship.shapes

object Shapes {
  import scala.collection.mutable.ListBuffer
  import scala.collection.mutable.HashSet

  sealed trait Ship{ def size: Int }
  case object Carrier extends Ship { val size = 5 }
  case object Battleship extends Ship { val size = 4 }
  case object Cruiser extends Ship { val size = 3 }
  case object Submarine extends Ship { val size = 3 }
  case object Destroyer extends Ship { val size = 2 }

  sealed trait Shot
  case object Hit extends Shot
  case object Sunk extends Shot
  case object Win extends Shot
  case object AlreadyTaken extends Shot
  case object Miss extends Shot
  case object OutOfBounds extends Shot

  sealed trait BattleRec
  case class FieldRec( row: Int, col: Int ) extends BattleRec
  case class ShipRec( ship: Ship, bow: FieldRec, stern: FieldRec ) extends BattleRec {
    val hits = ListBuffer.empty[FieldRec]
  }
  case class BattleArena( rows: Int, cols: Int, ships: List[ShipRec] ) extends BattleRec {
    val shotRecs = HashSet.empty[FieldRec]
  }
  case class ShotFieldRec( rows: Int, cols: Int ) extends BattleRec {
    val misses = HashSet.empty[FieldRec]
    val ships = ListBuffer.empty[ShipRec]
  }

}


