package org.zerock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

//import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

	@Setter(onMethod_ = { @Autowired })
	private BoardMapper boardMapper;
	
	//게시판 객체를 모두 가져온다.
	@Override
	public List<BoardVO> getList(){
		return boardMapper.getList();
	}
	
	//게시판 객체를 페이징해서 가져온다.
	@Override
	public List<BoardVO> getListWithPaging(Criteria cri){
		return boardMapper.getListWithPaging(cri);
	}
	
	//게시판 객체의 총 개수를 가져온다
	@Override
	public int getTotalCount(Criteria cri) {
		return boardMapper.getTotalCount(cri);
	}
	
	//게시판 객체 상세 정보 가져온다.
	@Override
	public BoardVO read(Long bno) {
		return boardMapper.read(bno);
	}
	
	//게시판 객체에 올바른 정보가 있는 지 확인한다
	@Override
	public boolean boardValidation(BoardVO boardVO, Model model, String type) {
		Long bno = boardVO.getBno();
		String title = boardVO.getTitle();
		String content = boardVO.getContent();
		String writer = boardVO.getWriter();
		List<String> bnoResult = new ArrayList<String>();
		List<String> titleResult = new ArrayList<String>();
		List<String> contentResult = new ArrayList<String>();
		List<String> writerResult = new ArrayList<String>();
		
		if(type.equals("modify")) {
			log.warn("수정 중");
			if(bno == null) {
				log.warn("bno null");
				bnoResult.add("존재하지 않는 게시물입니다.");
			}
		}
		
		if(writer == null || writer.length() == 0) {
			log.warn("writer null");
			writerResult.add("로그인을 다시 해주세요.");
		}else {
			if(writer.length() > 50) {
				log.warn("writer length");
				writerResult.add("로그인을 다시 해주세요.");
			}
		}
		
		if(title == null || title.length() == 0) {
			log.warn("title null");
			titleResult.add("제목은 필수항목입니다.");
		}else {
			if(title.length() > 200) {
				log.warn("title length");
				titleResult.add("제목은 200 글자 아래로 입력해주세요.");
			}
		}
		
		if(content == null || content.length() == 0) {
			log.warn("content null");
			contentResult.add("내용은 필수항목입니다.");
		}else {
			if(content.length() > 2000) {
				log.warn("content length");
				contentResult.add("내용은 2000 글자 아래로 입력해주세요.");
			}
		}
		
		if(bnoResult.isEmpty() && writerResult.isEmpty() && titleResult.isEmpty() && contentResult.isEmpty()) {
			return true;
		}
		
		HashMap<String, List<String>> map = new HashMap<>();
		map.put("bnoResult", bnoResult);
		map.put("writerResult", writerResult);
		map.put("titleResult", titleResult);
		map.put("contentResult", contentResult);
		
		HashMap<String, String> prevValue = new HashMap<String, String>();
		if(type.equals("modify")) {
			prevValue.put("bno", Long.toString(bno));
		}
		prevValue.put("writer", writer);
		prevValue.put("title", title);
		prevValue.put("content", content);
		
		model.addAttribute("validation", map);
		model.addAttribute("prevValue", prevValue);
		
		return false;
	}
	
	//게시판 객체를 추가한다.
	@Override
	public int register(BoardVO boardVO) {
		return boardMapper.insert(boardVO);
	}
	
	//게시판 객체를 수정한다.
	@Override
	public int modify(BoardVO boardVO) {
		return boardMapper.update(boardVO);
	}
	
}
