
public class Player {
	private String name;
	private int balance;
	private int limit;
	
	public Player(String name, int balance) {
		if (name == null || name .isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty");
		if (balance < 0) throw new IllegalArgumentException("Balance cannot be negative");
		this.name = name;
		this.balance = balance;
		this.limit = 0;
	}
		
	public String getName() { return name; }
	public int getBalance() { return balance; }
	public int getLimit() { return limit; }
	
	public void setLimit(int limit) {
		if (limit < 0) throw new IllegalArgumentException("Limit cannot be negative.");
		if (limit > balance)  throw new IllegalArgumentException("Limit cannot be greater than balance.");
		this.limit = limit;
	}

	/* old code - public boolean balanceExceedsLimit() {
		return (balance > limit);
	}
	Player cannot reach betting limit:
	Limit set to 0, but game ends with player still with 5 (dollars) remaining
	
	solution - return (balance >= limit);
	*/
	public boolean balanceExceedsLimit() {
		return (balance >= limit);
	}
	
	public boolean balanceExceedsLimitBy(int amount) {
		return (balance - amount > limit);
	}
	
	public void takeBet(int bet) {
		if (bet < 0) throw new IllegalArgumentException("Bet cannot be negative.");
		if (!balanceExceedsLimitBy(bet)) throw new IllegalArgumentException("Placing bet would go below limit.");
		balance = balance - bet;
	}
	
	/*
	old code - public void receiveWinnings(int winnings) {
		if (winnings < 0) throw new IllegalArgumentException("Winnings cannot be negative.");
		balance = balance + winnings;		
	}
	
	bug 1- Game does not pay out at correct level.
	When player wins on 1 match, balance does not increase.
	
	solution-
	public void receiveWinnings(int winnings, int bet) {
		if (winnings < 0) throw new IllegalArgumentException("Winnings cannot be negative.");
		balance = balance + winnings + bet;		
	}
	
	*/
	public void receiveWinnings(int winnings, int bet) {
		if (winnings < 0) throw new IllegalArgumentException("Winnings cannot be negative.");
		balance = balance + winnings + bet;		
	}
	
	public String toString() {
		return String.format("Player: %s, Balance: %d, Limit: %d", name, balance, limit);
	}
}
