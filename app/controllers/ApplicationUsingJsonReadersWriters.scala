package controllers

import authentication.AuthenticationAction
import authentication.AuthenticatedRequest
import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, MessagesActionBuilder, MessagesRequest, Request, Result, Results}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.play.json._
import collection._
import models.{LoginDetails, UserComment, UserSearchForm}
import models.JsonFormats._
import reactivemongo.bson.BSONObjectID
import play.api.i18n.I18nSupport
import play.api.libs.json.{JsValue, Json, OFormat}
import reactivemongo.api.Cursor
import reactivemongo.bson.BSONDocument
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.commands.WriteResult

class ApplicationUsingJsonReadersWriters @Inject()(components :ControllerComponents, authAction: AuthenticationAction, val reactiveMongoApi :ReactiveMongoApi )
  extends AbstractController(components)
    with MongoController with ReactiveMongoComponents with I18nSupport
  {
    implicit def ec :ExecutionContext = { components.executionContext }

    def collection() :Future[JSONCollection] =
    {
      database.map(_.collection[JSONCollection]("persons"))
    }




    def create(comment :String) :Action[AnyContent] = authAction.async { implicit request :Request[AnyContent] =>
      val user = UserComment( request.session.get("username").get, comment )

      val futureResult = collection().flatMap(_.insert.one(user))
      futureResult.map( _ => Redirect( routes.ApplicationUsingJsonReadersWriters.getAllPosts() ) )
    }

    def getAllPosts()  = Action.async { implicit request :Request[AnyContent] =>
      val cursor :Future[Cursor[UserComment]] = collection().map
      {
        _.find( Json.obj() )
          .cursor[UserComment]()
      }

      val futureUsersList :Future[List[UserComment]] =
        cursor.flatMap (
          _.collect[List]( -1, Cursor.FailOnError[List[UserComment]]() )
        )

      futureUsersList.map { posts =>
        posts.foreach(println)
        Ok( views.html.view_all_posts(posts, UserSearchForm.searchForm, UserComment.userForm) )
      }
    }

    def getAllPostsFromUser(name :String) :Action[AnyContent] = Action.async { implicit request :Request[AnyContent] =>
      val cursor :Future[Cursor[UserComment]] = collection().map
      {
        _.find( Json.obj("name" -> name) )
          .sort( Json.obj("created" -> -1) )
          .cursor[UserComment]()
      }

      val futureUsersList :Future[List[UserComment]] =
        cursor.flatMap (
          _.collect[List]( -1, Cursor.FailOnError[List[UserComment]]() )
        )

      futureUsersList.map { posts =>
        Ok( views.html.view_all_posts(posts, UserSearchForm.searchForm, UserComment.userForm) )
      }
    }

    def deletePost(id :String) :Action[AnyContent] = Action { implicit request :Request[AnyContent] =>
      val result = id.slice(14, id.length-2)
      collection().map
      {
        //_.delete().one( BSONDocument("name" -> name, "comment" -> comment) )
        _.delete().one( BSONDocument("_id" -> result) )
      }
      //Redirect( routes.ApplicationUsingJsonReadersWriters.getAllPosts() )

      Redirect( routes.ApplicationUsingJsonReadersWriters.getAllPosts() )
    }

  }
