package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.Reviewer;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller 
public class ReviewerController {

    @RequestMapping(value="/Reviewers", method=RequestMethod.GET)
    public String listReviewers(Model model, HttpSession session) {    	
    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("reviewers", DatabaseConnector.getInstance().getReviewers());
    	
        return "reviewersList";    
    }
    
    @RequestMapping(value="/AddReviewer", method=RequestMethod.POST)
    public String addReviewer(
    		@RequestParam(value="reviewerName", required=false) String reviewerName,
    		@RequestParam(value="reviewerSurname", required=false) String reviewerSurname,
    		@RequestParam(value="reviewerTopic", required=false) String reviewerTopic,
    		@RequestParam(value="reviewerEmail", required=false) String reviewerEmail,
    		Model model, HttpSession session) {
    	
    	Reviewer reviewer = new Reviewer();
    	reviewer.setName(reviewerName);
    	reviewer.setSurname(reviewerSurname);
    	reviewer.setTopic(reviewerTopic);
    	reviewer.setEmail(reviewerEmail);
    	
    	DatabaseConnector.getInstance().addReviewer(reviewer);    	
    	model.addAttribute("message", "Nowy uczestnik został dodany");
    	model.addAttribute("reviewers", DatabaseConnector.getInstance().getReviewers());
        
    	return "reviewersList";
    }
    
    @RequestMapping(value="/UpdateReviewer", method=RequestMethod.POST)
    public String displayUpdateReviewerForm(@RequestParam(value="reviewerId", required=false) String reviewerId,
    										   Model model, HttpSession session) {
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	model.addAttribute("reviewer", DatabaseConnector.getInstance().getReviewer(reviewerId));
    	
    	return "updateReviewer";	
    }
    
    @RequestMapping(value="/EditReviewer", method=RequestMethod.POST)
    public String updateReviewer(
    		@RequestParam(value="reviewerId", required=false) String reviewerId,
    		@RequestParam(value="reviewerName", required=false) String reviewerName,
    		@RequestParam(value="reviewerSurname", required=false) String reviewerSurname,
    		@RequestParam(value="reviewerTopic", required=false) String reviewerTopic,
    		@RequestParam(value="reviewerEmail", required=false) String reviewerEmail,
    		Model model, HttpSession session) {
    	    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	    	
    	DatabaseConnector.getInstance().updateReviewer(reviewerId, reviewerName, reviewerSurname, reviewerTopic, reviewerEmail);    	
       	model.addAttribute("reviewers", DatabaseConnector.getInstance().getReviewers());
    	model.addAttribute("message", "Uczestnik został zmieniony");
    	
    	return "reviewersList";
    }
    
    @RequestMapping(value="/DeleteReviewer")
    public String deleteReviewer(
    		@RequestParam(value="reviewerId", required=true) String reviewerId,
    		Model model, HttpSession session
    		) {
    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteReviewer(reviewerId);    	
       	model.addAttribute("reviewers", DatabaseConnector.getInstance().getReviewers());
    	model.addAttribute("message", "Uczestnik został usunięty");
         	
    	return "reviewersList";
    }
    
    @RequestMapping(value="/ChooseReviewer", method=RequestMethod.POST)
    public String chooseReviewer(
    		@RequestParam(value="articleId", required=false) String articleId,
    		Model model, HttpSession session) {
    	    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	    	
    	model.addAttribute("article", DatabaseConnector.getInstance().getArticle(articleId));
    	model.addAttribute("reviewers", DatabaseConnector.getInstance().getReviewers());
    	
    	return "choosingReviewerForm";
    }
        
    @RequestMapping(value="/SetReviewer", method=RequestMethod.POST)
    public String setReviewer(
    		@RequestParam(value="articleId", required=true) String articleId,
    		@RequestParam(value="reviewerId", required=true) String reviewerId,
    		Model model, HttpSession session) {
    	    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	Reviewer reviewer = DatabaseConnector.getInstance().getReviewer(reviewerId);
    	
       	DatabaseConnector.getInstance().getArticle(articleId).setReviewer(reviewer);
       	model.addAttribute("articles", DatabaseConnector.getInstance().getArticles());
    	model.addAttribute("message", "Recenzent został wybrany");
    	
    	return "articlesList";
    }
}