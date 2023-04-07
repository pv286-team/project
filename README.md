# PanByte

This project is implemented according to the [specification](https://is.muni.cz/auth/el/fi/jaro2023/PV286/um/lectures/Project_assignment.pdf).

## Cloning

```bash
git clone git@github.com:pv286-team/project.git
cd project
```

## Building

```bash
mvn package
```

## Running

```java
java -jar target/panbyte-1.0-SNAPSHOT.jar
```

## Tests

- Unit tests are launched automatically during build (and build fails if tests do not succeed)
- Integration tests can be run with `python3 util/assigment-tests.py`
