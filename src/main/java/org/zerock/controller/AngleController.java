package org.zerock.controller;

import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.service.AngleService;
import org.zerock.service.BoardService;
import org.zerock.domain.AngleVO;
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
	public String postCreate(BoardVO boardVO, Authentication auth, Model model, RedirectAttributes rttr) {
		CustomUser user = (CustomUser)auth.getPrincipal();
		MemberVO member = user.getMember();
		boardVO.setWriter(member.getUserId());
		if(boardService.boardValidation(boardVO, model, "create")) {
			int result = boardService.register(boardVO);
			if(result == 1) {
				log.info("게시물 추가 완료");
				rttr.addFlashAttribute("result", boardVO.getBno() +" 번 게시물이 등록되었습니다.");
				return "redirect:/angle/list";
			}
			log.info("게시물 추가 서버 에러");
			model.addAttribute("message", "서버 에러로 게시물 추가에 실패하였습니다.");
		}
		
		return "angle/create";
	}
	
	//  /angle/modify/2 게시판 수정 페이지
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{bno}")
	public String getModify(@PathVariable("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model, Authentication auth, RedirectAttributes rttr) {
		log.info("modify");
		CustomUser user = (CustomUser)auth.getPrincipal();
		MemberVO member = user.getMember();
		BoardVO boardVO = boardService.read(bno);
		if(boardVO.getWriter().equals(member.getUserId())) {
			model.addAttribute("board", boardVO);
			return "angle/modify";
		}
		
		rttr.addFlashAttribute("result", "해당 게시물에 권한이 없습니다.");
		return "redirect:/angle/list" + cri.getListLink();
	}
	
	//  /angle/modify/2 게시판 수정 post 요청 처리
	@PreAuthorize("principal.member.userId == #boardVO.writer")
	@PostMapping("/modify/{bno}")
	public String postModify(BoardVO boardVO, @ModelAttribute("cri") Criteria cri, Model model, Authentication auth,RedirectAttributes rttr) {
		if(boardService.boardValidation(boardVO, model, "modify")) {
			int result = boardService.modify(boardVO);
			if(result == 1) {
				rttr.addFlashAttribute("result", boardVO.getBno() + "번 게시물이 수정되었습니다.");
				return "redirect:/angle/list" + cri.getListLink();
			}
			log.info("게시물 수정 서버에러");
			model.addAttribute("message", "서버 에러로 게시물 수정에 실패하였습니다.");
		}
		
		return "angle/modify";
	}
	
	//  /angle/measure/create/2 각도 측정 페이지
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/measure/create/{bno}")
	public String getMeasureCreate(@PathVariable("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model, Authentication auth, RedirectAttributes rttr) {
		CustomUser user = (CustomUser)auth.getPrincipal();
		MemberVO member = user.getMember();
		BoardVO boardVO = boardService.read(bno);
		if(boardVO.getWriter().equals(member.getUserId())) {
			model.addAttribute("bno", bno);
			return "angle/measureCreate";
		}
		
		rttr.addFlashAttribute("result", "해당 게시물에 권한이 없습니다.");
		return "redirect:/angle/list" + cri.getListLink();
	}
	
	//  /angle/measure/create/2 각도 측정 post 요청 처리 
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/measure/create/{bno}")
	public String postMeasureCreate(@PathVariable("bno") Long bno, AngleVO angleVO, @ModelAttribute("cri") Criteria cri, Model model, Authentication auth, RedirectAttributes rttr) {
		HashMap <String, String> map = angleService.fileUpload(angleVO.getAngleFile());
		angleVO.setBno(bno);
		if(map.get("success").equals("success")) {
			String uuid = map.get("uuid");
			String uploadPath = map.get("uploadPath");
			String fileName = map.get("fileName");
			angleVO.setUuid(uuid);
			angleVO.setUploadPath(uploadPath);
			angleVO.setFileName(fileName);
			if(angleService.angleValidation(angleVO, model, "create")) {
				int result = angleService.register(angleVO);
				if(result == 1) {
					rttr.addFlashAttribute("result", angleVO.getBno() + "번 게시물에 각도 측정 결과가 추가되었습니다.");
					return "redirect:/angle/list" + cri.getListLink();
				}
				model.addAttribute("message", "서버 에러로 파일 업로드에 실패하였습니다.");
			}
		}else {
			log.info("파일 업로드 실패");
			model.addAttribute("message", "잘못된 파일이거나 서버 에러로 파일 업로드에 실패하였습니다.");
		}
		
		return "angle/measureCreate";
	}
}
