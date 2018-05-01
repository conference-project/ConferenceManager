package pl.edu.agh.ki.mwo.model;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="articles")
public class Article implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column
	private String title;
	
	@Column
	private String topic;
	
	@Column
	private String attachment;
	
	@ManyToOne //(cascade = {CascadeType.ALL})
	@JoinColumn(name="participant_id")
	private Participant participant;
	
	@Column
	private boolean isAprovedByReviewer; // to do: zmien na null-able bool

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}
	
	public boolean getIsAprovedByReviewer() {
		return isAprovedByReviewer;
	}
/*	public String getIsAprovedByReviewerTxt() { // do wyswietlania jako tekst a nie checkbox
		return isAprovedByReviewer ? "tak":"nie";
	}*/

	public void setAprovedByReviewer(boolean isAprovedByReviewer) {
		this.isAprovedByReviewer = isAprovedByReviewer;
	}

	public String toString() {
		return "Article: "+getTitle() ;
	}
}