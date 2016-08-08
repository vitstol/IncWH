package controllers
import java.io.{File, PrintWriter}
import java.nio.charset.StandardCharsets

import scala.sys.process._
import com.google.inject.Inject
import dispatch.url
import play.api.libs.json._
import play.api.mvc._
import play.api.libs.ws.WSClient
import play.api.mvc.MultipartFormData.FilePart
import slack.api.SlackApiClient
import slack.models.SlackFile
import slack.api.BlockingSlackApiClient

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
import scala.util.parsing.json.{JSONArray, JSONObject}

class Application @Inject() (ws: WSClient) extends Controller {
  val URL_SLACK_ROOM = "https://hooks.slack.com/services/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"
  val URL_TELEGRAM_BOT_PHOTO = "https://api.telegram.org/bot261740146:AAHrUpO42RNdNZEyauchn1KTNI28xuWVToQ/sendPhoto"
  val URL_TELEGRAM_BOT_MESSAGE= "https://api.telegram.org/bot261740146:AAHrUpO42RNdNZEyauchn1KTNI28xuWVToQ/sendMessage"

  val URL_SESSION_KEY = "http://testapi.monitorIT247.com/api/clientsessions"
  val PAYLOAD_FORARGUMENTS = Json.obj(
    "Name" -> "Eugene Malysh",
    "Password" -> "Eugene Malysh",
    "SessionType" -> "WordPressPlugin"
  )

  def responseSlack(sessionKey:String) = {
    val url1 = "http://testapi.monitorIT247.com/api/monitors?sessionKey=" + sessionKey + "&id=557&idType=M"
    ws.url(url1).get().map { request =>
      val fullResponse:JsValue = Json.parse(request.body)
      val advicePrev = fullResponse \\ "advice"
      val advice = advicePrev.head.toString().replace("<p>","_")
        .replace("</p>","_")
        .replace("<span style=font-size: 18px; font-family: arial; color: #2e75b6;>","")
        .replace("</span>","")
        .replace("<span style=font-size: 18px; font-family: arial; color: black;>","•")
        .replace("<span style=font-size: 18px; font-family: arial; color: red;>","")
        .replace("<ul>","").replace("</ul>","")
        .replace("<li>","                             ").replace("</li>","")
        .replace("\"","")
        .replace("\\r","\r")
        .replace("\\n","\n")
        .replace("Advice(please take note)","*_`Advice(please take note)`_*")
        .replace("•Note","Note")
        .replace("•nominated limit of","_nominated limit of_")
      val PAYLOAD_SLACK = Json.obj(
        "bot_access_token"->"xoxb-67157110468-fWjRy6P6iaboxBdK8LOZOmLF",
        "channel"->"#random",git
        "username"->"webhookbot",
        "mrkdwn" ->true,
        "text"->advice
      )
      ws.url(URL_SLACK_ROOM).post(PAYLOAD_SLACK)
    }
  }

  def responseTelegram(sessionKey:String) = {
    val url1 = "http://testapi.monitorIT247.com/api/monitors?sessionKey=" + sessionKey + "&id=557&idType=M"
    ws.url(url1).get().map { request =>
      val fullResponse:JsValue = Json.parse(request.body)
      val advicePrev = fullResponse \\ "advice"
      val advice = advicePrev.head.toString().replace("<p>","_")
        .replace("</p>","_")
        .replace("<span style=font-size: 18px; font-family: arial; color: #2e75b6;>","")
        .replace("</span>","")
        .replace("<span style=font-size: 18px; font-family: arial; color: black;>","•")
        .replace("<span style=font-size: 18px; font-family: arial; color: red;>","")
        .replace("<ul>","").replace("</ul>","")
        .replace("<li>","                             ").replace("</li>","")
        .replace("\"","")
        .replace("\\r","\r")
        .replace("\\n","\n")
        .replace("Advice(please take note)","_`Advice(please take note)`_")
        .replace("•Note","Note")
        .replace("•nominated limit of","_nominated limit of_")
      val PAYLOAD= Json.obj(
        "chat_id"->"39404509",
        "text"->advice,
        "parse_mode"->"Markdown"
      )
      ws.url(URL_TELEGRAM_BOT_MESSAGE).post(PAYLOAD)
    }
  }

