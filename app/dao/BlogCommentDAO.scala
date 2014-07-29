package dao

import anorm._
import entities.{BlogPost, BlogComment}
import play.api.db._
import play.api.Play.current

/**
 * Created by Ville on 23.7.2014.
 */
object BlogCommentDAO {

  def findByPost(post: BlogPost) = DB.withConnection { implicit conn =>
    val query = SQL(
      """
        select * from BLOGCOMMENT b
        where b.post_id = {id}
      """).on("id" -> post.id)

    query().map(row =>
      parseComment(row)
    ).toList
  }

  def parseComment(row: Row) = {
    BlogComment(row[Int]("id"), row[String]("author"), row[String]("comment"))
  }

  def findById(target: Int): Option[BlogComment] = DB.withConnection { implicit conn =>
    val query = SQL(
      """
        select * from BLOGCOMMENT b
        where b.id = {id};
      """).on("id" -> target)
    val result = query().map(row => {
      parseComment(row)
    })
    if (result.isEmpty) {
      None
    } else {
      Option[BlogComment](result.head)
    }
  }
}