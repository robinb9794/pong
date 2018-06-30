package rb.web.pong.gatekeeper.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import rb.web.pong.gatekeeper.ServiceNotFoundException;
import rb.web.pong.gatekeeper.stats.StatsEntry;

@Controller
@RequestMapping(value="/pong")
public class GatekeeperController {	
	private List<StatsEntry> stats;
	
	public GatekeeperController() {
		stats = new ArrayList<StatsEntry>();
		stats.add(new StatsEntry());
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model) {
		return "login";
	}
	
	@RequestMapping(value = "/stats/xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> getStatsAsXml() {
		return new ResponseEntity<List<StatsEntry>>(stats, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/stats/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStatsAsJson() {
		return new ResponseEntity<List<StatsEntry>>(stats, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lobby/{name}", method = RequestMethod.GET)
	public ModelAndView toLobby(@PathVariable("name") String name ) {
		ModelAndView mv = new ModelAndView("lobby");
		mv.addObject("name", name);
		return mv;
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
