package controllers

import authentication.AuthenticationAction

import authentication.AuthenticatedRequest
import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request, Results}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.play.json._
import collection._
import models.{LoginDetails, User}
import models.JsonFormats._
import play.api.libs.json.{JsValue, Json}
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}

class ApplicationUsingJsonReadersWriters @Inject()(components :ControllerComponents, authAction: AuthenticationAction, val reactiveMongoApi :ReactiveMongoApi )
  extends AbstractController(components)
    with MongoController with ReactiveMongoComponents
  {
    implicit def ec :ExecutionContext = { components.executionContext }

    def collection() :Future[JSONCollection] =
    {
      database.map(_.collection[JSONCollection]("persons"))
    }

    def create(comment :String) :Action[AnyContent] = authAction.async { implicit request :Request[AnyContent] =>
      val user = User( request.session.get("username").get, comment )

      val futureResult = collection().flatMap(_.insert.one(user))
      futureResult.map( _ => Ok("User Inserted") )
    }

    def createFromJson :Action[JsValue] = Action.async(parse.json) { request =>
      request.body.validate[User].map { user =>
        collection().flatMap( _.insert.one(user) ).map {
          _ => Ok("User inserted")
        }
      }.getOrElse( Future.successful(BadRequest("Invalid json")) )
    }

    def findByName(lastName :String) :Action[AnyContent] = Action.async
    {
      val cursor :Future[Cursor[User]] = collection().map
      {
        _.find( Json.obj("lastName" -> lastName) )
         .sort( Json.obj("created" -> -1) )
         .cursor[User]()
      }

      val futureUsersList :Future[List[User]] =
      cursor.flatMap (
        _.collect[List](
            -1,
            Cursor.FailOnError[List[User]]()
          )
      )

      futureUsersList.map { persons =>
        Ok(persons.toString)
      }
    }


  }
