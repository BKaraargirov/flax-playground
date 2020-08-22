package com.github.khousehold.oink.commons.reflection

import com.github.khousehold.oink.commons.services.testData.BasicFilterableClass
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ClassUtilsTests: StringSpec({
  class InternalTestClass(a: Int)

  val unitUnderTest = ClassUtils

  "Fully qualified name should be found" {
    val cls = BasicFilterableClass::class
    val expectedResult = cls.qualifiedName

    val actualResult = unitUnderTest.getClassName(cls)

    actualResult shouldNotBe null
    actualResult shouldBe expectedResult
  }

  "Internal class should use simple name" {
    val cls = InternalTestClass::class
    val expectedResult = cls.simpleName

    val actualResult = unitUnderTest.getClassName(cls)

    actualResult shouldNotBe null
    actualResult shouldBe expectedResult
  }

  "Anonymous classes should throw exception" {
    val anonmous = object {
      val a: Int = 5
    }
    val cls = anonmous::class

    val exception = shouldThrow<Exception> {
      unitUnderTest.getClassName(cls)
    }

    exception.message shouldBe "No class name found"
  }
})