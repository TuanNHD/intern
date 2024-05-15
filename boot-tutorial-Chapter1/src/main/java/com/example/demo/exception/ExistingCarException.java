package com.example.demo.exception;

public class ExistingCarException extends Exception {
public ExistingCarException() {
	super("the car doesn't exist");
}
}
