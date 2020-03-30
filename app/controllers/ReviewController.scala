package controllers

import authentication.AuthenticationAction
import javax.inject.{Inject, Singleton}
import models.UserReviewForm
import play.api.mvc.{AbstractController, Action, AnyContent, Call, ControllerComponents, Request}

@Singleton
class ReviewController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction, val app: DBManager) extends AbstractController(cc) with play.api.i18n.I18nSupport
{
  def reviewSubmit() :Action[AnyContent] = authAction { implicit request :Request[AnyContent] =>
    UserReviewForm.userReviewForm.bindFromRequest.fold({ formWithErrors =>
      BadRequest( views.html.reviews( List.empty, formWithErrors) )
    }, { reviewDetails =>
      Redirect( routes.DBManager.createReview(reviewDetails.name, reviewDetails.movieTitle, reviewDetails.rating.toString, reviewDetails.review) )
    })
  }

  def viewAllReviews() :Action[AnyContent] = authAction { implicit request :Request[AnyContent] =>
    Redirect( routes.DBManager.getAllReviews() )
  }
}