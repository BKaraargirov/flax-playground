package com.github.khousehold.oink.commons.filters.annotations

/**
 * Ignore a field and make it non searchable.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class NotFilterable {
}