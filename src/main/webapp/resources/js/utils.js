var utils = (function(){
	//빈값인 지 확인
	function isEmptyCheck(val, type){
		if(!val || val == null){
			alert(type + "을(를) 적어주세요.");
			return false;
		}
		return true;
	}
	
	//공백 여부 체크
	function isSpaceCheck(val1, val2, type){
		if(val1 != val2){
			alert(type + "의 공백을 제거해주세요.");
			return false;
		}
		return true;
	}
	
	//이메일 체크
	function validateEmail(email) {
	  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	  if(!re.test(email)){
		  alert("올바른 이메일을 적어주세요.");
		  return false;
	  }
	  return true;
	}
	
	//재전송 방지
	function reSubmitPrevent(){
		$(document).keydown(function (e) {
			
		    /* F5 번키 막음. */
		    if(e.keyCode == 116)
		    {
		    	var len = $("form").find(".warning").length;
				if(len > 0){
					e.keyCode = 0;
			        alert("경고문구를 확인해주세요.");
			        return false;
				}
		        
		    }
            
		});
	}
	
	return {
		isEmptyCheck: isEmptyCheck,
		isSpaceCheck: isSpaceCheck,
		validateEmail: validateEmail,
		reSubmitPrevent: reSubmitPrevent
	};
})();