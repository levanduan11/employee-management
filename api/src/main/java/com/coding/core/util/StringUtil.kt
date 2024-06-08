package com.coding.core.util

fun camelCaseToSnakeCase(input: String): String {
    return input
        .split("(?=[A-Z])".toRegex())
        .joinToString("_")
        .trim('_')
        .lowercase()
}

fun camelCaseToSnakeCase2(input: String): String {
    return buildString {
        val chars = input.toCharArray()
        for (i in chars.indices) {
            val char = chars[i]
            if (char.isUpperCase() && i > 0) {
                append('_')
            }
            append(char.lowercase())
        }
    }
}
