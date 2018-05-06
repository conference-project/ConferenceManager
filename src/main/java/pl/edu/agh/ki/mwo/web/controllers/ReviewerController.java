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

    @RequestMapping(value="/Review/{articleId}")
    public String listArticleForReviewer(Model model, HttpSession session, @PathVariable(value = "articleId") int articleId) {    	

    	model.addAttribute("article", DatabaseConnector.getInstance().getArticle(((Integer)articleId).toString()));
    	
        return "articleRevision";    
    }


}