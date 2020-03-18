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
      futureResult.map( _ => Redirect( routes.ApplicationUsingJsonReadersWriters.getAllPosts() ) )
    }

    def getAllPosts() :Action[AnyContent] = Action.async
    {
      val cursor :Future[Cursor[User]] = collection().map
      {
        _.find( Json.obj() )
          .cursor[User]()
      }

      val futureUsersList :Future[List[User]] =
        cursor.flatMap (
          _.collect[List]( -1, Cursor.FailOnError[List[User]]() )
        )

      futureUsersList.map { posts =>
        //var listPosts :String = ""
        //posts.foreach( post => { listPosts += post + "\n" })
        //Ok(listPosts)
        Ok( views.html.view_all_posts(posts) )
      }

      //Ok( "asdf" )
    }


  }
