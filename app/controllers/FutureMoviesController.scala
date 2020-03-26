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
    val actors = List("Dave", "Rob","Ryan")
    val showTimes = List("11:45","15:00","19:00")
    val futureResult = mongoService.createFutureMovie(FutureFilmDetails("Harry Potter","David Yates",actors,"Fantasy",showTimes,"https://vignette.wikia.nocookie.net/harrypotter/images/2/23/Hp6_poster_br.jpg/revision/latest/scale-to-width-down/673?cb=20141215114252"))
    futureResult.map(_ => Ok("New Release Movie Created"))
  }

  def futureListingsGallery(): Action[AnyContent] = Action.async {
    mongoService.findFutureMovies().map(futureFilmList =>
    Ok(views.html.newReleaseListings(futureFilmList))
    )
  }

}
