
public class Copy {
	
	private Resource resource;
	private User borrower;
	private int copyID;
	
	
	public Copy(Resource resource) {
		this.resource = resource;
		this.borrower = null;
	}

	public void setBorrower(User borrower) {
		this.borrower = borrower;
	}
	
	public void setDueDate() {
		
	}

}
