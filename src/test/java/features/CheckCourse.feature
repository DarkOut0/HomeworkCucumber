Feature: Проверка курса

  @open
  Scenario: Проверяем курс на сайте
    Given перейти на сайт 'https://www.open.ru/'
    Then проверить курс 'USD'
    Then закончить работу