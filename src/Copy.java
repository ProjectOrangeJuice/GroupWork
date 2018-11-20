
public class Copy {
	
	private Resource resource;
	private int borrower;
	private final int COPY_ID;
	
	
	public Copy(Resource resource, int copyID, int borrower) {
		this.resource = resource;
		this.borrower = borrower;
		this.COPY_ID = copyID;
	}

	public void setBorrower(int borrower) {
		this.borrower = borrower;
	}
	
	public void setDueDate() {
		
	}
	
	
	
	
	

}
