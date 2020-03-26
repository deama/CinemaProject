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

  def createFilm(): Action[AnyContent] = Action.async{
    val actors = List("Dave", "Steve","John")
    val showTimes = List("11:30","17:00","21:45")
    val futureResult = mongoService.createMovie(FilmDetails("Inception", "Christopher Nolan", actors, "Sci-Fi", showTimes, "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT3VT-Ynisr-nRV7R65rC8iZ4jyJKgLHU7wvROHYTnc1X7zg_4i"))
    futureResult.map(_ => Ok("Movie Created"))
  }
  def listingGallery(): Action[AnyContent] = Action.async {
    mongoService.findCurrentMovies().map(filmList =>
      Ok(views.html.listings(filmList))
    )
  }
}
