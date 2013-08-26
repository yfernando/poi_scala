import play.api._

import models._
import anorm._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }

}

/**
 * Initial set of data to be imported
 * in the sample application.
 */
object InitialData {

  def insert() = {

    if(User.findAll.isEmpty) {

      Seq(
        User("jo@gmail.com", "Jo Bort", "password"),
        User("maxi@gmail.com", "Maxi Million", "password"),
        User("yohan@gmail.com", "Yohan Fernando", "password"),
        User("Nick@gmail.com", "Nick Cleg", "password")
      ).foreach(User.create)


      Seq(
        PoiCategory(1,"Pub","some path"),
        PoiCategory(2,"Hotel","some path"),
        PoiCategory(3,"Airport","some path"),
        PoiCategory(4,"Underground","some path")
      ).foreach(PoiCategory.create)

    }

  }

}