class Dto {

	
	contructor() {
	
		this.json = {};
	
	}
	
	playerDto(username, password){
	
		this.json = {
			    username: username,
                password: password
		}
		
	}
	
	sessionDto(username, gameName) {
	   this.json = {
            username: username,
            gameName: gameName
        }
	
	}
	
	saveGameDto(username, gameName, overwrite) {
	
	   this.json = {
            username: username,
            gameName: gameName,
            overwrite: overwrite
        }
	
	}
	
	closeGameDto(username, gameName, closeWithoutSave){
	
		   this.json = {
            username: username,
            gameName: gameName,
            closeWithoutSave: closeWithoutSave
        }
		
	}
	
	moveDto(originRow, originCol, targetRow, targetCol){
	    this.json = {
            originRow: originRow,
            originCol: originCol,
            targetRow: targetRow,
            targetCol: targetCol
        }
	
	}




}