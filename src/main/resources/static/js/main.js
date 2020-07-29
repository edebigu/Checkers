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
let boardView;
let numberOfCells;
let view = new View();


$(document).ready(function () {
    containerBoard.style.display = "none";
    containerForm.style.display = "none";

    $('form').on('click', '#btn-start', function (evento) {
        startApp();
        evento.preventDefault();
    });

    $('form').on('click', '#btn_login', function (evento) {
        startLogin();
        evento.preventDefault();
    });

    $('form').on('click', '#btn_register', function (evento) {
        startRegister();
        evento.preventDefault();
    });

    $('form').on('click', '#btn_submitLogin', function (evento) {
        var username = $("#username").val().trim();
        var password = $("#pwd").val().trim();
        if (username != "" && password != "") {
            var user = {
                username: username,
                password: password
            }
            loginUser(user);
        }
        evento.preventDefault();
    });

    $('form').on('click', '#btn_cancelLogin', function (evento) {
        view.addInitGameView();
        evento.preventDefault();
    });

    $('form').on('click', '#btn_submitRegister', function (evento) {
        var username = $("#username").val().trim();
        var password1 = $("#pwd").val().trim();
        var password2 = $("#pwd2").val().trim();
        if (username != "" && password1 != "" && password1 === password2) {
            var user = {
                username: username,
                password: password1
            }
            registerUser(user);

        }
        else {
            alert("Passwords are differents");
            optionForm.reset();
        }
        evento.preventDefault();
    });

    $('form').on('click', '#btn_cancelRegister', function (evento) {
        addInitGameView();
        evento.preventDefault();
    });

    $('form').on('click', '#btn_createGame', function (evento) {
        var session = {
            username: player,
            gameName: "",
        }
        createGame(session);
        evento.preventDefault();
    });

    $('form').on('click', '#btn_logout', function (evento) {
        if (!confirm("Do you really want to quit the game?")) {
            event.preventDefault();
        } else {
            var session = {
                username: player,
                gameName: gameName
            }
            startLogout(session);
            evento.preventDefault();
        }

    });

    var contador = 0;
    $('form').on('change', 'select', function () {
        contador++;
        if (contador === 2) {
            $('button').prop("disabled", false);
            contador = 0;
        }
    });

    $('form').on('click', '#send', function () {
        formCoordSelection.style.display = "none";
        boardView.disableAll();
        var movement = { originRow: $('#selectedCoord').attr('data-row'), originCol: $('#selectedCoord').attr('data-col'), targetRow: $('#newRow').prop('value'), targetCol: $('#newColum').prop('value') };
        sendMove(movement);
        formCoordSelection.reset();
    });

    $('form').on('click', '#cancelCoord', function () {
        formCoordSelection.style.display = "none";
        formCoordSelection.reset();
        setTurn();
    });

    $('form').on('click', '#move', function () {
        optionsGame.style.display = "none";
        getTurn();

    });

    $('form').on('click', '#saveGame', function () {
        var gameToSave = {
            username: player,
            gameName: gameName,
            overwrite: "false"
        }
        saveGame(gameToSave);


    });

    $('form').on('click', '#btn_submitSaveGame', function (evento) {
        var nameToSave = $("#gameName").val().trim();
        var gameToSave = {
            username: player,
            gameName: nameToSave,
            overwrite: "false"
        }
        saveGame(gameToSave);
        evento.preventDefault();
    });

    $('form').on('click', '#btn_cancelSaveGame', function (evento) {
        containerBoard.removeAttribute('style');
        optionForm.style.display = "none";
        optionsGame.removeAttribute('style');
        evento.preventDefault();
    });

    $('form').on('click', '#btn_openGame', function (evento) {
        getGames("Open");
        evento.preventDefault();
    });

    $('form').on('click', '#btn_submitOpenGame', function (evento) {
        var nameToOpen = $("#gameName").val().trim();
        var session = {
            username: player,
            gameName: nameToOpen
        }
        openGame(session);
        evento.preventDefault();
    });


    $('form').on('click', '#btn_cancelOpenGame', function (evento) {
        addCloseGameView();
        evento.preventDefault();
    });

    $('form').on('click', '#closeGame', function () {
        if (confirm("Do you really want to exit the game?")) {
            optionsGame.style.display = "none";
            var session = {
                username: player,
                gameName: gameName,
                closeWithoutSave: "false"
            }
            console.log("session " + JSON.stringify(session));
            closeGame(session);
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
            alert(data.msg);
            optionForm.reset();
            if (data.result === json_result.OK) {
                view.addInitGameView();
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

function getTurn() {
    var apiURL = "http://localhost:8080/game/" + player + "/getTurn";
    var callbacks = {
        successCallback: function (data) {
            setTurn();
        },
        doneCallback: function () {
        }
    };
    sendGetAjax(apiURL, callbacks.successCallback, callbacks.doneCallback);
}

function getBoard() {
    var apiURL = "http://localhost:8080/game/" + player + "/getStatus";
    var callbacks = {
        successCallback: function (data) {
            //updateBoard(data);
            boardView.updateBoard(data);
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
            view.addSaveGameView(data.listGame, typeView);
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
                var session = {
                    username: player,
                    gameName: gameName,
                    closeWithoutSave: "true"
                }
                closeGame(session);
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
    boardView.ready = true;
    boardView.enableAll();
    alert("Click on piece to move");
    boardView.onMark = cellId => {
        let childCoord = document.getElementById('selectedCoord');
        boardView.setCoordenada(cellId, childCoord);
        formCoordSelection.removeAttribute('style');
    };
}

