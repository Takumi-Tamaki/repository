var rootUrl = "/java_s04/api/v1.1/logIn"

$('#logIn').click(function(){

	var myForm = $('#myForm').serialize();
	var id = $('.id').val();
	var pass = $('.pass').val();

	var requestQuery = {
			"id" : id,
			"pass" : pass
	}
	$.ajax({
		type : "POST",
		url : rootUrl,
		dataType: "json",
		contentType: "application/x-www-form-urlencoded",
		data: myForm,
		success: logInCheck
		,error : function(XMLHTTPRequest, textStatus, error){
			alert('エラーです');
		}
	})
})

function logInCheck(data, textStatus, jqXML){
	console.log("返却値",data)
	if(data == true){
		alert('成功です');
	}else{
		alert('だめです');
	}

}