package application;

import java.sql.Date;

public class ExplorerRow {
	private String resourceTitle;
	private String keeper;
	
	private int copyID;
	private int resourceID;
	private int loanDuration;
	private int orderNumber;
	
	private String borrowDate;
	private String lastRenewal;
	private String dueDate;
	
	public ExplorerRow(String resourceTitle, String keeper, int copyID, int resourceID, int loanDuration, String borrowDate, String lastRenewal, String dueDate) {
		this.resourceTitle = resourceTitle;
		this.keeper = keeper;
		this.copyID = copyID;
		this.resourceID = resourceID;
		this.loanDuration = loanDuration;
		this.borrowDate = borrowDate;
		this.lastRenewal = lastRenewal;
		this.dueDate = dueDate;
	}

	public ExplorerRow(String resourceTitle, String keeper, int copyID, int resourceID, String borrowDate, String dueDate) {
		this.resourceTitle = resourceTitle;
		this.keeper = keeper;
		this.copyID = copyID;
		this.resourceID = resourceID;
		this.borrowDate = borrowDate;
		this.dueDate = dueDate;
	}

	public ExplorerRow(String keeper, int resourceID) {
		this.keeper = keeper;
		this.resourceID = resourceID;
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

	public String getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(String borrowDate) {
		this.borrowDate = borrowDate;
	}

	public String getLastRenewal() {
		return lastRenewal;
	}

	public void setLastRenewal(String lastRenewal) {
		this.lastRenewal = lastRenewal;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
}
