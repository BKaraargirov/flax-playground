package com.github.khousehold.oink.commons.utils

fun <T> Iterator<T>.toList(): List<T> =
    ArrayList<T>().apply {
      while (hasNext())
        this += next()
    }