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
        cellHead.appendChild(document.createTextNode(counter+1));
        r.appendChild(cellHead);
    }

    createPeon(color, cell) {
        let peonColor;
        if (color === 'blanco') {
            peonColor = '\u{026C0}';
            cell.classList.add('peonBlanco');
        }
        else if (color === 'negro')
        {
            peonColor = '\u{026C2}'
            cell.classList.add('peonNegro');
        }

        cell.appendChild(document.createTextNode(peonColor));


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

            r = row.cloneNode(false);
            this.createTableHeadColum(r, counter);
            

            for (let j = i; j < i + 8; j++) {
            	this.cells[j].setAttribute('data-col', String.fromCharCode(letterNum));
                this.cells[j].setAttribute('data-row', counter+1);
                this.cells[j].setAttribute('data-y', counter);
                this.cells[j].setAttribute('data-x', j);
                if (counter % 2 == 0) {
                    if (j % 2 == 0) {
                        this.cells[j].classList.add('tenue');    
                    }
                    else {
                        this.cells[j].classList.add('opaca');
                        if (counter < 3){
                            this.createPeon ('negro',this.cells[j] );
                            this.cells[j].setAttribute('data-color', 'negro');

                        }
                        else if (counter >= 5) {
                            this.createPeon ('blanco',this.cells[j] );
                            this.cells[j].setAttribute('data-color', 'blanco');
                        }
                    }
                    
                }
                else {
                    if (j % 2 == 0){
                        this.cells[j].classList.add('opaca');
                        if (counter < 3){
                            this.createPeon ('negro',this.cells[j] );
                            this.cells[j].setAttribute('data-color', 'negro');
                        }
                        else if (counter >= 5) {
                            this.createPeon ('blanco',this.cells[j] );
                            this.cells[j].setAttribute('data-color', 'blanco');
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

    enableTurn(playerName) {
        for (let cell of this.cells) {
            if (cell.getAttribute('marked') === 'false') {
               if (playerName === this.players[0] && cell.getAttribute('data-color') === 'blanco') {
                cell.classList.remove('notActive');
                cell.setAttribute('active', 'true');
               }
               
               else if (playerName === this.players[1] && cell.getAttribute('data-color') === 'negro') {
            	   cell.classList.remove('notActive');
                   cell.setAttribute('active', 'true');
               }
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

    doMark(cellId, newCellId, label) {
        let cell = this.cells[cellId];;
        let color = cell.getAttribute('data-color');
        cell.removeAttribute('data-color');
        cell.textContent = '';
        let newCell = this.cells[newCellId];
        console.log ("color " , color);
        this.createPeon(color, newCell);
        newCell.setAttribute('data-color', color);

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
    	this.addSelectRow(container);
    	this.addSelectColum(container);
    	this.setCoordenada(cellId, container);
    	this.addButtonForm(container);	
    }
    
    setCoordenada(cellId, container) {
    	let cell = this.cells[cellId];
    	let label = document.createElement('label');
    	label.setAttribute('class', 'description');
    	label.setAttribute('for', 'coordenadas');
    	label.appendChild(document.createTextNode('Coordenada'));
    	label.style.display = "none";
    	
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
    	input.style.display = "none";
    	
    	container.appendChild(label);
    	container.appendChild(input);
    	
    }
    
     addSelectColum(container) {
    	
    	let newColum = document.createElement('div');
    	
    	let label = document.createElement('label');
    	label.setAttribute('class', 'description');
    	label.setAttribute('for', 'newColum');
    	label.appendChild(document.createTextNode('Colum '));
    	
    	newColum.appendChild(label);
    	
    	let select = document.createElement ('select');
    	select.setAttribute('class', 'element select medium');
    	select.setAttribute('id', 'newColum');
    	select.setAttribute('name', 'newColum');
    	
    	let option = document.createElement('option');
    	option.setAttribute('value', '');
    	option.setAttribute('selected', true);
    	option.setAttribute('disabled', true);
    	option.setAttribute('hiden', true);
    	option.appendChild(document.createTextNode('Choose one option'));
    	
        select.appendChild(option);
        
        let letter = 'a';
        let letterNum = letter.charCodeAt();
        for (let i = 1; i <= 8; i++) {
            option = document.createElement('option');
            option.setAttribute('value', String.fromCharCode(letterNum));
            option.appendChild(document.createTextNode(String.fromCharCode(letterNum)));
            select.appendChild(option);
            letterNum++;
        }

        newColum.appendChild(select);
    	container.appendChild (newColum);
    }
    
    addSelectRow(container) {
    	
    	let newRow = document.createElement('div');
    	
    	let label = document.createElement('label');
    	label.setAttribute('class', 'description');
    	label.setAttribute('for', 'newRow');
    	label.appendChild(document.createTextNode('Row '));
    	
    	newRow.appendChild(label);
    	
    	let select = document.createElement ('select');
    	select.setAttribute('class', 'element select medium');
    	select.setAttribute('id', 'newRow');
    	select.setAttribute('name', 'newRow');
    	
    	let option = document.createElement('option');
    	option.setAttribute('value', '');
    	option.setAttribute('selected', true);
    	option.setAttribute('disabled', true);
    	option.setAttribute('hiden', true);
    	option.appendChild(document.createTextNode('Choose one option'));
    	
        select.appendChild(option);
        
        for (let i = 1; i <= 8; i++) {
            option = document.createElement('option');
            option.setAttribute('value', i);
            option.appendChild(document.createTextNode(i));
            select.appendChild(option);
        }
    	
        newRow.appendChild(select);
    	container.appendChild (newRow);
    	
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
    
    	this.players.push(player);
    	this.players.push("machine");

        /*if (this.players.length < 2) {

            if (this.players.length === 0 || this.players[0].id != player.id) {

                this.players.push(player);

                let score = this.scoreBoard[this.players.length - 1];

                if (this.players.length === 1) {
                    //score.textContent = player.label + ' ' + player.name;
                    score.textContent = player;
                } else {
                   // score.textContent = player.name + ' ' + player.label;
                    score.textContent = player;
                }

                score.setAttribute('playerId', player.id);
            }
        }*/
    }
    
    getCellId(dataRow, dataCol) {
    	console.log ("dataRow " + dataRow);
    	console.log ("dataCol" +  dataCol);
    	  for (let cell of this.cells) {
    		  console.log ("data-row " + cell.getAttribute('data-row'));
    	    	console.log ("data-col" +  cell.getAttribute('data-col'));
              if (cell.getAttribute('data-row') === dataRow && cell.getAttribute('data-col') === dataCol){
            	console.log ("dentro del if con id " + cell.getAttribute('id'));
              	return cell.getAttribute('id');
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

