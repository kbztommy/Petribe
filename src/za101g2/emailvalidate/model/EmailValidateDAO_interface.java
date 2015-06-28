package za101g2.emailvalidate.model;

public interface EmailValidateDAO_interface {
	public EmailValidateVO insert(EmailValidateVO emailValidateVO);
	public void delete(String validateCode);
    public void validate(String validateCode);
    public EmailValidateVO findByMemId(Integer memId);
}
