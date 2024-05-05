package com.coding.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatusCode;

import java.io.Serial;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ApiResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Object data;
    private Status status;
    private Object error;
    private String message;
    private HttpStatusCode httpStatusCode;
    private Pagination pagination;

    private ApiResponse(Builder builder) {
        this.data = builder.data;
        this.status = builder.status;
        this.error = builder.error;
        this.message = builder.message;
        this.pagination = builder.pagination;
        this.httpStatusCode = builder.httpStatusCode;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public Object getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public Status getStatus() {
        var httpStatusCode = getHttpStatusCode();
        if (httpStatusCode == null) {
            return status;
        } else {
            return httpStatusCode.isError() ? Status.FALSE : Status.TRUE;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public enum Status {
        TRUE,
        FALSE,
    }

    public static class Pagination {
        private int currentPage;
        private int totalPage;
        private int totalCount;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }
    }

    public static class Builder {
        private Object data;
        private Status status;
        private Object error;
        private String message;
        private Pagination pagination;
        private HttpStatusCode httpStatusCode;

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder error(Object error) {
            this.error = error;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder pagination(Pagination pagination) {
            this.pagination = pagination;
            return this;
        }

        public Builder httpStatusCode(HttpStatusCode httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        public ApiResponse build() {
            return new ApiResponse(this);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
