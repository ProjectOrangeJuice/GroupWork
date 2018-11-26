import java.util.ArrayList;

public class Transactions {

	private int username;
	private ArrayList<Payment> payments;
	private ArrayList<Fine> fines;
	
	
	public Transactions(int username, ArrayList<Payment> payments, ArrayList<Fine> fines) {
		this.username = username;
		this.payments = payments;
		this.fines = fines;
	}
	
	public int getUsername() {
		return username;
	}
	
	public void setUsername(int username) {
		this.username = username;
	}
	
	public ArrayList<Payment> getPayments() {
		return payments;
	}
	
	public void addPayment(Payment payment) {
		payments.add(payment);
	}
	
	public ArrayList<Fine> getFines() {
		return fines;
	}
	
	public void addFines(Fine fine) {
		fines.add(fine);
	}
	
	
	
	
	
}
