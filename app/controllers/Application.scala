package controllers



import com.google.inject.Inject
import play.api.libs.json
import play.api.libs.json.{JsError, JsResult, JsValue, Json}
import play.api.mvc._
import play.api.libs.ws.{WSAuthScheme, WSClient, WSRequest, WSResponse}


import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Application @Inject() (ws: WSClient) extends Controller {
  val URL_SLACK_ROOM = "https://hooks.slack.com/services/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"

  val URL_SESSION_KEY = "http://testapi.monitorIT247.com/api/clientsessions"


  val PAYLOAD = {
    """{"channel": "@vitaliys","advice":"html","text": "Test message \n <https://slack.com|Slack main page>","username": "webhookbot","icon_emoji":":ghost:"}"""}

  val PAYLOAD_FORARGUMENTS =Json.obj(
    "Name" ->"Eugene Malysh",
    "Password"->"Eugene Malysh",
    "SessionType"->"WordPressPlugin"
  )

  def getSessionKey = Action.async {
    ws.url(URL_SESSION_KEY).post(PAYLOAD_FORARGUMENTS).map{ request =>
      val parts =   request.body.split(",")
      val sessionKey = parts.filter(_.contains("SessionKey"))
      val sessionValue = sessionKey.toList.toString().substring(19,55)
      Ok(request.body)
    }
  }

  def getArguments(sessionValue:String) =Action.async {

    val URL_GET_ARGUMENTS = "http://testapi.monitorIT247.com/api/monitors?sessionKey="+sessionValue+"&id=557&idType=M"
    ws.url(URL_GET_ARGUMENTS).get().map{request=>
      val fullResponse:JsValue = Json.parse(request.body)
      val advice = (fullResponse \ "advice").get
      Ok(request.body)
    }
  }



 // val futureResponse: Future[WSResponse] = ws.url(service.URL_SLACK_ROOM).post(service.PAYLOAD)


//  def index() = Action.async {
//
//   ws.url(service.URL_SLACK_ROOM).get().map{request=>
//   Ok(request.body)
//
// ws.url(URL_GET_ARGUMENTS).get().map(response=>
// Ok(response.body))
//
//
}

