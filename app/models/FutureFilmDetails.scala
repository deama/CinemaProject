package models

import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID

object FutureFilmDetails {
  def apply(
             title: String,
             director: String,
             actors: List[String],
             genre: String,
             url: String) = new FutureFilmDetails(BSONObjectID.generate(), title, director, actors, genre, url)

  import reactivemongo.play.json._
  import reactivemongo.play.json.collection.JSONCollection
  import play.api.libs.json._

  implicit val feedFormat: OFormat[FutureFilmDetails] = Json.format[FutureFilmDetails]
}

case class FutureFilmDetails(
                              _id: BSONObjectID,
                              title: String,
                              director: String,
                              actors: List[String],
                              genre: String,
                              url: String
                            )
