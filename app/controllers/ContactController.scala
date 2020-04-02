package controllers

import authentication.AuthenticationAction
import javax.inject.{Inject, Singleton}
import models.{BookingForm, FilmDetails, EmailForm}
import play.api.mvc.{AbstractController, Action, AnyContent, Call, ControllerComponents, Request}

import scala.concurrent._
import ExecutionContext.Implicits.global

@Singleton
class ContactController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction, val db: DBManager) extends AbstractController(cc) with play.api.i18n.I18nSupport {
  def contact(): Action[AnyContent] = authAction { implicit request: Request[AnyContent] =>

    Ok(views.html.contact(EmailForm.emailForm))
  }

  def emailSubmit(): Action[AnyContent] = authAction { implicit request: Request[AnyContent] =>

    EmailForm.emailForm.bindFromRequest.fold({ formWithErrors =>
      BadRequest(views.html.contact(formWithErrors))
    }, { emailForm =>
      Ok("Email Submitted")
    })
  }
}
