package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.OFormat

case class EditForm( newComment :String )

object EditForm
{
  val editForm :Form[EditForm] = Form(
    mapping(
      "newComment" -> nonEmptyText
    )(EditForm.apply)(EditForm.unapply)
  )

  import play.api.libs.json.Json

  implicit val userFormat :OFormat[EditForm] = Json.format[EditForm]
}