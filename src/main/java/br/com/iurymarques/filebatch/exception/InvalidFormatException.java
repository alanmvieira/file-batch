package br.com.iurymarques.filebatch.exception;

public class InvalidFormatException extends Exception {

    public InvalidFormatException() {
        super("The provided file has a invalid format input line");
    }

}
