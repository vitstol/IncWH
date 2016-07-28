package service

import akka.actor.Address
import play.api.libs.json._

/**
  * Created by vitaliys on 28.07.16.
  */
class JSonConvert {

  val json:JsValue = JsObject(Seq(
   "payload"->JsString("This is a line of text in a channel.\n And this is another line of text.")
  ))

  def writePayment(payload: PayLoad) = {
    JsObject(Seq(
      "text" -> JsString(payload.text)
    ))
  }

  def readPayment(jsonPayment: JsValue) = {
    val text = (jsonPayment \ "text").as[String]
    PayLoad(text)
  }


  case class PayLoad(text: String)

}
