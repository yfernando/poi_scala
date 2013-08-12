package controllers

import play.api.mvc._


/**
 * Created with IntelliJ IDEA.
 * User: yfernan
 * Date: 10/08/2013
 * Time: 05:36
 * To change this template use File | Settings | File Templates.
 */
object PoiController extends Controller {



  def getPoiCollectionForCategory (name: String) =  Action {

    //get POi collection


    Ok(content = views.html.poi(name))

  }
}
