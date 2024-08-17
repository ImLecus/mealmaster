package defs

enum PRICE_LEVEL {
  case LOW, MODERATE, HIGH

  def describe(): String = this match {
    case LOW => "low"
    case MODERATE => "moderate"
    case HIGH => "high"
  }
}