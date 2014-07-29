package entities

import java.util.Date

import play.api.libs.json.{Writes, JsValue, Json}
import play.api.Logger


/**
 * Created by Ville on 29.7.2014.
 */
case class User(id: Option[Int], nickName: String, realName: String, email: String, registerDate: Date, password: Option[String], salt: Option[String]) {

  def getJsonId: String = {
    Logger.info("User id: " + id)
    id match {
      case Some(id) => {
        id.toString
      }
      case None => {
        "null"
      }
    }
  }

  override def toString = s"User: id=$id, nickName=$nickName, realName=$realName, email=$email, registerDate=$registerDate"
}

object User {

  def apply(nickName: String, realName: String, email: String, registerDate: Date, password: String, salt: String): User = {
    Logger.info("using first constructor for user")
    new User(None, nickName, realName, email, registerDate, Option[String](password), Option[String](salt))
  }

  def apply(id: Int, nickName: String, realName: String, email: String, registerDate: Date): User = {
    Logger.info("using second constructor for user")
    new User(Option[Int](id), nickName, realName, email, registerDate, None, None)
  }

  implicit val userWrites = new Writes[User] {
    def writes(user: User): JsValue = {
      Json.obj(
        "id" -> user.getJsonId,
        "email" -> user.email,
        "nickName" -> user.nickName,
        "realName" -> user.realName,
        "registerDate" -> user.registerDate
      )
    }
  }
}