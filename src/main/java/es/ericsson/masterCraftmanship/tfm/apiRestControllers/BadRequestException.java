package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException{
    private static final String DESCRIPTION = "Bad Request Exception (400)";

    public BadRequestException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
