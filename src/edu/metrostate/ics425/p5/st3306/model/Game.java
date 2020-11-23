package edu.metrostate.ics425.p5.st3306.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

public class Game implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int GAME_POCKETS = 14;

	private static LinkedList<Integer> board = new LinkedList<Integer>();

	private boolean gameWon;
	private int playerTurn;
	private String disableSide;
	private String gameMessage;

	public Game() 
	{
		newGame();
	}

	/**
	 * Creates new game and sets default values
	 * player is randomly selected between p0 and p1
	 * games board is created with four stones in each pocket and zero in store
	 */
	public final void newGame() 
	{
		int count = 0;
		if(Game.board != null) {
			Game.board.clear();
		}
		while(count < Game.GAME_POCKETS) {
			if(count == 0 || count == 13) {
				Game.board.add(0);
			} else {
				Game.board.add(4);
			}
			count++;
		}

		gameWon = false;
		this.setMessage("New Game Started");

		Random rand = new Random();
		playerTurn = rand.nextInt(2);

		if(playerTurn == 0) {
			disableSide = "B";
		} else {
			disableSide = "A";
		}
	}

	/**
	 * @return Game board
	 * getter method for game board
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<Integer> getBoard()
	{
		return (LinkedList<Integer>) Game.board.clone();
	}

	/**
	 * @param position
	 * the method will first clone the game board
	 * post-cloding the user selected pocket is set as the position
	 * the target position is consider the next element
	 * the method then loops through the value found for the select position until the value is 0
	 * the value is distributed evenly
	 * if the last value lands on the store a point is added and player gets to play agin
	 * if the last value lands in a pocket that is empty (0) and is on players side and other pit has stones the the opposite side and the current stone
	 * are captured to the store
	 * the method then checks for a winner, if no winner then the next turn is played by the next player
	 * Game.board is set as the clone at the end
	 */
	public void makeMove(Integer position)
	{
		@SuppressWarnings("unchecked")
		LinkedList<Integer> boardClone = (LinkedList<Integer>) Game.board.clone();
		Integer startPositionValue = boardClone.get(position);
		Integer currentPosition = position;

		boardClone.set(position, 0);
		processmove: while(startPositionValue > 0)
		{
			Integer targetPosition = currentPosition+1;

			if(targetPosition > boardClone.size()-1) {
				currentPosition = 0;
				targetPosition = 1;
			}

			Integer targetPositionValue = boardClone.get(targetPosition);
			if(targetPositionValue == 0 && startPositionValue == 1 && (targetPosition > 0 && targetPosition < 13)) {
				Integer oppositePocket = this.positionCaptureCheck(targetPosition);
				Integer oppositePocketValue = boardClone.get(oppositePocket);
				if(oppositePocketValue > 0) {
					if(playerTurn == 0 && targetPosition >= 7 && targetPosition < 13) {
						boardClone.set(13, boardClone.getLast()+oppositePocketValue+1);
						boardClone.set(oppositePocket, 0);
						boardClone.set(currentPosition, 0);
						this.setMessage("Player 2 Captures!");
						playerTurn = this.isTurn(7);
						break processmove;
					} else if(playerTurn == 1 && targetPosition >= 1 && targetPosition < 7) {
						boardClone.set(0, boardClone.getFirst()+oppositePocketValue+1);
						boardClone.set(oppositePocket, 0);
						boardClone.set(currentPosition, 0);
						this.setMessage("Player 1 Captures!");
						playerTurn = this.isTurn(5);
						break processmove;
					}
				}
			}

			if(playerTurn == 1 && targetPosition == 7) {
				boardClone.set(0, boardClone.getFirst()+1);
				startPositionValue--;
				if(startPositionValue != 0) {
					boardClone.set(7, boardClone.get(7)+1);
				} else {
					playerTurn = this.isTurn(0);
				}

			} else {
				boardClone.set(targetPosition, targetPositionValue+1);
			}

			if(startPositionValue == 1) {

				playerTurn = this.isTurn(targetPosition);
			}

			currentPosition++;
			startPositionValue--;
		}

		Integer p1PocketsNotEmpty = this.checkForEmptySide(boardClone, 1, 7);
		Integer p2PocketsNotEmpty = this.checkForEmptySide(boardClone, 7, 13);
		if(p1PocketsNotEmpty == 0) {
			for(int i = 7; i < 13; i++) {
				boardClone.set(13, boardClone.get(13) + boardClone.get(i));
				boardClone.set(i, 0);
			}
			this.isWinner(boardClone.getFirst(), boardClone.getLast());
		} else if(p2PocketsNotEmpty == 0) {
			for(int i = 1; i < 7; i++) {
				boardClone.set(0, boardClone.get(0) + boardClone.get(i));
				boardClone.set(i, 0);
			}
			this.isWinner(boardClone.getFirst(), boardClone.getLast());
		}

		Game.board = boardClone;
	}

	/**
	 * @param currentPosition
	 * @return number
	 * takes the current position and subtracts it from the number of pockets to find the opposite position
	 */
	private Integer positionCaptureCheck(Integer currentPosition) {

		return ((Game.GAME_POCKETS-1) - currentPosition);
	}


	/**
	 * @param endPosition
	 * @return current turn
	 * determines the next player to have a turn and sets the message to show why its the next turn if needed
	 */
	private int isTurn(Integer endPosition)
	{

		if(endPosition == 13 && playerTurn == 0) {
			this.setDisable("B");
			this.setMessage("Free Move");
			return 0;
		} else if(endPosition == 0 && playerTurn == 1) {
			this.setDisable("A");
			this.setMessage("Free Move");
			return 1;
		} else {
			if(playerTurn == 0) {
				this.setDisable("A");
				this.setMessage("Next Players Turn");
				return 1;
			} else {
				this.setDisable("B");
				this.setMessage("Next Players Turn");
				return 0;
			}
		}
	}

	/**
	 * @param game
	 * @param rangeStart
	 * @param rangeEnd
	 * @return integer
	 * checks if either player has all empty pockets to end game and calculate totals
	 */
	private Integer checkForEmptySide(LinkedList<Integer>game, int rangeStart, int rangeEnd)
	{
		Integer count = 0;
		for(int i = rangeStart; i < rangeEnd; i++) {
			if(game.get(i) != 0) {
				count++;
			}
		}

		return count;
	}

	/**
	 * @param p1
	 * @param p2
	 * takes each players total and compares for a win or draw and sets the message as well as disabling the board on a win/draw
	 * condition
	 */
	private void isWinner(Integer p1, Integer p2) 
	{
		if((p1 + p2) == 48) {
			if(p1 > p2) {
				this.setMessage("Player 1 Wins");
			} else if(p2 > p1){
				this.setMessage("Player 2 Wins");
			} else {
				this.setMessage("Draw");
			}
			gameWon = true;
		}
		gameWon = false;
	}

	/**
	 * @param value
	 * set the game message to players
	 */
	private void setMessage(String value)
	{
		gameMessage = value;
	}

	/**
	 * @param value
	 * set the player to disabled ability to play
	 */
	private void setDisable(String value)
	{
		disableSide = value;
	}

	/**
	 * @return
	 * return current message
	 */
	public String getMessage()
	{
		return gameMessage;
	}

	/**
	 * @return
	 * gets the current disabled user
	 */
	public String getDisable()
	{
		return disableSide;
	}

	/**
	 * @return
	 * returns if the game is won or in a draw
	 */
	public boolean isGameWon()
	{
		return gameWon;
	}
}
