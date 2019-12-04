package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.zerock.domain.AngleVO;
import org.zerock.mapper.AngleMapper;
import org.zerock.domain.Criteria;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AngleServiceImpl implements AngleService {

	@Setter(onMethod_ = { @Autowired })
	private AngleMapper angleMapper;
	
	//각도 측정 객체를 모두 가져온다.
	@Override
	public List<AngleVO> getList(){
		return angleMapper.getList();
	}
	
	//게시판과 연결된 각도 측정 객체를 페이징해서 가져온다.
	@Override
	public List<AngleVO> getListWithPaging(Criteria cri, Long bno){
		log.warn(""+cri);
		return angleMapper.getListWithPaging(cri, bno);
	}
}
