package com.zzzpro.zzz.sol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zzzpro.zzz.sol.dto.BoardDto;
import com.zzzpro.zzz.sol.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {
	@Autowired
	private BoardService bSer;
	
	@GetMapping("/board/list")
	public String boardList(int page, Model model) {
		bSer.boardList(page, model);
		return "boardList";
	}
	
	@GetMapping("/board/detail")
	public String boardDetail(BoardDto bDto, Model model) {
		if(bSer.boardDetail(bDto, model)) {
			return "boardDetail";
		}
		return "boardList";
	}
	
	@GetMapping("/board/boardWrite")
	public String boardWrite(Model model) {
		return "boardWrite";
	}
	
	@GetMapping("/board/writeSub")
	public String writeSub(BoardDto bDto, RedirectAttributes ra) {	
		bSer.writeSub(bDto, ra);
		return "redirect:/board/list?page=1";
	}
	
	@PostMapping("/board/boardDelete")
	public String boardDelete(BoardDto bDto, RedirectAttributes ra) {
		if(bSer.boardDelete(bDto, ra)) {
			return "redirect:/board/list?page=1";
		}
		return "redirect:/board/detail?b_num="+bDto.getB_num();
	}
	
	@PostMapping("/board/boardUpdate")
	public String boardUpdate(BoardDto bDto, Model model) {
		if(bSer.boardUpdate(bDto, model)) {
			return "boardUpdate";
		}
		return "redirect:/board/detail?b_num="+bDto.getB_num();
	}
	
	@GetMapping("/board/updateSub")
	public String updateSub(BoardDto bDto, RedirectAttributes ra) {
		bSer.updateSub(bDto,ra);
		return "redirect:/board/detail?b_num="+bDto.getB_num();
	}
}
