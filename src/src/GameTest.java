/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.game;

import java.util.Random;

import junit.framework.TestCase;
import org.junit.Test;
import skt.game.*;

/**
 *
 * @author skt
 */
public class GameTest extends TestCase {

	Random RANDOM = new Random();

	Dice d1;
	Dice d2;
	Dice d3;

	Player player;
	Game game;

	@Override
	protected void setUp() {
		d1 = new Dice();
		d2 = new Dice();
		d3 = new Dice();
		player = new Player("Fred", 21, 100);
		int limit = 0;
		player.setLimit(limit);
		game = new Game(d1, d2, d3);
	}

	@Test
	public void testBug1() {
		int startBalance = player.getBalance();
		int bet = 5;
		int winnings = game.playRound(player, DiceValue.CLUB, bet);
		int currentBalance = 0;
		if (winnings > 0) {
			currentBalance = startBalance + winnings;
		} else {
			currentBalance = startBalance - bet;
		}
		assertEquals(currentBalance, player.getBalance());
	}

	@Test
	public void testBug2() {
		int bet = 25;
		while (player.balanceExceedsLimitBy(bet) && player.getBalance() <= 200) {
			DiceValue pick = DiceValue.getRandom();
			game.playRound(player, pick, bet);
		}
		int balance = player.getBalance();
		System.out.println(balance);
		assertEquals(true, balance == 0 || balance > 200);

	}

	@Test
	public void testBug3() {
		int bet = 5;
		int winCount = 0;
		int loseCount = 0;
		while (player.balanceExceedsLimitBy(bet) && player.getBalance() <= 200) {
			DiceValue pick = DiceValue.getRandom();
			int winnings = game.playRound(player, pick, bet);

			if (winnings > 0) {
				System.out.printf("%s won %d, balance now %d\n\n", player.getName(), winnings, player.getBalance());
				winCount++;
			} else {
				System.out.printf("%s lost, balance now %d\n\n", player.getName(), player.getBalance());
				loseCount++;
			}
		}
		float winRatio = (float) winCount / (winCount + loseCount);
		assertEquals(true, winRatio - 0.42 < 0.5);

	}

	@Test
	public void testBug4_under_age() {
		try {
			player = new Player("Fred", 12, 100);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().equals("Under age 18 you cannot participated."));
		}

	}

	@Test
	public void testBug5_allDiceValuesRolledPossibleWithRandomFunction() {
		// Returns a pseudorandom, uniformly distributed int value between 0
		// (inclusive) and the specified value (exclusive),
		// drawn from this random number generator's sequence.
		int rolledValue = RANDOM.nextInt(DiceValue.SPADE.ordinal() + 1);
		System.out.println(rolledValue);
		assertTrue(rolledValue == 0 || rolledValue == 1 || rolledValue == 2 || rolledValue == 3 || rolledValue == 4
				|| rolledValue == 5);
	}

}
