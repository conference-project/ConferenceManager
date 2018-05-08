package pl.edu.agh.ki.mwo.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "participants")
public class Participant implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private String name;
	
	@Column
	private String surname;
	
	@Column
	private String university;
	
	@Column
	private String email;
	
	@Column
	private boolean doIHaveArticle = false;
	
	
	public boolean isDoIHaveArticle() {
		return doIHaveArticle;
	}


	// @OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="participant_id")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "participant")
	private Set<Article> articles;

	public Participant() {
		articles = new HashSet<Article>();
	}

	public void addArticle(Article newArticle) {
		articles.add(newArticle);
		newArticle.setParticipant(this);
		controlAuthorship();
	}
	
	public void removeArticle(Article article) {
		articles.remove(article);
		article.setParticipant(null);
		controlAuthorship();
	}
	
	public void controlAuthorship() {
		if (articles.size() == 0) {
			doIHaveArticle = false;
		}
		else {
			doIHaveArticle = true;
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDoIHaveArticle(boolean doIHaveArticle) {
		this.doIHaveArticle = doIHaveArticle;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
		controlAuthorship();
	}

	public String toString() {
		String participantType = isDoIHaveArticle()? "autor: " : "uczestnik: ";
		return participantType + getName() + " " + getSurname() + ", "+getUniversity();
	}
}
