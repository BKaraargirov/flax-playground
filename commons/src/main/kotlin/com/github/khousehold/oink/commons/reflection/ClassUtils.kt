package com.github.khousehold.oink.commons.reflection

import java.lang.Exception
import java.lang.reflect.Method
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

object ClassUtils {
  fun getClassName(clsInfo: KClass<*>): String {
    return when {
      clsInfo.qualifiedName != null -> clsInfo.qualifiedName!!
      clsInfo.simpleName != null -> clsInfo.simpleName!!
      else -> throw Exception("No class name found")
    }
  }

  fun hasGetter(classInfo: KClass<*>,field: KProperty<*>): Boolean =
      hasGetter(classInfo.java.declaredMethods, field)


  fun hasGetter(methods: Array<Method>, field: KProperty<*>): Boolean =
      methods.any { it.name.toLowerCase().contains("get" + field.name.toLowerCase()) }
}