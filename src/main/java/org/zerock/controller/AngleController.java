package org.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.service.AngleService;
import org.zerock.service.BoardService;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/angle/*")
public class AngleController {

	@Setter(onMethod_ = { @Autowired })
	private BoardService boardService;
	
	@Setter(onMethod_ = { @Autowired })
	private AngleService angleService;
	
	// /angle/list 리스트 페이지
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		log.info("cri: " + cri);
		int total = boardService.getTotalCount(cri);
		model.addAttribute("list", boardService.getListWithPaging(cri));
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
	
	@GetMapping("/detail/{bno}")
	public String detail(@PathVariable("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		model.addAttribute("board", boardService.read(bno));
		model.addAttribute("angleList", angleService.getListWithPaging(new Criteria(), bno));
		return "angle/detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public void getCreate() {
		log.info("create");
	}
}
