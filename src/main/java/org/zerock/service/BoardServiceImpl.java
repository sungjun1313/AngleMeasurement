package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.zerock.domain.BoardVO;
import org.zerock.mapper.BoardMapper;

//import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

	@Setter(onMethod_ = { @Autowired })
	private BoardMapper mapper;
	
	//게시판 객체를 모두 가져온다.
	@Override
	public List<BoardVO> getList(){
		return mapper.getList();
	}
}
