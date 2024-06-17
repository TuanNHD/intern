package com.tuan.filemanager.exception;

import com.tuan.filemanager.message.MessageReader;

public class CustomException extends Exception {

    public CustomException(String keyMessage) {
        super(MessageReader.getMessage(keyMessage));
    }
}
