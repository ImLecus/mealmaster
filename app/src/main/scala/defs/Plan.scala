package defs

import scala.collection.mutable.ListBuffer
class Plan (
           val meals: ListBuffer[Meal],
           val planType: String
           ) extends Serializable {

}
