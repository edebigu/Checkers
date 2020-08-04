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
let openGameView;
let numberOfCells;
let view = new Views();
let dto = new Dto();


$(document).ready(function () {
    containerBoard.style.display = "none";
    containerForm.style.display = "none";

    $('form').on('click', '#btn-start', function (event) {
        startApp();
        event.preventDefault();
    });

    $('form').on('click', '#btn_login', function (event) {
        startLogin();
        event.preventDefault();
    });

    $('form').on('click', '#btn_register', function (event) {
        startRegister();
        event.preventDefault();
    });

    $('form').on('click', '#btn_submitLogin', function (event) {
        var username = $("#username").val().trim();
        var password = $("#pwd").val().trim();
        if (username != "" && password != "") {
            dto.playerDto(username, password);
            loginUser(dto.json);
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
            registerUser(dto.json);
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
        createGame(dto.json);
        event.preventDefault();
    });

    $('form').on('click', '#btn_logout', function (event) {
        if (confirm("Do you really want to quit the game?")) {
            dto.sessionDto(player, gameName);
            startLogout(dto.json);
        }
        event.preventDefault();

    });

    var counter = 0;
    $('form').on('change', 'select', function () {
        counter++;
        if (counter === 2) {
            $('button').prop("disabled", false);
            counter = 0;
        }
    });

    $('form').on('click', '#send', function () {
        formCoordSelection.style.display = "none";
        openGameView.disableAll();
        dto.moveDto($('#selectedCoord').attr('data-row'), $('#selectedCoord').attr('data-col'), $('#newRow').prop('value'), $('#newColum').prop('value'));
        sendMove(dto.json);
        formCoordSelection.reset();
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
        saveGame(dto.json);
    });

    $('form').on('click', '#btn_submitSaveGame', function (event) {
        var nameToSave = $("#gameName").val().trim();
        dto.saveGameDto(player, nameToSave, "false");
        saveGame(dto.json);
        event.preventDefault();
    });

    $('form').on('click', '#btn_cancelSaveGame', function (event) {
        containerBoard.removeAttribute('style');
        optionForm.style.display = "none";
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
        openGame(dto.json);
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
            closeGame(dto.json);
        }
    });
});

function startApp() {
    var apiURL = "http://localhost:8080/start";
    var callbacks = {
        successCallback: function (data) {
            view.addInitGameView();
        },
        doneCallback: function () {
        }
    };

    sendGetAjax(apiURL, callbacks.successCallback, callbacks.doneCallback);
}

function startLogin() {
    var apiURL = "http://localhost:8080/start/login";
    var callbacks = {
        successCallback: function (data) {
            view.addLoginView();
        },
        doneCallback: function () {
        }
    };
    sendGetAjax(apiURL, callbacks.successCallback, callbacks.doneCallback);
}

function startRegister() {
    var apiURL = "http://localhost:8080/start/register";
    var callbacks = {
        successCallback: function (data) {
            view.addRegisterView();
        },
        doneCallback: function () {
        }
    };
    sendGetAjax(apiURL, callbacks.successCallback, callbacks.doneCallback);
}

function loginUser(user) {
    var apiURL = "http://localhost:8080/login";
    var callbacks = {
        successCallback: function (data) {
            console.log ("user login: " + user);
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
        doneCallback: function () {
            optionForm.reset();
        }
    }

    sendPostAjax(user, apiURL, callbacks.successCallback, callbacks.doneCallback);
}

function registerUser(user) {
    var apiURL = "http://localhost:8080/register";
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
        doneCallback: function () { }
    }

    sendPostAjax(user, apiURL, callbacks.successCallback, callbacks.doneCallback);
}

function startLogout(session) {
    var apiURL = "http://localhost:8080/logout";
    var callbacks = {
        successCallback: function (data) {
            gameName = "";
            player = "";
        },
        doneCallback: function () {
            view.addInitGameView();
            view.removeUserLogin();
        }
    }
    sendPostAjax(session, apiURL, callbacks.successCallback, callbacks.doneCallback);

}

function getBoard() {
    var apiURL = "http://localhost:8080/game/" + player + "/getStatus";
    var callbacks = {
        successCallback: function (data) {
            openGameView.updateBoard(data);
        },
        doneCallback: function () {
        }
    };
    sendGetAjax(apiURL, callbacks.successCallback, callbacks.doneCallback);
}

