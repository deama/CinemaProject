package controllers

import authentication.AuthenticationAction
import javax.inject._
import play.api.mvc._

@Singleton
class ScreenController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction) extends AbstractController(cc) {

  def screens(): Action[AnyContent] = authAction {
    Ok(views.html.screens())
  }

}
