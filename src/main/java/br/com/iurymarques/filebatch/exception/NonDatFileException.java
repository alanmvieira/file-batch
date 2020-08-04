package br.com.iurymarques.filebatch.exception;

public class NonDatFileException extends RuntimeException {

    public NonDatFileException() {
        super("The provided file is not a .dat");
    }

}
