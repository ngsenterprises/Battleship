package com.ngs.games.battleship.test

import org.scalatest.{FlatSpec, Matchers}
import com.ngs.games.battleship.shapes.Shapes._
import com.ngs.games.battleship.utils.Utils._

class AttackSpec extends FlatSpec with Matchers {

  "For an empty 10x10 Arena grid, attack" should "return [Miss] when called on all positions." in {

    val boardsize = (10, 10)
    val player = Player( "Mac", boardsize, List.empty[ShipRec] )

    ( 0 until boardsize._1 ).foreach( i => ( 0 until boardsize._2 ).foreach( j =>
      assert( player.attack( i, j ) == Miss )
    ) )
  }

  "For an empty 10x10 Arena grid, attack" should "return [OutOfBounds] for all perimeter positions." in {

    val boardsize = (10, 10)
    val player = Player( "Mac", boardsize, List.empty[ShipRec] )

    Seq( -1 ).foreach( i => ( 0 until boardsize._2 ).foreach( j =>
      assert( player.attack( i, j ) == OutOfBounds )
    ) )
    Seq( boardsize._1 ).foreach( i => ( 0 until boardsize._2 ).foreach( j =>
      assert( player.attack( i, j ) == OutOfBounds )
    ) )
    ( 0 until boardsize._1 ).foreach( i => Seq( -1 ).foreach( j =>
      assert( player.attack( i, j ) == OutOfBounds )
    ) )
    ( 0 until boardsize._1 ).foreach( i => Seq( boardsize._2 ).foreach( j =>
      assert( player.attack( i, j ) == OutOfBounds )
    ) )
  }

  "For an empty 10x10 Arena grid, attack" should "return [Miss, AlreadyTaken] for 2 identical shot positions." in {

    val boardsize = (10, 10)
    val player = Player( "Mac", boardsize, List.empty[ShipRec] )

    assert( player.attack( 5, 5 ) == Miss )
    assert( player.attack( 5, 5 ) == AlreadyTaken )
  }

  "For a 10x10 Arena grid with 1 Destroyer, attack" should "return [Hit, AlreadyTaken] for identical attack positions." in {

    val boardsize = (10, 10)
    val ships = List( ShipRec( Destroyer, bow=FieldRec( 0, 0 ), stern=FieldRec( 0, 1) ) )

    val player = Player( "Mac", boardsize, ships )

    assert( player.attack( 0, 0 ) == Hit )
    assert( player.attack( 0, 0 ) == AlreadyTaken )
  }


  "For a 10x10 Arena grid with 2 Destroyers, attack" should "return [Hit, Sunk] on one Destroyer ship." in {

    val boardsize = (10, 10)
    val ships = List( ShipRec( Destroyer, bow=FieldRec( 0, 0 ), stern=FieldRec( 0, 1) ),
                      ShipRec( Destroyer, bow=FieldRec( boardsize._1 -1, 0 ), stern=FieldRec( boardsize._1 -1, 1) ) )

    val player = Player( "Mac", boardsize, ships )

    assert( player.attack( 0, 0 ) == Hit )
    assert( player.attack( 0, 1 ) == Sunk )
  }

  "For a 10x10 Arena grid with 2 Destroyers, attack" should "return [Hit, Sunk, Hit, Win] on 2 Destroyer ships." in {

    val boardsize = (10, 10)
    val ships = List( ShipRec( Destroyer, bow=FieldRec( 0, 0 ), stern=FieldRec( 0, 1) ),
      ShipRec( Destroyer, bow=FieldRec( boardsize._1 -1, 0 ), stern=FieldRec( boardsize._1 -1, 1) ) )

    val player = Player( "Mac", boardsize, ships )

    assert( player.attack( 0, 0 ) == Hit )
    assert( player.attack( 0, 1 ) == Sunk )
    assert( player.attack( boardsize._1 -1, 0 ) == Hit )
    assert( player.attack( boardsize._1 -1, 1 ) == Win )
  }

  "For a 10x10 Arena grid with a fleet of ships, attack" should "return [Hit, Hit, Hit, Hit, Sunk],[Hit, Hit, Hit, Sunk],[Hit, Hit, Sunk],[Hit, Hit, Sunk],[Hit, Win] on a fleet od ships." in {

    val boardsize = (10, 10)
    val ships = List(
      ShipRec( Carrier, bow=FieldRec( 0, 1 ), stern=FieldRec( 0, 5) ),
      ShipRec( Battleship, bow=FieldRec( 1, 1 ), stern=FieldRec( 1, 4) ),
      ShipRec( Cruiser, bow=FieldRec( 3, 3 ), stern=FieldRec( 5, 3) ),
      ShipRec( Submarine, bow=FieldRec( 3, 4 ), stern=FieldRec( 5, 4) ),
      ShipRec( Destroyer, bow=FieldRec( 3, 5 ), stern=FieldRec( 4, 5) )
    )

    val player = Player( "Mac", boardsize, ships )

    assert( player.attack( 0, 1 ) == Hit )
    assert( player.attack( 0, 2 ) == Hit )
    assert( player.attack( 0, 3 ) == Hit )
    assert( player.attack( 0, 4 ) == Hit )
    assert( player.attack( 0, 5 ) == Sunk )

    assert( player.attack( 1, 1 ) == Hit )
    assert( player.attack( 1, 2 ) == Hit )
    assert( player.attack( 1, 3 ) == Hit )
    assert( player.attack( 1, 4 ) == Sunk )

    assert( player.attack( 3, 3 ) == Hit )
    assert( player.attack( 4, 3 ) == Hit )
    assert( player.attack( 5, 3 ) == Sunk )

    assert( player.attack( 3, 4 ) == Hit )
    assert( player.attack( 4, 4 ) == Hit )
    assert( player.attack( 5, 4 ) == Sunk )

    assert( player.attack( 3, 5 ) == Hit )
    assert( player.attack( 4, 5 ) == Win )
  }




}
