package pl.edu.agh.ki.mwo.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.edu.agh.ki.mwo.model.Participant;
import pl.edu.agh.ki.mwo.model.Article;

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
	
/*	public void addSchool(Participant school) {
		Transaction transaction = session.beginTransaction();
		session.save(school);
		transaction.commit();
	}

	public void deleteSchool(String schoolId) {
		String hql = "FROM School S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		List<Participant> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (Participant s : results) {
			session.delete(s);
		}
		transaction.commit();
	}*/



/*	public void editSchool(String schoolId, String name, String surname) {
		String hql = "FROM School S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		List<Participant> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (Participant s : results) {
			s.setName(name);
			s.setSurname(surname);
			session.update(s);
		}
		transaction.commit();
	}

*/

/*	public void addSchoolClass(Article schoolClass, String schoolId) {
		String hql = "FROM School S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		List<Participant> results = query.list();
		Transaction transaction = session.beginTransaction();
		if (results.size() == 0) {
			session.save(schoolClass);
		} else {
			Participant school = results.get(0);
			school.addArticle(schoolClass);
			session.save(school);
		}
		transaction.commit();
	}

	public void deleteSchoolClass(String schoolClassId) {
		String hql = "FROM SchoolClass S WHERE S.id=" + schoolClassId;
		Query query = session.createQuery(hql);
		List<Article> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (Article s : results) {
			session.delete(s);
		}
		transaction.commit();
	}
	
	public void editSchoolClass(String schoolClassId, String schoolClassProfile, String schoolClassStartYear,
			String schoolClassCurrentYear, String schoolID) {
		String hql = "FROM SchoolClass S WHERE S.id=" + schoolClassId;
		Query query = session.createQuery(hql);
		List<Article> results = query.list();
		String hqlSchool = "FROM School S WHERE S.id=" + schoolID;
		Query querySchool = session.createQuery(hqlSchool);
		List<Participant> schoolResults = querySchool.list();
		Transaction transaction = session.beginTransaction();
		for (Article s : results) {
			s.setProfile(schoolClassProfile);
			s.setStartYear(Integer.valueOf(schoolClassStartYear));
			s.setCurrentYear(Integer.valueOf(schoolClassCurrentYear));
			s.setSchool(schoolResults.get(0));
			session.update(s);
		}
		transaction.commit();
		
	}*/





}
