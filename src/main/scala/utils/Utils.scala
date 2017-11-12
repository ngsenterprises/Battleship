package com.ngs.games.battleship.utils

import com.ngs.games.battleship.shapes.Shapes._

object Utils {

  case class Player( name: String, arena: BattleArena, shotField: ShotFieldRec ) {

    def attack( row: Int, col: Int ): Shot = {
      //function for locating a ship
      def isShip( row: Int, col: Int, rec: ShipRec ): Boolean = {
        math.min(rec.bow.row, rec.stern.row) <= row && row <= math.max(rec.bow.row, rec.stern.row) &&
          math.min(rec.bow.col, rec.stern.col) <= col && col <= math.max(rec.bow.col, rec.stern.col)
      }
      //out of bounds check
      if ( row < 0 || arena.rows <= row || col < 0 || arena.cols <= col) OutOfBounds else {

        arena.ships.find( isShip( row, col, _) ) match {
          //ship located
          case Some(shipRec) => {
            val fieldRec = FieldRec( row, col )
            if ( shipRec.hits.contains( fieldRec ) ) AlreadyTaken else {
              shipRec.hits += fieldRec
              if ( shipRec.ship.size <= shipRec.hits.length )
                if ( arena.ships.forall( r => r.ship.size <= r.hits.length ) ) Win else Sunk
              else Hit
            }
          }
          //no ship located
          case None => {
            val fieldRec = FieldRec( row, col )
            if ( arena.shotRecs.contains( fieldRec ) ) AlreadyTaken else {
              arena.shotRecs += fieldRec
              Miss
            }
          }
        }
      }
    }
  }
  object Player {
    def apply( name: String, boardSize: (Int, Int), ships: List[ShipRec] ) =
    {
      new Player(name, BattleArena(boardSize._1, boardSize._2, ships), ShotFieldRec(boardSize._1, boardSize._2))
    }
  }





}
