package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current


case class Member(var id: Long,
                  var name: String,
                  var email: String)

object Member {

  def all(): List[Member] = DB.withConnection { implicit c =>
    SQL("select * from members").as(member *)
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from members where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

  def getMember(id: Long): List[Member] = {
    DB.withConnection { implicit connection =>
      SQL("select * from members where id = {id}").on(
        'id -> id).as(member *)
    }
  }

  def add(name: String, email: String) {
    DB.withConnection { implicit connection =>
      SQL("insert into members (name, email) values ({name},{email})").on(
        'name -> name,
        'email -> email).executeUpdate()
    }
  }

  val member = {
      get[Long]("id") ~
      get[String]("name") ~
      get[String]("email") map {
      case id~name~email => Member(id, name, email)
    }
  }

}
