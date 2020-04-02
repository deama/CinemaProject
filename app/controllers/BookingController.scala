package controllers

import authentication.AuthenticationAction
import javax.inject.{Inject, Singleton}
import models.{BookingForm, FilmDetails, PaymentForm}
import play.api.mvc.{AbstractController, Action, AnyContent, Call, ControllerComponents, Request}

import scala.concurrent._
import ExecutionContext.Implicits.global

@Singleton
class BookingController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction, val db: DBManager) extends AbstractController(cc) with play.api.i18n.I18nSupport {
  def booking(id: String): Action[AnyContent] = authAction.async { implicit request: Request[AnyContent] =>
    db.findCurrentMovies().map { films =>
      val filmDetails: FilmDetails = films.filter(film => id == film._id.toString()).head
      val seq = filmDetails.showTimes.map(item => {
        (item, item)
      }).toSeq

      Ok(views.html.booking(BookingForm.bookingForm, id, filmDetails.title, seq))
    }
  }

  def bookingSubmit(id: String): Action[AnyContent] = authAction.async { implicit request: Request[AnyContent] =>
    db.findCurrentMovies().map { films =>
      val filmDetails: FilmDetails = films.filter(film => id == film._id.toString()).head
      val seq = filmDetails.showTimes.map(item => {
        (item, item)
      }).toSeq

      BookingForm.bookingForm.bindFromRequest.fold({ formWithErrors =>
        BadRequest(views.html.booking(formWithErrors, id, filmDetails.title, seq))
      }, { bookingForm =>
        Redirect(routes.DBManager.createBooking(filmDetails._id.toString(), filmDetails.title, bookingForm.screeningTime,
          bookingForm.nameOfBooker, bookingForm.adults, bookingForm.children, bookingForm.concession))
      })
    }
  }
}
