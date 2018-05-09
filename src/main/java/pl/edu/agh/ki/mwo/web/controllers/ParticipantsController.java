package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.Participant;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller 
public class ParticipantsController {

    @RequestMapping(value="/Participants", method=RequestMethod.GET)
    public String listParticipants(Model model, HttpSession session) {    	
    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("participants", DatabaseConnector.getInstance().getParticipants());
    	
        return "participantsList";    
    }
    
    @RequestMapping(value="/AddParticipant", method=RequestMethod.POST)
    public String addParticipant(
    		@RequestParam(value="participantName", required=false) String participantName,
    		@RequestParam(value="participantSurname", required=false) String participantSurname,
    		@RequestParam(value="participantUniversity", required=false) String participantUniversity,
    		@RequestParam(value="participantEmail", required=false) String participantEmail,
    		@RequestParam(value="isAuthor", required=false) boolean isAuthor,
    		Model model, HttpSession session) {
    	
    	Participant participant = new Participant();
    	participant.setName(participantName);
    	participant.setSurname(participantSurname);
    	participant.setUniversity(participantUniversity);
    	participant.setEmail(participantEmail);
    	participant.setDoIHaveArticle(false);
    	
    	DatabaseConnector.getInstance().addParticipant(participant);    	
    	model.addAttribute("message", "Nowy uczestnik został dodany");
        
    	if (isAuthor) {
           	model.addAttribute("participant", participant);
    		return "addArticle";
    	}
    	else {
    		model.addAttribute("alert", "Twoja rejestracja została zakończona");
    		return "main";
    	}
    }
    
    @RequestMapping(value="/UpdateParticipant", method=RequestMethod.POST)
    public String displayUpdateParticipantForm(@RequestParam(value="participantId", required=false) String participantId,
    										   Model model, HttpSession session) {
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	model.addAttribute("participant", DatabaseConnector.getInstance().getParticipant(participantId));
    	
    	return "updateParticipant";	
    }
    
    @RequestMapping(value="/EditParticipant", method=RequestMethod.POST)
    public String updateParticipant(
    		@RequestParam(value="participantId", required=false) String participantId,
    		@RequestParam(value="participantName", required=false) String participantName,
    		@RequestParam(value="participantSurname", required=false) String participantSurname,
    		@RequestParam(value="participantUniversity", required=false) String participantUniversity,
    		@RequestParam(value="participantEmail", required=false) String participantEmail,
    		Model model, HttpSession session) {
    	    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	    	
    	DatabaseConnector.getInstance().updateParticipant(participantId, participantName, participantSurname, participantUniversity, participantEmail);    	
       	model.addAttribute("participants", DatabaseConnector.getInstance().getParticipants());
    	model.addAttribute("message", "Uczestnik został zmieniony");
    	
    	return "participantsList";
    }
    
    @RequestMapping(value="/DeleteParticipant")
    public String deleteParticipant(
    		@RequestParam(value="participantId", required=true) String participantId,
    		Model model, HttpSession session
    		) {
    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteParticipant(participantId);    	
       	model.addAttribute("participants", DatabaseConnector.getInstance().getParticipants());
    	model.addAttribute("message", "Uczestnik został usunięty");
         	
    	return "participantsList";
    }

}