const json_result = {
	OK : '200 OK',
	CREATED : '201 CREATED',
	NO_CONTENT : '204 NO CONTENT',
	BAD_REQUEST : '400 BAD REQUEST',
	UNAUTHORIZED : '401 UNAUTHORIZED',
	NOT_FOUND : '404 NOT FOUND',
	CONFLICT : '409 CONFLICT'

}


let container = document.querySelector('#limiter');
let checkers = document.querySelector('#checkers');
let optionForm = document.querySelector('#optionForm');

let containerBoard = document.querySelector('#gameBoard');
let containerForm = document.querySelector('#boardForm');
let formContainer = document.querySelector('#form_container');
let optionsGame = document.querySelector('#optionsGame');
let formCoordSelection = document.querySelector('#form_coord');

let scoreBoard = [
    document.querySelector('#p1Score'),
    document.querySelector('#p2Score')
];

let player;
let gameName = "";
let board;
let numberOfCells;


$(document).ready(function() {
    containerBoard.style.display = "none";
    containerForm.style.display = "none";

	$('form').on('click', '#btn-start', function(evento) {
		startApp();
		evento.preventDefault();
	});

	$('form').on('click', '#btn_login', function(evento) {
		startLogin();
		evento.preventDefault();
	});

	$('form').on('click', '#btn_register', function(evento) {
		startRegister();
		evento.preventDefault();
	});

	$('form').on('click', '#btn_submitLogin', function(evento) {
		var username = $("#username").val().trim();
		var password = $("#pwd").val().trim();
		if ( username != "" && password != "") {
			var user = {
					username : username,
					password : password
			}
			loginUser(user);  
		}
		 evento.preventDefault();
	});
	
	$('form').on('click', '#btn_cancelLogin', function(evento) {
		addInitialOptions();
		evento.preventDefault();
	});
	
	$('form').on('click', '#btn_submitRegister', function(evento) {
		var username = $("#username").val().trim();
		var password1 = $("#pwd").val().trim();
		var password2 = $("#pwd2").val().trim();
		if ( username != "" && password1 != "" && password1 === password2){
			var user = {
					username : username,
					password : password1
			}
			registerUser(user);
			
		}
		else {
			alert ("Passwords are differents");
			optionForm.reset();
		}
		evento.preventDefault();
	});
	
	$('form').on('click', '#btn_cancelRegister', function(evento) {
		addInitialOptions();
		evento.preventDefault();
	});
	
	$('form').on('click', '#btn_createGame', function(evento) {
		var session = {
				username : player,
				gameName : ""
		}
		createGame(session);
		evento.preventDefault();
	});
	
	$('form').on('click', '#btn_logout', function(evento) {
		if (!confirm("Do you really want to quit the game?")){
    	    event.preventDefault();
    	}else {
    			var session = {
				username : player,
				gameName : gameName
			}
    		startLogout(session);
    		evento.preventDefault();
    	}
		
	});
	
	var contador = 0;
	$('form').on('change','select',function() {
		contador ++;
		if (contador === 2){
			$('button').prop("disabled", false);
		}
	});
	
	$('form').on('click','#send',function() {
	    formCoordSelection.style.display = "none";
		board.disableAll();
		var movement = { originRow: $('#selectedCoord').attr('data-row'), originCol: $('#selectedCoord').attr('data-col'), targetRow: $('#newRow').prop('value'), targetCol: $('#newColum').prop('value')};
		sendMove(movement);
		formCoordSelection.reset();
	});
	
	$('form').on('click','#cancel',function() {
		 containerForm.style.display = "none";
		 formCoordSelection.reset();
		 board.enableAll();
	});
	
	$('form').on('click','#move',function() {
		 optionsGame.style.display = "none";
		 getTurn();
		 
	});
	
	$('form').on('click','#saveGame',function() {
		 optionsGame.style.display = "none";
		 getGames();
		 containerBoard.style.display = "none";
		 optionForm.removeAttribute('style');
		 
	});
	
	$('form').on('click','#btn_submitSaveGame', function(evento) {
	var nameToSave = $("#saveGameName").val().trim();
	var gameToSave = {
		username : player,
		gameName : gameName,
		newGameName: nameToSave,
		overwrite: "false"
	}
		saveGame(gameToSave);
		evento.preventDefault(); 
	});
	
	$('form').on('click','#btn_cancelSaveGame',function(evento) {
		 containerBoard.removeAttribute('style');
	     optionForm.style.display = "none";
		 optionsGame.removeAttribute('style');
		 evento.preventDefault(); 
	});
	
	
	
});



