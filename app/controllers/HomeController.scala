package controllers

import authentication.AuthenticationAction
import javax.inject.{Inject, Singleton}
import models.{BookingForm, PaymentForm}
import play.api.mvc._
import models.FilmDetails
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class HomeController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction, val app: DBManager) extends AbstractController(cc) with play.api.i18n.I18nSupport
{
//  def index() :Action[AnyContent] = authAction { implicit request :Request[AnyContent] =>
//    //Ok( views.html.index("QACinema") )
//    Ok( views.html.home() )
//    //Ok( views.html.booking(BookingForm.bookingForm, "asdf") )
//  }

  def index(): Action[AnyContent] = Action.async {
    app.findCurrentMovies().map(filmList =>
      Ok( views.html.home(filmList) )
    )
  }



}
