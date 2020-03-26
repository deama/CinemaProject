package controllers

import authentication.AuthenticationAction
import javax.inject.{Inject, Singleton}
import models.{BookingForm, PaymentForm}
import play.api.mvc.{AbstractController, Action, AnyContent, Call, ControllerComponents, Request}

@Singleton
class BookingController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction, val app: DBManager) extends AbstractController(cc) with play.api.i18n.I18nSupport
{
  def bookingSubmit( movieTitle :String ) :Action[AnyContent] = authAction { implicit request :Request[AnyContent] =>
    BookingForm.bookingForm.bindFromRequest.fold({ formWithErrors =>
      BadRequest( views.html.booking(formWithErrors, movieTitle) )
    }, { bookingForm =>
      Redirect( routes.DBManager.createBooking(movieTitle, bookingForm.screeningTime,
        bookingForm.nameOfBooker, bookingForm.adults, bookingForm.children, bookingForm.concession) )
    })
  }
}
