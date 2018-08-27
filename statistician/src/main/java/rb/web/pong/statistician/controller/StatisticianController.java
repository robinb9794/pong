package rb.web.pong.statistician.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import rb.web.pong.statistician.StatsService;
import rb.web.pong.statistician.model.Player;
import rb.web.pong.statistician.model.Recorder;

@Controller
@RequestMapping(value = "/pong")
public class StatisticianController {
	@Autowired
	private StatsService statsService;
	
	@RequestMapping(value = "/stats/get/xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public synchronized ResponseEntity<List<Player>> getStatsAsXml(){
		Recorder.LOG.info("RETURNING STATS AS XML");
		if (statsService.isEmpty())
            return new ResponseEntity<List<Player>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Player>>(StatsService.stats, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/stats/get/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ResponseEntity<List<Player>> getStatsAsJson(){
		Recorder.LOG.info("RETURNING STATS AS JSON");
		if (statsService.isEmpty())
            return new ResponseEntity<List<Player>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Player>>(StatsService.stats, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/stats/insert", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> insertStatsAndGoToLogin(@RequestBody Player player, UriComponentsBuilder ucBuilder) {
		Recorder.LOG.debug("INSERTING PLAYER IN STATS");
		if(statsService.exists(player.getName()))
			statsService.update(player.getName());
		else
			statsService.insert(player.getName());
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("http://localhost:8080/pong/").build().toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
}
