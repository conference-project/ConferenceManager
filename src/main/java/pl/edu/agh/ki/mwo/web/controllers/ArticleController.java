package pl.edu.agh.ki.mwo.web.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import pl.edu.agh.ki.mwo.model.Participant;
import pl.edu.agh.ki.mwo.model.Article;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller //konieczna adnotacja oznaczjaca obiekt kontrolera od strony widoku
public class ArticleController {
	
	String pathInProject = "src/main/resources/templates/pdf/";

    @RequestMapping(value="/Articles", method=RequestMethod.GET)
    public String listArticles(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("articles", DatabaseConnector.getInstance().getArticles());
    	
        return "articlesList";    
    }
    
    @RequestMapping(value="/AddArticle", method=RequestMethod.POST)
    public String addArticle(
    		@RequestParam(value="articleTitle", required=false) String articleTitle,
    		@RequestParam(value="articleTopic", required=false) String articleTopic,
    		@RequestParam(value="participantId", required=false) String participantId,
    		@RequestParam(value="articleFile", required=false) MultipartFile articleFile,
    		Model model, HttpSession session) {
    	
    	Article article = new Article();
    	article.setTitle(articleTitle);
    	article.setTopic(articleTopic);

    	DatabaseConnector.getInstance().addArticle(article, participantId);    	
       	model.addAttribute("articles", DatabaseConnector.getInstance().getArticles());
    	model.addAttribute("message", "Nowy artykuł został dodany");
    	model.addAttribute("alert", "Twoja rejestracja została zakończona");
    	
    	byte[] bytes;
		try {
			bytes = articleFile.getBytes();
			Path path = Paths.get(pathInProject+((Long)article.getId()).toString());
	        Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return "main";
    }
    
    @RequestMapping(value="/UpdateArticle", method=RequestMethod.POST)
    public String displayUpdateArticleForm(@RequestParam(value="articleId", required=false) String articleId,
    									   Model model, HttpSession session) {
    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	model.addAttribute("article", DatabaseConnector.getInstance().getArticle(articleId));
    	model.addAttribute("participants", DatabaseConnector.getInstance().getParticipants());
    	
    	return "updateArticle";	
    }
    
    @RequestMapping(value="/EditArticle", method=RequestMethod.POST)
    public String updateArticle(
    		@RequestParam(value="articleId", required=false) String articleId,
    		@RequestParam(value="articleTitle", required=false) String articleTitle,
    		@RequestParam(value="articleTopic", required=false) String articleTopic,
    		@RequestParam(value="participantId", required=false) String participantId,
    		Model model, HttpSession session) {
    	    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().updateArticle(articleId, articleTitle, articleTopic, participantId);;    	
       	model.addAttribute("articles", DatabaseConnector.getInstance().getArticles());
    	model.addAttribute("message", "Artykuł został zmieniony");
    	
    	return "articlesList";
    }
    
    @RequestMapping(value="/DeleteArticle")
    public String deleteArticle(
    		@RequestParam(value="articleId", required=true) String articleId,
    		Model model,HttpSession session
    		) {
    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteArticle(articleId);
        try {
        	Path path = Paths.get(pathInProject + articleId);
        	Files.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
       	model.addAttribute("articles", DatabaseConnector.getInstance().getArticles());
    	model.addAttribute("message", "Artykuł został usunięty");
         	
    	return "articlesList";
    }
        
/*	@RequestMapping(value="/AddArticles")
    public String displayAddSchoolClassForm(Model model, HttpSession session) {    	
		if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

       	model.addAttribute("participants", DatabaseConnector.getInstance().getParticipants());
       	
        return "ArticleForm";    
    }*/
    
/*    @RequestMapping(value="/CreateSchoolClass", method=RequestMethod.POST)
    public String createSchoolClass(@RequestParam(value="schoolClassStartYear", required=false) String startYear,
    		@RequestParam(value="schoolClassCurrentYear", required=false) String currentYear,
    		@RequestParam(value="schoolClassProfile", required=false) String profile,
    		@RequestParam(value="schoolClassSchool", required=false) String schoolId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	//School school= DatabaseConnector.getInstance().getSchool(schoolId);
    	Article schoolClass = new Article();
    	schoolClass.setStartYear(Integer.valueOf(startYear));
    	schoolClass.setCurrentYear(Integer.valueOf(currentYear));
    	schoolClass.setProfile(profile);
    	//schoolClass.setSchool(school);

    	
    	DatabaseConnector.getInstance().addSchoolClass(schoolClass, schoolId);    	
       	model.addAttribute("schoolsClasses", DatabaseConnector.getInstance().getSchoolClasses());
    	model.addAttribute("message", "Nowa klasa została dodana");
         	
    	return "schoolsClassesList";
    }
    

    @RequestMapping(value="/DeleteSchoolClass", method=RequestMethod.POST)
    public String deleteSchoolClass(@RequestParam(value="schoolClassId", required=true) String schoolClassId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteSchoolClass(schoolClassId);    	
       	model.addAttribute("schoolsClasses", DatabaseConnector.getInstance().getSchoolClasses());
    	model.addAttribute("message", "klasa została usunięta");
         	
    	return "schoolsClassesList";
    }
    
    @RequestMapping(value="/EditSchoolClass", method=RequestMethod.POST)
    public String editSchool(@RequestParam(value="schoolClassId", required=false) String schoolClassId,
    	   Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	model.addAttribute("schoolClassId", schoolClassId);
    	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
        return "editSchoolClassForm";
    }
    
    @RequestMapping(value="/CorrectSchoolClassData", method=RequestMethod.POST)
    public String correctSchoolClassData(@RequestParam(value="schoolClassId", required=true) String schoolClassId,
    		@RequestParam(value="schoolClassProfile", required=false) String schoolClassProfile,
    		@RequestParam(value="schoolClassStartYear", required=false) String schoolClassStartYear,
    		@RequestParam(value="schoolClassCurrentYear", required=false) String schoolClassCurrentYear,
    		@RequestParam(value="schoolClassSchool", required=false) String schoolClassSchool,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	 	
    	DatabaseConnector.getInstance().editSchoolClass(schoolClassId, schoolClassProfile, schoolClassStartYear, schoolClassCurrentYear, schoolClassSchool);
    	model.addAttribute("schoolsClasses", DatabaseConnector.getInstance().getSchoolClasses());
    	model.addAttribute("message", "Dane klasy zostały zmienione");
         	
    	return "schoolsClassesList";
    }*/

}