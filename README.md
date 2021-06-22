# Справочник автомобилей
Реализован простейший веб-сервис в виде справочника автомобилей. Информация (марка, модель, цвет, год выпуска, регистрационный номер) хранится в таблице базы данных postgresql на сервере.
Возможно вносить новые, удалять, а также просматривать записи с фильтром по нескольким параметрам и без.

Присутствует проверка вводимых значений. Например год выпуска должен быть четырехзначным десятичным числом в диапазоне [1900-настоящий_год]. 
Регистрационный номер должен соответствовать ГОСТу и быть уникальным. Вверху страницы выводится минимальная статистика базы данных(количество записей в базе, время самой ранней записи, время последней записи).

Основные технологии Spring Boot, Spring Data, Thymeleath. Сервис доступен по адресу http://localhost:8080/. Реализован REST API (http://localhost:8080/rest). Для работы необходимо создать базу "data" на сервере Postgresql с логин/паролем postgres/postgres, а в базе создать пустую таблицу "car". Либо необходимо изменить файл application.properties.


![alt-текст](https://github.com/NesterovSU/SpringBootData/blob/master/2021-06-18_17-37-45.png "Вид справочника")
