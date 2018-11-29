
public class Payment {
	private int transactionId;
	private int username;
	private float amount;
	private String stamp;

	public Payment(int transactionId, int username, float amount, String stamp) {
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

	public int getusername() {
		return username;
	}
}
