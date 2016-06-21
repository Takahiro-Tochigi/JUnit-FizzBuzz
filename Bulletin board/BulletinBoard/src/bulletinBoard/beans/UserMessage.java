package bulletinBoard.beans;

import java.io.Serializable;
import java.util.Date;

public class UserMessage implements Serializable  {

	private  static final long serialVersionUID = 1L;
	private int id;
	private String name ;
	private String subject;
	private String body;
	private String category;
	private int name_id;
	private Date insert_date;
	private Date update_date;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getName_id() {
		return name_id;
	}
	public void setName_id(int name_id) {
		this.name_id = name_id;
	}
	public Date getInsert_date() {
		return insert_date;
	}
	public void setInsert_date(Date insert_date) {
		this.insert_date = insert_date;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

}
