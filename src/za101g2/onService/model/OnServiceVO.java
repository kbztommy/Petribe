package za101g2.onService.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

import za101g2.orderBoard.model.OrderBoardVO;
import za101g2.storePhoto.model.StorePhotoVO;

public class OnServiceVO implements java.io.Serializable {
	
	private Integer id;
	private String comments;
	private Timestamp releaseDate;
	private OrderBoardVO orderBoardVO;
	private Set<StorePhotoVO> storePhotos = new HashSet<StorePhotoVO>();

	public Set<StorePhotoVO> getStorePhotos() {
		return storePhotos;
	}



	public void setStorePhotos(Set<StorePhotoVO> storePhotos) {
		this.storePhotos = storePhotos;
	}



	public Integer getId() {
		return id;
	}

	

	public OrderBoardVO getOrderBoardVO() {
		return orderBoardVO;
	}



	public void setOrderBoardVO(OrderBoardVO orderBoardVO) {
		this.orderBoardVO = orderBoardVO;
	}



	public void setId(Integer id) {
		this.id = id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Timestamp releaseDate) {
		this.releaseDate = releaseDate;
	}

	

}
