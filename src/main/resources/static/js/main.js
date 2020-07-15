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
let formCoordSelection = document.querySelector('#form_coord');

let scoreBoard = [
    document.querySelector('#p1Score'),
    document.querySelector('#p2Score')
];

let player;
let gameName;
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
			console.log("json user " + JSON.stringify(user));
			loginUser(user);
		   
		}
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
			console.log("Json register " + JSON.stringify(user));
			registerUser(user);
			
		}
		evento.preventDefault();
	});
	
	$('form').on('click', '#btn_createGame', function(evento) {
		var session = {
				username : player,
				gameName : ""
		}
		console.log("Json session " + JSON.stringify(session));
		createGame(session);
		evento.preventDefault();
	});
	
	var contador = 0;
	$('form').on('change','select',function() {
		contador ++;
		if (contador === 2){
			$('button').prop("disabled", false);
		}
	});
	
	$('form').on('click','#enviar',function() {
	    containerForm.style.display = "none";
		board.disableAll();
	    //var cellIdNueva = board.getCellId($('#newRow').prop('value'), $('#newColum').prop('value'));
		var movement = { originRow: $('#coordenadas').attr('data-row'), originCol: $('#coordenadas').attr('data-col'), targetRow: $('#newRow').prop('value'), targetCol: $('#newColum').prop('value')};
		sendMove(movement);
		formCoordSelection.reset();
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
	.done(function( data ) {
		 getTurn();
	});
}

function sendMove(movement){
    console.log ("player " +  player);
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
		       alert("Turn for " + player + ". Select piece to move");
		     }
		    
		},
		error : function(e) {
			var json =  "<span class='login100-form-title p-b-21'>" + e.responseText + "</span>";
			$('#checkers').html(json);
			console.log("ERROR : ", e);
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
	buttonLogin.classList.add ('btn');
	buttonLogin.classList.add ('btn-primary');
	buttonLogin.classList.add ('btn-block');
	buttonLogin.setAttribute('id', 'btn_login');
	buttonLogin.setAttribute('name', 'btn_login');
	buttonLogin.setAttribute('type', 'submit');
	buttonLogin.appendChild(document.createTextNode('LOGIN'));
	
	let buttonRegister = document.createElement('button');
	buttonRegister.classList.add ('btn');
	buttonRegister.classList.add ('btn-primary');
	buttonRegister.classList.add ('btn-block');
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
  buttonSubmit.classList.add('btn');
  buttonSubmit.classList.add('btn-primary');
  buttonSubmit.classList.add('btn-block');
  buttonSubmit.setAttribute('id', 'btn_submitLogin');
  buttonSubmit.setAttribute('name', 'btn_submitLogin');
  buttonSubmit.appendChild(document.createTextNode('Submit'));
  submit.appendChild(buttonSubmit);
  
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
	  buttonSubmit.classList.add('btn');
	  buttonSubmit.classList.add('btn-primary');
	  buttonSubmit.classList.add('btn-block');
	  buttonSubmit.setAttribute('id', 'btn_submitRegister');
	  buttonSubmit.setAttribute('name', 'btn_submitRegister');
	  buttonSubmit.appendChild(document.createTextNode('Submit'));
	  submit.appendChild(buttonSubmit);
	  
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
		buttonCreateGame.classList.add ('btn');
		buttonCreateGame.classList.add ('btn-primary');
		buttonCreateGame.classList.add ('btn-block');
		buttonCreateGame.setAttribute('id', 'btn_createGame');
		buttonCreateGame.setAttribute('name', 'btn_createGame');
		buttonCreateGame.setAttribute('type', 'submit');
		buttonCreateGame.appendChild(document.createTextNode('Create Game'));
		
		let buttonOpenGame = document.createElement('button');
		buttonOpenGame.classList.add ('btn');
		buttonOpenGame.classList.add ('btn-primary');
		buttonOpenGame.classList.add ('btn-block');
		buttonOpenGame.setAttribute('id', 'btn_openGame');
		buttonOpenGame.setAttribute('name', 'btn_openGame');
		buttonOpenGame.setAttribute('type', 'submit');
		buttonOpenGame.appendChild(document.createTextNode('Open Game'));
		
		let buttonLogout = document.createElement('button');
		buttonLogout.classList.add ('btn');
		buttonLogout.classList.add ('btn-primary');
		buttonLogout.classList.add ('btn-block');
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
	  player.classList.add('col-sm-3');
	  player.classList.add('col-sm-offset-3');
	  player.classList.add('scoreBoard');
	  
	  let h1 =  document.createElement('h1');
	  
	  let span =  document.createElement('span');
	  span.setAttribute('id', 'player');
	  span.appendChild(document.createTextNode(playerName));
	  
	  h1.appendChild(span);
	  player.appendChild(h1);
	  
	  checkers.insertBefore(player,optionForm);
	  
  }


function startGame(){
    optionForm.style.display = "none";
    containerBoard.removeAttribute('style');
	board = new Board(scoreBoard);
	board.addTable(containerBoard);
	board.addPlayer(player);
	board.addForm(formCoordSelection);
	getBoard();
	
}

function setTurn() {
    board.ready = true;
	board.enableTurn(player);
	//board.addForm(formCoordSelection);
	board.onMark = cellId => {
	  addFormChooseCoordinates(cellId);
	};
}

function addFormChooseCoordinates (cellId) {
	//board.addForm(cellId,formCoordSelection);
	removeSelectCoord();
	board.setCoordenada(cellId, formCoordSelection);
	containerForm.removeAttribute('style');	
}

function removeSelectCoord () {
    let parentCoord = document.getElementById('form_coord');
    console.log ("form_coord " + form_coord);
	let childCoord = document.getElementById('coordenadas');
	console.log ("childCoord " + childCoord);
	parentCoord.removeChild(childCoord);
}

function updateBoard(data) {
   board.doUpdate(data);
}
