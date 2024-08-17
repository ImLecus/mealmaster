package defs

enum PLANNER_MODE {
  case NONE, NOW, DAILY, WEEKLY, FULL_WEEKLY

  def describe(): String = this match {
    case NONE => "none"
    case NOW => "now"
    case DAILY => "daily"
    case WEEKLY => "weekly"
    case FULL_WEEKLY => "full-weekly"

  }

  def getMealNumber: Int = this match {
    case NONE => 0
    case NOW => 1
    case DAILY => 2
    case WEEKLY => 10
    case FULL_WEEKLY => 14

  }
}