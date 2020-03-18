package controllers

import authentication.AuthenticationAction
import javax.inject.{Inject, Singleton}
import models.PostDetails
import play.api.mvc.{AbstractController, Action, AnyContent, Call, ControllerComponents, Request}

@Singleton
class PostController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction, val app: ApplicationUsingJsonReadersWriters) extends AbstractController(cc) with play.api.i18n.I18nSupport
{
  def post() :Action[AnyContent] = Action { implicit request :Request[AnyContent] =>
    Ok( views.html.post(PostDetails.postForm) )
  }

  def postSubmit() :Action[AnyContent] = authAction { implicit request :Request[AnyContent] =>
    PostDetails.postForm.bindFromRequest.fold({ formWithErrors =>
      BadRequest( views.html.post(formWithErrors) )
    }, { postDetails =>
      Redirect( routes.ApplicationUsingJsonReadersWriters.create(postDetails.post) )
    })
  }

  def viewAllPosts() :Action[AnyContent] = authAction { implicit request :Request[AnyContent] =>
    Redirect( routes.ApplicationUsingJsonReadersWriters.getAllPosts() )
  }
}
