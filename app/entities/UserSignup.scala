package entities

import java.util.Date

import play.api.libs.json.{JsPath, Reads, Json, JsValue}
import play.api.libs.functional.syntax._

/**
 * Created by Ville on 29.7.2014.
 */
case class UserSignup(nickName: String, realName: String, email: String, password: String, passwordConfirm: String) {

}

object UserSignup {

  implicit val userSignUpRead: Reads[UserSignup] = (
    (JsPath \ "nickName").read[String] and
      (JsPath \ "realName").read[String] and
      (JsPath \ "email").read[String] and
      (JsPath \ "password").read[String] and
      (JsPath \ "passwordConfirm").read[String])(UserSignup.apply _)

}