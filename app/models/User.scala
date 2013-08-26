package models

/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 20/08/2013
 * Time: 22:30
 * To change this template use File | Settings | File Templates.
 */
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class User(email: String, name: String, password: String)

object User {

  // -- Parsers

  /**
   * Parse a User from a ResultSet
   */
  val simple = {
    get[String]("users.email") ~
      get[String]("users.name") ~
      get[String]("users.password") map {
      case email~name~password => User(email, name, password)
    }
  }

  // -- Queries

  /**
   * Retrieve a User from email.
   */
  def findByEmail(email: String): Option[User] = {
    DB.withConnection("poi") { implicit connection =>
      SQL("select * from users where email = {email}").on(
        'email -> email
      ).as(User.simple.singleOpt)
    }
  }

  /**
   * Retrieve all users.
   */
  def findAll: Seq[User] = {
    DB.withConnection("poi")  { implicit connection =>
      SQL("select * from users").as(User.simple *)
    }
  }

  /**
   * Authenticate a User.
   */
  def authenticate(email: String, password: String): Option[User] = {
    DB.withConnection("poi") { implicit connection =>
      SQL(
        """
         select * from users where
         email = {email} and password = {password}
        """
      ).on(
        'email -> email,
        'password -> password
      ).as(User.simple.singleOpt)
    }
  }

  /**
   * Create a User.
   */
  def create(user: User): User = {
    DB.withConnection("poi") { implicit connection =>
      SQL(
        """
          insert into users (email,name,password) values (
            {email}, {name}, {password}
          )
        """
      ).on(
        'email -> user.email,
        'name -> user.name,
        'password -> user.password
      ).executeInsert()

      user

    }
  }

}