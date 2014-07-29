package dao

import java.util.Date
import anorm._
import entities.{BlogPost, UserSignup, User}
import play.Logger
import play.api.db.DB
import play.api.Play.current
import scala.util.Random
import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by Ville on 29.7.2014.
 */

object UserDAO {

  val SALT_LENGTH = 16

  def newUser(userInfo: UserSignup): Option[User] = {

    if (userInfo.password == userInfo.passwordConfirm) {
      val salt = BCrypt.gensalt(8)
      val passwordHash = BCrypt.hashpw(userInfo.password, salt)
      val registeredUser = User(userInfo.nickName, userInfo.realName, userInfo.email, new Date, passwordHash, salt)

      persistUser(registeredUser) match {
        case Some(user) => {
          Logger.info("New user created: " + user)
          Option[User](user)
        }
        case None => {
          None
        }
      }
    } else {
      None
    }
  }

  def findByEmail(email: String): Option[User] = DB.withConnection { implicit conn =>
    val query = SQL(
      """
        select * from USER u
        where u.email = {email};
      """).on("email" -> email)
    val result = query().map(row => {
      parseUser(row)
    })
    if (result.isEmpty) {
      None
    } else {
      Logger.info("UserDAO found: " + result.head);
      Option[User](result.head)
    }
  }

  def parseUser(row: Row): User = {
    User(row[Int]("id"), row[String]("nickname"), row[String]("realname"), row[String]("email"), new Date())
  }

  def isEmailUnique(email: String): Boolean = {
    findByEmail(email) match {
      case Some(value) => false
      case None => true
    }
  }

  def persistUser(user: User): Option[User] = DB.withConnection { implicit conn =>
    if (!isEmailUnique(user.email)) {
      None
    } else {
      val query = SQL(
        """
      insert into User(author, enabled, visible, nickname, realname, email, password, registerdate, salt)
      values (false, true, true, {nickname}, {realname}, {email}, {password}, {registerdate}, {salt});
        """).on("nickname" -> user.nickName,
          "realname" -> user.realName,
          "email" -> user.email,
          "password" -> user.password,
          "registerdate" -> user.registerDate,
          "salt" -> user.salt).executeInsert();
      findByEmail(user.email)
    }
  }

}
