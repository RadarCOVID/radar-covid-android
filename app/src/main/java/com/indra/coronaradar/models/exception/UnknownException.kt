package com.indra.coronaradar.models.exception

class UnknownException : Exception() {
  override val message: String?
    get() = "Internal service error"
}