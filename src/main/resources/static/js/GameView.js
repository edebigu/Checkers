class GameView {

    constructor() {

    }

    removeChilds(container) {
        while (container.firstChild) {
            container.removeChild(container.firstChild);
        }
    }
    
    addButton(container, id, text) {
        let button = document.createElement('button');
        button.setAttribute('class', 'btn btn-primary btn-block');
        button.setAttribute('id', id);
        button.setAttribute('name', id);
        button.setAttribute('type', 'submit');
        button.appendChild(document.createTextNode(text));
    
        container.appendChild(button);
    }
    
    addInput(container, type, id, placeholder, disabled) {
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

    addStartGameView() {
        containerForm.removeAttribute('style');
        containerBoard.removeAttribute('style');
        formContainer.removeAttribute('style');
        optionsGame.removeAttribute('style');
        formCoordSelection.style.display = "none";
        boardView = new BoardView(scoreBoard);
        boardView.addTable(containerBoard);
        boardView.addPlayer(player);
        boardView.addOptionsGame(optionsGame);
        boardView.addForm(formCoordSelection);
        getBoard();
    }

    addInitGameView() {

        let title = document.getElementById("title");
        title.textContent = "Click one option";
    
        let options = document.getElementById('options');
        this.removeChilds(options);
    
        this.addButton(options, 'btn_login', 'Login');
        this.addButton(options, 'btn_register', 'Register');
    
        optionForm.appendChild(options);
    }
    
    addLoginView() {
    
        let title = document.getElementById("title");
        title.textContent = "Login Form";
    
        let options = document.getElementById('options');
    
        let userName = document.createElement('div');
        userName.classList.add('form-group');
        this.addInput(userName, 'text', 'username', 'User name');
    
        let password = document.createElement('div');
        password.classList.add('form-group');
        this.addInput(password, 'password', 'pwd', 'Password');
    
        let submit = document.createElement('div');
        submit.classList.add('form-group');
        this.addButton(submit, 'btn_submitLogin', 'Submit');
        this.addButton(submit, 'btn_cancelLogin', 'Cancel');
    
        this.removeChilds(options);
    
        options.appendChild(userName);
        options.appendChild(password);
        options.appendChild(submit);
        optionForm.appendChild(options);
    
    }
    
    addRegisterView() {
    
        let title = document.getElementById("title");
        title.textContent = "Register Form";
    
        let options = document.getElementById('options');
    
        let userName = document.createElement('div');
        userName.classList.add('form-group');
        this.addInput(userName, 'text', 'username', 'User name');
    
        let password = document.createElement('div');
        password.classList.add('form-group');
        this.addInput(password, 'password', 'pwd', 'Password');
    
        let password2 = document.createElement('div');
        password2.classList.add('form-group');
        this.addInput(password2, 'password', 'pwd2', 'Password');
    
        let submit = document.createElement('div');
        submit.classList.add('form-group');
        this.addButton(submit, 'btn_submitRegister', 'Submit');
        this.addButton(submit, 'btn_cancelRegister', 'Cancel');
    
        this.removeChilds(options);
    
        options.appendChild(userName);
        options.appendChild(password);
        options.appendChild(password2);
        options.appendChild(submit);
        optionForm.appendChild(options);
    
    }
    
    addCloseGameView() {
    
        let title = document.getElementById("title");
        title.textContent = "Click one option";
    
        let options = document.getElementById('options');
        this.removeChilds(options);
    
        this.addButton(options, 'btn_createGame', 'Create Game');
        this.addButton(options, 'btn_openGame', 'Open Game');
        this.addButton(options, 'btn_logout', 'Logout');
        optionForm.appendChild(options);
    }
    
    addUserLogin(playerName) {
    
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
    
    addSaveGameView(listGames, typeView) {
        let options = document.getElementById('options');
        this.removeChilds(options);
    
        for (let i = 0; i < listGames.length; i++) {
            let inputGroup = document.createElement('div');
            inputGroup.classList.add('input-group');
            this.addInput(inputGroup, 'text', 'game' + i, listGames[i], true);
            options.appendChild(inputGroup);
        }
        let gameName = document.createElement('div');
        gameName.classList.add('form-group');
        this.addInput(gameName, 'text', 'gameName', 'Enter name');
    
        options.appendChild(gameName);
    
        let btnGroup = document.createElement('div');
        btnGroup.classList.add('form-group');
        this.addButton(btnGroup, 'btn_submit' + typeView + 'Game', 'Submit');
        this.addButton(btnGroup, 'btn_cancel' + typeView + 'Game', 'Cancel');
        options.appendChild(btnGroup);
    }
    
    removeUserLogin() {
        let parent = document.getElementById('checkers');
        let childPlayer = document.getElementById('playerContent');
        parent.removeChild(childPlayer);
    }
    
    

}