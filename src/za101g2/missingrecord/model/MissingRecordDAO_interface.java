package za101g2.missingrecord.model;

import java.util.*;

public interface MissingRecordDAO_interface {
	public void insert(MissingRecordVO missingRecordVO);
	public void update(MissingRecordVO missingRecordVO);
	public void delete(Integer id);
	public MissingRecordVO findByPrimaryKey(Integer id);
	public MissingRecordVO findByPetId(Integer petId);
	public List<MissingRecordVO> findByCity(String city);
	public List<MissingRecordVO> findByCityBounty(String city);
	public List<MissingRecordVO> findByCityNoBounty(String city);
	public List<MissingRecordVO> getAllBounty();
	public List<MissingRecordVO> getAllNoBounty();
    public List<MissingRecordVO> getAll();
}
