package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID

case class UserComment( _id :String, name :String, comment :String )

object UserComment
{
  def apply(name :String, comment :String) :UserComment =
  {
    new UserComment( BSONObjectID.generate().toString, name, comment )
  }

  def unapply(userComment :UserComment) :Option[(String, String)] =
  {
    Option(userComment.name, userComment.comment)
  }

  val userForm :Form[UserComment] = Form(
    mapping(
      "name" -> nonEmptyText,
      "comment" -> nonEmptyText
    )(UserComment.apply)(UserComment.unapply)
  )

  import play.api.libs.json.Json

  implicit val userFormat :OFormat[UserComment] = Json.format[UserComment]
}