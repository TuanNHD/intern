package com.tuan.filemanager.message;

import java.util.ResourceBundle;

public class MessageReader {
    private static final String MESSAGE_FILE = "message";
    private static final ResourceBundle messages = ResourceBundle.getBundle(MESSAGE_FILE);

    public static String getMessage(String key) {
        return messages.getString(key);
    }
}
