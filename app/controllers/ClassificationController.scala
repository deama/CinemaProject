package controllers

import authentication.AuthenticationAction
import javax.inject._
import play.api.mvc._

@Singleton
class ClassificationController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction) extends AbstractController(cc) {
  def index(): Action[AnyContent] = Action {
    Ok(views.html.classification())
  }
}
