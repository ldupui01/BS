package main;

/**
 * Created by Guilherme on 23/04/2014.\n
 *
 * Exception called by in-game menu when user wants to finish the game prematurely.
 *
 */
public class UserCancelsException extends Exception {
        public UserCancelsException() { super(); }
        public UserCancelsException(String message) { super(message); }
        public UserCancelsException(String message, Throwable cause) { super(message, cause); }
        public UserCancelsException(Throwable cause) { super(cause); }

}