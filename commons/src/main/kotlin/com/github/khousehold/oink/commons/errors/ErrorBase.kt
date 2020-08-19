package com.github.khousehold.oink.commons.errors

open class ErrorBase(
  open val id: String,
  override val message: String,
  open val origin: String
) : RuntimeException()

