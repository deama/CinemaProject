package controllers

import authentication.AuthenticationAction
import javax.inject._
import play.api.mvc._
import javax.inject.Singleton
import models.FilmDetails
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ListingGalleryController @Inject() (cc: ControllerComponents, val mongoService: MongoService) extends AbstractController (cc)
{

  def test(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok("Hello World!")
  }
  def hello(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.listings())
  }
  def createFilm() = Action.async{
    val actors = List("Leonardo", "James","Alan")
    val showTimes = List("14:30","18:00","21:30")
    val futureResult = mongoService.createFilm(FilmDetails("Inception", "Christopher Nolan", actors, "Sci-Fi", showTimes, "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT3VT-Ynisr-nRV7R65rC8iZ4jyJKgLHU7wvROHYTnc1X7zg_4i"))
    futureResult.map(_ => Ok("Film Created"))
  }
}
