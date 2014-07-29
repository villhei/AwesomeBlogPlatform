package controllers


import dao.{UserDAO, BlogCommentDAO, BlogPostDAO}
import play.api._
import play.api.libs.json.{JsError, JsValue, JsArray, Json}
import play.api.mvc._
import entities.{UserSignup, BlogPost}

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def signup = Action {
    Ok(views.html.signup())
  }

  def newUser = Action(BodyParsers.parse.json) { request =>
    val userInfoResult = request.body.validate[UserSignup]
    userInfoResult.fold(
      errors => {
        BadRequest(Json.obj("status" -> "BAD REQUEST"
          , "message" -> JsError.toFlatJson(errors)))
      },
      userInfo => {
        UserDAO.newUser(userInfo) match {
          case Some(user) => {
            Logger.debug("Responding with: " + user);
            Ok(Json.toJson(user))
          }
          case None => {
            BadRequest(Json.obj("status" -> "BAD_REQUEST",
              "message" -> "Error creating user"))
          }
        }
      }
    )
  }

  def posts = Action {
    val response = Json.toJson(BlogPostDAO.getPosts)
    Ok(response)
  }

  def postById(id: Int) = Action {
    val blogPost = BlogPostDAO.findById(id)
    val response = blogPost match {
      case Some(post) =>
        Json.toJson(post)
      case None =>
        Json.obj("error" -> "Post not found")
    }
    Ok(response)
  }

  def postCommentsById(id: Int) = Action {
    val blogPost = BlogPostDAO.findById(id)
    val response = blogPost match {
      case Some(post) =>
        Json.toJson(BlogCommentDAO.findByPost(post));
      case None =>
        Json.obj("error" -> "Post not found")
    }
    Ok(response)
  }
}