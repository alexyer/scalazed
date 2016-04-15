package alexyer.scalazed

import scala.io.Source
import scala.util.control.Breaks._

/**
  * Created by olexander.yermakov on 13.04.16.
  */
class NameGenerator(chainLen: Int = 2, maxLen: Int = 9) {
  require(chainLen >= 2 && chainLen <= 9, "Chain length must be >=2 and <= 9")

  private val markovDict = new MarkovDict

  Source.fromFile("names/male_and_neutral.txt").getLines.flatMap(_.split(" ")).foreach { name =>
    val s = " " * chainLen + name
    for (i <- 0 until name.length) {
      markovDict.addKey(s.slice(i, i+chainLen), s(i+chainLen).toString)
    }

    markovDict.addKey(s.slice(name.length, name.length + chainLen), "\n")
  }

  def name: String = {
    var prefix = " " * chainLen
    var name = ""
    var suffix = ""

    breakable {
      while (true) {
        suffix = markovDict.suffix(prefix)

        if ((suffix == "\n") || (name.length > maxLen)) {
          break
        } else {
          name += suffix
          prefix = prefix.tail + suffix
        }
      }
    }

    name.capitalize
  }
}
