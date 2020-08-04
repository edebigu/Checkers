class Service {

	constructor() {
		this.apiURL = "";
	
	}
	
	
	start(successCallback, errorCallback, doneCallback){
		this.apiURL = "http://localhost:8080/start";
		this.sendGet(successCallback, errorCallback, doneCallback);
	}
	
	login(data,successCallback, errorCallback, doneCallback){
	  this.apiURL = "http://localhost:8080/login";
	  this.sendPost(data, successCallback, errorCallback, doneCallback);
	}
	
	register(data, successCallback, errorCallback, doneCallback) {
    	this.apiURL =  "http://localhost:8080/register";
    	 this.sendPost(data, successCallback, errorCallback, doneCallback);
    }
    
    logout(data,successCallback, errorCallback, doneCallback){
    	this.apiURL = "http://localhost:8080/logout";
    	 this.sendPost(data, successCallback, errorCallback, doneCallback);
    }
    
    getBoard(player,successCallback, errorCallback, doneCallback) {
    	this.apiURL = "http://localhost:8080/game/" + player + "/getStatus";
    	this.sendGet(successCallback, errorCallback, doneCallback);
    }
    
    getGames(player,successCallback, errorCallback, doneCallback){
    	this.apiURL = "http://localhost:8080/game/getGames/" + player;
    	this.sendGet(successCallback, errorCallback, doneCallback);
    }
    
    move(data, player,successCallback, errorCallback, doneCallback){
    	this.apiURL = "http://localhost:8080/game/move/" + player;
    	 this.sendPost(data, successCallback, errorCallback, doneCallback);
    }
    
    createGame(data, successCallback, errorCallback, doneCallback){
    	this.apiURL = "http://localhost:8080/createGame";
    	 this.sendPost(data, successCallback, errorCallback, doneCallback);
    }
    
    saveGame(data,successCallback, errorCallback, doneCallback){
    	this.apiURL = "http://localhost:8080/saveGame";
    	 this.sendPost(data, successCallback, errorCallback, doneCallback);
    }
    
    openGame(data, successCallback, errorCallback, doneCallback){
    	this.apiURL = "http://localhost:8080/openGame/";
    	 this.sendPost(data, successCallback, errorCallback, doneCallback);
    }
    
    closeGame(data,successCallback, errorCallback, doneCallback){
    	this.apiURL = "http://localhost:8080/closeGame/";
    	this.sendPost(data, successCallback, errorCallback, doneCallback);
    }
    
	sendGet(successCallback, errorCallback, doneCallback) {
    $.ajax({
        url: this.apiURL,
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

   sendPost(dataToSend, successCallback, errorCallback, doneCallback) {

    $.ajax({
        url: this.apiURL,
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

	


}