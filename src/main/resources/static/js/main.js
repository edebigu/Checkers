let joinForm = document.querySelector('#joinForm');

$(document).ready(function () {

   $("#login").submit(function(evento){
    var username = $("#username").val().trim();
    var password = $("#pass").val().trim();
    if( username != "" && password != "" ){
        alert("loggin");
    }

    else{
        alert("Vacio");
    }

    if( username != "" && password != "" ){
    	var user = {
    			username: username,
    			password: password
    	}
    	console.log ("json user " +  JSON.stringify(user));
        login(user);
        joinForm.style.display = "none";
    	evento.preventDefault();
    	

    }

   });


});


function login(user)
{
	console.log ("Login with user" + JSON.stringify(user));
	$.ajax({
        url: "http://localhost:8080/login",
        type: 'POST',
        data: JSON.stringify(user),
		 processData: false,
		 contentType: "application/json",
		 success: function (data) {
			var json = "<span class='login100-form-title p-b-41'> Login Response </span><form class='login100-form validate-form p-b-33 p-t-5' id='login'>" +  JSON.stringify(data,null, 4) + "</form>";
			 //var json = "<h4>Ajax Response</h4><pre>" + JSON.stringify(data,null, 4) + "</pre>";
			 $('#feedback').html(json);
			 console.log("SUCCESS : ", data) },
			error: function (e) {
			 var json = "<span class='login100-form-title p-b-41'> Login Response </span><form class='login100-form validate-form p-b-33 p-t-5' id='login'>" +   e.responseText + "</form>";
			 //var json = "<h4>Ajax Response</h4><pre>" + e.responseText + "</pre>";
			 $('#feedback').html(json);
			 console.log("ERROR : ", e); }
	})
}

	 

