package controllers

import authentication.AuthenticationAction
import javax.inject._
import play.api.mvc._

@Singleton
class ContactController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction) extends AbstractController(cc) {

  def contact(): Action[AnyContent] = authAction {
    Ok(views.html.contact())
  }

}
