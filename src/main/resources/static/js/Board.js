class Board {

    constructor(scoreBoard) {

        this.scoreBoard = scoreBoard;

        this.cells = [];
        this.players = [];
        this.ready = false;

        this.createTable();
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
        let letter = 'A';
        let letterNum = letter.charCodeAt();
        for (let i = 1; i < 9; i++) {
            cellHead = document.createElement('td');
            cellHead.classList.add('gameCell');
            cellHead.classList.add('notActive');
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
        cellHead.setAttribute('marked', 'false');
        cellHead.appendChild(document.createTextNode(counter+1));
        r.appendChild(cellHead);
    }

    createPeon(color, cell) {
        var peonColor;
        if (color === 'blanco') {
            peonColor = '\u{026C0}';
        }
        else if (color === 'negro')
        {
            peonColor = '\u{026C2}'
        }

        let span = document.createElement('span');
        span.setAttribute('style', 'font-size:35px;');
        span.appendChild(document.createTextNode(peonColor));
        cell.appendChild(span);

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
            newCell.setAttribute('id', 'cell-' + i);
            this.cells.push(newCell);
        }

        this.createTableHeadRow(this.table);
        let counter = 0
        let letter = 'A';

        for (let r, i = 0; i < 64; i += 8) {
        	let letterNum = letter.charCodeAt();

            r = row.cloneNode(false);
            this.createTableHeadColum(r, counter);
            

            for (let j = i; j < i + 8; j++) {
            	this.cells[j].setAttribute('data-row', String.fromCharCode(letterNum));
                this.cells[j].setAttribute('data-col', counter+1);
                this.cells[j].setAttribute('data-y', counter);
                this.cells[j].setAttribute('data-x', j);
                if (counter % 2 == 0) {
                    if (j % 2 == 0) {
                        this.cells[j].classList.add('tenue');    
                    }
                    else {
                        this.cells[j].classList.add('opaca');
                        if (counter < 3){
                            this.createPeon ('blanco',this.cells[j] );

                        }
                        else if (counter >= 5) {
                            this.createPeon ('negro',this.cells[j] );
                        }
                    }
                    
                }
                else {
                    if (j % 2 == 0){
                        this.cells[j].classList.add('opaca');
                        if (counter < 3){
                            this.createPeon ('blanco',this.cells[j] );
                        }
                        else if (counter >= 5) {
                            this.createPeon ('negro',this.cells[j] );
                        }
                    }
                    else {
                        this.cells[j].classList.add('tenue');
                    }

                }
                r.appendChild(this.cells[j]);
                letterNum++;
            }
            this.createTableHeadColum(r, counter);

            this.table.appendChild(r);

            counter++;
        }

        this.createTableHeadRow(this.table);

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
        }
    }

    enableTurn() {
    console.log ("Turno habilitado");
        for (let cell of this.cells) {
            if (cell.getAttribute('marked') === 'false') {
                cell.classList.remove('notActive');
                cell.setAttribute('active', 'true');
            }
        }
    }

    highlightCells(positions) {

        for (let i of positions) {
            this.cells[i].classList.add('colorRed');
        }

        for (let cell of this.cells) {
            cell.setAttribute('marked', 'true');
        }
    }

    lowlightCells() {
        for (let cell of this.cells) {
            cell.classList.add('colorWhite');
        }
    }

    onMark(cellId) { 
        console.log ("He llamado a onMark");
    }

    markEvent(event) {
        let target = event.target;

        if (this.ready && target.getAttribute('data-intent') === 'gameCell' &&
            target.getAttribute('active') === 'true') {
            this.onMark(this.cells.indexOf(target));
            this.disableAll();
        }
    }

    doMark(cellId, label) {
        let cell = this.cells[cellId];
        cell.textContent = label;
        cell.classList.add('notActive');
        cell.setAttribute('marked', 'true');

    }

    doPutShip(cellId, label, name) {
    for(let i=0; i < cellId.length; i++){
        let cell = this.cells[cellId[i]];
        let imag = document.createElement('img');
        imag.setAttribute('src', label);
        imag.setAttribute('id', name);
        imag.setAttribute('class', 'imageShip');
        cell.appendChild(imag);
        cell.classList.add('notActive');
        cell.setAttribute('marked', 'true');
        console.log("coordenadas " + cell.getAttribute('data-x') + " " + cell.getAttribute('data-y'));
    }

    }

    doWinner(winner, pos) {

        let looser;
        if (winner === this.players[0].name) {
            looser = this.players[1].name;
        } else {
            looser = this.players[0].name;
        }

        alert(winner + " wins! " + looser + " looses.");

        this.disableAll();
        this.highlightCells(pos);
    }

    doDraw() {
        alert("Draw!");
        this.lowlightCells();
    }

    highlightScoreboard(playerId) {

        for (let board of this.scoreBoard) {
            board.classList.remove('active');

            if (board.getAttribute('playerId') == playerId) {
                board.classList.add('active');
            }
        }
    }
    
    addForm(cellId, container){
    	this.setCoordenada(cellId, container);
    	this.addSelectOrientation(container);
    	this.addSelectDirection(container);
    	this.addButtonForm(container);	
    }
    
    setCoordenada(cellId, container) {
    	let cell = this.cells[cellId];
    	let label = document.createElement('label');
    	label.setAttribute('class', 'description');
    	label.setAttribute('for', 'coordenadas');
    	label.appendChild(document.createTextNode('Coordenada'));
    	
    	let input = document.createElement('input');
    	input.setAttribute('type', 'text');
    	input.setAttribute('name', 'coordenadas');
    	input.setAttribute('class', 'form-control');
    	input.setAttribute('id', 'coordenadas');
    	input.setAttribute('placeholder', cell.getAttribute('data-col') + cell.getAttribute('data-row'));
    	input.setAttribute('data-x', cell.getAttribute('data-x'));
    	input.setAttribute('data-y', cell.getAttribute('data-y'));
    	input.setAttribute('data-cellId', cellId);
    	input.setAttribute('aria-describedby', 'basic-addon');
    	input.setAttribute('disabled', true);
    	
    	container.appendChild(label);
    	container.appendChild(input);
    	
    }
    
     addSelectOrientation(container) {
    	
    	let orientacion = document.createElement('div');
    	
    	let label = document.createElement('label');
    	label.setAttribute('class', 'description');
    	label.setAttribute('for', 'orientation');
    	label.appendChild(document.createTextNode('Orientation'));
    	
    	orientacion.appendChild(label);
    	
    	let select = document.createElement ('select');
    	select.setAttribute('class', 'element select medium');
    	select.setAttribute('id', 'orientation');
    	select.setAttribute('name', 'orientation');
    	
    	let option = document.createElement('option');
    	option.setAttribute('value', '');
    	option.setAttribute('selected', true);
    	option.setAttribute('disabled', true);
    	option.setAttribute('hiden', true);
    	option.appendChild(document.createTextNode('Choose one option'));
    	
    	select.appendChild(option);
    	
    	option = document.createElement('option');
    	option.setAttribute('value', 'Vertical');
    	option.appendChild(document.createTextNode('Vertical'));
    	
    	select.appendChild(option);
    	
    	option = document.createElement('option');
    	option.setAttribute('value', 'Horizontal');
    	option.appendChild(document.createTextNode('Horizontal'));
    	
    	select.appendChild(option);
    	
    	orientacion.appendChild(select);
    	
    	container.appendChild (orientacion)
    	
    	
    }
    
    addSelectDirection(container) {
    	
    	let direction = document.createElement('div');
    	
    	let label = document.createElement('label');
    	label.setAttribute('class', 'description');
    	label.setAttribute('for', 'direction');
    	label.appendChild(document.createTextNode('Direction'));
    	
    	direction.appendChild(label);
    	
    	let select = document.createElement ('select');
    	select.setAttribute('class', 'element select medium');
    	select.setAttribute('id', 'direction');
    	select.setAttribute('name', 'direction');
    	
    	let option = document.createElement('option');
    	option.setAttribute('value', '');
    	option.setAttribute('selected', true);
    	option.setAttribute('disabled', true);
    	option.setAttribute('hiden', true);
    	option.appendChild(document.createTextNode('Choose one option'));
    	
    	select.appendChild(option);
    	
    	option = document.createElement('option');
    	option.setAttribute('value', 'Norte');
    	option.appendChild(document.createTextNode('Norte'));
    	option.setAttribute('disabled', true);
    	
    	select.appendChild(option);
    	
    	option = document.createElement('option');
    	option.setAttribute('value', 'Sur');
    	option.appendChild(document.createTextNode('Sur'));
    	option.setAttribute('disabled', true);
    	
    	select.appendChild(option);
    	
    	option = document.createElement('option');
    	option.setAttribute('value', 'Este');
    	option.appendChild(document.createTextNode('Este'));
    	option.setAttribute('disabled', true);
    	
    	select.appendChild(option);
    	
    	option = document.createElement('option');
    	option.setAttribute('value', 'Oeste');
    	option.appendChild(document.createTextNode('Oeste'));
    	option.setAttribute('disabled', true);
    	
    	select.appendChild(option);
    	
    	direction.appendChild(select);
    	
    	//select.setAttribute('disabled', true);
    	
    	container.appendChild (direction)
    	
    }
    
    addButtonForm(container) {
    	let divButton = document.createElement('div');
    	divButton.setAttribute('class', 'input-group');
    	
    	let button =  document.createElement('button');
    	button.setAttribute('type', 'button');
    	button.setAttribute('value', 'submit');
    	button.setAttribute('value', 'enviar');
    	button.setAttribute('class', 'btn btn-primary btn-md d-block mx-auto');
    	button.setAttribute('id', 'enviar');
    	button.setAttribute('disabled', true);
    	button.appendChild(document.createTextNode('Submit'));
    	
    	divButton.appendChild(button);
    	
    	container.appendChild(divButton);
    	
    }

    addPlayer(player) {

        if (this.players.length < 2) {

            if (this.players.length === 0 || this.players[0].id != player.id) {

                this.players.push(player);

                let score = this.scoreBoard[this.players.length - 1];

                if (this.players.length === 1) {
                    score.textContent = player.label + ' ' + player.name;
                } else {
                    score.textContent = player.name + ' ' + player.label;
                }

                score.setAttribute('playerId', player.id);
            }
        }
    }

    restart() {

        for (let cell of this.cells) {

            cell.classList.remove('colorRed');
            cell.classList.add('notActive');

            cell.classList.remove('colorWhite');
            cell.classList.remove('colorRed');

            cell.setAttribute('marked', 'false');
            cell.setAttribute('active', 'false');

            cell.textContent = '';
        }
    }




}

