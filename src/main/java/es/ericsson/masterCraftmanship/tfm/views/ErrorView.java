package es.ericsson.masterCraftmanship.tfm.views;

public enum ErrorView {
	    OK("OK"),
		CREATED("CREATED"),
		NO_CONTENT ("NO_CONTENT"),
		BAD_REQUEST ("BAD_REQUEST"),
		UNAUTHORIZED("UNAUTHORIZED"),
		NOT_FOUND("NOT_FOUND"),
	    CONFLICT("CONFLICT");
	    
		
		private String error;
		
		private ErrorView(String error) {
			this.error = error;
		}
		
		public String getError() {
			return this.error;
		}
	

}
