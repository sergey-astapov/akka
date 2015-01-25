package com.cqrs.backend.model

object Protocol {
  case class GetState()
  case class StateResult(total: Int)
}
