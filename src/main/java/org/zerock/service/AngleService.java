package org.zerock.service;

import java.util.List;

import org.zerock.domain.AngleVO;
import org.zerock.domain.Criteria;

public interface AngleService {

	public List<AngleVO> getList();
	
	public List<AngleVO> getListWithPaging(Criteria cri, Long bno);
}