  def mergeJson(sessionKey:String)={
    val url1 = "http://testapi.monitorIT247.com/api/monitors?sessionKey=" + sessionKey + "&id=557&idType=M"
    ws.url(url1).get().map { request =>
      val fullResponse: JsValue = Json.parse(request.body)
      val SERIES_1_NAME= fullResponse \\ "SERIES-1-NAME"
      val SERIES_1_DATA = fullResponse \\ "SERIES-1-DATA"
      val SERIES_2_NAME= fullResponse \\ "SERIES-2-NAME"
      val SERIES_2_DATA = fullResponse \\ "SERIES-2-DATA"
      val SERIES_3_NAME= fullResponse \\ "SERIES-3-NAME"
      val SERIES_3_DATA = fullResponse \\ "SERIES-3-DATA"
      val SERIES_4_NAME= fullResponse \\ "SERIES-4-NAME"
      val SERIES_4_DATA = fullResponse \\ "SERIES-4-DATA"
      val CATEGORY_DATA = fullResponse \\ "CATEGORY-DATA"
      val TABLE_COLS = fullResponse \\ "TABLE-COLS"
      val TABLE_ROWS = fullResponse \\ "TABLE-ROWS"

      val displayPreferenceJson = fullResponse \\ "displayPreferences"

      val displayPreference = displayPreferenceJson.head.toString()
        .replace("$SERIES-1-NAME",SERIES_1_NAME.head.toString())
        .replace("$SERIES-1-DATA",SERIES_1_DATA.head.toString())
        .replace("$SERIES-2-NAME",SERIES_2_NAME.head.toString())
        .replace("$SERIES-2-DATA",SERIES_2_DATA.head.toString())
        .replace("$SERIES-3-NAME",SERIES_3_NAME.head.toString())
        .replace("$SERIES-3-DATA",SERIES_3_DATA.head.toString())
        .replace("$SERIES-4-NAME",SERIES_4_NAME.head.toString())
        .replace("$SERIES-4-DATA",SERIES_4_DATA.head.toString())
        .replace("$CATEGORY-DATA",CATEGORY_DATA.last.toString())
        .replace("$TABLE-COLS",TABLE_COLS.head.toString())
        .replace("$TABLE-ROWS",TABLE_ROWS.head.toString())
        .replace("{\"highcharts\":\"","")
        .replace("\\n","")
        .replace("\"","")
        .replace("\\","'")
        .replace("$","")
        .replace("xAxis", "{xAxis")
        .substring(715,1766)
      new PrintWriter("j.son") { write(displayPreference); close() }
      val term = "phantomjs /home/vitaliys/project/scala-bot/IncWH/public/phantomjs/bin/highcharts-convert.js -infile /home/vitaliys/project/scala-bot/IncWH/j.son -outfile chart.png"
      val output = term.!!
      output
    }
  }

  def uploadImageToSlack = {
    val term = "curl -F file=@chart.png -F channels=#random -F token=xoxb-67157110468-fWjRy6P6iaboxBdK8LOZOmLF  https://slack.com/api/files.upload"
    val uploadFile = term.!
    uploadFile
  }
  def uploadImageToTelegram = {
    val term = "curl -s -X POST https://api.telegram.org/bot261740146:AAHrUpO42RNdNZEyauchn1KTNI28xuWVToQ/sendPhoto -F chat_id=39404509 -F photo=@chart.png"
    val uploadFile = term.!
    uploadFile
  }


  def getArguments = Action.async {
    ws.url(URL_SESSION_KEY).post(PAYLOAD_FORARGUMENTS).map { request =>
      val parts: Array[String] = request.body.split(",")
      val sessionKey: Option[String] = parts.find(_.contains("SessionKey"))
      mergeJson(sessionKey.getOrElse("").substring(14, 50))
      responseSlack(sessionKey.getOrElse("").substring(14, 50))
      uploadImageToSlack
      responseTelegram(sessionKey.getOrElse("").substring(14, 50))
      uploadImageToTelegram
      Ok("")
    }
  }
}


