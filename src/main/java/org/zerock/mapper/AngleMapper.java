package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.AngleVO;

public interface AngleMapper {

	//각도 측정 객체 모두 가져온다.
	public List<AngleVO> getList();
}
