package com.coding.web.response;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserResponse implements Serializable {
    private final String username;
    private final String email;
    private final String imageUrl;
    private final Boolean activated;
    private final LocalDateTime createdDate;

    public UserResponse(String username, String email, String imageUrl, Boolean activated, LocalDateTime createdDate) {
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.activated = activated;
        this.createdDate = createdDate;
    }

    public Boolean isActivated() {
        return activated;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        return (this == obj)
                || (obj instanceof UserResponse that && this.username != null && this.username.equals(that.username));
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "activated=" + activated +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
