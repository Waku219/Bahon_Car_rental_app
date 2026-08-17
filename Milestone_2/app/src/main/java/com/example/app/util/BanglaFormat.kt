package com.example.app.util

private val banglaDigits = charArrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')

/** "3500" -> "৩৫০০" */
fun String.toBanglaDigits(): String = map { ch ->
    if (ch in '0'..'9') banglaDigits[ch - '0'] else ch
}.joinToString("")

/** 3500 -> "৩,৫০০" */
fun Long.toBanglaNumber(): String = "%,d".format(this).toBanglaDigits()

fun Int.toBanglaNumber(): String = this.toLong().toBanglaNumber()

/** 3500 -> "৳৩,৫০০" */
fun Long.toTaka(): String = "৳" + toBanglaNumber()

/** 4.8 -> "৪.৮" */
fun Double.toBanglaRating(): String = "%.1f".format(this).toBanglaDigits()
