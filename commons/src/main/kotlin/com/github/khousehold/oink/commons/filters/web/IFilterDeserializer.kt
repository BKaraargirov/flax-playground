package com.github.khousehold.oink.commons.filters.web

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.github.khousehold.oink.commons.filters.models.IFilter

class IFilterDeserializer : JsonDeserializer<IFilter> {
  override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): IFilter {
    val oc = p!!.codec
    val node: JsonNode = oc.readTree(p)

    if(node.has("type")) {
      // This is a Logical filter
        val typeName = node.asText()
        val subFilters: List<IFilter> =
            if(node.has("fields")) {
              node.get("fields").map {  deserialize() }
        } else listOf()

    } else {
      // This is a Filter
    }

    TODO("Not yet implemented")
  }
}