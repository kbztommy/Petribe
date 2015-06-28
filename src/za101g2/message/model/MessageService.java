package za101g2.message.model;

import java.util.List;

public class MessageService {
	private MessageDAO_interface dao;
	
	public MessageService(){
		dao = new MessageDAO();
	}
	
	public void insertMessage(Integer memId, Integer targetMemId, String msg){
		MessageVO messageVO = new MessageVO();
		messageVO.setMemId(memId);
		messageVO.setTargetMemId(targetMemId);
		messageVO.setMsg(msg);
		dao.insert(messageVO);
	}
	
	public void messageBeRead(Integer memId, Integer targetMemId) {
		
		MessageVO messageVO = new MessageVO();
		messageVO.setMemId(memId);
		messageVO.setTargetMemId(targetMemId);
		dao.update(messageVO);
	}
	
	public List<MessageVO> messageHistory(Integer memId, Integer targetMemId){
		
		return dao.messageHistory(memId, targetMemId);
	}
	
	public Long messageNew(Integer memId, Integer targetMemId){
		
		return dao.messageNew(memId, targetMemId);
	}
	
	public MessageVO sent(Integer memId){
		return dao.sent(memId);
	}
}
