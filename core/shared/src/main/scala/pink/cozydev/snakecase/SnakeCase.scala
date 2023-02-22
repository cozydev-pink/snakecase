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

import cats.parse.{Parser => P}
import cats.parse.Rfc5234.digit

final case class SnakeCase private (override val toString: String)
object SnakeCase {

  // "[a-z][a-z0-9_]+"
  val lowAlpha: P[Char] = P.charIn('a' to 'z')
  val underscore: P[Char] = P.char('_').as('_')
  val snake: P[String] =
    (lowAlpha ~ (lowAlpha | digit | underscore).rep0).string

  def unsafeFromString(value: String): SnakeCase =
    new SnakeCase(value)
}
