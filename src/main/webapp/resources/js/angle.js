/*
jQuery
$(document).ready(function(){

});
ready 기능을 순수 자바스크립트로 다음과 같이 쓸 수 있다.
*/

// Mozilla, Opera, Webkit
if (document.addEventListener) {
	document.addEventListener("DOMContentLoaded", function () {
		document.removeEventListener("DOMContentLoaded", arguments.callee, false);
		domReady();
	}, false);

}

// Internet Explorer
else if (document.attachEvent) {
	document.attachEvent("onreadystatechange", function () {
		if (document.readyState === "complete") {
			document.detachEvent("onreadystatechange", arguments.callee);
			domReady();
		}
	});
}

//element select
var container = document.querySelector("#angleBox");
var canvas = document.querySelector("#canvas");
var dragItem1 = document.querySelector(".cir1");
var dragItem2 = document.querySelector(".cir2");
var dragItem3 = document.querySelector(".cir3");
var fileInput = document.querySelector("#id_angleFile");
var result = document.querySelector("#result");
var poly1 = document.querySelector(".poly1");
var poly2 = document.querySelector(".poly2");
var angleInput = document.querySelector("#id_angle");

//circle state
var active1 = false;
var currentX1;
var currentY1;
var initialX1;
var initialY1;
var xOffset1 = 0;
var yOffset1 = 0;

var active2 = false;
var currentX2;
var currentY2;
var initialX2;
var initialY2;
var xOffset2 = 0;
var yOffset2 = 0;

var active3 = false;
var currentX3;
var currentY3;
var initialX3;
var initialY3;
var xOffset3 = 0;
var yOffset3 = 0;

//원의 반지름
var rad = 50 / 2;

//element 절대좌표
var winX;
var winY;
var canvasT;

var infoX1;
var infoY1;
var infoX2;
var infoY2;
var infoX3;
var infoY3;

//드래그 시작
function dragStart(e) {
  if (e.type === "touchstart") {
    if(e.target === dragItem1){
      initialX1 = e.touches[0].clientX - xOffset1;
      initialY1 = e.touches[0].clientY - yOffset1;
    }else if(e.target === dragItem2){
      initialX2 = e.touches[0].clientX - xOffset2;
      initialY2 = e.touches[0].clientY - yOffset2;
    }else if(e.target === dragItem3){
      initialX3 = e.touches[0].clientX - xOffset3;
      initialY3 = e.touches[0].clientY - yOffset3;
    }

  } else {
    if(e.target === dragItem1){
      initialX1 = e.clientX - xOffset1;
      initialY1 = e.clientY - yOffset1;
    }else if(e.target === dragItem2){
      initialX2 = e.clientX - xOffset2;
      initialY2 = e.clientY - yOffset2;
    }else if(e.target === dragItem3){
      initialX3 = e.clientX - xOffset3;
      initialY3 = e.clientY - yOffset3;
    }

  }

  if (e.target === dragItem1) {
    active1 = true;
  }else if(e.target === dragItem2){
    active2 = true;
  }else if(e.target === dragItem3){
    active3 = true;
  }
}

//드래그 중지
function dragEnd(e) {
  if(e.target === dragItem1){
    initialX1 = currentX1;
    initialY1 = currentY1;

    active1 = false;
  }else if(e.target === dragItem2){
    initialX2 = currentX2;
    initialY2 = currentY2;

    active2 = false;
  }else if(e.target === dragItem3){
    initialX3 = currentX3;
    initialY3 = currentY3;

    active3 = false;
  }else{
    active1 = false;
    active2 = false;
    active3 = false;
  }
}

//드래그 중
function drag(e) {
  e.preventDefault();
  if (active1) {
    if (e.type === "touchmove") {
      currentX1 = e.touches[0].clientX - initialX1;
      currentY1 = e.touches[0].clientY - initialY1;
    } else {
      currentX1 = e.clientX - initialX1;
      currentY1 = e.clientY - initialY1;
    }

    xOffset1 = currentX1;
    yOffset1 = currentY1;

    setTranslate(currentX1, currentY1, dragItem1);
    init();
  }else if(active2){
    if (e.type === "touchmove") {
      currentX2 = e.touches[0].clientX - initialX2;
      currentY2 = e.touches[0].clientY - initialY2;
    } else {
      currentX2 = e.clientX - initialX2;
      currentY2 = e.clientY - initialY2;
    }

    xOffset2 = currentX2;
    yOffset2 = currentY2;

    setTranslate(currentX2, currentY2, dragItem2);
    init();
  }else if(active3){
    if (e.type === "touchmove") {
      currentX3 = e.touches[0].clientX - initialX3;
      currentY3 = e.touches[0].clientY - initialY3;
    } else {
      currentX3 = e.clientX - initialX3;
      currentY3 = e.clientY - initialY3;
    }

    xOffset3 = currentX3;
    yOffset3 = currentY3;

    setTranslate(currentX3, currentY3, dragItem3);
    init();
  }


}

//원의 위치 변경
function setTranslate(xPos, yPos, el) {
  el.style.transform = "translate3d(" + xPos + "px, " + yPos + "px, 0)";
}

