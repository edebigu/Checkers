package es.ericsson.masterCraftmanship.tfm.views;

public enum Error {
	    OK("OK"),
		CREATED("CREATED"),
		NO_CONTENT ("NO_CONTENT"),
		BAD_REQUEST ("BAD_REQUEST"),
		UNAUTHORIZED("UNAUTHORIZED"),
		NOT_FOUND("NOT_FOUND"),
	    CONFLICT("CONFLICT");
	    
		
		private String error;
		
		private Error(String error) {
			this.error = error;
		}
		
		public String getError() {
			return this.error;
		}
	

}
