package com.github.khousehold.oink.commons.reflection

import java.lang.Exception
import kotlin.reflect.KClass

object ClassUtils {
  fun getClassName(clsInfo: KClass<*>): String {
    return when {
      clsInfo.qualifiedName != null -> clsInfo.qualifiedName!!
      clsInfo.simpleName != null -> clsInfo.simpleName!!
      else -> throw Exception("No class name found")
    }
  }
}