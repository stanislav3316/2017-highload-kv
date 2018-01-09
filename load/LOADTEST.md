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
[![](https://pp.userapi.com/c841027/v841027344/565f4/iSb_UmQXhQU.jpg)](https://pp.userapi.com/c841027/v841027344/565f4/iSb_UmQXhQU.jpg)

[![](https://pp.userapi.com/c841027/v841027344/565ea/6Cng0slVoc8.jpg)](https://pp.userapi.com/c841027/v841027344/565ea/6Cng0slVoc8.jpg)

[![](https://pp.userapi.com/c841027/v841027344/565e0/KF1P9NCgWu0.jpg)](https://pp.userapi.com/c841027/v841027344/565e0/KF1P9NCgWu0.jpg)

#### GET 2/3,  1 подключение, без повторов
[![](https://pp.userapi.com/c841027/v841027344/56610/5QJOwLLV2Y4.jpg)](https://pp.userapi.com/c841027/v841027344/56610/5QJOwLLV2Y4.jpg)

[![](https://pp.userapi.com/c841027/v841027344/56606/7m_eQ7XJcyo.jpg)](https://pp.userapi.com/c841027/v841027344/56606/7m_eQ7XJcyo.jpg)

[![](https://pp.userapi.com/c841027/v841027344/565fd/ZK_gIVeWAlQ.jpg)](https://pp.userapi.com/c841027/v841027344/565fd/ZK_gIVeWAlQ.jpg)

#### GET 3/3,  1 подключение, с повтором
[![](https://pp.userapi.com/c841027/v841027344/56636/VmnNRDlaE44.jpg)](https://pp.userapi.com/c841027/v841027344/56636/VmnNRDlaE44.jpg)

[![](https://pp.userapi.com/c841027/v841027344/5662c/GiGWggnvvcE.jpg)](https://pp.userapi.com/c841027/v841027344/5662c/GiGWggnvvcE.jpg)

[![](https://pp.userapi.com/c841027/v841027344/56622/bH4B_Tw3QPo.jpg)](https://pp.userapi.com/c841027/v841027344/56622/bH4B_Tw3QPo.jpg)

#### GET 3/3,  1 подключение, без повторов
[![](https://pp.userapi.com/c841027/v841027344/56653/2GxHatWnzjk.jpg)](https://pp.userapi.com/c841027/v841027344/56653/2GxHatWnzjk.jpg)

[![](https://pp.userapi.com/c841027/v841027344/56649/tF7xhodhpyk.jpg)](https://pp.userapi.com/c841027/v841027344/56649/tF7xhodhpyk.jpg)

[![](https://pp.userapi.com/c841027/v841027344/5663f/co2fJ5aoc_o.jpg)](https://pp.userapi.com/c841027/v841027344/5663f/co2fJ5aoc_o.jpg)

#### PUT 2/3,  1 подключение, с перезаписью
[![](https://pp.userapi.com/c841027/v841027344/56678/FWs4DLmMdjg.jpg)](https://pp.userapi.com/c841027/v841027344/56678/FWs4DLmMdjg.jpg)

[![](https://pp.userapi.com/c841027/v841027344/56665/T99hn4bEU6A.jpg)](https://pp.userapi.com/c841027/v841027344/56665/T99hn4bEU6A.jpg)

[![](https://pp.userapi.com/c841027/v841027344/5666e/Ln7ESnA3N1Y.jpg)](https://pp.userapi.com/c841027/v841027344/5666e/Ln7ESnA3N1Y.jpg)

#### PUT 2/3,  1 подключение, без перезаписи
[![](https://pp.userapi.com/c841027/v841027344/56686/ysyCn4fosiI.jpg)](https://pp.userapi.com/c841027/v841027344/56686/ysyCn4fosiI.jpg)

[![](https://pp.userapi.com/c841027/v841027344/5668f/Vj8gKKC3M48.jpg)](https://pp.userapi.com/c841027/v841027344/5668f/Vj8gKKC3M48.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7dfd3/ivnsuKe6j6Q.jpg)](https://pp.userapi.com/c834204/v834204344/7dfd3/ivnsuKe6j6Q.jpg)

#### PUT 3/3,  1 подключение, с перезаписью
[![](https://pp.userapi.com/c834204/v834204344/7dfef/ikAkJd4tf04.jpg)](https://pp.userapi.com/c834204/v834204344/7dfef/ikAkJd4tf04.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7dfe5/R8l1c_BTi_M.jpg)](https://pp.userapi.com/c834204/v834204344/7dfe5/R8l1c_BTi_M.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7dfdc/lOGz_EEd7KU.jpg)](https://pp.userapi.com/c834204/v834204344/7dfdc/lOGz_EEd7KU.jpg)

#### PUT 3/3,  1 подключение, без перезаписи
[![](https://pp.userapi.com/c834204/v834204344/7e002/rz18EJlQrn0.jpg)](https://pp.userapi.com/c834204/v834204344/7e002/rz18EJlQrn0.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e00c/yP5cksVaUow.jpg)](https://pp.userapi.com/c834204/v834204344/7e00c/yP5cksVaUow.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7dff8/ObnuMeCUzd0.jpg)](https://pp.userapi.com/c834204/v834204344/7dff8/ObnuMeCUzd0.jpg)

------------

#### GET 2/3,  2 подключения, с повтором
[![](https://pp.userapi.com/c834204/v834204344/7e028/OwR6kn5Jzms.jpg)](https://pp.userapi.com/c834204/v834204344/7e028/OwR6kn5Jzms.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e01e/hiBH0jAmePI.jpg)](https://pp.userapi.com/c834204/v834204344/7e01e/hiBH0jAmePI.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e015/P4sMYtbhp5w.jpg)](https://pp.userapi.com/c834204/v834204344/7e015/P4sMYtbhp5w.jpg)

#### GET 2/3,  2 подключения, без повторов
[![](https://pp.userapi.com/c834204/v834204344/7e055/PFVKfDLgSns.jpg)](https://pp.userapi.com/c834204/v834204344/7e055/PFVKfDLgSns.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e04b/CeeFxpCKd3U.jpg)](https://pp.userapi.com/c834204/v834204344/7e04b/CeeFxpCKd3U.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e042/rH2H_z3-fUg.jpg)](https://pp.userapi.com/c834204/v834204344/7e042/rH2H_z3-fUg.jpg)

#### GET 3/3,  2 подключения, с повтором
[![](https://pp.userapi.com/c834204/v834204344/7e071/xp_1pzBTw_A.jpg)](https://pp.userapi.com/c834204/v834204344/7e071/xp_1pzBTw_A.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e067/FO9jvUA4d8c.jpg)](https://pp.userapi.com/c834204/v834204344/7e067/FO9jvUA4d8c.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e05e/K-omFDMbyWU.jpg)](https://pp.userapi.com/c834204/v834204344/7e05e/K-omFDMbyWU.jpg)

#### GET 3/3,  2 подключения, без повторов
[![](https://pp.userapi.com/c834204/v834204344/7e08d/bo6gg_8tjFg.jpg)](https://pp.userapi.com/c834204/v834204344/7e08d/bo6gg_8tjFg.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e083/wlYpFjmjKP8.jpg)](https://pp.userapi.com/c834204/v834204344/7e083/wlYpFjmjKP8.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e07a/xS31fwMclx8.jpg)](https://pp.userapi.com/c834204/v834204344/7e07a/xS31fwMclx8.jpg)

#### PUT 2/3,  2 подключения, с перезаписью
[![](https://pp.userapi.com/c834204/v834204344/7e0b3/KBFuaLhxyh0.jpg)](https://pp.userapi.com/c834204/v834204344/7e0b3/KBFuaLhxyh0.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e0a9/-xNUnOyrBFQ.jpg)](https://pp.userapi.com/c834204/v834204344/7e0a9/-xNUnOyrBFQ.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e0a0/upVJzQbEf80.jpg)](https://pp.userapi.com/c834204/v834204344/7e0a0/upVJzQbEf80.jpg)

#### PUT 2/3,  2 подключения, без перезаписи
[![](https://pp.userapi.com/c834204/v834204344/7e0cf/44pNcsgiiWE.jpg)](https://pp.userapi.com/c834204/v834204344/7e0cf/44pNcsgiiWE.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e0c5/VaOBT6Vy_do.jpg)](https://pp.userapi.com/c834204/v834204344/7e0c5/VaOBT6Vy_do.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e0bc/Z69lHDr-H2o.jpg)](https://pp.userapi.com/c834204/v834204344/7e0bc/Z69lHDr-H2o.jpg)

#### PUT 3/3,  2 подключения, с перезаписью
[![](https://pp.userapi.com/c834204/v834204344/7e0f5/_lvkEIs6gb8.jpg)](https://pp.userapi.com/c834204/v834204344/7e0f5/_lvkEIs6gb8.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e0eb/vpsHerCxRRo.jpg)](https://pp.userapi.com/c834204/v834204344/7e0eb/vpsHerCxRRo.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e0e2/RM3IA48Uxjc.jpg)](https://pp.userapi.com/c834204/v834204344/7e0e2/RM3IA48Uxjc.jpg)

#### PUT 3/3,  2 подключения, без перезаписи
[![](https://pp.userapi.com/c834204/v834204344/7e118/rFSvObQ176g.jpg)](https://pp.userapi.com/c834204/v834204344/7e118/rFSvObQ176g.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e10e/Y9sMUu7kLzc.jpg)](https://pp.userapi.com/c834204/v834204344/7e10e/Y9sMUu7kLzc.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e105/PupFSR0_kls.jpg)](https://pp.userapi.com/c834204/v834204344/7e105/PupFSR0_kls.jpg)

------------

#### GET 2/3, 4 подключения, с повтором
[![](https://pp.userapi.com/c834204/v834204344/7e13d/W9vEN9dyDQM.jpg)](https://pp.userapi.com/c834204/v834204344/7e13d/W9vEN9dyDQM.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e133/pe9zaj0PF7g.jpg)](https://pp.userapi.com/c834204/v834204344/7e133/pe9zaj0PF7g.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e12a/MjZjecdzNcM.jpg)](https://pp.userapi.com/c834204/v834204344/7e12a/MjZjecdzNcM.jpg)

#### GET 2/3,  4 подключения, без повторов
[![](https://pp.userapi.com/c834204/v834204344/7e159/dgQrd2blFWU.jpg)](https://pp.userapi.com/c834204/v834204344/7e159/dgQrd2blFWU.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e14f/65ioObncShE.jpg)](https://pp.userapi.com/c834204/v834204344/7e14f/65ioObncShE.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e146/UE-x3CXd2wM.jpg)](https://pp.userapi.com/c834204/v834204344/7e146/UE-x3CXd2wM.jpg)

#### GET 3/3,  4 подключения, с повтором
[![](https://pp.userapi.com/c834204/v834204344/7e188/3Poxhc1lMS4.jpg)](https://pp.userapi.com/c834204/v834204344/7e188/3Poxhc1lMS4.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e17e/sP7cTC5bJVw.jpg)](https://pp.userapi.com/c834204/v834204344/7e17e/sP7cTC5bJVw.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e175/rnw-XsBvGEw.jpg)](https://pp.userapi.com/c834204/v834204344/7e175/rnw-XsBvGEw.jpg)

#### GET 3/3,  4 подключения, без повторов
[![](https://pp.userapi.com/c834204/v834204344/7e1b1/lbfwVYMX1Ow.jpg)](https://pp.userapi.com/c834204/v834204344/7e1b1/lbfwVYMX1Ow.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e1a7/x_pjdHIzEZU.jpg)](https://pp.userapi.com/c834204/v834204344/7e1a7/x_pjdHIzEZU.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e19e/W8snw-sVJiQ.jpg)](https://pp.userapi.com/c834204/v834204344/7e19e/W8snw-sVJiQ.jpg)

#### PUT 2/3,  4 подключения, с перезаписью
[![](https://pp.userapi.com/c834204/v834204344/7e1d6/A-cUmZIj6go.jpg)](https://pp.userapi.com/c834204/v834204344/7e1d6/A-cUmZIj6go.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e1cc/i-vwf07nTgY.jpg)](https://pp.userapi.com/c834204/v834204344/7e1cc/i-vwf07nTgY.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e1c3/x1jVyPVEQzI.jpg)](https://pp.userapi.com/c834204/v834204344/7e1c3/x1jVyPVEQzI.jpg)

#### PUT 2/3,  4 подключения, без перезаписи
[![](https://pp.userapi.com/c834204/v834204344/7e1f6/BzAQFZT8gS0.jpg)](https://pp.userapi.com/c834204/v834204344/7e1f6/BzAQFZT8gS0.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e1ec/RU3WhPiOZGc.jpg)](https://pp.userapi.com/c834204/v834204344/7e1ec/RU3WhPiOZGc.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e1e3/FDpeDXR4J_A.jpg)](https://pp.userapi.com/c834204/v834204344/7e1e3/FDpeDXR4J_A.jpg)

#### PUT 3/3,  4 подключения, с перезаписью
[![](https://pp.userapi.com/c834204/v834204344/7e212/OIuFZvEcNkc.jpg)](https://pp.userapi.com/c834204/v834204344/7e212/OIuFZvEcNkc.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e208/jVhDSdSHVuQ.jpg)](https://pp.userapi.com/c834204/v834204344/7e208/jVhDSdSHVuQ.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e1ff/hHYSOCtW0XY.jpg)](https://pp.userapi.com/c834204/v834204344/7e1ff/hHYSOCtW0XY.jpg)

#### PUT 3/3,  4 подключения, без перезаписи
[![](https://pp.userapi.com/c834204/v834204344/7e238/NltmeoMCrSU.jpg)](https://pp.userapi.com/c834204/v834204344/7e238/NltmeoMCrSU.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e22e/A_GukxnrLHo.jpg)](https://pp.userapi.com/c834204/v834204344/7e22e/A_GukxnrLHo.jpg)

[![](https://pp.userapi.com/c834204/v834204344/7e21b/Y7TaJav-qls.jpg)](https://pp.userapi.com/c834204/v834204344/7e21b/Y7TaJav-qls.jpg)

### Выводы по 1 этапу
Не все тесты смогли продлиться даже 1 минуту, есть коды ошибок и в целом обработка медленная - это Фиаско.

## Тестирование после оптимизации







