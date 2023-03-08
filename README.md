## Embedika test task

![Java CI](https://github.com/6londo9/embedika-test-task/actions/workflows/Java-CI.yml/badge.svg)
<a href="https://codeclimate.com/github/6londo9/embedika-test-task/maintainability"><img src="https://api.codeclimate.com/v1/badges/e438ea8eea518ac89d66/maintainability" /></a>
<a href="https://codeclimate.com/github/6londo9/embedika-test-task/test_coverage"><img src="https://api.codeclimate.com/v1/badges/e438ea8eea518ac89d66/test_coverage" /></a>
---

### It is a simple web app, that adds cars to DB.

#### Docker image is available [here](https://hub.docker.com/repository/docker/6londo9/embedika-test-task/general) with all instructions to start it.

---

A little bit information about the app:

- For development DB I used h2 database / For production DB - PostgreSQL.
- The view of pages was made with Bootstrap and Thymeleaf. Actually some buttons was made using JS and AJAX. But it was not my work, the google helps me.

---

Here is a quick review of project:

- Home page, that has a form for adding car.
- - If car is already presented in DB, then page will be reloaded with session flash image.
- - If user add a car, which is not already presented in DB, then he will see the next page.
- After successfully adding a car, user will be redirected to cars table.
- - Cars table has an option for sorting, that was made with '@Query' in CarRepository.
This view also has a delete buttons, that make delete request for each presented car. Also there is a pagination,
that I made with 'Pageable' class.
- In the &lt;navbar&gt; you can actually find, that there is a 'Stats' link. It will show you:
- - How many entities are in the DB right now,
- - What is the last car, that was added,
- - And finally, what is the first added car in the DB.

---
#### P.S. If you want to make some requests with Postman, here is a few tips:
#### - When you make 'Post' requests, please use x-www-form-urlencoded type of body.
#### - Some of the response codes are not supposed to be like what it looks like now, such as '200' code if Delete request was sent to invalid id, etc.
#### - Also I use swagger for making documentation, but for some reason, it's not showing all the API, that I made.

---
If you still have some question or you may want to contact me, please, find me via [telegram](https://t.me/blondog).