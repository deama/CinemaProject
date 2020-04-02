package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.OFormat

case class BookingForm(screeningTime: String, nameOfBooker: String, adults: Int, children: Int, concession: Int)

object BookingForm {
  val bookingForm: Form[BookingForm] = Form(
    mapping(
      "screeningTime" -> nonEmptyText,
      "nameOfBooker" -> nonEmptyText,
      "adults" -> default(number(max = 10), 0),
      "children" -> default(number(max = 10), 0),
      "concession" -> default(number(max = 10), 0)
    )(BookingForm.apply)(BookingForm.unapply)
  )
}