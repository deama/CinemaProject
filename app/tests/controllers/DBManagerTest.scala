package controllers

import controllers.DBManager
import akka.actor.ActorRefFactory
import akka.stream.ActorMaterializer
import models.UserReviewData
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONObjectID

class DBManagerTest extends PlaySpec with Results with MockitoSugar {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  "Example Page#users" should {
    "should be valid" in {
      val mongoService = mock[DBManager]
      val controller = new UserController(Helpers.stubControllerComponents(), mongoService)
      val userList = Future {
        List[UserReviewData](
          UserReviewData(BSONObjectID.generate(), 21, "Tom", "Johnes"),
          UserReviewData(21, "Tom", "Johnes")
        )
      }
      when(mongoService.findAllUsers()).thenReturn(userList)
      val result: Future[Result] = controller.getAllUsers().apply(FakeRequest())
      contentType(result) mustBe Some("text/plain")
      userList.map{
        value => contentAsString(result) must be("Users: " + value.toString())
      }
    }
  }

}