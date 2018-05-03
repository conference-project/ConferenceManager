package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @RequestMapping(value={"/", "/Start"}, method=RequestMethod.GET)
    public String displayMainForm() {
        return "main";
    }

//	@RequestMapping(value="/AddUser", method=RequestMethod.POST)
//	public String doLogin(@RequestParam(value="login") String login, Model model, HttpSession session) {
//		session.setAttribute("userLogin", login);
//		return "redirect:/Welcome";
//	}

}