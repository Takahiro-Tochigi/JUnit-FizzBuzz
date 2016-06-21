package bulletinBoard.beans;

import java.io.Serializable;

public class Branch implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String branchName ;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}
