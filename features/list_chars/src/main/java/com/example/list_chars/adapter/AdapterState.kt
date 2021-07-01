
package com.example.list_chars.adapter

sealed class AdapterState(
    val RowExtra: Boolean
) {
    object AddChar : AdapterState(RowExtra = true)

    object AddLoad : AdapterState(RowExtra = true)

    object AddError : AdapterState(RowExtra = true)

    fun isAddError() = this is AddError


}
