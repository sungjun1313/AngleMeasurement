package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.AngleVO;
import org.zerock.domain.Criteria;

public interface AngleMapper {

	//각도 측정 객체 모두 가져온다.
	public List<AngleVO> getList();
	
	//게시판과 연결된 각도 측정 객체를 페이징해서 가져온다.
	//인자가 두 개 이상일 때 Param으로 정의해서 mapper.xml에 넘겨준다.
	public List<AngleVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);
	
}
