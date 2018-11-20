
public class Copy {
	
	private Resource resource;
	private User borrower;
	private final int COPY_ID;
	
	
	public Copy(Resource resource, int copyID, User borrower) {
		this.resource = resource;
		this.borrower = borrower;
		this.COPY_ID = copyID;
	}

	public void setBorrower(User borrower) {
		this.borrower = borrower;
	}
	
	public void setDueDate() {
		
	}

}
