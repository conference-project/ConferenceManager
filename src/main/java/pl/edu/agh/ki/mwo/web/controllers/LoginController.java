package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @RequestMapping(value={"/Login"}, method=RequestMethod.GET)
    public String displayLoginForm(HttpSession session) {
    	if (session.getAttribute("userLogin") != null)
    		return "redirect:/Welcome";
        return "loginForm";
    }

	@RequestMapping(value="/Login", method=RequestMethod.POST)
	public String doLogin(
			@RequestParam(value="login") String login, @RequestParam(value="password") String password, 
			Model model, HttpSession session) {
		if (login.equals("edi") && password.equals("pass")) {
			session.setAttribute("userLogin", login);
			return "redirect:/Welcome";
		} else {
			model.addAttribute("alert", "Nieprawidłowy login lub hasło");
			return "loginForm";
		}
	}

    @RequestMapping(value="/Welcome")
    public String welcome(Model model, HttpSession session) {
    	model.addAttribute("message", "Witamy w systemie konferencyjnym!");
        return "welcome";
    }
    
    @RequestMapping(value={"/Logout"}, method=RequestMethod.GET)
    public String logout(HttpSession session) {
    	session.setAttribute("userLogin", null);
        return "logout";
    }
}