function getGames(typeView) {
    var apiURL = "http://localhost:8080/game/getGames/" + player;
    var callbacks = {
        successCallback: function (data) {
            view.addOpenSaveGameView(data.listGame, typeView);
        },
        doneCallback: function () {
        }
    };
    sendGetAjax(apiURL, callbacks.successCallback, callbacks.doneCallback);
}

function sendMove(movement) {
    var apiURL = "http://localhost:8080/game/move/" + player
    var callbacks = {
        successCallback: function (data) {
            if (data.result === "LOST_MESSAGE" || data.result === "LOST_MESSAGE_MACHINE") {
                alert("Player " + data.username + " lost!!");
                /*var session = {
                    username: player,
                    gameName: gameName,
                    closeWithoutSave: "true"
                }*/
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
        doneCallback: function () {
        }
    }

    sendPostAjax(movement, apiURL, callbacks.successCallback, callbacks.doneCallback);

}

function createGame(session) {
    var apiURL = "http://localhost:8080/createGame";
    var callbacks = {
        successCallback: function (data) {
            if (data.result === json_result.OK) {
                gameName = "";
                view.addStartGameView();
            }
        },
        doneCallback: function () {
        }
    }

    sendPostAjax(session, apiURL, callbacks.successCallback, callbacks.doneCallback);
}

function saveGame(gameToSave) {
    var apiURL = "http://localhost:8080/saveGame";
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
                        gameToSave.overwrite = "true";
                        saveGame(gameToSave);
                    }
                    break;

                case json_result.OK:
                    gameName = gameToSave.gameName;
                    containerBoard.removeAttribute('style');
                    optionForm.style.display = "none";
                    optionsGame.removeAttribute('style');
                    break;

                default:

            }
        },
        doneCallback: function () {
        }
    }

    sendPostAjax(gameToSave, apiURL, callbacks.successCallback, callbacks.doneCallback);

}

function openGame(session) {
    var apiURL = "http://localhost:8080/openGame/";
    var callbacks = {
        successCallback: function (data) {
            if (data.result === json_result.OK) {
                gameName = session.gameName;
                optionForm.style.display = "none";
                view.addStartGameView();

            }
            else {
                alert("Game not exist!!!!")
                optionForm.reset();
            }
        },
        doneCallback: function () {
        }
    }

    sendPostAjax(session, apiURL, callbacks.successCallback, callbacks.doneCallback);

}

function closeGame(session) {
    var apiURL = "http://localhost:8080/closeGame/";
    var callbacks = {
        successCallback: function (data) {
            if (data.result === json_result.NOT_FOUND) {
                if (!confirm("Do you want close game without saved?")) {
                    getGames("Save");
                }
                else {
                    session.closeWithoutSave = "true";
                    closeGame(session);
                }
            }
            else {
                view.addCloseGameView();
                removeGame();
            }
        },
        doneCallback: function () {
            containerBoard.style.display = "none";
            optionForm.removeAttribute('style');
        }
    }

    sendPostAjax(session, apiURL, callbacks.successCallback, callbacks.doneCallback);

}

function sendGetAjax(apiURL, successCallback, doneCallback) {
    $.ajax({
        url: apiURL,
        type: 'GET',
        contentType: "application/json",
        success: function (data) {
            console.log("SUCCESS : ", data);
            successCallback(data);
        },
        error: function (e) {
            error(apiURL);
        }
    })
}

function sendPostAjax(dataToSend, apiURL, successCallback, doneCallback) {

    $.ajax({
        url: apiURL,
        type: 'POST',
        data: JSON.stringify(dataToSend),
        contentType: "application/json",
        dataType: 'json',
        success: function (data) {
            console.log("SUCCESS : ", data);
            successCallback(data);
        },
        error: function (e) {
            error(apiURL);
        }
    }).done(function () {
        doneCallback();
    });

}

function error(url) {
    var json = "<span class='login100-form-title p-b-21 colorBlue'>Can not get resource " + url + "</span>";
    $('#checkers').html(json);
}

function removeGame() {
    view.removeChilds(containerBoard);
    view.removeChilds(optionsGame);
    view.removeChilds(formCoordSelection);
}

function setTurn() {
    openGameView.ready = true;
    openGameView.enableAll();
    alert("Click on piece to move");
    openGameView.onMark = cellId => {
        let childCoord = document.getElementById('selectedCoord');
        openGameView.setCoordenada(cellId, childCoord);
        formCoordSelection.removeAttribute('style');
    };
}

