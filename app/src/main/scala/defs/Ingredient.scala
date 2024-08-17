package defs

class Ingredient(
                var name: String,
                var price: Float,
                var calories: Int,
                var categories: CategoryMap
                ) extends Serializable {
}
