package models

import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID

case class FilmDetails(
                        _id: BSONObjectID,
                        title: String,
                        director: String,
                        actors: List[String],
                        genre: String,
                        showTimes: List[String],
                        url: String
                      )

object FilmDetails {
  def apply(
             title: String,
             director: String,
             actors: List[String],
             genre: String,
             showTimes: List[String],
             url: String) = new FilmDetails(BSONObjectID.generate(), title, director, actors, genre, showTimes, url)

  import reactivemongo.play.json._
  import reactivemongo.play.json.collection.JSONCollection
  import play.api.libs.json._

  implicit val feedFormat: OFormat[FilmDetails] = Json.format[FilmDetails]

}



