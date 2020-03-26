package controllers

import authentication.AuthenticationAction
import javax.inject.{Inject, Singleton}
import models.{BookingForm, PaymentForm}
import play.api.mvc.{AbstractController, Action, AnyContent, Call, ControllerComponents, Request}

@Singleton
class PaymentController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction, val app: DBManager) extends AbstractController(cc) with play.api.i18n.I18nSupport
{
  def paymentSubmit() :Action[AnyContent] = authAction { implicit request :Request[AnyContent] =>
    PaymentForm.paymentForm.bindFromRequest.fold({ formWithErrors =>
      BadRequest( views.html.payment(formWithErrors) )
    }, { paymentForm =>
      Redirect( routes.DBManager.createPayment(paymentForm.name, paymentForm.cardNumber, paymentForm.expDate.toString, paymentForm.securityCode) )
    })
  }
}
