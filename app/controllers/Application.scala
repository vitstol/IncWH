package controllers



import com.google.inject.Inject
import play.api.mvc._
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Application @Inject() (ws: WSClient) extends Controller {

  val url = "https://hooks.slack.com/services/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"

  val payload = {
    """{"text": "Test message",
        "channel": "@dmitrytsky", "username": "webhookbot", "icon_emoji":":ghost:"}"""}
  val futureResponse:Future[WSResponse]=ws.url(url).post(payload)

  def wsAction = Action.async {
    ws.url(url).get().map{ response =>
      Ok(response.body)
    }
  }
}