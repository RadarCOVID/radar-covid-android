package com.indra.contacttracing.models.exception

class UnknownException : Exception() {
  override val message: String?
    get() = "Internal service error"
}