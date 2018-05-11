package pl.edu.agh.ki.mwo.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.edu.agh.ki.mwo.model.Article;
import pl.edu.agh.ki.mwo.model.Participant;
import pl.edu.agh.ki.mwo.model.Reviewer;

public class DatabaseConnector {

	protected static DatabaseConnector instance = null;

	public static DatabaseConnector getInstance() {
		if (instance == null) {
			instance = new DatabaseConnector();
		}
		return instance;
	}

	Session session;

	protected DatabaseConnector() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void teardown() {
		session.close();
		HibernateUtil.shutdown();
		instance = null;
	}

	//methods for Participant
	public Iterable<Participant> getParticipants() {

		String hql = "FROM Participant";
		Query query = session.createQuery(hql);
		List participants = query.list();

		return participants;
	}

	public Participant getParticipant(String participantId) {
		
		String hql = "FROM Participant P WHERE P.id=" + participantId;
		Query query = session.createQuery(hql);
		List<Participant> results = query.list();
		
		return results.get(0);
	}
	
	public void addParticipant(Participant participant) {
		
		Transaction transaction = session.beginTransaction();
		session.save(participant);
		transaction.commit();
	}
	
	public void updateParticipant(String participantId,
								  String participantName,
								  String participantSurname,
								  String participantUniversity,
								  String participantEmail) {

		Participant participant = getParticipant(participantId);
		participant.setName(participantName);
		participant.setSurname(participantSurname);
		participant.setUniversity(participantUniversity);
		participant.setEmail(participantEmail);
		
		Transaction transaction = session.beginTransaction();
		session.update(participant);
		transaction.commit();
	}
	
	public void deleteParticipant(String participantId) {
		
		String hql = "FROM Participant P WHERE P.id=" + participantId;
		Query query = session.createQuery(hql);
		List<Participant> results = query.list();
		
		Transaction transaction = session.beginTransaction();
		session.delete(results.get(0));
		transaction.commit();
	}
	
	//methods for Article
	public Iterable<Article> getArticles() {
		
		String hql = "FROM Article";
		Query query = session.createQuery(hql);
		List articles = query.list();

		return articles;
	}
	
	public Article getArticle(String articleId) {
		
		String hql = "FROM Article A WHERE A.id=" + articleId;
		Query query = session.createQuery(hql);
		List<Article> results = query.list();
		
		return results.get(0);
	}
		
	public void addArticle(Article article, String participantId) {

		String hql = "FROM Participant P WHERE P.id=" + participantId;
		Query query = session.createQuery(hql);
		List<Participant> results = query.list();
		Participant author = results.get(0);
		author.addArticle(article);
		
		Transaction transaction = session.beginTransaction();
		session.save(author);
		transaction.commit();
	}
	
	public void updateArticle(String articleId,
			  				  String articleTitle,
			  				  String articleTopic,
			  				  String participantId) {// czy dodajemy mozliwosc zmiany oceny???

		Article article = getArticle(articleId);
		article.setTitle(articleTitle);
		article.setTopic(articleTopic);
		Participant previousAuthor = article.getParticipant();
		previousAuthor.removeArticle(article);
		Participant author = getParticipant(participantId);
		article.setParticipant(author);
		author.addArticle(article);

		Transaction transaction = session.beginTransaction();
		session.update(article);
		session.update(author);
		session.update(previousAuthor);
		transaction.commit();
		
	}
	
	public void rateArticle(String articleId, int rate) {
		
		Article article = getArticle(articleId);
		article.setRate(rate);
		
		Transaction transaction = session.beginTransaction();
		session.update(article);
		transaction.commit();
	}
	
	public void commentForArticle(String articleId, String comment) {
		
		Article article = getArticle(articleId);
		article.setComment(comment);
		
		Transaction transaction = session.beginTransaction();
		session.update(article);
		transaction.commit();		
	}
	
	public void deleteArticle(String articleId) {

		String hql = "FROM Article A WHERE A.id=" + articleId;
		Query query = session.createQuery(hql);
		List<Article> results = query.list();
		Article article = results.get(0);
		Participant author = article.getParticipant();
		author.removeArticle(article);
		
		Transaction transaction = session.beginTransaction();
		session.delete(article);
		session.update(author);
		transaction.commit();
	}
	
	//methods for Reviewer
	public Iterable<Reviewer> getReviewers() {

		String hql = "FROM Reviewer";
		Query query = session.createQuery(hql);
		List reviewers = query.list();

		return reviewers;
	}

	public Reviewer getReviewer(String reviewerId) {
		
		String hql = "FROM Reviewer P WHERE P.id=" + reviewerId;
		Query query = session.createQuery(hql);
		List<Reviewer> results = query.list();
		
		return results.get(0);
	}
	
	public void addReviewer(Reviewer reviewer) {
		
		Transaction transaction = session.beginTransaction();
		session.save(reviewer);
		transaction.commit();
	}
	
	public void updateReviewer(String reviewerId,
								  String reviewerName,
								  String reviewerSurname,
								  String reviewerTopic,
								  String reviewerEmail) {

		Reviewer reviewer = getReviewer(reviewerId);
		reviewer.setName(reviewerName);
		reviewer.setSurname(reviewerSurname);
		reviewer.setTopic(reviewerTopic);
		reviewer.setEmail(reviewerEmail);
		
		Transaction transaction = session.beginTransaction();
		session.update(reviewer);
		transaction.commit();
	}
	
	public void deleteReviewer(String reviewerId) {
		
		String hql = "FROM Reviewer P WHERE P.id=" + reviewerId;
		Query query = session.createQuery(hql);
		List<Reviewer> results = query.list();
		
		Transaction transaction = session.beginTransaction();
		session.delete(results.get(0));
		transaction.commit();
	}
	

}
