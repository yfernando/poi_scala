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
        PoiCategory(1,"art_gallery","some path"),
        PoiCategory(2,"atm","some path"),
        PoiCategory(3,"bakery","some path"),
        PoiCategory(4,"bank","some path"),
        PoiCategory(5,"bar","some path"),
        PoiCategory(6,"beauty_salon","some path"),
        PoiCategory(7,"bicycle_store","some path"),
        PoiCategory(8,"book_store","some path"),
        PoiCategory(9,"bowling_alley","some path"),
        PoiCategory(10,"bus_station","some path"),
        PoiCategory(11,"cafe","some path"),
        PoiCategory(12,"campground","some path"),
        PoiCategory(13,"car_dealer","some path"),
        PoiCategory(14,"car_rental","some path"),
        PoiCategory(15,"car_repair","some path")
//        PoiCategory("car_wash","some path"),
//        PoiCategory("casino","some path"),
//        PoiCategory("cemetery","some path"),
//        PoiCategory("church","some path"),
//        PoiCategory("city_hall","some path"),
//        PoiCategory("clothing_store","some path"),
//        PoiCategory("convenience_store","some path"),
//        PoiCategory("courthouse","some path"),
//        PoiCategory("dentist","some path"),
//        PoiCategory("department_store","some path"),
//        PoiCategory("doctor","some path"),
//        PoiCategory("electrician","some path"),
//        PoiCategory("electronics_store","some path")

      ).foreach(PoiCategory.create)

    }

  }

}