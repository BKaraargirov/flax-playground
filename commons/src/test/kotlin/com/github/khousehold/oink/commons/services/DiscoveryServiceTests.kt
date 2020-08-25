package com.github.khousehold.oink.commons.services

import com.github.khousehold.oink.commons.reflection.ClassUtils
import com.github.khousehold.oink.commons.services.testData.NotCompletelyFilterableClass
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubclassOf

class DiscoveryServiceTests: StringSpec({
  val unitUnderTest = DiscoveryService(ClassUtils)

  "Filterable classes should be discovered" {
    val targetPackage = "com.github.khousehold.oink.commons.services"
    val expectedResultSize = 2

    val actualResult = unitUnderTest.findFilterables(targetPackage)

    actualResult.size shouldBe expectedResultSize
  }

  "Discovery service should be able to find only filterable fields" {
    val targetPackage = "com.github.khousehold.oink.commons.services"
    val targetClass = NotCompletelyFilterableClass::class.simpleName
    val expectedResultSize = 2

    val cls = unitUnderTest.findFilterables(targetPackage).filter { it.simpleName == targetClass }.first()
    val result = unitUnderTest.getFilterableFields(cls)

    result.classInfo.isSubclassOf(NotCompletelyFilterableClass::class) shouldBe true
    result.filterableProperties.size shouldBe expectedResultSize
    result.filterableProperties.containsKey("age") shouldBe true
    result.filterableProperties.get("age") shouldBe Int::class.createType()
    result.filterableProperties.containsKey("name") shouldBe true
    result.filterableProperties.get("name") shouldBe String::class.createType()
  }

})