package rb.web.pong.gatekeeper.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import rb.web.pong.gatekeeper.ServiceNotFoundException;

@Controller
@RequestMapping(value="/pong")
public class GatekeeperController {	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model) {
		return "login";
	}
	
	@ExceptionHandler(ServiceNotFoundException.class)
	public ModelAndView handleServiceNotFound(HttpServletRequest request, Exception exception) {
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", exception);
		mv.addObject("message", exception.getMessage());
		mv.addObject("url", request.getRequestURL());
		return mv;
	}
}
