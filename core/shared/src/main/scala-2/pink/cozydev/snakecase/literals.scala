/*
 * Copyright 2023 CozyDev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pink.cozydev.snakecase

import org.typelevel.literally.Literally

object literals {
  object SnakeCaseLiteral extends Literally[SnakeCase] {
    def validate(c: Context)(s: String): Either[String, c.Expr[SnakeCase]] = {
      import c.universe._
      SnakeCase.snake.parseAll(s) match {
        case Left(_) =>
          Left(
            s"Invalid SnakeCase -- string may only contain a-z, 0-9, _, and must start with a letter"
          )
        case Right(_) => Right(c.Expr(q"SnakeCase.unsafeFromString($s)"))
      }
    }

    def make(c: Context)(args: c.Expr[Any]*): c.Expr[SnakeCase] =
      apply(c)(args: _*)
  }
}
