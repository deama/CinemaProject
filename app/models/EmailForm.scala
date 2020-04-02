package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.OFormat

case class EmailForm( address :String, subject :String, content :String )

object EmailForm
{
  val emailForm :Form[EmailForm] = Form(
    mapping(
      "address" -> email,
      "subject" -> nonEmptyText,
      "content" -> nonEmptyText
    )(EmailForm.apply)(EmailForm.unapply)
  )
}