package models

import java.util.Date

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID

case class PaymentData(id: String, name: String, cardNumber: String, expDate: String, securityCode: String, movieTitle: String)

object PaymentData {

  import reactivemongo.play.json._
  import reactivemongo.play.json.collection.JSONCollection
  import play.api.libs.json._

  implicit val bookingFormat: OFormat[PaymentData] = Json.format[PaymentData]
}