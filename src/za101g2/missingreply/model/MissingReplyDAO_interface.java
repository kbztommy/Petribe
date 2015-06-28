package za101g2.missingreply.model;

import java.util.*;

public interface MissingReplyDAO_interface {
	public void insert(MissingReplyVO missingreplyVO);
	public void update(MissingReplyVO missingreplyVO);
	public void delete(Integer id);
	public MissingReplyVO findByPrimaryKey(Integer id);
	public MissingReplyVO findByRecordIdMemId(Integer recordId, Integer memId);
	public Set<MissingReplyVO> findByRecordId(Integer recordId);
	public Set<MissingReplyVO> findByMemId(Integer memId);
    public List<MissingReplyVO> getAll();
}
