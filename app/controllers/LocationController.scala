package controllers

import authentication.AuthenticationAction
import javax.inject._
import play.api.mvc._

@Singleton
class LocationController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction) extends AbstractController(cc) {

  def gettingHere(): Action[AnyContent] = authAction {
    Ok(views.html.location())
  }

}