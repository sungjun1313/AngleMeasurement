package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;

public interface BoardMapper {

	//게시판 객체 모두 가져온다.
	public List<BoardVO> getList();
}
