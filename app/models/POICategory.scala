package models

import play.api.Play.current
import anorm.SqlParser._
import anorm._
import play.api.db._


/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 11/08/2013
 * Time: 00:10
 * To change this template use File | Settings | File Templates.
 */
case class PoiCategory(id: Long, name: String, imagePath: String)


object PoiCategory {





  // -- Parsers

  /**
   * Parse a User from a ResultSet
   */
  val simple = {
    get[Long]("poi_categories.id") ~
      get[String]("poi_categories.name") ~
      get[String]("poi_categories.image_path") map {
      case id ~ name ~ image_path => PoiCategory(id, name, image_path)
    }
  }

  def getAllPois(): List[Poi] = {
    return List[Poi]()
  }

  def findAll(): List[PoiCategory] = {

    DB.withConnection("poi") {
      implicit connection =>
        SQL("select * from poi_categories").as(PoiCategory.simple *)
    }
  }

  def create(poiCategory: PoiCategory) = {

    DB.withConnection("poi") {
      implicit connection =>
        SQL( """
        insert into poi_categories (name, image_path) values (
        {name}, {image_path}
        )
             """
        ).on(
          'name -> poiCategory.name,
          'image_path -> poiCategory.imagePath
        ).executeInsert()

        poiCategory

    }

  }
}


