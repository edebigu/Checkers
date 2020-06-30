package es.ericsson.masterCraftmanship.tfm.views;

public enum Error {
	    OK("200 OK"),
		CREATED("201 CREATED"),
		NO_CONTENT ("204 NO CONTENT"),
		BAD_REQUEST ("400 BAD REQUEST"),
		UNAUTHORIZED("401 UNAUTHORIZED"),
		NOT_FOUND("404 NOT FOUND"),
	    CONFLICT("409 CONFLICT");
		
		private String error;
		
		private Error(String error) {
			this.error = error;
		}
		
		public String getError() {
			return this.error;
		}
	

}
