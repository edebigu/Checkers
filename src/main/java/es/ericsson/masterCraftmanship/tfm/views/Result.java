package es.ericsson.masterCraftmanship.tfm.views;

public enum Result {
	    OK("OK"),
		NO_CONTENT ("NO_CONTENT"),
		BAD_REQUEST ("BAD_REQUEST"),
		UNAUTHORIZED("UNAUTHORIZED"),
		NOT_FOUND("NOT_FOUND"),
	    CONFLICT("CONFLICT");
	    
		
		private String result;
		
		private Result(String result) {
			this.result = result;
		}
		
		public String getResult() {
			return this.result;
		}
	

}
