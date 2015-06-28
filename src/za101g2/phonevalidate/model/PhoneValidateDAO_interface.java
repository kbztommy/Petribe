package za101g2.phonevalidate.model;

public interface PhoneValidateDAO_interface {
	public PhoneValidateVO insert(PhoneValidateVO phoneValidateVO);
	public PhoneValidateVO update(Integer memId);
	public void delete(Integer memId);
	public PhoneValidateVO findByPrimaryKey(Integer memId);
	public Long findByPhone(String phone);
	public void validate(PhoneValidateVO phoneValidateVO);
}
