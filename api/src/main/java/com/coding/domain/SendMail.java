package com.coding.domain;

import com.google.common.base.Preconditions;

public class SendMail {
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String content;
    private boolean isHtml;
    private boolean isMultipart;

    private SendMail(String[] bcc, String[] cc, String content, boolean isHtml, boolean isMultipart, String subject, String[] to) {
        this.bcc = bcc;
        this.cc = cc;
        this.content = content;
        this.isHtml = isHtml;
        this.isMultipart = isMultipart;
        this.subject = subject;
        this.to = to;
    }

    public SendMail(Builder builder) {
        this.to = builder.to;
        this.subject = builder.subject;
        this.content = builder.content;
        this.isHtml = builder.isHtml;
        this.isMultipart = builder.isMultipart;
        this.cc = builder.cc;
        this.bcc = builder.bcc;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }

    public boolean isMultipart() {
        return isMultipart;
    }

    public void setMultipart(boolean multipart) {
        isMultipart = multipart;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public static class Builder {
        private String[] to;
        private String[] cc = {};
        private String[] bcc = {};
        private String subject;
        private String content;
        private boolean isHtml = true;
        private boolean isMultipart = false;

        public Builder to(String[] to) {
            Preconditions.checkArgument(to != null, "to must not be null");
            Preconditions.checkArgument(to.length > 0, "to must not be empty");
            this.to = to;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder isHtml(boolean isHtml) {
            this.isHtml = isHtml;
            return this;
        }

        public Builder isMultipart(boolean isMultipart) {
            this.isMultipart = isMultipart;
            return this;
        }

        public Builder cc(String[] cc) {
            this.cc = cc;
            return this;
        }

        public Builder bcc(String[] bcc) {
            this.bcc = bcc;
            return this;
        }

        public SendMail build() {
            return new SendMail(this);
        }
    }

}
