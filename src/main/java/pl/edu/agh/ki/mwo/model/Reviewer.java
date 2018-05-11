package pl.edu.agh.ki.mwo.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "reviewers")
public class Reviewer implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private String name;
	
	@Column
	private String surname;
	
	@Column
	private String topic;
	
	@Column
	private String email;
	
	@JoinColumn(name="reviewer_id")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reviewer")
	private Set<Article> articles;

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

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

    public String toString() {
		//return getName() + " " + getSurname() + ", topic: "+getTopic();
		return getName() + " " + getSurname();

	}
}
