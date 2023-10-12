package com.example.chall3.ui.login.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ResultLogin<out T : Any> {

    data class Success<out T : Any>(val data: T) : ResultLogin<T>()
    data class Error(val exception: Exception) : ResultLogin<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}