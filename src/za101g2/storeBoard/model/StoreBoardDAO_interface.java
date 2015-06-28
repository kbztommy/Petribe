package za101g2.storeBoard.model;
import java.util.*;
public interface StoreBoardDAO_interface {
	StoreBoardVO insert(StoreBoardVO storeBoardVO);
	void update(StoreBoardVO storeBoardVO);
	void delete(Integer id);
	StoreBoardVO findByPrimaryKey(Integer id);
	List<StoreBoardVO> getAll();
	List<StoreBoardVO> getUndelete(Integer storeId);
	List<StoreBoardVO> getMoreUndelete(Integer storeId,Integer from,Integer count);
}
