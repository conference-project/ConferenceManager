package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.Participant;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller //konieczna adnotacja oznaczjaca obiekt kontrolera od strony widoku
public class ParticipantsController {

    @RequestMapping(value="/Participants")
    public String listParticipants(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("participants", DatabaseConnector.getInstance().getParticipants());
    	
        return "participantsList";    
    }
    
    /*@RequestMapping(value="/AddParticipant")
    public String displayAddSchoolForm(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
        return "participantForm";    
    }*/
    
    @RequestMapping(value="/AddParticipant", method=RequestMethod.POST)
    public String addParticipant(
    		@RequestParam(value="participantId", required=false) String participantId,
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
       	model.addAttribute("participants", DatabaseConnector.getInstance().getParticipants());
    	model.addAttribute("message", "Nowy uczestnik został dodany");
        
    	if (isAuthor) {
    		return "AddArticle";
    	}
    	else {
    		return "main";
    	}
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

/*    @RequestMapping(value="/CreateSchool", method=RequestMethod.POST)
    public String createSchool(@RequestParam(value="schoolName", required=false) String name,
    		@RequestParam(value="schoolAddress", required=false) String address,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	Participant school = new Participant();
    	school.setName(name);
    	school.setAddress(address);
    	
    	DatabaseConnector.getInstance().addSchool(school);    	
       	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
    	model.addAttribute("message", "Nowa szkoła została dodana");
         	
    	return "schoolsList";
    }
    
    @RequestMapping(value="/DeleteSchool", method=RequestMethod.POST)
    public String deleteSchool(@RequestParam(value="schoolId", required=false) String schoolId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteSchool(schoolId);    	
       	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
    	model.addAttribute("message", "Szkoła została usunięta");
         	
    	return "schoolsList";
    }
    
    @RequestMapping(value="/EditSchool", method=RequestMethod.POST)
    public String editSchool(@RequestParam(value="schoolId", required=false) String schoolId,
    	   Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	model.addAttribute("schoolId", schoolId);
    	model.addAttribute("schoolName", DatabaseConnector.getInstance().getSchool(schoolId).getName());
    	model.addAttribute("schoolAddress", DatabaseConnector.getInstance().getSchool(schoolId).getAddress());
        return "editSchoolForm";
    }
    
    @RequestMapping(value="/CorrectSchoolData", method=RequestMethod.POST)
    public String correctSchoolData(@RequestParam(value="schoolId", required=true) String schoolId,
    		@RequestParam(value="schoolName", required=false) String name,
    		@RequestParam(value="schoolAddress", required=false) String address,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
          	
    	DatabaseConnector.getInstance().editSchool(schoolId, name, address);
    	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
    	model.addAttribute("message", "Dane szkoły zostały zmienione");
         	
    	return "schoolsList";
    }*/
}