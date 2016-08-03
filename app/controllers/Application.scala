package controllers

import com.google.inject.Inject
import play.api.libs.json._
import play.api.mvc._
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.language.postfixOps
import scala.util.parsing.json.JSONArray

class Application @Inject() (ws: WSClient) extends Controller {
  val URL_SLACK_ROOM = "https://hooks.slack.com/services/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"
  val URL_SESSION_KEY = "http://testapi.monitorIT247.com/api/clientsessions"
  val PAYLOAD_FORARGUMENTS = Json.obj(
    "Name" -> "Eugene Malysh",
    "Password" -> "Eugene Malysh",
    "SessionType" -> "WordPressPlugin"
  )

  def futureResponse(sessionKey:String) = {
    val url1 = "http://testapi.monitorIT247.com/api/monitors?sessionKey=" + sessionKey + "&id=557&idType=M"
    ws.url(url1).get().map { request =>
      val fullResponse:JsValue = Json.parse(request.body)
      val advicePrev = fullResponse \\ "advice"
      val advice = advicePrev.head.toString()
      val Payload = Json.obj(
        "channel"->"@vitaliys",
        "text"->"<advice>",
        "username"->"webhookbot"
      )

      ws.url(URL_SLACK_ROOM).post(Payload)
    }
  }

  def getArguments = Action.async {
    ws.url(URL_SESSION_KEY).post(PAYLOAD_FORARGUMENTS).map { request =>
      val parts: Array[String] = request.body.split(",")
      val sessionKey: Option[String] = parts.find(_.contains("SessionKey"))
      futureResponse(sessionKey.getOrElse("").substring(14, 50))
      Ok("")
    }
  }
}

