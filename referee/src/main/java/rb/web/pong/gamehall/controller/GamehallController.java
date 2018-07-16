package rb.web.pong.gamehall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import rb.web.pong.gamehall.model.Recorder;

@Controller
@RequestMapping(value = "/pong")
public class GamehallController {
	@RequestMapping(value = "/sportshall/{hallId}/{numberOfRegisteredPlayers}/{racket}/{name}", method = RequestMethod.GET)
	public synchronized ModelAndView toGameHall(@PathVariable(value = "hallId") String hallId, @PathVariable("numberOfRegisteredPlayers") String numberOfRegisteredPlayers, 
			@PathVariable("racket") String racket, @PathVariable("name") String name) {
		Recorder.LOG.debug("BRINGING PLAYER " + name + " TO HALL " + hallId + ", SIZE: " + numberOfRegisteredPlayers);
		ModelAndView mv = new ModelAndView("hall");
		mv.addObject("hallId", hallId);
		mv.addObject("numberOfRegisteredPlayers", numberOfRegisteredPlayers);
		mv.addObject("racket", racket);
		mv.addObject("name", name);		
		return mv;
	}
}
