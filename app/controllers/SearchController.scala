package controllers

import authentication.AuthenticationAction
import javax.inject.{Inject, Singleton}
import models.{PostDetails, UserSearchForm}
import play.api.mvc.{AbstractController, Action, AnyContent, Call, ControllerComponents, Request}

@Singleton
class SearchController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction, val app: ApplicationUsingJsonReadersWriters) extends AbstractController(cc) with play.api.i18n.I18nSupport
{
  def searchByName() :Action[AnyContent] = authAction { implicit request :Request[AnyContent] =>
    UserSearchForm.searchForm.bindFromRequest.fold({ formWithErrors =>
      BadRequest( views.html.view_all_posts(List.empty, formWithErrors) )
    }, { searchDetails =>
      Redirect( routes.ApplicationUsingJsonReadersWriters.getAllPostsFromUser(searchDetails.name) )
    })
  }
}
