package rb.web.pong.gamehall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import rb.web.pong.gamehall.model.Recorder;

@Controller
@RequestMapping(value = "/pong")
public class GamehallController {
	@RequestMapping(value = "/gamehall/{lobbyId}/{name}", method = RequestMethod.GET)
	public synchronized ModelAndView toGameHall(@PathVariable("lobbyId") String lobbyId, @PathVariable("name") String name) {
		Recorder.LOG.debug("BRINGING PLAYERS FROM LOBBY " + lobbyId + " TO GAME HALL");
		ModelAndView mv = new ModelAndView("gamehall");
		String waitingroomUrl = "http://localhost:8080/pong/lobby/" + lobbyId + "/getplayers";
		RestTemplate waitingroomTemplate = new RestTemplate();
		String waitingroomData = waitingroomTemplate.getForObject(waitingroomUrl, String.class);
		Recorder.LOG.info(waitingroomData);
		mv.addObject("waitingroomData", waitingroomData);
		return mv;
	}
}
