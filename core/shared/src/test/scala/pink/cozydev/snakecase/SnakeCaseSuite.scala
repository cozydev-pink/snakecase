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
import SnakeCase._
class SnakeCaseSuite extends munit.FunSuite {

  test("snake should parse: 'a'") {
    assert(snake.parseAll("a").isRight)
  }

  test("snake should parse: 'sam_is_cool'") {
    assert(snake.parseAll("sam_is_cool").isRight)
  }

  test("snake should parse: 'sam'") {
    assert(snake.parseAll("sam").isRight)
  }

  test("snake should parse: 'sam123_456'") {
    assert(snake.parseAll("sam123_456").isRight)
  }

  test("snake should not parse: 'sam_IS_cool'") {
    assert(snake.parseAll("sam_IS_cool").isLeft)
  }

  test("snake should not parse: '_'") {
    assert(snake.parseAll("_").isLeft)
  }

  test("snake should not parse: '12'") {
    assert(snake.parseAll("12").isLeft)
  }

  test("snake should not parse: 'sam is cool'") {
    assert(snake.parseAll("sam is cool").isLeft)
  }
}

class LiteralSnakeCaseSuite extends munit.FunSuite {
  import pink.cozydev.snakecase.literals._

  test("snake_case string construction") {
    assertEquals(snake"sam_is_cool", SnakeCase.unsafeFromString("sam_is_cool"))
  }

  test("a bad string should not compile") {
    assert(compileErrors("""snake"_"""").nonEmpty)
    assert(compileErrors("""snake"12"""").nonEmpty)
    assert(compileErrors("""snake"sam_IS_cool"""").nonEmpty)
    assert(compileErrors("""snake"sam is cool"""").nonEmpty)
  }
}
