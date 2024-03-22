package com.zzzpro.zzz.wan;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExService {
	@Autowired
	public ExDao eDao;

	public List<Map<String, Object>> champions(ExDto eDto) {
		return eDao.champions(eDto);
	}

	public List<Map<String, Object>> championList(ExDto eDto) {
		return eDao.championList(eDto);
	}

	public void rank(Model model, ExDto eDto) {
		List<Map<String, Object>> aa = champions(eDto);
		List<Map<String, Object>> bb = championList(eDto);
		model.addAttribute("champions", aa);
		model.addAttribute("championList", bb);
		log.info("@@ 초상화 가나다순 -> " + aa);
		log.info("@@ 초상화 승률순 -> " + bb);
	}

	public List<Map<String, Object>> mostLine(String championName) {
		return eDao.mostLine(championName);
	}

	public List<Map<String, Object>> rune(String championName, String highest_pick_rate_position) {
		return eDao.rune(championName, highest_pick_rate_position);
	}

	public List<Map<String, Object>> spell(String championName, String highest_pick_rate_position) {
		return eDao.spell(championName, highest_pick_rate_position);
	}

	public List<Map<String, Object>> skill3lv(String championName, String highest_pick_rate_position) {
		return eDao.skill3lv(championName, highest_pick_rate_position);
	}

	public List<Map<String, Object>> skill6lv(String championName, String highest_pick_rate_position) {
		return eDao.skill6lv(championName, highest_pick_rate_position);
	}

	public List<Map<String, Object>> core3(String championName, String highest_pick_rate_position) {
		return eDao.core3(championName, highest_pick_rate_position);
	}

	public List<Map<String, Object>> sitem(String championName, String highest_pick_rate_position) {
		return eDao.sitem(championName, highest_pick_rate_position);
	}

	public List<Map<String, Object>> shoes(String championName, String highest_pick_rate_position) {
		return eDao.shoes(championName, highest_pick_rate_position);
	}

	public List<Map<String, Object>> counter(String championName, String highest_pick_rate_position) {
		return eDao.counter(championName, highest_pick_rate_position);
	}

	public List<Map<String, Object>> counterDESC(String championName, String highest_pick_rate_position) {
		return eDao.counterDESC(championName, highest_pick_rate_position);
	}

	public void allinfo(String championName, String highest_pick_rate_position, Model model) {
		model.addAttribute("mostLine", mostLine(championName));
		model.addAttribute("rune", rune(championName, highest_pick_rate_position));
		model.addAttribute("spell", spell(championName, highest_pick_rate_position));
		model.addAttribute("skill3lv", skill3lv(championName, highest_pick_rate_position));
		model.addAttribute("skill6lv", skill6lv(championName, highest_pick_rate_position));
		model.addAttribute("core3", core3(championName, highest_pick_rate_position));
		model.addAttribute("sitem", sitem(championName, highest_pick_rate_position));
		model.addAttribute("shoes", shoes(championName, highest_pick_rate_position));
		model.addAttribute("counter", counter(championName, highest_pick_rate_position));
		model.addAttribute("counterDESC", counterDESC(championName, highest_pick_rate_position));
	}

	public List<ExDto> tabKr(ExDto eDto) { // champions 메서드에서 받아온 챔피언값 필요함 html에서 라인탭누르면 이쪽으로 넘어온 js실행원리
		eDto.setHighest_pick_rate_position('%' + eDto.getHighest_pick_rate_position() + '%');
		return eDao.tabKr(eDto);
	}

	public List<ExDto> searchBar(ExDto eDto) {
		eDto.setSearchText('%' + eDto.getSearchText() + '%');
		return eDao.searchBar(eDto);
	}

	public List<ExDto> tabWin(ExDto eDto) { // champions 메서드에서 받아온 챔피언값 필요함 html에서 라인탭누르면 이쪽으로 넘어온 js실행원리
		eDto.setHighest_pick_rate_position('%' + eDto.getHighest_pick_rate_position() + '%');
		return eDao.tabWin(eDto);
	}
}
