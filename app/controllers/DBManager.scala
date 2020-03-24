package controllers

import authentication.AuthenticationAction
import authentication.AuthenticatedRequest
import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{Await, ExecutionContext, Future}
import reactivemongo.play.json._
import collection._
import models.BookingData
import reactivemongo.bson.BSONObjectID
import play.api.i18n.I18nSupport
import play.api.libs.json.{JsValue, Json, OFormat}
import reactivemongo.api.Cursor
import reactivemongo.bson.BSONDocument
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.duration.Duration

class DBManager @Inject()(components :ControllerComponents, authAction: AuthenticationAction, val reactiveMongoApi :ReactiveMongoApi )
  extends AbstractController(components)
    with MongoController with ReactiveMongoComponents with I18nSupport {
  implicit def ec: ExecutionContext = {
    components.executionContext
  }

  def collection() :Future[JSONCollection] = {
    database.map(_.collection[JSONCollection]("bookings"))
  }

  def createBooking( movieTitle :String, screening :String, userName :String, adults :Int, children :Int, concession :Int )
    :Action[AnyContent] = authAction.async { implicit request :Request[AnyContent] =>
    val booking = BookingData(  BSONObjectID.generate().stringify, movieTitle, screening, userName, adults, children, concession )

    val futureResult = collection().map(_.insert.one(booking))
    futureResult.map( _ => Ok("submitted") )
  }
}