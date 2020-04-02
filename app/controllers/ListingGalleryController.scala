package controllers

import authentication.AuthenticationAction
import javax.inject._
import play.api.mvc._
import javax.inject.Singleton
import models.FilmDetails
import scala.concurrent.ExecutionContext.Implicits.global


@Singleton
class ListingGalleryController @Inject()(cc: ControllerComponents, val mongoService: DBManager) extends AbstractController(cc) {

  def createFilm(): Action[AnyContent] = Action.async {
    val actors = List("Daniel Radcliffe", "Rupert Grint", "Emma Watson")
    val showTimes = List("14:00", "17:00", "21:00")
    val futureResult = mongoService.createMovie(FilmDetails("Harry Potter and the Deathly Hallows – Part 2", "David Yates", actors, "Fantasy", showTimes, "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTgXSuLAFerQGZdPCWv8EHI_ucQq6RTl3xf91F4aN54PDA_oCtB"))
    futureResult.map(_ => Ok("Movie Created"))
  }

  def listingGallery(): Action[AnyContent] = Action.async {
    mongoService.findCurrentMovies().map(filmList =>
      Ok(views.html.listings(filmList))
    )
  }

  def currentFilmsInfo(id: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    mongoService.findCurrentMovies().map { films =>
      Ok(views.html.currentFilmsInfo(films.filter(film => id == film._id.toString()).head))
    }
  }
}
