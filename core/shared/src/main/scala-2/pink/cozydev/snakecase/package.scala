package pink.cozydev

import pink.cozydev.snakecase.literals._

package object snakecase {
  implicit class snake(val sc: StringContext) extends AnyVal {
    def snake(args: Any*): SnakeCase = macro SnakeCaseLiteral.make
  }
}
