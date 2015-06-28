package za101g2.phonevalidate.model;

public class PhoneValidateService {

	private PhoneValidateDAO_interface dao;
	
	public PhoneValidateService() {
		dao = new PhoneValidateDAO();
	}
	
	public PhoneValidateVO addPhoneValidate(Integer memId, String phone,String validateCode) {
		PhoneValidateVO phoneValidateVO = new PhoneValidateVO();
		phoneValidateVO.setMemId(memId);
		phoneValidateVO.setPhone(phone);
		phoneValidateVO.setValidateCode(validateCode);
		phoneValidateVO = dao.insert(phoneValidateVO);
		return phoneValidateVO;
	}
	
	public PhoneValidateVO errorCountPlus(Integer memId){
		return dao.update(memId);
	}
	
	public void deletePhoneValidate(Integer memId) {
		dao.delete(memId);
	}
	
	public PhoneValidateVO findOne(Integer memId){
		return dao.findByPrimaryKey(memId);
	}
	
	public Long findOneByPhone(String phone){
		return dao.findByPhone(phone);
	}
	
	public void validate(PhoneValidateVO phoneValidateVO){
		dao.validate(phoneValidateVO);
	}	
}
