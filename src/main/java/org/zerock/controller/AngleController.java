package org.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.MemberVO;
import org.zerock.domain.PageDTO;
import org.zerock.security.domain.CustomUser;

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
	
	//  /angle/detail/2  게시판 상세 페이지
	@GetMapping("/detail/{bno}")
	public String detail(@PathVariable("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		model.addAttribute("board", boardService.read(bno));
		model.addAttribute("angleList", angleService.getListWithPaging(new Criteria(), bno));
		return "angle/detail";
	}
	
	//  /angle/create 게시판 추가 페이지
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public void getCreate() {
		log.info("create");
	}
	
	//  /angle/create 게시판 추가 post 요청 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String postCreate(BoardVO boardVO, Authentication auth, RedirectAttributes rttr) {
		CustomUser user = (CustomUser)auth.getPrincipal();
		MemberVO member = user.getMember();
		boardVO.setWriter(member.getUserId());
		boardService.register(boardVO);
		rttr.addFlashAttribute("result", boardVO.getBno() +" 번 게시물이 등록되었습니다.");
		return "redirect:/angle/list/";
	}
	
	//  /angle/modify/2 게시판 수정 페이지
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{bno}")
	public String getModify(@PathVariable("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info("modify");
		model.addAttribute("board", boardService.read(bno));
		return "angle/modify";
	}
	
//  /angle/modify 게시판 수정 post 요청 처리
	@PreAuthorize("principal.member.userId == #boardVO.writer")
	@PostMapping("/modify")
	public String postModify(BoardVO boardVO, Criteria cri, Authentication auth,RedirectAttributes rttr) {
		if(boardService.modify(boardVO) == 1) {
			rttr.addFlashAttribute("result", boardVO.getBno() + "번 게시물이 수정되었습니다.");
		}
		return "redirect:/angle/list" + cri.getListLink();
	}
	
	//  /angle/measure/create 각도 측정 페이지
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/measure/create")
	public String getMeasureCreate() {
		return "angle/measureCreate";
	}
	
	//  /angle/measure/create 각도 측정 post 요청 처리 
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/measure/create")
	public String postMeasureCreate() {
		return null;
	}
}
