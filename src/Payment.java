
public class Payment {
	private int transactionId;
	private int userId;
	private float amount;
	private String stamp;
	
	public Payment(int transactionId, int userId, float amount, String stamp) {
		this.transactionId = transactionId;
		this.userId = userId;
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
	
	public int getUserId() {
		return userId;
	}
}
