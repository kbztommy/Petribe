package za101g2.journal.model;
import java.sql.Date;

public class JournalVO implements java.io.Serializable{
	private Integer id;
	private String title;
	private String article;
	private Date releaseDate;
	private Date ediltDate;
	private Integer memId;
	private String status;
	private String isPublic;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Date getEdiltDate() {
		return ediltDate;
	}
	public void setEdiltDate(Date ediltDate) {
		this.ediltDate = ediltDate;
	}
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	public String getFirstImg(){		
		int i = article.indexOf("<img");
		int j = article.indexOf(">", i);
		if(i==-1)
			return "";
		else
			return article.substring(i, j+1);
	}
	
	public String getShortArticle(){
		article=article.replaceAll("<[^>]*>", "");
		if(article.length()<101)
			return article;
		else
			return article.substring(0,100)+"......";
	}
}
