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
  def search( title :String ) :Action[AnyContent] = authAction.async { implicit request :Request[AnyContent] =>
    db.findCurrentMovies().map{ films =>
      var filmList :List[FilmDetails] = null
      try { filmList = films.filter(film => film.title.toString().toLowerCase.contains(title.toLowerCase) ) }
      catch{ case ex: NoSuchElementException =>{  } }

      if( filmList == null )
      {
        Await.result(
        db.findFutureMovies().map { films =>
          var futureFilmList :List[FutureFilmDetails] = null
          try { futureFilmList = films.filter(film => film.title.toString().toLowerCase.contains(title.toLowerCase) ) }
          catch{ case ex: NoSuchElementException =>{  } }

          if( futureFilmList == null )
          {
            Ok( views.html.emptySearch() )
          }
          else
          {
            Ok( views.html.newReleaseListings(futureFilmList) )
          }

        }, Duration.Inf )
      }
      else
      {
        Ok( views.html.listings(filmList) )
      }
    }
  }
}
