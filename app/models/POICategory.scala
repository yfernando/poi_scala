package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

/**
 * Poi Category object
 *
 * User: yfernan
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

  /**
   * Find all Poi categories
   *
   * @return  List of the PoiCategory objects
   */
  def findAll(): List[PoiCategory] = {
      DB.withConnection("poi") { implicit connection =>
          SQL(
            """
              |select * from poi_categories
            """"
          ).as(PoiCategory.simple *)
      }
  }

  /**
   * Create a POi category
   *
   * @param poiCategory  new poi category object
   * @return
   */
  def create(poiCategory: PoiCategory) = {
      DB.withConnection("poi") { implicit connection =>
          SQL(
            """
              |insert into poi_categories (name, image_path)
              |values ({name}, {image_path})
            """
          ).on(
              'name -> poiCategory.name,
              'image_path -> poiCategory.imagePath
          ).executeInsert()
          poiCategory
      }
  }
}


