class PlayGameView {

    constructor(scoreBoard) {

        this.scoreBoard = scoreBoard;
        this.cells = [];
        this.players = [];
        this.ready = false;

        this.createTable();
    }

    createTable() {
        this.table = document.createElement('table');
        this.table.setAttribute('id', 'playerBoard');
        this.table.addEventListener('click', event => this.markEvent(event));
        this.table.classList.add('gameBoard');

        let rowCol = document.createElement('td');
        rowCol.classList.add('boardRow');
        rowCol.classList.add('bgWhite');
        rowCol.setAttribute('colspan', 22);

        let row = document.createElement('tr');

        let cell = document.createElement('td');
        cell.classList.add('gameCell');
        cell.classList.add('notActive');
        cell.setAttribute('marked', 'false');
        cell.setAttribute('data-intent', 'gameCell');

        for (let i = 0; i < 64; i++) {
            let newCell = cell.cloneNode(true);
            newCell.setAttribute('id', i);
            this.cells.push(newCell);
        }

        this.createTableHeadRow(this.table);

        let counter = 0
        let letter = 'a';
        for (let r, i = 0; i < 64; i += 8) {
            let letterNum = letter.charCodeAt();
            let counterCol = 0;
            r = row.cloneNode(false);
            this.createTableHeadColum(r, counter);

            for (let j = i; j < i + 8; j++) {
                this.cells[j].setAttribute('data-col', counterCol);
                this.cells[j].setAttribute('data-row', counter);
                if (counter % 2 == 0) {
                    if (j % 2 == 0) {
                        this.cells[j].classList.add('tenue');
                    }
                    else {
                        this.cells[j].classList.add('opaca');
                    }

                }
                else {
                    if (j % 2 == 0) {
                        this.cells[j].classList.add('opaca');

                    }
                    else {
                        this.cells[j].classList.add('tenue');
                    }

                }
                r.appendChild(this.cells[j]);
                counterCol++;
            }
            this.createTableHeadColum(r, counter);
            this.table.appendChild(r);
            counter++;
        }
        this.createTableHeadRow(this.table);
    }

    createTableHeadRow() {
        let row = document.createElement('tr');
        let rowCol = document.createElement('td');
        let coordenada = row.cloneNode(false);
        let cellHead = document.createElement('td');
        cellHead.classList.add('gameCell');
        cellHead.classList.add('notActive');
        cellHead.setAttribute('marked', 'false');
        coordenada.appendChild(cellHead);
        let letter = 'a';
        let letterNum = letter.charCodeAt();
        for (let i = 1; i < 9; i++) {
            cellHead = document.createElement('td');
            cellHead.classList.add('gameCell');
            cellHead.classList.add('notActive');
            cellHead.classList.add('headTable');
            cellHead.setAttribute('marked', 'false');
            cellHead.appendChild(document.createTextNode(String.fromCharCode(letterNum)));
            coordenada.appendChild(cellHead);
            letterNum++;
        }

        cellHead = document.createElement('td');
        cellHead.classList.add('gameCell');
        cellHead.classList.add('notActive');
        cellHead.setAttribute('marked', 'false');
        coordenada.appendChild(cellHead);

        this.table.appendChild(coordenada);

    }

    createTableHeadColum(r, counter) {
        let cellHead = document.createElement('td');
        cellHead.classList.add('gameCell');
        cellHead.classList.add('notActive');
        cellHead.classList.add('headTable');
        cellHead.setAttribute('marked', 'false');
        cellHead.appendChild(document.createTextNode(counter + 1));
        r.appendChild(cellHead);
    }

    createPawn(color, cell) {
        let pawn;
        if (color === 'WHITE') {
            pawn = '\u{026C0}';
            cell.classList.add('whitePiece');
        }
        else if (color === 'BLACK') {
            pawn = '\u{026C2}'
            cell.classList.add('blackPiece');
        }

        cell.appendChild(document.createTextNode(pawn));
    }

    createQueen(color, cell) {
        let queen;
        if (color === 'WHITE') {
            queen = '\u{026C1}';
            cell.classList.add('whitePiece');
        }
        else if (color === 'BLACK') {
            queen = '\u{026C3}';
            cell.classList.add('blackPiece');
        }
        cell.appendChild(document.createTextNode(queen));
    }

    addTable(container) {
        container.appendChild(this.table);
    }


    disableAll() {
        for (let cell of this.cells) {
            cell.classList.add('notActive');
            cell.setAttribute('active', 'false');
        }
    }

    enableAll() {
        for (let cell of this.cells) {
            cell.classList.remove('notActive');
            cell.setAttribute('marked', 'false');
            cell.setAttribute('active', 'true');
        }
    }

    onMark(cellId) {

    }

    markEvent(event) {
        let target = event.target;

        if (this.ready && target.getAttribute('data-intent') === 'gameCell' &&
            target.getAttribute('active') === 'true') {
            this.onMark(this.cells.indexOf(target));
            this.disableAll();
        }
    }


