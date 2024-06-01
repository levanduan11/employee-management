package com.coding.web

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatusCode
import java.io.Serial
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiResponseKotlin private constructor(builder: Builder) : Serializable {
    var data: Any?
        private set
    private var status: Status?
        private set
        get() {
            val httpStatusCode = httpStatusCode
            return if (httpStatusCode == null) {
                field
            } else {
                if (httpStatusCode.isError) Status.FALSE else Status.TRUE
            }
        }
    var error: Any?
        private set
    var message: String?
        private set
    var httpStatusCode: HttpStatusCode?
        private set
    var pagination: Pagination?
        private set

    init {
        this.data = builder.data
        this.status = builder.status
        this.error = builder.error
        this.message = builder.message
        this.pagination = builder.pagination
        this.httpStatusCode = builder.httpStatusCode
    }

    enum class Status {
        TRUE,
        FALSE,
    }

    class Pagination {
        var currentPage: Int = 0
        var totalPage: Int = 0
        var totalCount: Int = 0
    }

    class Builder {
        internal var data: Any? = null
        internal var status: Status? = null
        internal var error: Any? = null
        internal var message: String? = null
        internal var pagination: Pagination? = null
        internal var httpStatusCode: HttpStatusCode? = null

        @JvmName(name = "data")
        fun data(data: Any?): Builder {
            this.data = data
            return this
        }

        @JvmName(name = "status")
        fun status(status: Status?): Builder {
            this.status = status
            return this
        }

        @JvmName(name = "error")
        fun error(error: Any?): Builder {
            this.error = error
            return this
        }

        @JvmName(name = "message")
        fun message(message: String?): Builder {
            this.message = message
            return this
        }

        @JvmName(name = "pagination")
        fun pagination(pagination: Pagination?): Builder {
            this.pagination = pagination
            return this
        }

        @JvmName(name = "httpStatusCode")
        fun httpStatusCode(httpStatusCode: HttpStatusCode?): Builder {
            this.httpStatusCode = httpStatusCode
            return this
        }

        fun build(): ApiResponseKotlin {
            return ApiResponseKotlin(this)
        }
    }

    companion object {
        @Serial
        private const val serialVersionUID = 1L

        @JvmStatic
        fun builder(): Builder {
            return Builder()
        }
    }
}
