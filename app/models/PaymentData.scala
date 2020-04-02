package models

case class PaymentData(id: String, name: String, cardNumber: String, expDate: String, securityCode: String, movieTitle: String)

object PaymentData {

  import play.api.libs.json._

  implicit val paymentFormat: OFormat[PaymentData] = Json.format[PaymentData]
}