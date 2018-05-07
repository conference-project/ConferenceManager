package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.Participant;
import pl.edu.agh.ki.mwo.model.Article;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller 
public class ReviewerController {

    @RequestMapping(value="/Article/{articleId}/Review/")
    public String listArticleForReviewer(Model model, HttpSession session, @PathVariable(value = "articleId") int articleId) {    	

    	model.addAttribute("article", DatabaseConnector.getInstance().getArticle(articleId));
    	
        return "articleRevision";    
    }
    
    @RequestMapping(value="/Article/{articleId}/Review/Rate")
    public boolean rateArticle(@RequestParam(value="rate", required=true) double rate, Model model, HttpSession session, @PathVariable(value = "articleId") int articleId) {    	
    	// double rate - to leave possibilty of having fractional parts
    	
    	
        return true;    
    }

/*    @RequestMapping(value="/AcceptArticle", method=RequestMethod.POST)
    public String deleteSchoolClass(@RequestParam(value="schoolClassId", required=true) String schoolClassId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteSchoolClass(schoolClassId);    	
       	model.addAttribute("schoolsClasses", DatabaseConnector.getInstance().getSchoolClasses());
    	model.addAttribute("message", "klasa została usunięta");
         	
    	return "schoolsClassesList";
    }*/

}
