package alexyer.scalazed

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
  * Created by olexander.yermakov on 13.04.16.
  */
class MarkovDict {
  private val markovDict = mutable.Map[String, mutable.ListBuffer[String]]()

  def addKey(prefix: String, suffix: String) = {
    if (markovDict contains prefix) {
      markovDict(prefix) += suffix
    } else {
      markovDict += (prefix -> ListBuffer(suffix))
    }
  }

  def suffix(prefix: String): String = {
    val rand = new Random(System.currentTimeMillis())
    val randomIndex = rand.nextInt(markovDict(prefix).length)

    markovDict(prefix)(randomIndex)
  }
}