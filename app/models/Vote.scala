package models

import java.util.Date
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import java.text.{DateFormat, SimpleDateFormat}

/**
 * Vote object
 *
 * Created with IntelliJ IDEA.
 * User: yfernan
 */
case class Vote(id: Long,
                userId: Long,
                poiCatId: Long,
                externalPoiId: String,
                voted: Boolean,
                dateVoted: Date)

object Vote {

  // -- Parsers

  /**
   * Parse a Vote from a ResultSet
   */
  val simple = {
      get[Long]("votes.id") ~
      get[Long]("votes.user_id") ~
      get[Long]("votes.poi_cat_id") ~
      get[String]("votes.external_poi_id") ~
      get[Boolean]("votes.voted") ~
      get[Date]("votes.date_voted") map {
        case id ~ user_id ~ poi_cat_id ~ external_poi_id ~ voted ~ date_voted =>
          Vote(id, user_id, poi_cat_id, external_poi_id, voted, date_voted)
      }
  }

  // -- Queries

  /**
   * Retrieve the last vote given for the POi by the user.
   * If no vote could be found return null.
   *
   * @param userId user id
   * @param exPoiId selected poi
   * @return Vote object if found or Null(not yet voted)
   */
  def findLastVote (userId: Long, exPoiId: String): Option[Vote] = {
    DB.withConnection("poi") { implicit connection =>
      SQL(
          """
            |select *
            |from votes
            |where user_id = {userId} and
            |      external_poi_id = {exPoiId};
          """.stripMargin
      ).on(
          "userId" -> userId,
          "exPoiId" -> exPoiId
      ).as (Vote.simple.singleOpt)
    }
}

  /**
   * Create a Vote.
   */
  def createVote (userId: Long, poiCatId: Long, extPoiId: String, userVote: String) = {

    DB.withConnection("poi") { implicit connection =>
      SQL(
        """
          |insert into votes
          |(user_id, poi_cat_id, external_poi_id, voted, date_voted)
          |values
          |({userId}, {poiCatId}, {extPoiId}, {vote}, {date_voted});
        """.stripMargin
      ).on(
          'userId -> userId,
          'poiCatId -> poiCatId,
          'extPoiId -> extPoiId,
          'vote -> userVote.toBoolean,
          'date_voted -> new Date()
      ).executeInsert()
    }
  }

  /**
   * Update a Vote.
   */
  def updateVote (userId: Long, poiCatId: Long, extPoiId: String, userVote: String): Int = {

    DB.withConnection("poi") { implicit connection =>
      SQL(
        """
          |update votes
          |set voted = {vote}, date_voted = {date_voted}, poi_cat_id = {poiCatId}
          |where user_id = {userId} and external_poi_id = {extPoiId};
        """.stripMargin
      ).on(
          'userId -> userId,
          'poiCatId -> poiCatId,
          'vote -> userVote.toBoolean,
          'date_voted -> new Date(),
          'extPoiId -> extPoiId
      ).executeUpdate()
    }
  }


}
