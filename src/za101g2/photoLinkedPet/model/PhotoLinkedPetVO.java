package za101g2.photoLinkedPet.model;

public class PhotoLinkedPetVO implements java.io.Serializable{
	private Integer photoId;
	private Integer petId;
	
	public Integer getPhotoId() {
		return photoId;
	}
	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}
	public Integer getPetId() {
		return petId;
	}
	public void setPetId(Integer petId) {
		this.petId = petId;
	}
}
