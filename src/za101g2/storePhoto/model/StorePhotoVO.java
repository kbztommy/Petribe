package za101g2.storePhoto.model;
import java.sql.Date;
import java.sql.Timestamp;

import za101g2.onService.model.OnServiceVO;
import za101g2.store.model.StoreVO;

public class StorePhotoVO implements java.io.Serializable{
	private Integer id;
	private String format;
	private byte[] photoFile;
	private Timestamp updateDate;
	private StoreVO storeVO;
	private OnServiceVO onServiceVO;
	public StoreVO getStoreVO() {
		return storeVO;
	}
	public OnServiceVO getOnServiceVO() {
		return onServiceVO;
	}
	public void setOnServiceVO(OnServiceVO onServiceVO) {
		this.onServiceVO = onServiceVO;
	}
	public void setStoreVO(StoreVO storeVO) {
		this.storeVO = storeVO;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public byte[] getPhotoFile() {
		return photoFile;
	}
	public void setPhotoFile(byte[] photoFile) {
		this.photoFile = photoFile;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	
}
