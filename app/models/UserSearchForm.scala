package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.OFormat

case class UserSearchForm( name :String )

object UserSearchForm
{
  val searchForm :Form[UserSearchForm] = Form(
    mapping(
      "name" -> nonEmptyText
    )(UserSearchForm.apply)(UserSearchForm.unapply)
  )

  import play.api.libs.json.Json

  implicit val userFormat :OFormat[UserSearchForm] = Json.format[UserSearchForm]
}