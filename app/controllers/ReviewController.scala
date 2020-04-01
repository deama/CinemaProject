package controllers

import authentication.AuthenticationAction
import javax.inject.{Inject, Singleton}
import models.{FilmDetails, UserReviewForm}
import play.api.mvc.{AbstractController, Action, AnyContent, Call, ControllerComponents, Request}

import scala.concurrent._
import ExecutionContext.Implicits.global

@Singleton
class ReviewController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction, val db: DBManager) extends AbstractController(cc) with play.api.i18n.I18nSupport
{
  def reviewSubmit() :Action[AnyContent] = authAction.async { implicit request :Request[AnyContent] =>
    db.findCurrentMovies().map { films =>
      val seq = Seq()
      for( film <- films )
      {
        seq :+ film.title
      }


      UserReviewForm.userReviewForm.bindFromRequest.fold({ formWithErrors =>
        BadRequest(views.html.reviews(List.empty, formWithErrors, seq))
      }, { reviewDetails =>
        Redirect(routes.DBManager.createReview(reviewDetails.name, reviewDetails.movieTitle, reviewDetails.rating.toString, reviewDetails.review))
      })
    }
  }

  def viewAllReviews() :Action[AnyContent] = authAction.async { implicit request :Request[AnyContent] =>
    db.findCurrentMovies().map { films =>
      var movieTitleList :List[String] = List()
      for( film <- films )
      {
        movieTitleList :+ film
      }
      Redirect(routes.DBManager.getAllReviews(movieTitleList) )
    }
  }
}