//이미지 업로드 환경 확인
function checkImgEnv(){
  if(window.File && window.FileList && window.FileReader){
    return true;
  }else{
    return false;
  }
}

//자식 개수
function childrenLength(el){
  return el.childElementCount;
}

//이미지 체크
function checkImgType(el){
  if(el.type.match('image')){
    return true;
  }else{
    return false;
  }
}

//이미지 사이즈 체크
function checkImgSize(el){
  if(el.size > 20971520){
    return false;
  }else{
    return true;
  }
}

//이미지 삽입
function insertImg(file){
  var divLength = childrenLength(result);
  window.URL = window.URL || window.webkitURL;
  //var url = URL.createObjectURL(file, {oneTimeOnly: true});
  //console.log(url);

  var div = document.createElement("div");
  if(divLength !== 0){
    result.innerHTML = '';
  }
  var img = document.createElement("img");
  img.src = window.URL.createObjectURL(file);
  img.alt = "measure angle";
  img.onload = function(){
	  window.URL.revokeObjectURL(this.src);
  }
  div.appendChild(img);
  //div.innerHTML = "<img src='"+ url +"' alt='measure image' />";
  //console.log(div);
  result.insertBefore(div, null);
}

//폼 초기화
function initForm(el){
	result.innerHTML = '';
	el.value = '';
}

//이미지 업로드
function uploadImg(e){
  if(checkImgEnv()){
    var selectFile = e.target.files[0];
    
    if(selectFile){
    	if(!checkImgType(selectFile)){
    	      alert("이미지 파일만 가능합니다.");
    	      initForm(this);
    	      return false;
    	}
    	
    	if(!checkImgSize(selectFile)){
        	alert("20MB 보다 큰 이미지 파일은 전송할 수 없습니다.");
        	initForm(this);
        	return false;
        }
    	
    	insertImg(selectFile);
    }else{
    	initForm(this);
    }
    
  }else{
    console.log('최신 크롬 브라우저를 사용해주세요.');
  }

}

//polyline 배치
function init(){
  winX = window.pageXOffset;
  winY = window.pageYOffset;

  canvasX = winX + canvas.getBoundingClientRect().left;
  canvasY = winY + canvas.getBoundingClientRect().top;
 
  infoX1 = winX + dragItem1.getBoundingClientRect().left + rad - canvasX;
  infoY1 = winY + dragItem1.getBoundingClientRect().top + rad - canvasY;
  infoX2 = winX + dragItem2.getBoundingClientRect().left + rad - canvasX;
  infoY2 = winY + dragItem2.getBoundingClientRect().top + rad - canvasY;
  infoX3 = winX + dragItem3.getBoundingClientRect().left + rad - canvasX;
  infoY3 = winY + dragItem3.getBoundingClientRect().top + rad - canvasY;

  poly1.setAttribute('points', infoX1+","+infoY1+" "+infoX2+","+infoY2);
  poly2.setAttribute('points', infoX2+","+infoY2+" "+infoX3+","+infoY3);

  measureAngle();
}

//각도 측정 제 2 코사인 법칙
function measureAngle(){
  var c12 = Math.sqrt(Math.pow(infoX1-infoX2, 2) + Math.pow(infoY1-infoY2, 2));
  var c13 = Math.sqrt(Math.pow(infoX1-infoX3, 2) + Math.pow(infoY1-infoY3, 2));
  var c23 = Math.sqrt(Math.pow(infoX2-infoX3, 2) + Math.pow(infoY2-infoY3, 2));
  var angle = Math.acos((c12*c12+c23*c23-c13*c13)/(2*c12*c23));
  var angleDeg = (angle * (180 / Math.PI)).toFixed(2);
  //console.log(angleDeg);
  angleInput.value = angleDeg;
  //document.querySelector("#submitAngle").value = angleDeg;
}


//DOM이 모두 로드 되었을 때
function domReady () {
  //처리할 내용
  console.log('ready');
  window.addEventListener("resize", init, false);
  init();
  
  //object.addEventListener (eventName, function, useCapture);
  //useCapture: Boolean that specifies whether the event needs to be captured or not. One of the following values
  //useCapture(false) -> Register the event handler for the bubbling phase. 
  //useCapture(true) -> Register the event handler for the capturing phase.
  //bubbling phase: 대상에 도달한 후에 이벤트 처리
  //capturing phase: 대상에 도달하기 전에 이벤트 처리
  //드래그 이벤트는 container에 주고 container 안에서 드래그했을 때, 클릭한 원만 드래그 액션이
  //적용될 수 있게 하기 위해서는 bubbling phase가 되야 하기 때문에 uspCapture은 false가 되야한다.

  //모바일 드래그
  container.addEventListener("touchstart", dragStart, false);
  container.addEventListener("touchend", dragEnd, false);
  container.addEventListener("touchmove", drag, false);

  //pc 드래그
  container.addEventListener("mousedown", dragStart, false);
  container.addEventListener("mouseup", dragEnd, false);
  container.addEventListener("mousemove", drag, false);

  //이미지 업로드
  fileInput.addEventListener("change", uploadImg);

}
