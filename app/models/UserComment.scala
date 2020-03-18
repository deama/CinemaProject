package models

import play.api.libs.json.OFormat

//case class User( age :Int, firstName :String, lastName :String, feeds :List[Feed] )
case class UserComment(name :String, comment :String )

object JsonFormats
{
  import play.api.libs.json.Json

  implicit val userFormat :OFormat[UserComment] = Json.format[UserComment]
}