package org.zerock.service;


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
	
	
	@Transactional
	@Override
	public AngleVO getTime() {
		mapper.getTime();
		return mapper.getTime();
	}
}
