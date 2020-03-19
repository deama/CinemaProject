package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID

case class UserComment( id :String, name :String, comment :String )

object UserComment
{
}

object JsonFormats
{
  import reactivemongo.play.json._
  import reactivemongo.play.json.collection.JSONCollection
  import play.api.libs.json._

  implicit val userCommentFormat :OFormat[UserComment] = Json.format[UserComment]
}