function startApp() {
	$.ajax({
		url : "http://localhost:8080/start",
		type : 'GET',
		processData : false,
		contentType : "application/json",
		success : function(data) {
			addInitialOptions();
			console.log("SUCCESS : ", data)
		},
		error : function(e) {
			var json =  "<span class='login100-form-title p-b-21'>" + e.responseText + "</span>";
			$('#checkers').html(json);
			console.log("ERROR : ", e);
		}
   })
}

function startLogin() {
	$.ajax({
		url : "http://localhost:8080/start/login",
		type : 'GET',
		processData : false,
		contentType : "application/json",
		success : function(data) {
			addLoginForm();
			console.log("SUCCESS : ", data)
		},
		error : function(e) {
			var json =  "<span class='login100-form-title p-b-21'>" + e.responseText + "</span>";
			$('#checkers').html(json);
			console.log("ERROR : ", e);
		}
	})
}

function startRegister() {
	$.ajax({
		url : "http://localhost:8080/start/register",
		type : 'GET',
		processData : false,
		contentType : "application/json",
		success : function(data) {
			addRegisterForm();
			console.log("SUCCESS : ", data)
		},
		error : function(e) {
			var json =  "<span class='login100-form-title p-b-21'>" + e.responseText + "</span>";
			$('#checkers').html(json);
			console.log("ERROR : ", e);
		}
	})

}

function loginUser(user) {
	console.log("Login with user" + JSON.stringify(user));
	$.ajax({
				url : "http://localhost:8080/login",
				type : 'POST',
				data : JSON.stringify(user),
				processData : false,
				contentType : "application/json",
				dataType : 'json',
				success : function(data) {
					alert(data.msg);
					optionForm.reset();
					console.log("SUCCESS : ", data.error + ": " + data.msg);
					if ( data.error === json_result.OK ){
						addUserLogin(data.username);
						addCloseGameOptions();
						player=data.username;
					}
				},
				error : function(e) {
					var json =  "<span class='login100-form-title p-b-21'>" + e.responseText + "</span>";
					$('#checkers').html(json);
					console.log("ERROR : ", e);
				}
			})
}

function registerUser(user) {
	console.log("Register with user" + JSON.stringify(user));
	$.ajax({
			     url : "http://localhost:8080/register",
				type : 'POST',
				data : JSON.stringify(user),
				processData : false,
				contentType : "application/json",
				dataType : 'json',
				success : function(data) {
					console.log("SUCCESS : ", data.error + ": " + data.msg);
					alert(data.msg);
					optionForm.reset();
					if ( data.error === json_result.CREATED ){
						addInitialOptions();
						
					}
				},
				error : function(e) {
					var json =  "<span class='login100-form-title p-b-21'>" + e.responseText + "</span>";
					$('#checkers').html(json);
					console.log("ERROR : ", e);
				}
			})
}

function startLogout(session) {
	$.ajax({
		url : "http://localhost:8080/logout",
		type : 'POST',
		data : JSON.stringify(session),
		processData : false,
		contentType : "application/json",
		dataType : 'json',
		success : function(data) {
		console.log("SUCCESS : ", data);
		    gameName = "";
		    player = "";

		},
		error : function(e) {
			var json =  "<span class='login100-form-title p-b-21'>" + e.responseText + "</span>";
			$('#checkers').html(json);
			console.log("ERROR : ", e);
			console.log("textError: " + e.responseText);
		}
	}).done(function( data ) {
		 addInitialOptions();
		 removeUserLogin();
	});
}

