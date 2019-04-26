package beans;

public class Expense {
	
	private int appricationId;
	
	private String appricationDate;
	
	private String appricant;
	
	private String expenseTitle;
	
	private int price;
	
	private String payee;
	
	private String status;
	
	private String updatePerson;
	
	private String rightComment;
	
	//アクセッサ

	public int getAppricationId() {
		return appricationId;
	}

	public void setAppricationId(int appricationId) {
		this.appricationId = appricationId;
	}

	public String getAppricationDate() {
		return appricationDate;
	}

	public void setAppricationDate(String appricationDate) {
		this.appricationDate = appricationDate;
	}

	public String getAppricant() {
		return appricant;
	}

	public void setAppricant(String appricant) {
		this.appricant = appricant;
	}

	public String getExpenseTitle() {
		return expenseTitle;
	}

	public void setExpenseTitle(String expenseTitle) {
		this.expenseTitle = expenseTitle;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public String getRightComment() {
		return rightComment;
	}

	public void setRightComment(String rightComment) {
		this.rightComment = rightComment;
	}
}
