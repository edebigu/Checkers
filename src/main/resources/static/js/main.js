const json_result = {
    OK: 'OK',
    CREATED: 'CREATED',
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
let gameView;
let numberOfCells;


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
        addInitGameView();
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
        gameView.disableAll();
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
            addInitGameView();
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
            addLoginForm();
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
            addRegisterForm();
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
            alert(data.msg);
            optionForm.reset();
            if (data.error === json_result.OK) {
                addUserLogin(data.username);
                addCloseGameView();
                player = data.username;
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
            if (data.error === json_result.CREATED) {
                addInitGameView();
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
            addInitGameView();
            removeUserLogin();
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
            updateBoard(data);
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
            addSaveGameView(data.listGame, typeView);
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
            if (data.error === "LOST_MESSAGE" || data.error === "LOST_MESSAGE_MACHINE") {
                alert("Player " + data.username + " lost!!");
                var session = {
                    username: player,
                    gameName: gameName,
                    closeWithoutSave: "true"
                }
                closeGame(session);
                addCloseGameView();

                containerBoard.style.display = "none";
            }
            else {
                if (data.error !== null) {
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
            if (data.error === json_result.CREATED) {
                gameName = "";
                startGame();
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
            switch (data.error) {
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

                case json_result.CREATED:
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
            if (data.error === json_result.OK) {
                gameName = session.gameName;
                optionForm.style.display = "none";
                startGame();

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
            if (data.error === json_result.NOT_FOUND) {
                if (!confirm("Do you want close game without saved?")) {
                    getGames("Save");
                }
                else {
                    session.closeWithoutSave = "true";
                    closeGame(session);
                }
            }
            else {
                addCloseGameView();
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

function removeChilds(container) {
    while (container.firstChild) {
        container.removeChild(container.firstChild);
    }
}



function addButton(container, id, text) {
    let button = document.createElement('button');
    button.setAttribute('class', 'btn btn-primary btn-block');
    button.setAttribute('id', id);
    button.setAttribute('name', id);
    button.setAttribute('type', 'submit');
    button.appendChild(document.createTextNode(text));

    container.appendChild(button);
}

function addInput(container, type, id, placeholder, disabled) {
    let input = document.createElement('input');
    input.classList.add('form-control');
    input.setAttribute('type', type);
    input.setAttribute('id', id);
    input.setAttribute('name', type);
    input.setAttribute('placeholder', placeholder);
    if (disabled) {
        input.setAttribute('disabled', disabled);
    }
    container.appendChild(input);
}

function addInitGameView() {

    let title = document.getElementById("title");
    title.textContent = "Click one option";

    let options = document.getElementById('options');
    removeChilds(options);

    addButton(options, 'btn_login', 'Login');
    addButton(options, 'btn_register', 'Register');

    optionForm.appendChild(options);
}

function addLoginForm() {

    let title = document.getElementById("title");
    title.textContent = "Login Form";

    let options = document.getElementById('options');

    let userName = document.createElement('div');
    userName.classList.add('form-group');
    addInput(userName, 'text', 'username', 'User name');

    let password = document.createElement('div');
    password.classList.add('form-group');
    addInput(password, 'password', 'pwd', 'Password');

    let submit = document.createElement('div');
    submit.classList.add('form-group');
    addButton(submit, 'btn_submitLogin', 'Submit');
    addButton(submit, 'btn_cancelLogin', 'Cancel');

    removeChilds(options);

    options.appendChild(userName);
    options.appendChild(password);
    options.appendChild(submit);
    optionForm.appendChild(options);

}

function addRegisterForm() {

    let title = document.getElementById("title");
    title.textContent = "Register Form";

    let options = document.getElementById('options');

    let userName = document.createElement('div');
    userName.classList.add('form-group');
    addInput(userName, 'text', 'username', 'User name');

    let password = document.createElement('div');
    password.classList.add('form-group');
    addInput(password, 'password', 'pwd', 'Password');

    let password2 = document.createElement('div');
    password2.classList.add('form-group');
    addInput(password2, 'password', 'pwd2', 'Password');

    let submit = document.createElement('div');
    submit.classList.add('form-group');
    addButton(submit, 'btn_submitRegister', 'Submit');
    addButton(submit, 'btn_cancelRegister', 'Cancel');

    removeChilds(options);

    options.appendChild(userName);
    options.appendChild(password);
    options.appendChild(password2);
    options.appendChild(submit);
    optionForm.appendChild(options);

}

function addCloseGameView() {

    let title = document.getElementById("title");
    title.textContent = "Click one option";

    let options = document.getElementById('options');
    removeChilds(options);

    addButton(options, 'btn_createGame', 'Create Game');
    addButton(options, 'btn_openGame', 'Open Game');
    addButton(options, 'btn_logout', 'Logout');
    optionForm.appendChild(options);
}

function addUserLogin(playerName) {

    let player = document.createElement('div');
    player.setAttribute('id', 'playerContent');
    player.setAttribute('class', 'col-sm-3 col-sm-offset-3 user');

    let h1 = document.createElement('h1');

    let span = document.createElement('span');
    span.setAttribute('id', 'player');
    span.appendChild(document.createTextNode(playerName));

    h1.appendChild(span);
    player.appendChild(h1);

    checkers.insertBefore(player, optionForm);

}

function addSaveGameView(listGames, typeView) {
    let options = document.getElementById('options');
    removeChilds(options);

    for (let i = 0; i < listGames.length; i++) {
        let inputGroup = document.createElement('div');
        inputGroup.classList.add('input-group');
        addInput(inputGroup, 'text', 'game' + i, listGames[i], true);
        options.appendChild(inputGroup);
    }
    let gameName = document.createElement('div');
    gameName.classList.add('form-group');
    addInput(gameName, 'text', 'gameName', 'Enter name');

    options.appendChild(gameName);

    let btnGroup = document.createElement('div');
    btnGroup.classList.add('form-group');
    addButton(btnGroup, 'btn_submit' + typeView + 'Game', 'Submit');
    addButton(btnGroup, 'btn_cancel' + typeView + 'Game', 'Cancel');
    options.appendChild(btnGroup);
}

function removeUserLogin() {
    let parent = document.getElementById('checkers');
    let childPlayer = document.getElementById('playerContent');
    parent.removeChild(childPlayer);
}


function startGame() {
    containerForm.removeAttribute('style');
    containerBoard.removeAttribute('style');
    formContainer.removeAttribute('style');
    optionsGame.removeAttribute('style');
    formCoordSelection.style.display = "none";
    gameView = new GameView(scoreBoard);
    gameView.addTable(containerBoard);
    gameView.addPlayer(player);
    gameView.addOptionsGame(optionsGame);
    gameView.addForm(formCoordSelection);
    getBoard();
}

function removeGame() {
    removeChilds(containerBoard);
    removeChilds(optionsGame);
    removeChilds(formCoordSelection);
}

function setTurn() {
    gameView.ready = true;
    gameView.enableAll();
    alert("Click on piece to move");
    gameView.onMark = cellId => {
        addFormChooseCoordinates(cellId);
    };
}

function addFormChooseCoordinates(cellId) {
    let parentCoord = document.getElementById('form_coord');
    let childCoord = document.getElementById('selectedCoord');
    gameView.setCoordenada(cellId, childCoord);
    formCoordSelection.removeAttribute('style');
}

function updateBoard(data) {
    gameView.updateBoard(data);
}
