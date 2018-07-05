package rb.web.pong.gatekeeper.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import rb.web.pong.gatekeeper.ServiceNotFoundException;
import rb.web.pong.gatekeeper.model.Player;
import rb.web.pong.gatekeeper.model.Recorder;

@Controller
@RequestMapping(value="/pong")
public class GatekeeperController {	
	private List<Player> stats;
	
	public GatekeeperController() {
		stats = new ArrayList<Player>();
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showLoginPage() {
		Recorder.LOG.debug("MOVING TO LOGIN");
		return "login";
	}
	
	@RequestMapping(value = "/stats/xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> getStatsAsXml() {
		Recorder.LOG.debug("GETTING STATS AS XML");
		return new ResponseEntity<List<Player>>(stats, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/stats/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStatsAsJson() {
		Recorder.LOG.debug("GETTING STATS AS JSON");
		return new ResponseEntity<List<Player>>(stats, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lobby/{name}/{racket}", method = RequestMethod.GET)
	public ModelAndView toLobby(@PathVariable("name") String name, @PathVariable("racket") String racket) {
		Recorder.LOG.debug("MOVING TO WAITING ROOM");
		ModelAndView mv = new ModelAndView("waitingroom");
		mv.addObject("name", name);
		mv.addObject("racket", racket);
		return mv;
	}
	
	@ExceptionHandler(ServiceNotFoundException.class)
	public ModelAndView handleServiceNotFound(HttpServletRequest request, Exception exception) {
		Recorder.LOG.error(exception.getMessage());
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", exception);
		mv.addObject("message", exception.getMessage());
		mv.addObject("url", request.getRequestURL());
		return mv;
	}	
	
	public void addStatistic(Player entry) {
		Recorder.LOG.debug("ADDING STATISTIC");
		stats.add(entry);
	}
}
