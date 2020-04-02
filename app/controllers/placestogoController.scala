package controllers

import authentication.AuthenticationAction
import javax.inject._
import play.api.mvc._

@Singleton
class placestogoController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction) extends AbstractController(cc) {
  def places(): Action[AnyContent] = Action {
    Ok(views.html.placestogo())
  }
}
