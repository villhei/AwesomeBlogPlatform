package entities

import play.api.libs.json.{Writes, JsValue, Json}

/**
 * Created by Ville on 23.7.2014.
 */
case class BlogPost(id: Option[Int], title: String, content: String, comments: Option[Seq[BlogComment]])

object BlogPost {

  def apply(id: Int, title: String, content: String, comments: Option[Seq[BlogComment]]) = {
    new BlogPost(Option[Int](id), title, content, comments)
  }

  def apply(title: String, content: String) = {
    new BlogPost(None, title, content, None)
  }

  def apply(id: Int, title: String, content: String) = {
    new BlogPost(Option[Int](id), title, content, None);
  }
  implicit val blogPostWrites = new Writes[BlogPost] {
    def writes(bp: BlogPost): JsValue = {
      Json.obj(
        "id" -> bp.id,
        "title" -> bp.title,
        "content" -> bp.content
      )
    }
  }
}