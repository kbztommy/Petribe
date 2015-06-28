package za101g2.onService.model;

import java.util.*;

public interface OnServiceDAO_interface {
	void insert(OnServiceVO onServiceVO);

	void update(OnServiceVO onServiceVO);

	void delete(Integer id);

	OnServiceVO findByPrimaryKey(Integer id);

	List<OnServiceVO> getAll();
}