function getTurn(){
 $.ajax({
		url : "http://localhost:8080/game/" + gameName + "/getTurn",
		type : 'GET',
		processData : false,
		contentType : "application/json",
		success : function(data) {
			console.log("SUCCESS : ", data);
		},
		error : function(e) {
			var json =  "<span class='login100-form-title p-b-21'>" + e.responseText + "</span>";
			$('#checkers').html(json);
			console.log("ERROR : ", e);
		}
	}).done(function( data ) {
		 setTurn();
	});
}

function getBoard(){
 $.ajax({
		url : "http://localhost:8080/game/" + gameName + "/getStatus",
		type : 'GET',
		processData : false,
		contentType : "application/json",
		success : function(data) {
			console.log("SUCCESS : ", data);
			updateBoard(data);
		},
		error : function(e) {
			var json =  "<span class='login100-form-title p-b-21'>" + e.responseText + "</span>";
			$('#checkers').html(json);
			console.log("ERROR : ", e);
		}
	}) 
}

function getGames(){
	$.ajax({
		url : "http://localhost:8080/game/getGames/" + player,
		type : 'GET',
		processData : false,
		contentType : "application/json",
		success : function(data) {
			console.log("SUCCESS : ", data);
		},
		error : function(e) {
			var json =  "<span class='login100-form-title p-b-21'>" + e.responseText + "</span>";
			$('#checkers').html(json);
			console.log("ERROR : ", e);
		}
	}).done(function( data ) {
		 addSaveGameView(data.listGame);
	});
}

function sendMove(movement){
	$.ajax({
		url : "http://localhost:8080/game/" + gameName + "/move/" + player,
		type : 'POST',
		data : JSON.stringify(movement),
		processData : false,
		contentType : "application/json",
		dataType : 'json',
		success : function(data) {
		     console.log("SUCCESS : ", data);
		     if (data.error === null){
		       getBoard();
		       optionsGame.removeAttribute('style');
		     }
		     else {
		       if (data.error === "LOST_MESSAGE" || data.error === "LOST_MESSAGE_MACHINE"){
		       	 alert ("Player " + data.username + " lost!!");
		       }
		       else {
		         alert ("Error: movement not allowed");
		         formCoordSelection.reset();
		         setTurn();
		       }
		     }
		    
		},
		error : function(e) {
			var json =  "<span class='login100-form-title p-b-21'>" + e.responseText + "</span>";
			$('#checkers').html(json);
			console.log("ERROR : ", e);
		}
	})
	
}

function createGame(session) {
	$.ajax({
		url : "http://localhost:8080/createGame",
		type : 'POST',
		data : JSON.stringify(session),
		processData : false,
		contentType : "application/json",
		dataType : 'json',
		success : function(data) {
		console.log("SUCCESS : ", data)
		if ( data.error === json_result.CREATED ) {
		    gameName = data.gameName;
			startGame();
		}
		},
		error : function(e) {
			var json =  "<span class='login100-form-title p-b-21'>" + e.responseText + "</span>";
			$('#checkers').html(json);
			console.log("ERROR : ", e);
			console.log("textError: " + e.responseText);
		}
	})
}

function saveGame(gameToSave){
	$.ajax({
		url : "http://localhost:8080/saveGame",
		type : 'POST',
		data : JSON.stringify(gameToSave),
		processData : false,
		contentType : "application/json",
		dataType : 'json',
		success : function(data) {
			console.log("SUCCESS : ", data)
			if ( data.error === json_result.CREATED ){
				gameName = gameToSave.newGameName;
				containerBoard.removeAttribute('style');
				optionForm.style.display = "none";
		        optionsGame.removeAttribute('style');
				
			}
			else if (data.error === json_result.CONFLICT)
			{
				if (confirm("Do you really want to overwrite the game?")) {
					gameToSave.overwrite = "true";
					saveGame(gameToSave);
				}
			}
		},
		error : function(e) {
			var json =  "<span class='login100-form-title p-b-21'>" + e.responseText + "</span>";
			$('#checkers').html(json);
			console.log("ERROR : ", e);
			console.log("textError: " + e.responseText);
		}
	})

}

