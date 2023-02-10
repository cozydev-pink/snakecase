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

object literals2 {
  extension (inline ctx: StringContext) {
    inline def snake(inline args: Any*): SnakeCase =
      ${SnakeCaseLiteral('ctx, 'args)}
  }

  object SnakeCaseLiteral extends Literally[SnakeCase] {
    def validate(s: String)(using Quotes) =
      SnakeCase.snake.parseAll(s) match {
        case Left(err) => Left(s"${err}")
        case Right(_) => Right('{SnakeCase.unsafeFromString( ${Expr(s)} ) } )
    }
  }
}

// ---

case class Port private (value: Int)
object Port {
  val MinValue = 0
  val MaxValue = 65535

  def fromInt(i: Int): Option[Port] =
    if (i < MinValue || i > MaxValue) None else Some(new Port(i))
}

object literals {
  extension (inline ctx: StringContext) {
    inline def port(inline args: Any*): Port =
      ${PortLiteral('ctx, 'args)}
  }

  object PortLiteral extends Literally[Port] {
    def validate(s: String)(using Quotes) =
      s.toIntOption.flatMap(Port.fromInt) match {
        case None => Left(s"invalid port - must be integer between ${Port.MinValue} and ${Port.MaxValue}")
        case Some(_) => Right('{Port.fromInt(${Expr(s)}.toInt).get})
      }
  }
}