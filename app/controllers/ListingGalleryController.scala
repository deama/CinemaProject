package controllers

import authentication.AuthenticationAction
import javax.inject._
import play.api.mvc._

import javax.inject.Singleton

@Singleton
class ListingGalleryController @Inject() (cc: ControllerComponents) extends AbstractController (cc)
{

  def test(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok("Hello World!")
  }
  def hello(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.listings())
  }
}
