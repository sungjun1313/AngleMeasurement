package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	
}
