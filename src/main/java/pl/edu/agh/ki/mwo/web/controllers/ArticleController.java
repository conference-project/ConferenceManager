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

@Controller
public class ArticleController {
	
	String pathInProject = "src/main/resources/static/files/";

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
    		@RequestParam(value="moreThanOneArticle", required=false) boolean moreThanOneArticle,
    		Model model, HttpSession session) {
    	
    	Article article = new Article();
    	article.setTitle(articleTitle);
    	article.setTopic(articleTopic);

    	byte[] bytes;

		try {
	    	if(articleFile==null || articleFile.getBytes().length==0) {
	    		model.addAttribute("message", "Nie załączono żadnej treści artykułu");
	        	if(DatabaseConnector.getInstance().getParticipant(participantId).getArticles().isEmpty()) {
	        		DatabaseConnector.getInstance().deleteParticipant(participantId);
		        	model.addAttribute("alert2", "Nie załączyłeś pliku. Twoja rejestracja nie powiodła się");
	        	}
	        	else {
		        	model.addAttribute("alert2", "Nie załączyłeś kolejnego pliku. Poprzednie pliki zostały zachowane");
	        	}
	    			    		
	    	}else {
	        	DatabaseConnector.getInstance().addArticle(article, participantId);    	
	           	model.addAttribute("articles", DatabaseConnector.getInstance().getArticles());
	        	model.addAttribute("message", "Nowy artykuł został dodany");
	        	model.addAttribute("alert", "Twoja rejestracja została zakończona");
	        	
				bytes = articleFile.getBytes();
				Path path = Paths.get(pathInProject+((Long)article.getId()).toString()+".pdf");
		        Files.write(path, bytes);
		        
		        if (moreThanOneArticle) {
		        	model.addAttribute("participant", DatabaseConnector.getInstance().getParticipant(participantId));
		        	return "addArticle";
		        }
	    	}
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

}
