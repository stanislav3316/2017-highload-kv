#  3 Этап - нагрузочное тестирвоание
## используемые инструменты
#### Нагрузка
Нагрузку KV сервиса осуществляет Yandex-Tank (JMeter как Load Generator ).
Запуск танка производится следующей командой:
```sh
    $ yandex-tank -c load.ini
```
файл load.ini


```sh
    [tank]
    ; Disable phantom:
    plugin_phantom=
    ; Enable JMeter insted:
    plugin_jmeter=yandextank.plugins.JMeter
    plugin_uploader=yandextank.plugins.DataUploader overload
    plugin_telegraf=yandextank.plugins.Telegraf
    		
    [jmeter]
    jmx=/home/iters/high-dynamic/1 thread/GET/2_3 replicas/without_repeats/plan.jmx
    jmeter_ver=2.11
    
    [overload]
    # to obtain a token click on your avatar in the top-right corner
    token_file=token.txt
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

------------


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

------------


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

------------


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

#### GET 2/3,  1 подключение, с повтором
[![](https://pp.userapi.com/c834204/v834204046/7cabf/ibv-HL2LW98.jpg)](https://pp.userapi.com/c834204/v834204046/7cabf/ibv-HL2LW98.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cab5/Hh0VhvNFOrc.jpg)](https://pp.userapi.com/c834204/v834204046/7cab5/Hh0VhvNFOrc.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7caac/RkzWqe1ouG4.jpg)](https://pp.userapi.com/c834204/v834204046/7caac/RkzWqe1ouG4.jpg)

#### GET 2/3,  1 подключение, без повторов
[![](https://pp.userapi.com/c834204/v834204046/7caef/qIXnrCW8_is.jpg)](https://pp.userapi.com/c834204/v834204046/7caef/qIXnrCW8_is.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cae5/5x0TBZP0890.jpg)](https://pp.userapi.com/c834204/v834204046/7cae5/5x0TBZP0890.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cadc/3c-AZoSEk1I.jpg)](https://pp.userapi.com/c834204/v834204046/7cadc/3c-AZoSEk1I.jpg)

#### GET 3/3,  1 подключение, с повтором
[![](https://pp.userapi.com/c834204/v834204046/7cb1f/z7reTtwvAEI.jpg)](https://pp.userapi.com/c834204/v834204046/7cb1f/z7reTtwvAEI.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cb15/qFCqJMHzz4Q.jpg)](https://pp.userapi.com/c834204/v834204046/7cb15/qFCqJMHzz4Q.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cb0c/a_aXBWKXIF0.jpg)](https://pp.userapi.com/c834204/v834204046/7cb0c/a_aXBWKXIF0.jpg)

#### GET 3/3,  1 подключение, без повторов
[![](https://pp.userapi.com/c834204/v834204046/7cb3b/K1zGcEurnWY.jpg)](https://pp.userapi.com/c834204/v834204046/7cb3b/K1zGcEurnWY.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cb31/u2DxTek6ZOM.jpg)](https://pp.userapi.com/c834204/v834204046/7cb31/u2DxTek6ZOM.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cb28/t7SorIRwMrQ.jpg)](https://pp.userapi.com/c834204/v834204046/7cb28/t7SorIRwMrQ.jpg)

------------


#### PUT 2/3,  1 подключение, с перезаписью
[![](https://pp.userapi.com/c834204/v834204046/7cb6a/yg5DO2IKI8w.jpg)](https://pp.userapi.com/c834204/v834204046/7cb6a/yg5DO2IKI8w.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cb60/wVfuueJsGuc.jpg)](https://pp.userapi.com/c834204/v834204046/7cb60/wVfuueJsGuc.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cb57/sxRjlvfOOEs.jpg)](https://pp.userapi.com/c834204/v834204046/7cb57/sxRjlvfOOEs.jpg)

#### PUT 2/3,  1 подключение, без перезаписи
[![](https://pp.userapi.com/c834204/v834204046/7cb86/XmO_AAzbA8k.jpg)](https://pp.userapi.com/c834204/v834204046/7cb86/XmO_AAzbA8k.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cb73/hGv6QaO0Tec.jpg)](https://pp.userapi.com/c834204/v834204046/7cb73/hGv6QaO0Tec.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cb7c/eo7eRv5D8dk.jpg)](https://pp.userapi.com/c834204/v834204046/7cb7c/eo7eRv5D8dk.jpg)

#### PUT 3/3,  1 подключение, с перезаписью
[![](https://pp.userapi.com/c834204/v834204046/7cba2/tfM7F06oOgc.jpg)](https://pp.userapi.com/c834204/v834204046/7cba2/tfM7F06oOgc.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cb98/HdkDno-lnTA.jpg)](https://pp.userapi.com/c834204/v834204046/7cb98/HdkDno-lnTA.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cb8f/AdAxyeOL7rU.jpg)](https://pp.userapi.com/c834204/v834204046/7cb8f/AdAxyeOL7rU.jpg)

#### PUT 3/3,  1 подключение, без перезаписи
[![](https://pp.userapi.com/c834204/v834204046/7cbe2/3n-yMSk51sM.jpg)](https://pp.userapi.com/c834204/v834204046/7cbe2/3n-yMSk51sM.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cbd8/_40J3uIYjGo.jpg)](https://pp.userapi.com/c834204/v834204046/7cbd8/_40J3uIYjGo.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cbcf/5oArwFcVwN8.jpg)](https://pp.userapi.com/c834204/v834204046/7cbcf/5oArwFcVwN8.jpg)

------------

#### GET 2/3,  2 подключения, с повтором
[![](https://pp.userapi.com/c834204/v834204046/7cc0c/dJ_MOr0binw.jpg)](https://pp.userapi.com/c834204/v834204046/7cc0c/dJ_MOr0binw.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cc02/FJTpoGKVGrs.jpg)](https://pp.userapi.com/c834204/v834204046/7cc02/FJTpoGKVGrs.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cbf9/ibHM4FVd_QI.jpg)](https://pp.userapi.com/c834204/v834204046/7cbf9/ibHM4FVd_QI.jpg)

#### GET 2/3,  2 подключения, без повторов
[![](https://pp.userapi.com/c834204/v834204046/7cc32/Z4mkN-lks8E.jpg)](https://pp.userapi.com/c834204/v834204046/7cc32/Z4mkN-lks8E.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cc28/nzNEU7jLSfs.jpg)](https://pp.userapi.com/c834204/v834204046/7cc28/nzNEU7jLSfs.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cc1f/1dk3iRdMuSw.jpg)](https://pp.userapi.com/c834204/v834204046/7cc1f/1dk3iRdMuSw.jpg)

#### GET 3/3,  2 подключения, с повтором
[![](https://pp.userapi.com/c834204/v834204046/7cc69/AyQ_H9rtUNM.jpg)](https://pp.userapi.com/c834204/v834204046/7cc69/AyQ_H9rtUNM.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cc5f/VGqKPOOLpS0.jpg)](https://pp.userapi.com/c834204/v834204046/7cc5f/VGqKPOOLpS0.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cc56/qTN2Gy_gwqI.jpg)](https://pp.userapi.com/c834204/v834204046/7cc56/qTN2Gy_gwqI.jpg)

#### GET 3/3,  2 подключения, без повторов
[![](https://pp.userapi.com/c834204/v834204046/7cc98/h-8JfuQYJDU.jpg)](https://pp.userapi.com/c834204/v834204046/7cc98/h-8JfuQYJDU.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cc8e/5bHo9OqFWYM.jpg)](https://pp.userapi.com/c834204/v834204046/7cc8e/5bHo9OqFWYM.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cc85/OJ6zp1DNy_k.jpg)](https://pp.userapi.com/c834204/v834204046/7cc85/OJ6zp1DNy_k.jpg)

------------


#### PUT 2/3,  2 подключения, с перезаписью
[![](https://pp.userapi.com/c834204/v834204046/7ccb4/pvxbh_y86J4.jpg)](https://pp.userapi.com/c834204/v834204046/7ccb4/pvxbh_y86J4.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7ccaa/lJDC2N7Z5a4.jpg)](https://pp.userapi.com/c834204/v834204046/7ccaa/lJDC2N7Z5a4.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cca1/UhAXCjkqpqQ.jpg)](https://pp.userapi.com/c834204/v834204046/7cca1/UhAXCjkqpqQ.jpg)

#### PUT 2/3,  2 подключения, без перезаписи
[![](https://pp.userapi.com/c834204/v834204046/7ccd1/atFQds4LTjw.jpg)](https://pp.userapi.com/c834204/v834204046/7ccd1/atFQds4LTjw.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7ccc7/2fx1AAGZa3o.jpg)](https://pp.userapi.com/c834204/v834204046/7ccc7/2fx1AAGZa3o.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7ccbe/4qO_OdsIX5s.jpg)](https://pp.userapi.com/c834204/v834204046/7ccbe/4qO_OdsIX5s.jpg)

#### PUT 3/3,  2 подключения, с перезаписью
[![](https://pp.userapi.com/c834204/v834204046/7cced/etcGNvSvUTY.jpg)](https://pp.userapi.com/c834204/v834204046/7cced/etcGNvSvUTY.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7cce3/ajKc_RXuV6s.jpg)](https://pp.userapi.com/c834204/v834204046/7cce3/ajKc_RXuV6s.jpg)

[![](https://pp.userapi.com/c834204/v834204046/7ccda/j8UpLzL6C1I.jpg)](https://pp.userapi.com/c834204/v834204046/7ccda/j8UpLzL6C1I.jpg)

#### PUT 3/3,  2 подключения, без перезаписи
[![](https://pp.userapi.com/c834204/v834204894/812d3/9c5OlRW-2p0.jpg)](https://pp.userapi.com/c834204/v834204894/812d3/9c5OlRW-2p0.jpg)

[![](https://pp.userapi.com/c834204/v834204894/812c0/5gz-9wOj7sA.jpg)](https://pp.userapi.com/c834204/v834204894/812c0/5gz-9wOj7sA.jpg)

[![](https://pp.userapi.com/c834204/v834204894/812c9/Im-PeGS2cRI.jpg)](https://pp.userapi.com/c834204/v834204894/812c9/Im-PeGS2cRI.jpg)

------------

#### GET 2/3, 4 подключения, с повтором
[![](https://pp.userapi.com/c834204/v834204894/812f3/XEpE69NUdEk.jpg)](https://pp.userapi.com/c834204/v834204894/812f3/XEpE69NUdEk.jpg)

[![](https://pp.userapi.com/c834204/v834204894/812e9/LtsoXCFW7B8.jpg)](https://pp.userapi.com/c834204/v834204894/812e9/LtsoXCFW7B8.jpg)

[![](https://pp.userapi.com/c834204/v834204894/812e0/jzVnml69i4Y.jpg)](https://pp.userapi.com/c834204/v834204894/812e0/jzVnml69i4Y.jpg)

#### GET 2/3,  4 подключения, без повторов
[![](https://pp.userapi.com/c834204/v834204894/8130f/DJRhAcAedeM.jpg)](https://pp.userapi.com/c834204/v834204894/8130f/DJRhAcAedeM.jpg)

[![](https://pp.userapi.com/c834204/v834204894/81305/a0dODh6Ozrk.jpg)](https://pp.userapi.com/c834204/v834204894/81305/a0dODh6Ozrk.jpg)

[![](https://pp.userapi.com/c834204/v834204894/812fc/FVRXHXKT9Lk.jpg)](https://pp.userapi.com/c834204/v834204894/812fc/FVRXHXKT9Lk.jpg)

#### GET 3/3,  4 подключения, с повтором
[![](https://pp.userapi.com/c834204/v834204894/8132b/tiUBCQJxuIc.jpg)](https://pp.userapi.com/c834204/v834204894/8132b/tiUBCQJxuIc.jpg)

[![](https://pp.userapi.com/c834204/v834204894/81321/74mTC-_8NwQ.jpg)](https://pp.userapi.com/c834204/v834204894/81321/74mTC-_8NwQ.jpg)

[![](https://pp.userapi.com/c834204/v834204894/81318/pCd_NUx8FxU.jpg)](https://pp.userapi.com/c834204/v834204894/81318/pCd_NUx8FxU.jpg)

#### GET 3/3,  4 подключения, без повторов
[![](https://pp.userapi.com/c834204/v834204894/81347/JSsOtB4H_h8.jpg)](https://pp.userapi.com/c834204/v834204894/81347/JSsOtB4H_h8.jpg)

[![](https://pp.userapi.com/c834204/v834204894/8133d/0-phHLgABJY.jpg)](https://pp.userapi.com/c834204/v834204894/8133d/0-phHLgABJY.jpg)

[![](https://pp.userapi.com/c834204/v834204894/81334/xy3ripROy18.jpg)](https://pp.userapi.com/c834204/v834204894/81334/xy3ripROy18.jpg)

------------


#### PUT 2/3,  4 подключения, с перезаписью
[![](https://pp.userapi.com/c834204/v834204894/81375/gwLmID3cZdU.jpg)](https://pp.userapi.com/c834204/v834204894/81375/gwLmID3cZdU.jpg)

[![](https://pp.userapi.com/c834204/v834204894/8136b/C4B_DBT1QDY.jpg)](https://pp.userapi.com/c834204/v834204894/8136b/C4B_DBT1QDY.jpg)

[![](https://pp.userapi.com/c834204/v834204894/81362/mCcJ8W1DrxU.jpg)](https://pp.userapi.com/c834204/v834204894/81362/mCcJ8W1DrxU.jpg)

#### PUT 2/3,  4 подключения, без перезаписи
[![](https://pp.userapi.com/c834204/v834204894/81391/wzIH2ndKdu0.jpg)](https://pp.userapi.com/c834204/v834204894/81391/wzIH2ndKdu0.jpg)

[![](https://pp.userapi.com/c834204/v834204894/81387/sS5itDshyQ4.jpg)](https://pp.userapi.com/c834204/v834204894/81387/sS5itDshyQ4.jpg)

[![](https://pp.userapi.com/c834204/v834204894/8137e/qtqRNTNgwEI.jpg)](https://pp.userapi.com/c834204/v834204894/8137e/qtqRNTNgwEI.jpg)

#### PUT 3/3,  4 подключения, с перезаписью
[![](https://pp.userapi.com/c834204/v834204894/813b5/LcQCZ6x_2Gw.jpg)](https://pp.userapi.com/c834204/v834204894/813b5/LcQCZ6x_2Gw.jpg)

[![](https://pp.userapi.com/c834204/v834204894/813ab/GnK1hGyaYNE.jpg)](https://pp.userapi.com/c834204/v834204894/813ab/GnK1hGyaYNE.jpg)

[![](https://pp.userapi.com/c834204/v834204894/813a2/KYhPjbsVaHQ.jpg)](https://pp.userapi.com/c834204/v834204894/813a2/KYhPjbsVaHQ.jpg)

#### PUT 3/3,  4 подключения, без перезаписи
Фотки из консоли нет, т.к.  папка /tmp/ была перегружена и система вышла из строя. Заного запускать не захотел)

[![](https://pp.userapi.com/c834204/v834204894/813d9/Nh2JBS92qrM.jpg)](https://pp.userapi.com/c834204/v834204894/813d9/Nh2JBS92qrM.jpg)

[![](https://pp.userapi.com/c834204/v834204894/813d0/odpc966_5zI.jpg)](https://pp.userapi.com/c834204/v834204894/813d0/odpc966_5zI.jpg)

### Выводы по 2 этапу
Результаты оптимизаций отчетливо видны при 4 соединениях. При 1 и 2 соединениях коды ответов теперь без ошибок, сервис держит нагрузку и по графикам видно что он отлично справляется (без особых затруднений).

### Оптимизации
- добавлен пулл соединения HttpClient
- сохранение данных идет в отдельном потоке - saveData()
- кеширование часто используемых объектов
- FrontController пеперь отсылает запросы на BackControler непосредственно на нужный метод, а не на метод handler(которые уже смотрит метод запроса и вызывает обработчик соответствующий)
- добавление LFU кеш для storage
- убрана многопоточность при обработке запросов через ExecutorCompletionService, как показали замеры в "горячем" методе его лучше не использовать, т.к. тратится время на создание объектов Future
- упрощена схема вычисления хеша для id
- уверен что что-то еще, но я забыл






