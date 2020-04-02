package models

object JsonFormats {
  import reactivemongo.play.json._
  import reactivemongo.play.json.collection.JSONCollection
  import play.api.libs.json._
  implicit val feedFormat: OFormat[FilmDetails] = Json.format[FilmDetails]
}