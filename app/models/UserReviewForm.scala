package models

import java.util.Date

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.OFormat

case class UserReviewForm(name: String, movieTitle: String, rating: Int, review: String)

object UserReviewForm {
  val userReviewForm: Form[UserReviewForm] = Form(
    mapping(
      "name" -> nonEmptyText,
      "movieTitle" -> nonEmptyText,
      "rating" -> number(max = 10),
      "review" -> nonEmptyText
    )(UserReviewForm.apply)(UserReviewForm.unapply)
  )
}