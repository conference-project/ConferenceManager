package pl.edu.agh.ki.mwo.web.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class RevisionController {

	String pathInProject = "src/main/resources/static/files/";
	int positiveRate=3;

	@RequestMapping(value = "/Article/{articleId}/Review/")
	public String listArticleForReviewer(Model model, HttpSession session,
			@PathVariable(value = "articleId") String articleId) {
    	
		model.addAttribute("article", DatabaseConnector.getInstance().getArticle(articleId));

		return "articleRevision";
	}

	@RequestMapping(value = "/Article/{articleId}/Review/Rate")
	public String rateArticle(@RequestParam(value = "rate", required = true) int rate,
			@RequestParam(value = "comment", required = false) String comment, Model model, HttpSession session,
			@PathVariable(value = "articleId") String articleId) {
		
		if (DatabaseConnector.getInstance().getArticle(articleId).getRate()>=positiveRate) {
			// if is added to block possibility of changing evaluation for once positively rated article 
			
			model.addAttribute("article", DatabaseConnector.getInstance().getArticle(articleId));
			model.addAttribute("message", "Artykuł już został pozytywnie oceniony");
			model.addAttribute("alert", "Nie można zmienić oceny, ponieważ artykuł już został pozytywnie oceniony.");
			
		}else {
			DatabaseConnector.getInstance().rateArticle(articleId, rate);
			if(rate>2) {
				DatabaseConnector.getInstance().getArticle(articleId).setIsAprovedByReviewer(true);;
			}
			
			if (comment!=null || comment.equals("")) {
				DatabaseConnector.getInstance().commentForArticle(articleId, comment);
			}
			model.addAttribute("article", DatabaseConnector.getInstance().getArticle(articleId));
			model.addAttribute("message", "Artykuł został oceniony");
			model.addAttribute("alert", "Dziękujemy za recenzje i ocenę pracy.");
		}
		return "articleRevision";
	}

	@RequestMapping(value = "/Article/{articleId}/Review/pdf/{articleId}.pdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> showPdf(Model model, HttpSession session,
			@PathVariable(value = "articleId") String articleId) throws IOException {

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String filename = articleId + ".pdf";

		headers.add("content-disposition", "inline;filename=" + filename);
		Path path = Paths.get(pathInProject + articleId + ".pdf".toString());

		byte[] pdfAsBytes = Files.readAllBytes(path);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(pdfAsBytes, headers, HttpStatus.OK);
		return response;
	}
}
