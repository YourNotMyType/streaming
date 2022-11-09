package cput.streaming.environment

import com.typesafe.config.{Config, ConfigFactory}

import java.time.{LocalDateTime, ZoneId}
import java.util.Date

/**
 * Created by hashcode on 2015/11/07.
 */
object Util {

  import java.net._

  val config: Config      = ConfigFactory.load()
  val browserTimeOut: Int = config.getInt("browser.timeout")
  val time: Int           = config.getInt("resetKey.inMinutes")

  def md5Hash(text: String): String = {
    val hash = text + InetAddress.getLocalHost.getHostName
    java.security.MessageDigest
      .getInstance("MD5")
      .digest(hash.getBytes())
      .map(0xff & _)
      .map {
        "%02x".format(_)
      }
      .foldLeft("") {
        _ + _
      }
  }

  def cleanMd5Hash(text: String): String =
    java.security.MessageDigest
      .getInstance("MD5")
      .digest(text.getBytes())
      .map(0xff & _)
      .map {
        "%02x".format(_)
      }
      .foldLeft("") {
        _ + _
      }

  def getLocalDateTimeFromDate(date: Date): LocalDateTime =
    date
      .toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime()

  def trimContent(content: String, n: Int): String = {
    content.length match {
      case length: Int if length <= n => content + "..."
      case _                          => content.take(content.lastIndexWhere(_.isSpaceChar, n + 1)).trim + "..."
    }
  }


}
