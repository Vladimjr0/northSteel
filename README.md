# Веб-приложение Notes для Северстали
Проект представляет собой веб-приложение для работы с заметками. Заметки можно писать с помошью разных шрифтов(Arial, Courier New, Times New Roman), так же доступен курсив, выделение жирным, подчеркивание текста и вставка картинок. Так же можно сортировать заметки пользователя по дате их написания.
Главная страница доступна по адресу localhost:8080

# Стэк: 
Backend: Java, Spring Boot, Spring Data Jpa, Hibernate, H2, lombok, swagger;
Frontend: JavaScript, Html, CSS, Thymeleaf.

# Endpoints
Все эндпоинты можно посмотреть через swagger по ссылке после запуска приложения.
Ссылка - http://localhost:8080/swagger-ui/index.html#/

# Запуск
Для запуска приложения достаточно просто запустить его в IDE.
Данные о заметках сохранены в H2. Сама БД находиться в папке data.
Логин - sa, пароль - password
Так же для теста в resources/static/uploads лежат картинки.

# Что бы я добавил или же улучшил
1. Перенос на другую БД (лучше использовать PostgreSQL)
2. Добавить авторизацию и регистрацию с помощью Spring Security и JWT, чтобы приложением могло пользоваться множество юзеров
3. Пункт вытекающий из 3-го - обмен заметками между пользователями
4. Добавление DTO, если внутри Сущности Note появятся конфиденциальные данные
5. Добавление меток к заметкам и сортировка по ним.
