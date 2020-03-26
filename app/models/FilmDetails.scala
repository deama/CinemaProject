package models

import play.api.libs.json.OFormat
import reactivemongo.bson.BSONObjectID

object FilmDetails {
  def apply(
             title: String,
             director: String,
             actors: List[String],
             genre: String,
             showTimes: List[String],
             url: String) = new FilmDetails(BSONObjectID.generate(), title, director, actors, genre, showTimes,url)
}

case class FilmDetails(
                        _id: BSONObjectID,
                        title: String,
                        director: String,
                        actors: List[String],
                        genre: String,
                        showTimes:List[String],
                        url: String
                      )


