# CRUD-operations

В данном проекте реализованы CRUD опрерации над объектом Book.
В ходе реализации с помощью рефлексии создается таблица в базе данных имя которой = имя класса и имена столбцов = поля класса.
После создания таблицы имплементируются методы интерфейса CrudRepository, а именно:
- создание элемента таблицы
- поиск элемента таблицы по ID
- обновление элемента таблицы
- удаление элемента таблицы по ID
- получение списка всех элементов таблицы.

  
Так же имплементируются методы BookRepository:
- поиск книги по названию
- поиск списка книг по имени автора

## Для запуска необходимо:
- Выполнить ```$mvn clean install```
- Перейти в src/main/resources/db.properties и установить соответствующие параметры:
    - db.url=jdbc:postgresql://localhost:5432/postgres - URL базы данных.
    - db.user=postgres - Имя пользователя базы данных.
    - db.password= - Пароль пользователя базы данных.
    - db.driver.name=org.postgresql.Driver - Имя JDBC драйвера.
- Запустить Server Postgres.
- Выполнить ```$mvn exec:java``` из корневого католога.

## Для генерации и просмотра документации выполнить:
- ```$mvn clean install```
- ```$mvn site```
- Открыть файл target/site/index.html



