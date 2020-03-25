package controllers

import java.security.MessageDigest
import java.util.Date
import org.apache.commons.codec.binary.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

import authentication.AuthenticationAction
import authentication.AuthenticatedRequest
import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{Await, ExecutionContext, Future}
import reactivemongo.play.json._
import collection._
import models.{BookingData, PaymentData}
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

  def collectionBookings() :Future[JSONCollection] = {
    database.map(_.collection[JSONCollection]("bookings"))
  }

  def collectionPayments() :Future[JSONCollection] = {
    database.map(_.collection[JSONCollection]("payments"))
  }

  def hash(text :String) :String =
  {
    val secret = "secret"

    val sha256_HMAC = Mac.getInstance("HmacSHA256")
    val secret_key = new SecretKeySpec(secret.getBytes, "HmacSHA256")
    sha256_HMAC.init(secret_key)

    val hash = Base64.encodeBase64String(sha256_HMAC.doFinal(text.getBytes))
    return hash
  }



  def createBooking( movieTitle :String, screening :String, userName :String, adults :Int, children :Int, concession :Int )
    :Action[AnyContent] = authAction.async { implicit request :Request[AnyContent] =>
    val booking = BookingData(  BSONObjectID.generate().stringify, movieTitle, screening, userName, adults, children, concession )

    val futureResult = collectionBookings().map(_.insert.one(booking))
    futureResult.map( _ => Ok("submitted") )
  }

  def createPayment( name :String, cardNumber :Int, expDate :String, securityCode :Int )
    :Action[AnyContent] = authAction.async { implicit request :Request[AnyContent] =>

    val payment = PaymentData(  BSONObjectID.generate().stringify, hash(name), hash(cardNumber.toString), hash(expDate), hash(securityCode.toString) )

    val futureResult = collectionPayments().map(_.insert.one(payment))
    futureResult.map( _ => Ok("submitted") )
  }
}