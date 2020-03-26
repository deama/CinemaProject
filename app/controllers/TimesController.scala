package controllers

import authentication.AuthenticationAction
import javax.inject._
import play.api.mvc._

@Singleton
class TimesController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction) extends AbstractController(cc) {

  def times(): Action[AnyContent] = authAction {
    Ok(views.html.times())
  }

}
