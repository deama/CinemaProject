package controllers

import java.util.NoSuchElementException

import authentication.AuthenticationAction
import javax.inject.{Inject, Singleton}
import models.{BookingForm, FilmDetails, PaymentForm}
import play.api.mvc.{AbstractController, Action, AnyContent, Call, ControllerComponents, Request}

import scala.concurrent._
import ExecutionContext.Implicits.global

@Singleton
class SearchController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction, val db: DBManager) extends AbstractController(cc) with play.api.i18n.I18nSupport
{
  def search( title :String ) :Action[AnyContent] = authAction.async { implicit request :Request[AnyContent] =>
    db.findCurrentMovies().map{ films =>
      var filmDetails :FilmDetails = null
      try { filmDetails = films.filter(film => film.title.toString().toLowerCase.contains(title) ).head }
      catch{ case ex: NoSuchElementException =>{  } }

      if( filmDetails == null )
      {
        Ok( views.html.emptySearch() )
      }
      else
      {
        Redirect( routes.ListingGalleryController.currentFilmsInfo(filmDetails._id.toString()) )
      }
    }
  }
}
