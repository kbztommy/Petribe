package za101g2.message.model;

import java.util.List;

public interface MessageDAO_interface {
	public void insert(MessageVO messageVO);
	public void update(MessageVO messageVO);
	public List<MessageVO> messageHistory(Integer memId, Integer targetMemId);
	public Long messageNew(Integer memId, Integer targetMemId);
	public MessageVO sent(Integer memId);
}
