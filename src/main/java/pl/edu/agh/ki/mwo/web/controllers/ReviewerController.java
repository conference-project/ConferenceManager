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

	@RequestMapping(value = "/Article/{articleId}/Review/")
	public String listArticleForReviewer(Model model, HttpSession session,
			@PathVariable(value = "articleId") String articleId) {

		model.addAttribute("article", DatabaseConnector.getInstance().getArticle(articleId));

		return "articleRevision";
	}

	@RequestMapping(value = "/Article/{articleId}/Review/Rate")
	public String rateArticle(@RequestParam(value = "rate", required = true) int rate, Model model,
			HttpSession session, @PathVariable(value = "articleId") String articleId) {
		
		DatabaseConnector.getInstance().rateArticle(articleId, rate);
		model.addAttribute("article", DatabaseConnector.getInstance().getArticle(articleId));
		model.addAttribute("message", "Artykuł został oceniony");
		model.addAttribute("alert", "Twoja ocena została przesłana");
		
		return "articleRevision";
	}

}
