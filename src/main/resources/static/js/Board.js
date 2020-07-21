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
    
    
    

    createPawn(color, cell) {
        let pawn;
        if (color === 'WHITE') {
            pawn = '\u{026C0}';
            cell.classList.add('whitePiece');
        }
        else if (color === 'BLACK')
        {
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
        else if (color === 'BLACK')
        {
            queen = '\u{026C3}';
            cell.classList.add('blackPiece');
        }
		cell.appendChild(document.createTextNode(queen));
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
                this.cells[j].setAttribute('data-y', counter);
                this.cells[j].setAttribute('data-x', j);
                if (counter % 2 == 0) {
                    if (j % 2 == 0) {
                        this.cells[j].classList.add('tenue');    
                    }
                    else {
                        this.cells[j].classList.add('opaca');
                    }
                    
                }
                else {
                    if (j % 2 == 0){
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

    
    doUpdate(data){
       for (let i = 0; i < data.length; i ++) {
             let row =  data[i].coordX;
       		 let col =  data[i].coordY;
       		 let cellId = this.getCellId(row, col);
       		 let cell = this.cells[cellId];
       		 cell.removeAttribute('data-color');
       		 cell.classList.remove('whitePiece');
       		 cell.classList.remove('blackPiece');
          	cell.textContent = '';
          if (data[i].color !== null && data[i].piece !== null) {
          	if ( data[i].piece ==  data[i].piece.toLowerCase()){
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
    	
    	let moveButton =  document.createElement('button');
    	moveButton.setAttribute('class', 'btn btn-primary btn-block');
    	moveButton.setAttribute('id', 'move');
    	moveButton.setAttribute('name', 'move');
    	moveButton.setAttribute('type', 'button');
    	moveButton.appendChild(document.createTextNode('Move'));
    	
    	let saveGameButton =  document.createElement('button');
    	saveGameButton.setAttribute('class', 'btn btn-primary btn-block');
    	saveGameButton.setAttribute('id', 'saveGame');
    	saveGameButton.setAttribute('name', 'saveGame');
    	saveGameButton.setAttribute('type', 'button');
    	saveGameButton.appendChild(document.createTextNode('Save Game'));
    	
    	let closeGameButton =  document.createElement('button');
    	closeGameButton.setAttribute('class', 'btn btn-primary btn-block');
    	closeGameButton.setAttribute('id', 'closeGame');
    	closeGameButton.setAttribute('name', 'closeGame');
    	closeGameButton.setAttribute('type', 'button');
    	closeGameButton.appendChild(document.createTextNode('Close Game'));
    	
    	divButton.appendChild(moveButton);
    	divButton.appendChild(saveGameButton);
    	divButton.appendChild(closeGameButton);
        container.appendChild(divButton);
 
    }

    
     addForm(container){
    	this.addSelectRow(container);
    	this.addSelectColum(container);
    	this.addCoordenada(container);
    	this.addButtonsForm(container);	
    }
    
    setCoordenada(cellId,container) {
    	let cell = this.cells[cellId];
    	container.setAttribute('placeholder', cell.getAttribute('data-col') + cell.getAttribute('data-row'));
    	container.setAttribute('data-x', cell.getAttribute('data-x'));
    	container.setAttribute('data-y', cell.getAttribute('data-y'));
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
        for (let i = 0; i < 8; i++) {
            option = document.createElement('option');
            option.setAttribute('value', i);
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
        
        for (let i = 0; i < 8; i++) {
            option = document.createElement('option');
            option.setAttribute('value', i);
            option.appendChild(document.createTextNode(i+1));
            select.appendChild(option);
        }
    	
        newRow.appendChild(select);
    	container.appendChild (newRow);
    	
    }
    
    addButtonsForm(container) {
    	let divButton = document.createElement('div');
    	divButton.setAttribute('class', 'input-group');
    	
    	let button =  document.createElement('button');
    	button.setAttribute('type', 'button');
    	//button.setAttribute('value', 'submit');
    	button.setAttribute('value', 'send');
    	button.setAttribute('class', 'btn btn-primary btn-md d-block mx-auto');
    	button.setAttribute('id', 'send');
    	button.setAttribute('disabled', true);
    	button.appendChild(document.createTextNode('Submit'));
    	
    	let cancelButton =  document.createElement('button');
    	cancelButton.setAttribute('type', 'button');
    	//cancelButton.setAttribute('value', 'submit');
    	cancelButton.setAttribute('value', 'cancelCoord');
    	cancelButton.setAttribute('class', 'btn btn-primary btn-md d-block mx-auto');
    	cancelButton.setAttribute('id', 'cancelCoord');
    	cancelButton.appendChild(document.createTextNode('Cancel'));
    	
    	divButton.appendChild(button);
    	divButton.appendChild(cancelButton);
    	
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
              if (cell.getAttribute('data-row') == dataRow && cell.getAttribute('data-col') == dataCol){
              	return cell.getAttribute('id');
              }
          }
    	
    }



}

