package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.OFormat

case class BookingForm( screeningTime :String, nameOfBooker :String, adults :Int, children :Int, concession :Int )

object BookingForm
{
  val bookingForm :Form[BookingForm] = Form(
    mapping(
      "screeningTime" -> nonEmptyText,
      "nameOfBooker" -> nonEmptyText,
      "adults" -> number,
      "children" -> number,
      "concession" -> number
    )(BookingForm.apply)(BookingForm.unapply)
  )
}