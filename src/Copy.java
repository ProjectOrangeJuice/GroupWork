import java.sql.Date;

public class Copy {
	
	private Resource resource;
	private int borrower;
	private final int COPY_ID;
	
	/**
	 * Class Constructor
	 * @param resource
	 * @param copyID
	 * @param borrower
	 */
	public Copy(Resource resource, int copyID, int borrower) {
		this.resource = resource;
		this.borrower = borrower;
		this.COPY_ID = copyID;
	}
    
	/**
	 * 
	 * @param borrower
	 */
	public void setBorrower(int borrower) {
		this.borrower = borrower;
	}
	
	/**
	 * 
	 */
	public void setDueDate() {
		
	}
	
	/**
	 * Method that gets the duedate variable
	 * @return duedate
	 */
	public Date getDueDate(){
		return duedate;
	}
	
	/**
	 * Method that gets the duration variable
	 * @return duration
	 */
	public Date getDuration(){
		return duration;
	}
	
	/**
	 * Method that gets the resource variable
	 * @return resource
	 */
	public Resource getResource(){
		return resource;
	}
	
	/**
	 * Method that gets the COPY_ID final variable
	 * @return COPY_ID
	 */
	public int getCOPY_ID(){
		return COPY_ID;
	}
	
	
	
	


}
