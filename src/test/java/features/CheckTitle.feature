Feature: Проверка заголовка


  @open2
  Scenario: Тайтл верен
    Given перейти на сайт 'https://www.open.ru/'
    Then тайтл равен 'Открытие'
    Then закончить работу