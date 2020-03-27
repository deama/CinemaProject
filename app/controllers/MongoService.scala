package controllers

import authentication.AuthenticationAction
import javax.inject.Inject
import models.{FilmDetails, FutureFilmDetails}
import models.JsonFormats._
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoApiComponents, ReactiveMongoComponents}
import reactivemongo.api.Cursor
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json._
import reactivemongo.play.json.collection.{JSONCollection, _}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
class MongoService @Inject()(components :ControllerComponents, authAction: AuthenticationAction, val reactiveMongoApi :ReactiveMongoApi )
  extends AbstractController(components)
    with MongoController with ReactiveMongoComponents with I18nSupport {
  //class MongoService @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends ReactiveMongoApiComponents{
  def currentMoviesCollection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("listings"))
  def futureMoviesCollection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("future"))


  def findCurrentMovies(): Future[List[FilmDetails]] = {
    currentMoviesCollection.map {
      _.find(Json.obj()).sort(Json.obj("created" -> -1))
        .cursor[FilmDetails]()
    }.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[FilmDetails]]()
      )
    )
  }

  def createMovie(filmDetail: FilmDetails): Future[WriteResult] = {
    currentMoviesCollection.flatMap(_.insert.one(filmDetail))
  }

  def findFutureMovies(): Future[List[FutureFilmDetails]] = {
    futureMoviesCollection.map{
      _.find(Json.obj()).sort(Json.obj("created" -> -1))
        .cursor[FutureFilmDetails]()
    }.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[FutureFilmDetails]]()
      )
    )
  }

  def createFutureMovie(futureFilmDetails: FutureFilmDetails): Future[WriteResult] ={
    futureMoviesCollection.flatMap(_.insert.one(futureFilmDetails))
  }


}