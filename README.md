# Test Naming Conventions Plugin

Almost each project uses unit tests in its codebase. It is important to have a
common pattern of naming all that tests, because otherwise the project and tests
itself become a complete mess. It's a quite large discussion about
different [test naming patterns](https://stackoverflow.com/questions/155436/unit-test-naming-best-practices)
.
**test-naming-conventions** maven plugin helps to keep following a single common
test naming rule across all of your test classes.

## How to use

The plugin could be run using several approaches but for both of them you need
at least Maven 3.1.+ and Java 8+.

### Invoke the plugin directly

In order to use the plugin with the latest version just invoke the next command
in the root of your project:

```shell
mvn com.github.volodya-lombrozo:test-naming-conventions:check
```

After that you will see the result of the plugin execution in the console. If
you want to use specific (older) version of the plugin, for example `0.1.4`,
just run the next maven command with specified version:

```shell
mvn com.github.volodya-lombrozo:test-naming-conventions:0.1.4:check
```

### Add the plugin to your `pom.xml`

The more convenient way to use the plugin is to add it to your `pom.xml` file.
In order to do that, just add the next snippet to your `pom.xml`:

```xml

<build>
  <plugins>
    <plugin>
      <groupId>com.github.volodya-lombrozo</groupId>
      <artifactId>test-naming-conventions</artifactId>
      <version>0.1.4</version>
      <executions>
        <execution>
          <goals>
            <goal>check</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

The default plugin phase is `verify`, so you don't need to specify it directly,
but if you want to change it, just add the `phase` to `execution` section:

```xml

<execution>
  <phase>test</phase>
  <goals>
    <goal>check</goal>
  </goals>
</execution>
```

## How to Contribute

Fork repository, make changes, send us a pull request. We will review your
changes and apply them to the `main` branch shortly, provided they don't violate
our quality standards. 