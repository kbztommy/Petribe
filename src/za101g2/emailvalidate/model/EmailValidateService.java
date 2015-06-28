package za101g2.emailvalidate.model;

public class EmailValidateService {

private EmailValidateDAO_interface dao;
	
	public EmailValidateService(){
		dao = new EmailValidateDAO();
	}
	
	public EmailValidateVO addEmailValidate(Integer memId, String email,String validateCode){
		
		EmailValidateVO emailValidateVO = new EmailValidateVO();
		emailValidateVO.setMemId(memId);
		emailValidateVO.setEmail(email);
		emailValidateVO.setValidateCode(validateCode);
		emailValidateVO = dao.insert(emailValidateVO);
		
		return emailValidateVO;
	}
	
	public void deleteEmailValidate(String validateCode) {
		dao.delete(validateCode);
	}
	
	public void validate(String validateCode){
		dao.validate(validateCode);
	}
	
	public EmailValidateVO findByMemId(Integer memId){
		return dao.findByMemId(memId);
	}
	
}