    updateBoard(data) {
        for (let i = 0; i < data.length; i++) {
            let cellId = this.getCellId(data[i].coordX, data[i].coordY);
            let cell = this.cells[cellId];
            cell.removeAttribute('data-color');
            cell.classList.remove('whitePiece');
            cell.classList.remove('blackPiece');
            cell.textContent = '';
            if (data[i].color !== null && data[i].piece !== null) {
                if (data[i].piece == data[i].piece.toLowerCase()) {
                    this.createPawn(data[i].color, cell);
                }
                else {
                    this.createQueen(data[i].color, cell);
                }
                cell.setAttribute('data-color', data[i].color);
            }
        }
    }

    addOptionsGame(container) {
        let divButton = document.createElement('div');
        divButton.setAttribute('class', 'form-group');
        this.addButton(divButton, 'move', 'Move', false);
        this.addButton(divButton, 'saveGame', 'Save Game', false);
        this.addButton(divButton, 'closeGame', 'Close Game', false);
        container.appendChild(divButton);
    }

    addButton(container, id, text, disable) {
        let button = document.createElement('button');
        button.setAttribute('class', 'btn btn-primary btn-block');
        button.setAttribute('id', id);
        button.setAttribute('name', id);
        button.setAttribute('type', 'button');
        button.appendChild(document.createTextNode(text));
        if (disable){
        	 button.setAttribute('disabled', disable);
        }
        container.appendChild(button);
    }


    addForm(container) {
        this.addSelectRow(container);
        this.addSelectColum(container);
        this.addCoordenada(container);
        this.addButtonsForm(container);
    }

    setCoordenada(cellId, container) {
        let cell = this.cells[cellId];
        container.setAttribute('placeholder', cell.getAttribute('data-col') + cell.getAttribute('data-row'));
        container.setAttribute('data-col', cell.getAttribute('data-col'));
        container.setAttribute('data-row', cell.getAttribute('data-row'));
        container.setAttribute('data-cellId', cellId);
    }

    addCoordenada(container) {
        let input = document.createElement('input');
        input.setAttribute('type', 'text');
        input.setAttribute('name', 'selectedCoord');
        input.setAttribute('class', 'form-control');
        input.setAttribute('id', 'selectedCoord');
        input.setAttribute('aria-describedby', 'basic-addon');
        input.setAttribute('disabled', true);
        input.style.display = "none";
        container.appendChild(input);
    }



    addSelectColum(container) {

        let newColum = document.createElement('div');
        let label = document.createElement('label');
        label.setAttribute('class', 'description');
        label.setAttribute('for', 'newColum');
        label.appendChild(document.createTextNode('Colum '));

        newColum.appendChild(label);

        let select = document.createElement('select');
        select.setAttribute('class', 'element select medium');
        select.setAttribute('id', 'newColum');
        select.setAttribute('name', 'newColum');

        this.addSelectOption(select, '', 'Choose one option', true);

        let letter = 'a';
        let letterNum = letter.charCodeAt();
        for (let i = 0; i < 8; i++) {
            this.addSelectOption(select, i, String.fromCharCode(letterNum));
            letterNum++;
        }

        newColum.appendChild(select);
        container.appendChild(newColum);
    }

    addSelectOption(container, value, text, defaultOption) {

        let option = document.createElement('option');
        option.setAttribute('value', value);
        if (defaultOption) {
            option.setAttribute('selected', true);
            option.setAttribute('disabled', true);
            option.setAttribute('hiden', true);
        }
        option.appendChild(document.createTextNode(text));
        container.appendChild(option);

    }

    addSelectRow(container) {

        let newRow = document.createElement('div');

        let label = document.createElement('label');
        label.setAttribute('class', 'description');
        label.setAttribute('for', 'newRow');
        label.appendChild(document.createTextNode('Row '));

        newRow.appendChild(label);

        let select = document.createElement('select');
        select.setAttribute('class', 'element select medium');
        select.setAttribute('id', 'newRow');
        select.setAttribute('name', 'newRow');

        this.addSelectOption(select, '', 'Choose one option', true);

        for (let i = 0; i < 8; i++) {
            this.addSelectOption(select, i, i + 1);
        }

        newRow.appendChild(select);
        container.appendChild(newRow);

    }

    addButtonsForm(container) {
        let divButton = document.createElement('div');
        divButton.setAttribute('class', 'input-group');
        this.addButton(divButton, 'send', 'Submit', true);
        this.addButton(divButton, 'cancelCoord', 'Cancel', false);
        container.appendChild(divButton);
    }

    addPlayer(player) {
        this.players.push(player);
        this.players.push("machine");
    }

    removePlayer() {
        this.players.pop();
        this.players.pop();
    }

    getCellId(dataRow, dataCol) {
        for (let cell of this.cells) {
            if (cell.getAttribute('data-row') == dataRow && cell.getAttribute('data-col') == dataCol) {
                return cell.getAttribute('id');
            }
        }

    }
}

