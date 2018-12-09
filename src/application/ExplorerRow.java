package application;

import java.sql.Date;

public class ExplorerRow {
	private String username;
	private String resourceTitle;
	private String keeper;
	
	private int copyID;
	private int resourceID;
	private int loanDuration;
	private int orderNumber;
	
	private Date borrowDate;
	private Date lastRenewal;
	private Date dueDate;
	
	public ExplorerRow(String resourceTitle, String keeper, int copyID, int resourceID, int loanDuration, Date borrowDate, Date lastRenewal, Date dueDate) {
		this.resourceTitle = resourceTitle;
		this.keeper = keeper;
		this.copyID = copyID;
		this.resourceID = resourceID;
		this.loanDuration = loanDuration;
		this.borrowDate = borrowDate;
		this.lastRenewal = lastRenewal;
		this.dueDate = dueDate;
	}

	public ExplorerRow(String resourceTitle, String keeper, int copyID, int resourceID, Date borrowDate, Date dueDate) {
		super();
		this.resourceTitle = resourceTitle;
		this.keeper = keeper;
		this.copyID = copyID;
		this.resourceID = resourceID;
		this.borrowDate = borrowDate;
		this.dueDate = dueDate;
	}

	public ExplorerRow(String username, int resourceID, int orderNumber) {
		super();
		this.username = username;
		this.resourceID = resourceID;
		this.orderNumber = orderNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getResourceTitle() {
		return resourceTitle;
	}

	public void setResourceTitle(String resourceTitle) {
		this.resourceTitle = resourceTitle;
	}

	public String getKeeper() {
		return keeper;
	}

	public void setKeeper(String keeper) {
		this.keeper = keeper;
	}

	public int getCopyID() {
		return copyID;
	}

	public void setCopyID(int copyID) {
		this.copyID = copyID;
	}

	public int getResourceID() {
		return resourceID;
	}

	public void setResourceID(int resourceID) {
		this.resourceID = resourceID;
	}

	public int getLoanDuration() {
		return loanDuration;
	}

	public void setLoanDuration(int loanDuration) {
		this.loanDuration = loanDuration;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Date getLastRenewal() {
		return lastRenewal;
	}

	public void setLastRenewal(Date lastRenewal) {
		this.lastRenewal = lastRenewal;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
}
