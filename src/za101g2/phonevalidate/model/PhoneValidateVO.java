package za101g2.phonevalidate.model;

public class PhoneValidateVO implements java.io.Serializable {

	private static final long serialVersionUID = 4692983905052440591L;
	
	private Integer memId;
	private String phone;
	private String validateCode;
	private Integer errorCount;
	
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
}
