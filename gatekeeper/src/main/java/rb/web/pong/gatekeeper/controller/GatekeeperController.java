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
import rb.web.pong.gatekeeper.model.Lobby;
import rb.web.pong.gatekeeper.model.Player;
import rb.web.pong.gatekeeper.model.Recorder;
import rb.web.pong.gatekeeper.waitingroom.WaitingRoom;

@Controller
@RequestMapping(value="/pong")
public class GatekeeperController {	
	private List<Player> stats;
	
	public GatekeeperController() {
		stats = new ArrayList<Player>();
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public synchronized String showLoginPage() {
		Recorder.LOG.debug("MOVING TO LOGIN");
		return "login";
	}
	
	@RequestMapping(value = "/stats/xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public synchronized ResponseEntity<?> getStatsAsXml() {
		Recorder.LOG.debug("GETTING STATS AS XML");
		return new ResponseEntity<List<Player>>(stats, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/stats/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ResponseEntity<?> getStatsAsJson() {
		Recorder.LOG.debug("GETTING STATS AS JSON");
		return new ResponseEntity<List<Player>>(stats, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lobby/{racket}/{name}", method = RequestMethod.GET)
	public synchronized ModelAndView toLobby(@PathVariable("racket") String racket, @PathVariable("name") String name) {
		Recorder.LOG.debug("MOVING TO WAITING ROOM");
		ModelAndView mv = new ModelAndView("waitingroom");
		mv.addObject("name", name);
		mv.addObject("racket", racket);
		return mv;
	}
	
	@RequestMapping(value = "/lobby/{lobbyId}/getplayers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ResponseEntity<?> getPlayersAsJson(@PathVariable("lobbyId") String lobbyId){
		Recorder.LOG.debug("GETTING JSON FROM WAITING PLAYERS FROM LOBBY: " + lobbyId);
		Lobby lobby = WaitingRoom.getCreatedLobbyByID(Integer.parseInt(lobbyId));
		return new ResponseEntity<List<Player>>(lobby.getRegisteredPlayersAsList(), HttpStatus.OK);
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
	
	public synchronized void addStatistic(Player entry) {
		Recorder.LOG.debug("ADDING STATISTIC");
		stats.add(entry);
	}
}
