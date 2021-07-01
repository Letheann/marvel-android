
package com.example.core.network

 open class StatesRequest {
    data class SuccessRequest(
        val isNew: Boolean = false,
        val isEmpty: Boolean = false
    ) : StatesRequest()

    data class Loading(
        val isNew: Boolean = false
    ) : StatesRequest()

    data class Error(
        val isNew: Boolean = false
    ) : StatesRequest()
}
