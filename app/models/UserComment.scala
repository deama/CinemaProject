package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID

case class UserComment( val _id :BSONObjectID, name :String, comment :String )

object UserComment
{
  def apply(name :String, comment :String) :UserComment =
  {
    new UserComment( BSONObjectID.generate(), name, comment )
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
}

object JsonFormats
{
  import reactivemongo.play.json._
  import reactivemongo.play.json.collection.JSONCollection
  import play.api.libs.json._

  implicit val userCommentFormat :OFormat[UserComment] = Json.format[UserComment]
}