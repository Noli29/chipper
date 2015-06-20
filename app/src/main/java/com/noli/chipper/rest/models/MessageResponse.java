package com.noli.chipper.rest.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by noli on 13.06.15.
 */
public class MessageResponse {

    @SerializedName("recipient_id")
    public int recipientId;

    @SerializedName("sender_id")
    public int senderId;

    @SerializedName("subject")
    public String subject;

    @SerializedName("body")
    public String body;

    public MessageResponse() {
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
