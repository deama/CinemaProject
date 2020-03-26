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
    val actors = List("Joaquin Pheonix", "Robert De Niro","Frances Conroy")
    val showTimes = List("12:45","15:45","19:45")
    val futureResult = mongoService.createMovie(FilmDetails("Joker", "Todd Phillips", actors, "Action", showTimes, "https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg"))
    futureResult.map(_ => Ok("Movie Created"))
  }
  def listingGallery(): Action[AnyContent] = Action.async {
    mongoService.findCurrentMovies().map(filmList =>
      Ok(views.html.listings(filmList))
    )
  }
}
