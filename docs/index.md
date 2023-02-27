## snakecase

snakecase is a pre-alpha library that offers compile time validation of `snake_case` strings.

For the purposes of this library, a valid `snake_case` string:

- must start with a lowercase letter; `[a-z]`
- may only contain lowercase letters, digits, and underscores; `[a-z0-9_]+`

### Usage

This library is currently available for Scala binary versions 2.13 and 3.2.

To use the latest version, include the following in your `build.sbt`:

```scala
libraryDependencies ++= Seq(
  "pink.cozydev" %% "snakecase" % "@VERSION@"
)
```

A typical example of library use:

```scala mdoc
import pink.cozydev.snakecase._

val snakeString: SnakeCase = snake"western_coachwhip"
println(s"This is a snake case string: $snakeString")
```

If an invalid string is passed, a compiler error will be raised:

```scala mdoc:fail
val badString: SnakeCase = snake"CAPYBARA"
```
