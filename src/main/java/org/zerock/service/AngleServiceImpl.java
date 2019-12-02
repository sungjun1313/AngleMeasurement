package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.zerock.domain.AngleVO;
import org.zerock.mapper.AngleMapper;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AngleServiceImpl implements AngleService {

	@Setter(onMethod_ = { @Autowired })
	private AngleMapper mapper;
	
	//각도 측정 객체를 모두 가져온다.
	@Override
	public List<AngleVO> getList(){
		return mapper.getList();
	}
}
