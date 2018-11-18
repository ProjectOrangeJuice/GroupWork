
public class Copy {
	
	Resource resource;
	User borrower;
	
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
