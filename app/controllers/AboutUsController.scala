package controllers

import authentication.AuthenticationAction
import javax.inject._
import play.api.mvc._

class AboutUsController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction) extends AbstractController(cc) {
  def aboutUs(): Action[AnyContent] = Action {
    Ok(views.html.aboutUs())
  }

}
