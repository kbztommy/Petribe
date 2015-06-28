package za101g2.emailvalidate.model;

public class EmailValidateVO implements java.io.Serializable {

	private static final long serialVersionUID = -1964791524640554798L;
	private Integer memId;
	private String email;
	private String validateCode;
	
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
}
