package com.passgen.core.model

case class GeneratePassword(length: Integer)

case class PasswordResult(status: String, password: String, error: String)

object PasswordResult {
  def withPassword(pass: String) = PasswordResult("SUCCESS", pass, "")
  def withError(err: String) = PasswordResult("ERROR", "", err)
}
