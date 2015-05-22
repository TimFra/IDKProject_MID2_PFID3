package com.drawstuff.mah.drawstuff.Chat;

/**
 * @author greg
 * @since 6/21/13
 */
public class Chat {

        //message and author
    public String message;
    public String author;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    public Chat() {
    }

    public Chat(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
