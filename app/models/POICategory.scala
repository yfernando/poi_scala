package models

/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 11/08/2013
 * Time: 00:10
 * To change this template use File | Settings | File Templates.
 */
case class PoiCategory(id: Long, name: String) {

  def getAllPois(): List[Poi] = {
    return List[Poi]()
  }

}
