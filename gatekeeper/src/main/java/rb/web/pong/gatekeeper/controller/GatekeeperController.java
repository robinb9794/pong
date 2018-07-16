package rb.web.pong.gatekeeper.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import rb.web.pong.gatekeeper.ServiceNotFoundException;
import rb.web.pong.gatekeeper.model.Recorder;

@Controller
@RequestMapping(value="/pong")
public class GatekeeperController {	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public synchronized String showLoginPage() {
		Recorder.LOG.debug("MOVING TO LOGIN");
		return "login";
	}
	
	@RequestMapping(value = "/lobby/{racket}/{name}", method = RequestMethod.GET)
	public synchronized ModelAndView toLobby(@PathVariable("racket") String racket, @PathVariable("name") String name) {
		Recorder.LOG.debug("MOVING TO WAITING ROOM");
		ModelAndView mv = new ModelAndView("waitingroom");
		mv.addObject("name", name);
		mv.addObject("racket", racket);
		return mv;
	}
	
	@ExceptionHandler(ServiceNotFoundException.class)
	public synchronized ModelAndView handleServiceNotFound(HttpServletRequest request, Exception exception) {
		Recorder.LOG.error(exception.getMessage());
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", exception);
		mv.addObject("message", exception.getMessage());
		mv.addObject("url", request.getRequestURL());
		return mv;
	}	
}
