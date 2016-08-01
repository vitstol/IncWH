package controllers



import com.google.inject.Inject
import play.api.libs.json
import play.api.libs.json.{JsError, JsResult, JsValue, Json}
import play.api.mvc._
import play.api.libs.ws.{WSAuthScheme, WSClient, WSRequest, WSResponse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Application @Inject() (ws: WSClient) extends Controller {


  val urlSlack = "https://hooks.slack.com/services/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"

  val urlSessionKey = "http://testapi.monitorIT247.com/api/clientsessions"

  val urlGetArguments = "http://testapi.monitorIT247.com/api/monitors?sessionKey=4EBCC64D-12A7-402F-AA37-F57E5CCFF0F0&id=557&idType=M"

//  val payload = {
//    """{"channel_": "@vitaliys","advice":"html","text": "Test message \n <https://slack.com|Slack main page>","username": "webhookbot","icon_emoji":":ghost:"}"""}

  val payload =Json.obj(
    "Name" ->"Eugene Malysh",
    "Password"->"Eugene Malysh",
    "SessionType"->"WordPressPlugin"
  )


//  val futureResult: Future[_] = ws.url(url).post(payload).map {
//    response =>
//      response.json \ "SessionKey"
//  }
//  val futureResponse:Future[WSResponse]=ws.url(urlSessionKey).post(payload)


  def getSessionKey = Action.async {
    ws.url(urlSessionKey).post(payload).map{ request =>
//      val fullResponse:JsValue = Json.parse(request.body)
//      val sessionKey = (fullResponse \ "SessionKey").get
      Ok(request.body)
    }
  }

}

//request:http://testapi.monitorIT247.com/api/monitors?sessionKey=4EBCC64D-12A7-402F-AA37-F57E5CCFF0F0&id=557&idType=M
