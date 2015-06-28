package za101g2.pet.model;

import java.util.*;

public interface PetDAO_interface {
	public void insert(PetVO petVO);
	public void update(PetVO petVO);
	public void delete(Integer id);
	public PetVO findByPrimaryKey(Integer id);
	public List<PetVO> findIdByMemId(Integer memId);
    public List<PetVO> getAll();
}
