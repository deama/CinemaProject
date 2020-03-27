package controllers

import authentication.AuthenticationAction
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.{ExecutionContext, Future}

class HomeControllerTest extends PlaySpec with Results with MockitoSugar
{
  "Example Page#index" should
    {
    "should be valid" in
      {
        val authAction = mock[AuthenticationAction]
        val database = mock[DBManager]

        val controller = new HomeController( Helpers.stubControllerComponents(), authAction, database )
        val result: Future[Result] = controller.index().apply(FakeRequest().withSession("username" -> "admin") )

        contentType(result) mustBe Some("text/html")
        contentAsString(result) must include("Welcome to the QACinema experience")
    }
  }
}