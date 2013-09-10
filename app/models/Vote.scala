package models

import java.util.Date
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

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
                vote: Boolean,
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
      get[Boolean]("votes.vote") ~
      get[Date]("votes.date_voted") map {
        case id ~ user_id ~ poi_cat_id ~ external_poi_id ~ vote ~ date_voted =>
          Vote(id, user_id, poi_cat_id, external_poi_id, vote, date_voted)
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
                |where  user_id = {userId} and
                |       external_poi_id = {exPoiId}
              """.stripMargin
          ).on(
              "userId" -> userId,
              "exPoiId" -> exPoiId
          ).as (Vote.simple.singleOpt)
      }
  }

  /**
   * Create a Vote.
   *
   * @param vote  new Vote object
   * @return
   */
  def createVote (vote: Vote) = {
      DB.withConnection("poi") { implicit connection =>
          SQL(
            """
              |insert into votes
              |(user_id, poi_cat_id, external_poi_id, vote, date_voted)
              |values
              |({userId}, {poiCatId}, {extPoiId}, {vote}, {date_voted})
            """.stripMargin
          ).on(
              'userId -> vote.userId,
              'poiCatId -> vote.poiCatId,
              'extPoiId -> vote.externalPoiId,
              'vote -> vote.vote,
              'date_voted -> vote.dateVoted
          ).executeInsert()
          vote
    }
  }

  /**
   * Update a Vote.
   *
   * @param vote update Vote object
   * @return  returns the number of rows updated
   */
  def updateVote (vote: Vote): Int = {
      DB.withConnection("poi") { implicit connection =>
          SQL(
            """
              |update votes
              |set vote = {vote}, date_voted = {today}
              |where user_id = {userId} and external_poi_id = {extPoiId}
            """.stripMargin
          ).on(
              'userId -> vote.userId,
              'poiCatId -> vote.poiCatId,
              'vote -> vote.vote,
              'date_voted -> vote.dateVoted
          ).executeUpdate()
        }
  }


}
