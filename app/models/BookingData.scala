package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID

case class BookingData( id :String, movieTitle :String, screeningTime :String, nameOfBooker :String, adults :Int, children :Int, concession :Int )

object BookingData
{
  import reactivemongo.play.json._
  import reactivemongo.play.json.collection.JSONCollection
  import play.api.libs.json._

  implicit val bookingFormat :OFormat[BookingData] = Json.format[BookingData]
}