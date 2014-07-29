package dao

import entities.BlogPost
import play.api.db.DB
import play.api.Play.current
import anorm._

/**
 * Created by Ville on 23.7.2014.
 */
object BlogPostDAO {

  def getPosts: Seq[BlogPost] = DB.withConnection { implicit conn =>
    val query = SQL("Select * from BLOGPOST")
    query().map(row =>
      parseBlogPost(row)
    ).toList
  }

  def findById(target: Int): Option[BlogPost] = DB.withConnection { implicit conn =>
    val query = SQL(
      """
        select * from BLOGPOST b
        where b.id = {id};
      """).on("id" -> target)
    val result = query().map(row => {
      parseBlogPost(row)
    })
    if(result.isEmpty) {
      None
    } else {
      Option[BlogPost](result.head)
    }
  }

  def parseBlogPost(row: Row): BlogPost = {
    BlogPost(row[Int]("id"), row[String]("title"), row[String]("content"))
  }

}
