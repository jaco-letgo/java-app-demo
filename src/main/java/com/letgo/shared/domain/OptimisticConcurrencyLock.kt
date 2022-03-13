package com.letgo.shared.domain

class OptimisticConcurrencyLock(
    message: String = "Forbidden operation due to Optimistic Lock",
    cause: Throwable? = null,
) : RuntimeException(message, cause) {
    companion object {
        fun causedBy(exception: Throwable) = OptimisticConcurrencyLock(cause = exception)
    }
}
