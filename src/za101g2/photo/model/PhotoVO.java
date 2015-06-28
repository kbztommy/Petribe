package za101g2.photo.model;
import java.io.InputStream;
import java.sql.Date;

public class PhotoVO implements java.io.Serializable{
	private Integer id;
	private String name;
	private String format;
	private byte[] photoFile;
	private Date updateDate;
	private Integer memId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}

}
