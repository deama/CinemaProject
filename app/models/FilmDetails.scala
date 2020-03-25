package models

class FilmDetails(title: String, description:String, actors:List[String], director:String, posterUrl: String, releaseDate:String) {
  def getTitle: String = title
  def getDescription: String = description
  def getActors: Seq[String] => List[String] = List[String]
  def getDirector: String = director
  def getPosterUrl: String = posterUrl
  def getReleaseDate: String = releaseDate
}
