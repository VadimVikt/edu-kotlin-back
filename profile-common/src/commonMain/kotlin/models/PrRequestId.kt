package models

import kotlin.jvm.JvmInline

@JvmInline
value class PrRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = PrRequestId("")
    }
}