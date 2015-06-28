package za101g2.journalLinkedPet.model;
import java.util.*;

public interface JournalLinkedPetDAO_interface {
          public int insert(JournalLinkedPetVO VO);
          public int delete(Integer journalId,Integer petId);
          public List<JournalLinkedPetVO> findByPrimaryKey(Integer petId);
          public List<JournalLinkedPetVO> getAll();
          public int deleteByJournal(Integer journalId);
          public List<JournalLinkedPetVO> findByJournalId(Integer journalId);
}
