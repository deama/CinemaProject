package controllers

import authentication.AuthenticationAction
import javax.inject._
import play.api.mvc._

@Singleton
class ScrumDescriptionController @Inject()(cc: ControllerComponents, authAction: AuthenticationAction) extends AbstractController(cc)
{
  def scrum() :Action[AnyContent] = Action
  {
    Ok( views.html.scrumDescription() )
  }
}
