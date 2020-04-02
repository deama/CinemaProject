package models

case class UserReviewData(id: String, name: String, movieTitle: String, rating: String, review: String)

object UserReviewData {

  import play.api.libs.json._

  implicit val userReviewDataFormat: OFormat[UserReviewData] = Json.format[UserReviewData]
}