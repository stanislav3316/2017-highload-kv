#  3 Этап - нагрузочное тестирвоание
## используемые инструменты
#### Нагрузка
Нагрузку KV сервиса осуществляет Yandex-Tank (JMeter как Load Generator ).
Запуск танка производится следующей командой:
```sh
    $ yandex-tank -c load.ini
```
**Все планы тестов** JMeter доступны в папке load в репозитории (ветка onenio-load).
#### Профилирование
Профилирование осуществляется с помощью async-profiler и Flame Graph для визуализации результатов. **Результаты замеров** SVG лежат в папке loadl/

 ```bash
   ./profiler.sh -d 120 -o collapsed -f /home/iters/Документы/hotdata/first.txt [Pid]
   ./flamegraph.pl --colors=java /home/iters/Документы/hotdata/data.txt > /home/iters/Документы/hotdata/data.svg
```
## Первая нагрузка
#### GET 2/3,  1 подключение, с повтором
[![](https://ibb.co/kbnwHR)](https://ibb.co/kbnwHR)
