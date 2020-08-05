const json_result = {
    OK: 'OK',
    NO_CONTENT: 'NO_CONTENT',
    BAD_REQUEST: 'BAD_REQUEST',
    UNAUTHORIZED: 'UNAUTHORIZED',
    NOT_FOUND: 'NOT_FOUND',
    CONFLICT: 'CONFLICT'

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
let playGameView;
let numberOfCells;
let view = new View();
let dto = new Dto();
let service = new Service();


$(document).ready(function () {
    containerBoard.style.display = "none";
    containerForm.style.display = "none";

    $('form').on('click', '#btn-start', function (event) {
        start();
        event.preventDefault();
    });

    $('form').on('click', '#btn_login', function (event) {
        view.addLoginView();
        event.preventDefault();
    });

    $('form').on('click', '#btn_register', function (event) {
        view.addRegisterView();
        event.preventDefault();
    });

    $('form').on('click', '#btn_submitLogin', function (event) {
        var username = $("#username").val().trim();
        var password = $("#pwd").val().trim();
        if (username != "" && password != "") {
            dto.playerDto(username, password);
            login();
        }
        event.preventDefault();
    });

    $('form').on('click', '#btn_cancelLogin', function (event) {
        view.addInitGameView();
        event.preventDefault();
    });

    $('form').on('click', '#btn_submitRegister', function (event) {
        var username = $("#username").val().trim();
        var password1 = $("#pwd").val().trim();
        var password2 = $("#pwd2").val().trim();
        if (username != "" && password1 != "" && password1 === password2) {
            dto.playerDto(username, password1);
            register();
        }
        else {
            alert("Please enter user and same passwords!!");
            optionForm.reset();
        }
        event.preventDefault();
    });

    $('form').on('click', '#btn_cancelRegister', function (event) {
        view.addInitGameView();
        event.preventDefault();
    });

    $('form').on('click', '#btn_createGame', function (event) {
        dto.sessionDto(player, "");
        createGame();
        event.preventDefault();
    });

    $('form').on('click', '#btn_logout', function (event) {
        if (confirm("Do you really want to quit the game?")) {
            dto.sessionDto(player, gameName);
            logout();
        }
        event.preventDefault();

    });

    var counter = 0;
    $('form').on('change', 'select', function () {
        counter++;
        console.log("counter " + counter);
        if (counter === 2) {
            $('button').prop("disabled", false);
            counter = 0;
        }
    });

    $('form').on('click', '#send', function () {
        formCoordSelection.style.display = "none";
        playGameView.disableAll();
        dto.moveDto($('#selectedCoord').attr('data-row'), $('#selectedCoord').attr('data-col'), $('#newRow').prop('value'), $('#newColum').prop('value'));
        move();
        formCoordSelection.reset();
        document.getElementById('send').setAttribute('disabled', true);
    });

    $('form').on('click', '#cancelCoord', function () {
        formCoordSelection.style.display = "none";
        formCoordSelection.reset();
        setTurn();
    });

    $('form').on('click', '#move', function () {
        optionsGame.style.display = "none";
        setTurn();

    });

    $('form').on('click', '#saveGame', function () {
        dto.saveGameDto(player, gameName, "false");
        saveGame();
    });

    $('form').on('click', '#btn_submitSaveGame', function (event) {
        var nameToSave = $("#gameName").val().trim();
        dto.saveGameDto(player, nameToSave, "false");
        saveGame();
        event.preventDefault();
    });

    $('form').on('click', '#btn_cancelSaveGame', function (event) {
        optionForm.style.display = "none";
        containerBoard.removeAttribute('style');
        optionsGame.removeAttribute('style');
        event.preventDefault();
    });

    $('form').on('click', '#btn_openGame', function (event) {
        getGames("Open");
        event.preventDefault();
    });

    $('form').on('click', '#btn_submitOpenGame', function (event) {
        var nameToOpen = $("#gameName").val().trim();
        dto.sessionDto(player, nameToOpen);
        openGame();
        event.preventDefault();
    });


    $('form').on('click', '#btn_cancelOpenGame', function (event) {
        view.addCloseGameView();
        event.preventDefault();
    });

    $('form').on('click', '#closeGame', function () {
        if (confirm("Do you really want to exit the game?")) {
            optionsGame.style.display = "none";
            dto.closeGameDto(player, gameName, "false");
            closeGame();
        }
    });
});

function start() {
   var callbacks = {
        successCallback: function (data) {
            view.addInitGameView();
        },
        errorCallback: function (errorUrl) {
        	view.addError(errorUrl);
        },
        doneCallback: function () {
        }
    };
    service.start(callbacks.successCallback, callbacks.errorCallback, callbacks.doneCallback);

}

function login() {
    var callbacks = {
        successCallback: function (data) {
            optionForm.reset();
            if (data.result === json_result.OK) {
                alert("Login success!!");
                view.addUserLogin(data.username);
                view.addCloseGameView();
                player = data.username;
            }
            else {
                alert("Login unsuccess");
            }
        },
        errorCallback: function (errorUrl) {
        	view.addError(errorUrl);
        },
        doneCallback: function () {
            optionForm.reset();
        }
    }

    service.login(dto.json, callbacks.successCallback,callbacks.errorCallback, callbacks.doneCallback);
}

function register() {
    var callbacks = {
        successCallback: function (data) {
            optionForm.reset();
            if (data.result === json_result.OK) {
                view.addInitGameView();
                alert("Register success!!");
            }
            else {
            	alert("Register unsuccess!! User exist");
            }
        },
        errorCallback: function (errorUrl) {
        	view.addError(errorUrl);
        },
        doneCallback: function () { }
    }

    service.register(dto.json, callbacks.successCallback,callbacks.errorCallback, callbacks.doneCallback);
}

function logout() {
    var callbacks = {
        successCallback: function (data) {
            gameName = "";
            player = "";
        },
        errorCallback: function (errorUrl) {
        	view.addError(errorUrl);
        },
        doneCallback: function () {
            view.addInitGameView();
            view.removeUserLogin();
        }
    }
     service.logout(dto.json, callbacks.successCallback,callbacks.errorCallback, callbacks.doneCallback);
}

function getBoard() {
    var callbacks = {
        successCallback: function (data) {
            playGameView.updateBoard(data);
        },
        errorCallback: function (errorUrl) {
        	view.addError(errorUrl);
        },
        doneCallback: function () {
        }
    };
     service.getBoard(player,callbacks.successCallback,callbacks.errorCallback, callbacks.doneCallback);
}

function getGames(typeView) {
     var callbacks = {
        successCallback: function (data) {
            view.addOpenSaveGameView(data.listGame, typeView);
        },
        errorCallback: function (errorUrl) {
        	view.addError(errorUrl);
        },
        doneCallback: function () {
        }
    };
    service.getGames(player,callbacks.successCallback,callbacks.errorCallback, callbacks.doneCallback);
}

function move() {
    var callbacks = {
        successCallback: function (data) {
            if (data.result === "LOST_MESSAGE" || data.result === "LOST_MESSAGE_MACHINE") {
                alert("Player " + data.username + " lost!!");
                dto.closeGameDto(player,gameName, "true");
                closeGame(dto.json);
                view.addCloseGameView();
                containerBoard.style.display = "none";
            }
            else {
                if (data.result !== null) {
                    alert("Error: movement not allowed");
                }
                getBoard();
                optionsGame.removeAttribute('style');

            }
        },
        errorCallback: function (errorUrl) {
        	view.addError(errorUrl);
        },
        doneCallback: function () {
        }
    }
     service.move(dto.json,player,callbacks.successCallback,callbacks.errorCallback, callbacks.doneCallback);
}

function createGame() {
    var callbacks = {
        successCallback: function (data) {
            if (data.result === json_result.OK) {
                gameName = "";
                optionForm.style.display = "none";
                view.addStartGameView();
            }
        },
        errorCallback: function (errorUrl) {
        	view.addError(errorUrl);
        },
        doneCallback: function () {
        }
    }
	service.createGame(dto.json,callbacks.successCallback,callbacks.errorCallback, callbacks.doneCallback);
}

function saveGame() {
    var callbacks = {
        successCallback: function (data) {
            switch (data.result) {
                case json_result.NOT_FOUND:
                    optionsGame.style.display = "none";
                    getGames("Save");
                    containerBoard.style.display = "none";
                    optionForm.removeAttribute('style');
                    break;

                case json_result.CONFLICT:
                    optionsGame.style.display = "none";
                    if (confirm("Do you really want to overwrite the game?")) {
                        dto.json.overwrite = "true";
                        saveGame(dto.json);
                    }
                    break;

                case json_result.OK:
                    gameName = dto.json.gameName;
                    containerBoard.removeAttribute('style');
                    optionForm.style.display = "none";
                    optionsGame.removeAttribute('style');
                    break;

                default:
            }
        },
        errorCallback: function (errorUrl) {
        	view.addError(errorUrl);
        },
        doneCallback: function () {
        }
    }
    service.saveGame(dto.json,callbacks.successCallback,callbacks.errorCallback, callbacks.doneCallback);
}

function openGame() {
    var callbacks = {
        successCallback: function (data) {
            if (data.result === json_result.OK) {
                gameName = dto.json.gameName;
                optionForm.style.display = "none";
                view.addStartGameView();

            }
            else {
                alert("Game not exist!!!!")
                optionForm.reset();
            }
        },
        errorCallback: function (errorUrl) {
        	view.addError(errorUrl);
        },
        doneCallback: function () {
        }
    }
   service.openGame(dto.json,callbacks.successCallback,callbacks.errorCallback, callbacks.doneCallback);
}

function closeGame() {
    var callbacks = {
        successCallback: function (data) {
            if (data.result === json_result.NOT_FOUND) {
                if (!confirm("Do you want close game without saved?")) {
                    getGames("Save");
                }
                else {
                    dto.json.closeWithoutSave = "true";
                    closeGame();
                }
            }
            else {
                view.addCloseGameView();
                view.removeGame(containerBoard, optionsGame, formCoordSelection);
            }
        },
        errorCallback: function (errorUrl) {
        	view.addError(errorUrl);
        },
        doneCallback: function () {
           containerBoard.style.display = "none";
           optionForm.removeAttribute('style');
        }
    }
     service.closeGame(dto.json,callbacks.successCallback,callbacks.errorCallback, callbacks.doneCallback);
            
}

function setTurn() {
    playGameView.ready = true;
    playGameView.enableAll();
    alert("Click on piece to move");
    playGameView.onMark = cellId => {
        let childCoord = document.getElementById('selectedCoord');
        playGameView.setCoordenada(cellId, childCoord);
        formCoordSelection.removeAttribute('style');
    };
}

