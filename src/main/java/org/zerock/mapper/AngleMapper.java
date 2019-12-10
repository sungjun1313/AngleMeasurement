package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.AngleVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface AngleMapper {

	//각도 측정 객체 모두 가져온다.
	public List<AngleVO> getList();
	
	//게시판과 연결된 각도 측정 객체를 페이징해서 가져온다.
	//인자가 두 개 이상일 때 Param으로 정의해서 mapper.xml에 넘겨준다.
	public List<AngleVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);
	
	//각도 측정 객체를 추가한다.
	public int insert(AngleVO angleVO);
	
	//게시판과 해당 각도 측정 객체를 가져온다.
	public BoardVO readAngleWithBoard(String uuid);
	
	//각도 측정 객체를 수정한다.
	public int modify(AngleVO angleVO);
	
	//각도 측정 객체를 삭제한다.
	public int remove(String uuid);
	
	//게시판 관련 각도 측정 객체를 모두 삭제한다.
	public int removeAll(Long bno);
	
	//게시판 관련 각도 측정 객체를 모두 가져온다.
	public List<AngleVO> getListByBno(Long bno);
	
	//전날 각도 측정 객체를 가져온다.
	public List<AngleVO> getOldFiles();
	
	//게시판 관련 각도 측정 객체 총 개수를 가져온다.
	public int getTotalCount(Long bno);
}
