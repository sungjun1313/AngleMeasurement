$(document).ready(function(){
	var pagenationForm = $("#pagenationForm");
	var restBody = $("#restBody");
	
	$(".pageinate_button a").on("click", function(e){
		e.preventDefault();
		
		pagenationForm.append("<input type='hidden' name='anglePage' value='" + $(this).attr("href") + "' />");
		pagenationForm.attr("action", "/angle/detail/"+restBody.data("bno"));
		pagenationForm.submit();
	});
});