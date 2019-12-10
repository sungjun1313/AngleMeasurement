package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {

	//페이징 정보
	
	//화면에 보여줄 버톤 총 개수
	private final int BTN_NUM = 10;
	//시작버튼
	private int startPage;
	//마지막 버튼
	private int endPage;
	//총 개수에 따른 마지막 페이지
	private int lastPage;
	//총 개수
	private int total;
	//페이징 기준이 되는 변수
	private Criteria cri;
	//이전버튼
	private boolean prev;
	//다음버튼
	private boolean next;
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		this.lastPage = (int)(Math.ceil((total*1.0) / cri.getAmount()));
		this.endPage = (int)(Math.ceil((cri.getPageNum()*1.0) / this.BTN_NUM)) * this.BTN_NUM;
		this.startPage = this.endPage - this.BTN_NUM + 1;
		
		if(this.lastPage < this.endPage) {
			this.endPage = this.lastPage;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.startPage + this.BTN_NUM <= this.lastPage;
	}
}
