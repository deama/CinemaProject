package controllers

import java.util.NoSuchElementException

import authentication.AuthenticationAction
import javax.inject.{Inject, Singleton}
import models.{BookingForm, FilmDetails, FutureFilmDetails, PaymentForm}
import play.api.mvc.{AbstractController, Action, AnyContent, Call, ControllerComponents, Request}

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

@Singleton
class SearchController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction, val db: DBManager) extends AbstractController(cc) with play.api.i18n.I18nSupport
{
  def search( title :String ) :Action[AnyContent] = authAction { implicit request :Request[AnyContent] =>

    val filmList :List[FilmDetails] = Await.result( db.findCurrentMovies().map { films =>
      films.filter(film => film.title.toString().toLowerCase.contains(title.toLowerCase) )
    }, Duration.Inf )
    val futureFilmList :List[FutureFilmDetails] = Await.result( db.findFutureMovies().map { films =>
      films.filter(film => film.title.toString().toLowerCase.contains(title.toLowerCase) )
    }, Duration.Inf )

    Ok( views.html.searchResults(filmList, futureFilmList) )
  }
}