function removeChilds(container){
	while (container.firstChild) {
		container.removeChild(container.firstChild);
	}
}

function addInitialOptions() {
	
	let title = document.getElementById("title");
	title.textContent = "Choose one option";
	
	let options = document.getElementById('options');
	
	let buttonLogin = document.createElement('button');
	buttonLogin.setAttribute('class', 'btn btn-primary btn-block');
	buttonLogin.setAttribute('id', 'btn_login');
	buttonLogin.setAttribute('name', 'btn_login');
	buttonLogin.setAttribute('type', 'submit');
	buttonLogin.appendChild(document.createTextNode('LOGIN'));
	
	let buttonRegister = document.createElement('button');
	buttonRegister.setAttribute('class', 'btn btn-primary btn-block');
	buttonRegister.setAttribute('id', 'btn_register');
	buttonRegister.setAttribute('name', 'btn_register');
	buttonRegister.setAttribute('type', 'submit');
	buttonRegister.appendChild(document.createTextNode('Register'));
	
	removeChilds(options);

	options.appendChild(buttonLogin);
	options.appendChild(buttonRegister);
	optionForm.appendChild(options);
}



 
  function addLoginForm() { 
	  
	  let title = document.getElementById("title");
	  title.textContent = "Login Form";
			
	  let options = document.getElementById('options');
	
	
	  let userName = document.createElement('div');
	  userName.classList.add('form-group');
	  
	  let inputUserName = document.createElement('input'); 
	  inputUserName.classList.add('form-control');
	  inputUserName.setAttribute('type', 'text'); 
	  inputUserName.setAttribute('id','username'); 
	  inputUserName.setAttribute('name', 'username');
	  inputUserName.setAttribute('placeholder', 'User name');
	  userName.appendChild(inputUserName);
	  
	  let password = document.createElement('div');
	  password.classList.add('form-group');
	  
	  let inputPassword = document.createElement('input');
	  inputPassword.classList.add('form-control');
	  inputPassword.setAttribute('type', 'password');
	  inputPassword.setAttribute('id', 'pwd'); 
	  inputPassword.setAttribute('name','pwd'); 
	  inputPassword.setAttribute('placeholder', 'Password');
	  password.appendChild(inputPassword);
	  
	  let submit = document.createElement ('div');
	  submit.classList.add('form-group');
	  
	  let buttonSubmit= document.createElement('button');
	  buttonSubmit.setAttribute('type', 'submit');
	  buttonSubmit.setAttribute('class', 'btn btn-primary btn-block');
	  buttonSubmit.setAttribute('id', 'btn_submitLogin');
	  buttonSubmit.setAttribute('name', 'btn_submitLogin');
	  buttonSubmit.appendChild(document.createTextNode('Submit'));
	  submit.appendChild(buttonSubmit);
	  
	  let buttonCancelLogint= document.createElement('button');
	  buttonCancelLogint.setAttribute('type', 'submit');
	  buttonCancelLogint.setAttribute('class', 'btn btn-primary btn-block');
	  buttonCancelLogint.setAttribute('id', 'btn_cancelLogin');
	  buttonCancelLogint.setAttribute('name', 'btn_cancelLogin');
	  buttonCancelLogint.appendChild(document.createTextNode('Cancel'));
	  submit.appendChild(buttonCancelLogint);
	  
	  removeChilds(options);
	
	  options.appendChild(userName);
	  options.appendChild(password);
	  options.appendChild(submit);
	  optionForm.appendChild(options);
  
 }
  
  function addRegisterForm() {
	  
	  let title = document.getElementById("title");
	  title.textContent = "Login Form";
			
	  let options = document.getElementById('options');


	  let userName = document.createElement('div');
	  userName.classList.add('form-group');
	  
	  let inputUserName = document.createElement('input'); 
	  inputUserName.classList.add('form-control');
	  inputUserName.setAttribute('type', 'text'); 
	  inputUserName.setAttribute('id','username'); 
	  inputUserName.setAttribute('name', 'username');
	  inputUserName.setAttribute('placeholder', 'User name');
	  userName.appendChild(inputUserName);
	  
	  let password = document.createElement('div');
	  password.classList.add('form-group');
	  
	  let inputPassword = document.createElement('input');
	  inputPassword.classList.add('form-control');
	  inputPassword.setAttribute('type', 'password');
	  inputPassword.setAttribute('id', 'pwd'); 
	  inputPassword.setAttribute('name','pwd'); 
	  inputPassword.setAttribute('placeholder', 'Password');
	  password.appendChild(inputPassword);
	  
	  let password2 = document.createElement('div');
	  password2.classList.add('form-group');
	  
	  let inputPassword2 = document.createElement('input');
	  inputPassword2.classList.add('form-control');
	  inputPassword2.setAttribute('type', 'password');
	  inputPassword2.setAttribute('id', 'pwd2'); 
	  inputPassword2.setAttribute('name','pwd2'); 
	  inputPassword2.setAttribute('placeholder', 'Password');
	  password2.appendChild(inputPassword2);
	  
	  let submit = document.createElement ('div');
	  submit.classList.add('form-group');
	  
	  let buttonSubmit= document.createElement('button');
	  buttonSubmit.setAttribute('type', 'submit');
	  buttonSubmit.setAttribute('class', 'btn btn-primary btn-block');
	  buttonSubmit.setAttribute('id', 'btn_submitRegister');
	  buttonSubmit.setAttribute('name', 'btn_submitRegister');
	  buttonSubmit.appendChild(document.createTextNode('Submit'));
	  submit.appendChild(buttonSubmit);
	  
	  let buttonCancel= document.createElement('button');
	  buttonCancel.setAttribute('type', 'submit');
	  buttonCancel.setAttribute('class', 'btn btn-primary btn-block');
	  buttonCancel.setAttribute('id', 'btn_cancelRegister');
	  buttonCancel.setAttribute('name', 'btn_registerCancel');
	  buttonCancel.appendChild(document.createTextNode('Cancel'));
	  submit.appendChild(buttonCancel);
	  
	  removeChilds(options);

	  options.appendChild(userName);
	  options.appendChild(password);
	  options.appendChild(password2);
	  options.appendChild(submit);
	  optionForm.appendChild(options);

	 }
 
  function addCloseGameOptions() {
		
		let title = document.getElementById("title");
		title.textContent = "Choose one option";
		
		let options = document.getElementById('options');
		
		let buttonCreateGame = document.createElement('button');
		buttonCreateGame.setAttribute('class', 'btn btn-primary btn-block');
		buttonCreateGame.setAttribute('id', 'btn_createGame');
		buttonCreateGame.setAttribute('name', 'btn_createGame');
		buttonCreateGame.setAttribute('type', 'submit');
		buttonCreateGame.appendChild(document.createTextNode('Create Game'));
		
		let buttonOpenGame = document.createElement('button');
		buttonOpenGame.setAttribute('class', 'btn btn-primary btn-block');
		buttonOpenGame.setAttribute('id', 'btn_openGame');
		buttonOpenGame.setAttribute('name', 'btn_openGame');
		buttonOpenGame.setAttribute('type', 'submit');
		buttonOpenGame.appendChild(document.createTextNode('Open Game'));
		
		let buttonLogout = document.createElement('button');
		buttonLogout.setAttribute('class', 'btn btn-primary btn-block');
		buttonLogout.setAttribute('id', 'btn_logout');
		buttonLogout.setAttribute('name', 'btn_logout');
		buttonLogout.setAttribute('type', 'submit');
		buttonLogout.appendChild(document.createTextNode('Logout'));
		
		removeChilds(options);

		options.appendChild(buttonCreateGame);
		options.appendChild(buttonOpenGame);
		options.appendChild(buttonLogout);
		optionForm.appendChild(options);
	}
  
  function addUserLogin(playerName){
	  
	  let player = document.createElement('div');
	  player.setAttribute('id', 'playerContent');
	  player.setAttribute('class', 'col-sm-3 col-sm-offset-3 user');

	  let h1 =  document.createElement('h1');
	  
	  let span =  document.createElement('span');
	  span.setAttribute('id', 'player');
	  span.appendChild(document.createTextNode(playerName));
	  
	  h1.appendChild(span);
	  player.appendChild(h1);
	  
	  checkers.insertBefore(player,optionForm);
	  
  }
  
  function addSaveGameView(listGames){
  	let options = document.getElementById('options');
	removeChilds(options);
  	
  	for (let i = 0; i < listGames.length; i++) {
  	  	let inputGroup = document.createElement('div');
  		inputGroup.classList.add('input-group');
  		let input = document.createElement('input');
  		input.setAttribute('class', 'form-control');
  		input.setAttribute('type', 'text');
  		input.setAttribute('name', 'game'+i);
  		input.setAttribute('id', 'game'+i);
  		input.setAttribute('value', listGames[i]);
  		input.setAttribute('disabled', true);
  		inputGroup.appendChild(input);
  		options.appendChild(inputGroup);
  	}
      let saveGameName = document.createElement('div');
	  saveGameName.classList.add('form-group');
  	  let inputName = document.createElement('input');
	  inputName.classList.add('form-control');
	  inputName.setAttribute('type', 'text');
	  inputName.setAttribute('id', 'saveGameName'); 
	  inputName.setAttribute('name','saveGameName'); 
	  inputName.setAttribute('placeholder', 'Enter name');
	  saveGameName.appendChild(inputName);
	  
	  let submit = document.createElement ('div');
	  submit.classList.add('form-group');
	  
	  let buttonSendSaveGame= document.createElement('button');
	  buttonSendSaveGame.setAttribute('type', 'submit');
	  buttonSendSaveGame.setAttribute('class', 'btn btn-primary btn-block');
	  buttonSendSaveGame.setAttribute('id', 'btn_submitSaveGame');
	  buttonSendSaveGame.setAttribute('name', 'btn_submitSaveGame');
	  buttonSendSaveGame.appendChild(document.createTextNode('Submit'));
	  submit.appendChild(buttonSendSaveGame);
	  
	  let buttonCancel= document.createElement('button');
	  buttonCancel.setAttribute('type', 'submit');
	  buttonCancel.setAttribute('class', 'btn btn-primary btn-block');
	  buttonCancel.setAttribute('id', 'btn_cancelSaveGame');
	  buttonCancel.setAttribute('name', 'btn_cancelSaveGame');
	  buttonCancel.appendChild(document.createTextNode('Cancel'));
	  submit.appendChild(buttonCancel);
	  

  	 
  	  options.appendChild(saveGameName);
  	  options.appendChild(buttonSendSaveGame);
  	  options.appendChild(buttonCancel);
  	
  	 
  }
  
  function removeUserLogin(){
    let parent = document.getElementById('checkers');
	let childPlayer = document.getElementById('playerContent');
	parent.removeChild(childPlayer);
  }


function startGame(){
    containerForm.removeAttribute('style');
    containerBoard.removeAttribute('style');
    formContainer.removeAttribute('style');
    formCoordSelection.style.display = "none";
	board = new Board(scoreBoard);
	board.addTable(containerBoard);
	board.addPlayer(player);
	board.addOptionsGame(optionsGame);
	board.addForm(formCoordSelection);
	getBoard();
	
}

function setTurn() {
    board.ready = true;
	board.enableAll();
	board.onMark = cellId => {
	  addFormChooseCoordinates(cellId);
	};
}

function addFormChooseCoordinates (cellId) {
	let parentCoord = document.getElementById('form_coord');
	let childCoord = document.getElementById('selectedCoord');
	board.setCoordenada(cellId, childCoord);
	formCoordSelection.removeAttribute('style');	
}

function updateBoard(data) {
   board.doUpdate(data);
}
