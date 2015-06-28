package za101g2.storeCalendar.model;
import java.util.*;
import java.sql.Connection;
import java.sql.Date;
public interface StoreCalendarDAO_interface {
	void insert(StoreCalendarVO storeCalendarVO);
	void update(StoreCalendarVO storeCalendarVO);
	void delete(Integer id,Date serviceDate);
	StoreCalendarVO findByPrimaryKey(Integer id,Date serviceDate);
	List<StoreCalendarVO> getAll();
	void insertMonth(List<StoreCalendarVO> storeCalendarVOList);
	List<StoreCalendarVO> getMonthCalendar(Integer id,int year,int month);
	void update(Connection con,StoreCalendarVO storeCalendarVO);
	void updateByHibernate(StoreCalendarVO storeCalendarVO);
}
