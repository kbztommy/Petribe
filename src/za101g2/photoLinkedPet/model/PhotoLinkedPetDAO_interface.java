package za101g2.photoLinkedPet.model;
import java.util.*;

public interface PhotoLinkedPetDAO_interface {
          public int insert(PhotoLinkedPetVO VO);
          public int delete(Integer photoId,Integer petId);
          public int deleteByPhoto(Integer photoId);
          public List<PhotoLinkedPetVO> listByPhotoId(Integer photoId);
          public List<PhotoLinkedPetVO> listByPetId(Integer petId);
          public List<PhotoLinkedPetVO> getAll();
}
