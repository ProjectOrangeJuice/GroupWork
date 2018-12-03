package model;

public class Payment {
	private int transactionId;
	private String username;
	private float amount;
	private String stamp;

	public Payment(int transactionId, String username, float amount, String stamp) {
		this.transactionId = transactionId;
		this.username = username;
		this.amount = amount;
		this.stamp = stamp;
	}

	public float getAmount() {
		return amount;
	}


	public String getStamp() {
		return stamp;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public String getusername() {
		return username;
	}
}
