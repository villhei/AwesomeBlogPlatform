package entities

import dao.BlogCommentDAO
import play.api.libs.json.{Json, JsValue, Writes}

/**
 * Created by Ville on 23.7.2014.
 */
case class BlogComment(id: Int, author: String, comment: String)

object BlogComment {

  implicit val blogCommentWrites = new Writes[BlogComment] {
    def writes(bp: BlogComment): JsValue = {
      Json.obj(
        "id" -> bp.id,
        "author" -> bp.author,
        "comment" -> bp.comment
      )
    }
  }
}