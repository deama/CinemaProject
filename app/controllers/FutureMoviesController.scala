package controllers
import authentication.AuthenticationAction
import javax.inject._
import play.api.mvc._
import javax.inject.Singleton
import models.FutureFilmDetails

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class FutureMoviesController @Inject() (cc: ControllerComponents, val mongoService: MongoService) extends AbstractController (cc)
{
  def createFutureFilm(): Action[AnyContent] = Action.async{
    val actors = List("Tom Cruise", "Val Kilmer","Jennifer Connelly")
    val showTimes = List("14:45","18:45","22:30")
    val futureResult = mongoService.createFutureMovie(FutureFilmDetails("Top Gun: Maverick","Joseph Kosinski",actors,"Adventure/Action",showTimes,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS2NGKKI9_WhX5B8FBicetcfvfNE1g-2e--MvIBGdhNOpmNVmUY"))
    futureResult.map(_ => Ok("New Release Movie Created"))
  }

  def futureListingsGallery(): Action[AnyContent] = Action.async {
    mongoService.findFutureMovies().map(futureFilmList =>
    Ok(views.html.newReleaseListings(futureFilmList))
    )
  }

  def futureFilmsInfo(id:String): Action[AnyContent] = Action.async{ implicit request:
  Request[AnyContent] =>
    mongoService.findFutureMovies().map{ films =>
      Ok(views.html.futureFilmsInfo(films.filter(film => id == film._id.toString()).head))
    }
  }

}
