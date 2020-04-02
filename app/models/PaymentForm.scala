package models

import java.util.Date

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.OFormat

case class PaymentForm( name :String, cardNumber :Int, expDate :Date, securityCode :Int )

object PaymentForm
{
  val paymentForm :Form[PaymentForm] = Form(
    mapping(
      "name" -> nonEmptyText,
      "cardNumber" -> number,
      "expDate" -> date,
      "securityCode" -> number
    )(PaymentForm.apply)(PaymentForm.unapply)
  )
}