# NLChat星闪聊天软件

2024.8.9 已确定适配更多新款星闪产品，助力星闪相关项目落地参加活动，敬请期待1.4新版本，我们会在该版本上线适配完整的相关版本和相关代码。

![IMG.SHIELD.IO](https://img.shields.io/github/v/release/Hny0305Lin/NLChat?include_prereleases&display_name=release&style=for-the-badge&logo=Android&logoSize=amd&label=Hi2821_NLChat&labelColor=007FFC&color=4DDDFC&cacheSeconds=60) ![IMG.SHIELD.IO](https://img.shields.io/github/stars/Hny0305Lin/NLChat?style=for-the-badge&label=NLChat%20Star&labelColor=007FFC&color=4DDDFC&cacheSeconds=60)

![Android自动化构建 Debug](https://img.shields.io/github/actions/workflow/status/Hny0305Lin/NLChat/android.yml?branch=master&style=for-the-badge&logo=android&logoSize=amd&label=Android%20CI&labelColor=00CEFC&color=00C08B&cacheSeconds=600) ![Android自动化构建 Release](https://img.shields.io/github/actions/workflow/status/Hny0305Lin/NLChat/androidsign.yml?branch=master&style=for-the-badge&logo=android&logoSize=amd&label=Android%20CI%20Release&labelColor=00CEFC&color=00C08B&cacheSeconds=600)

[![霓光计划-Harmony/NLChat星闪通讯软件](https://gitee.com/light-harmonyOS/NLChat/widgets/widget_card.svg?colors=393222,ebdfc1,fffae5,d8ca9f,393222,a28b40)](https://gitee.com/light-harmonyOS/NLChat)

[点我前往H2821硬件侧端](https://github.com/Hny0305Lin/Bearpi_Hi2821_Pico_NLChat) [点我前往H3863硬件侧端](https://github.com/Hny0305Lin/Bearpi_Hi3863_Pico)

[点我前往烧写编译教程](https://github.com/Hny0305Lin/NLChat/blob/master/SLE_Device_Configure_Tutorial.md)

[51CTO文章1](https://ost.51cto.com/posts/29266)  [51CTO文章2](https://ost.51cto.com/posts/29275)  [51CTO文章3](https://ost.51cto.com/posts/29294) [51CTO文章4](https://ost.51cto.com/posts/29341)

![NLChat](https://raw.githubusercontent.com/Hny0305Lin/NLChat/master/DevSummary/NLChat.jpg)

# 项目简介

NLChat星闪聊天软件，是我基于小熊派H2821开发板和官方代码做的一款使用星闪网络的聊天软件。作为一款近场通讯软件，我们主打的核心就是：**让Android设备乃至其他，都能通过星闪网络互联。星闪，正是我们要凸显的一大特色**，它在低时延的近场通信场景下表现出色，使得我们可以做到文本聊天、收发及时。这款软件是在6月下旬启动开发调试，直到今天我们一直在做产品软件的完善和迭代更新，如今已经从最初的聊天，做到了聊天记录保存成SQL、聊天记录自动复制、滚动显示等聊天软件正常需求。

# 使用到的硬件 和 硬件重要资料

### Hi2821 BearPi小熊派

* 购买地址:[tb](https://item.taobao.com/item.htm?spm=a21n57.1.item.2.16c8523cW90sjA&priceTId=2147825e17192240256724107e3028&utparam=%7B%22aplus_abtest%22:%22112cd35adf597e1a65a58f5e2046df26%22%7D&id=803331789469&ns=1&abbucket=20)
* 芯片资料:[星闪保密机制 目前仅限于官网查看部分资料](https://bearpi.cn/core_board/bearpi/pico/h2821/hardware/Pinout.html#%F0%9F%93%91-pinout)
* 开发资料:[点我进入 目前仅限于官网查看部分资料](https://www.bearpi.cn/core_board/bearpi/pico/h2821/)
* 有关资料:[gitee点我查看](https://gitee.com/bearpi/bearpi-h2821_pico)
* ⚠️强烈建议中国大陆用户能使用GitHub就使用GitHub，Gitee仅仅是我们浩瀚银河中国大陆的仓库设置地，未来可能会缺少重要更新。

### Hi3863 BearPi小熊派

待补充，敬请期待。

# 🔝Haohanyh Computer Software Products Open Source LICENSE

* 下载地址1：https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE
* 下载地址2: 无

# 项目使用到的开源协议

### Copyright (c) @CompanyNameMagicTag 2023-2023. All rights reserved.

* 使用开源协议：未知
* 介绍：NLChat的成功，离不开小熊派开源社区的支持和公开的SLE_UART整套Demo代码支持。感谢背后推动星闪工作的你们，给星闪开发者们带来相关芯片和相关资料。
* Description: SLE UART Sample Source.
* 作者: @CompanyNameTag
* 代码开源地址: [点我进入](https://gitee.com/bearpi/bearpi-h2821_pico)

### Copyright (c) Nanjing Xiaoxiongpai Intelligent Technology Co., Ltd.

* 使用开源协议：未知
* 介绍: 南京小熊派智能科技有限公司，致力于鸿蒙生态的硬件开发，提供硬件和软件的解决方案。感谢背后推动星闪工作的你们，给星闪开发者们带来Hi3863、Hi2821等相关芯片和相关资料。
* 官网介绍：[点我进入](https://bearpi.cn/about/)
* 代码开源地址: [点我进入](https://gitee.com/bearpi/bearpi-h2821_pico)

### Copyright (c) HiSilicon (Shanghai) Technologies Co., Ltd. 2023-2023. All rights reserved.

* 使用开源协议：Apache-2.0
* 介绍: 上海海思技术有限公司，华为旗下半导体公司，提供海思芯片相关行业解决方案。感谢背后推动星闪工作的你们，给星闪开发者们带来Hi3863、Hi2821等相关芯片和相关资料。
* 官网介绍：[点我进入](https://bearpi.cn/about/)
* 代码开源地址: [点我进入](https://gitee.com/bearpi/bearpi-h2821_pico)

### Copyright (c) 2002-2024 南京沁恒微电子股份有限公司

* 使用开源协议：未知
* 使用如下源码：ch34x Library反编译相关代码。感谢沁恒提供的CH34X相关代码，让我们能做到Android连接CH34X USB芯片相关的开发板。
* 代码开源地址(含demo)：[点我进入](https://www.wch.cn/downloads/CH341SER_ANDROID_ZIP.html)

### Copyright (c) Yutils

* 使用开源协议：Apache-2.0 License
* 使用如下源码：ch34x部分相关代码，用于CH34X芯片与Android的USB通道通讯。
* 代码开源地址: [点我进入](https://github.com/yutils/CH34xUART)

### Copyright (c) GitHub

* 使用开源协议：未知
* 介绍：NLChat在海外Github仓库上已从1.1.28版本开始，启动Android CI自动化编译流水线，感谢Github提供性能生成测试和大量教程。
* 代码相关地址：[点我进入](https://github.com/Hny0305Lin/NLChat/tree/master/.github/workflows)

### Copyright (c) Drew Heavner

* 使用开源协议：MIT License
* 介绍：NLChat在海外Github仓库上已从1.2.1版本开始，启动Android CI Release自动化编译发行流水线，感谢这位作者提供实现能力仓库。
* 代码开源地址：[点我进入](https://github.com/r0adkll/sign-android-release)
* 代码相关地址：[点我进入](https://github.com/Hny0305Lin/NLChat/tree/master/.github/workflows/androidsign.yml)

### Copyright (c) Android SQLite support Library

* 使用开源协议：Apache-2.0 License
* 介绍：在Android APP上，使用SQLite。
* 代码开源地址：[点我进入](https://github.com/requery/sqlite-android)

### Copyright (c) 2017-2022 Adobe & Source

* 使用开源协议：SIL OPEN FONT LICENSE Version 1.1 - 26 February 2007
* 介绍：思源字体，是一套 OpenType/CFF 泛中日韩字体。这个开源项目不仅提供了可用的 OpenType 字体，还提供了利用 AFDKO 工具创建这些 OpenType 字体时的所有源文件。
* 代码开源地址：[点我进入](https://github.com/adobe-fonts/source-han-sans)

### Copyright (c) 2019 JT Foundry

* 使用开源协议：SIL Open Font License 1.1（官网标注）
* 介绍：翰字鑄造 JT Foundry，規劃以開源的思源黑體（Source Han Sans）為基礎進行改作。改作而成的字型以繁體中文世界的其中一個核心都市為名：台北黑體。
* 代码开源地址：[点我进入,Github暂无](https://github.com/jtfoundry)
* 下载地址: [点我进入](https://sites.google.com/view/jtfoundry/zh-tw/downloads)

### Copyright (c) Taobao (China) Software Co., Ltd

* 使用开源协议：未知，但允许个人商业使用
* 介绍：阿里妈妈东方大楷体字体及包含该字体的字库软件，合称“阿里妈妈东方大楷体”，知识产权和相关权益归属于淘宝（中国）软件有限公司。
* 下载地址: [国内地址,点我进入](https://www.iconfont.cn/fonts/detail?spm=a313x.fonts_index.i1.d9df05512.2db73a81rlvwNI&cnid=IhcTcFymWeyf)

### Copyright (c) Dingding (China) Information Technology Co., Ltd

* 使用开源协议：未知，但允许个人商业使用
* 介绍：钉钉进步体字体，包含钉钉进步体中文字体和西文字体、DingTalk Sans（西文字体），前述字体及包含该字体的字库软件，合称“钉钉进步体”，知识产权和相关权益归属于钉钉（中国）信息技术有限公司。
* 下载地址: [国内地址,点我进入](https://www.iconfont.cn/fonts/detail?spm=a313x.fonts_index.i1.d9df05512.2db73a81rlvwNI&cnid=clpB5hhpYWUN)

### Copyright (c) 2022--2024, atelierAnchor [https://atelier-anchor.com](https://atelier-anchor.com),with Reserved Font Name <Smiley> and <得意黑>.

* 使用开源协议：SIL OPEN FONT LICENSE Version 1.1 - 26 February 2007
* 介绍：得意黑是一款在人文观感和几何特征中寻找平衡的中文黑体。整体字身窄而斜，细节融入了取法手绘美术字的特殊造型。字体支持简体中文常用字（覆盖 GB/T 2312-1980 编码字符集和《通用规范汉字表》）、拉丁字母、西里尔字母、希腊字母、日文假名、阿拉伯数字和各类标点符号。
* 代码开源地址: [点我进入](https://github.com/atelier-anchor/smiley-sans)
* 下载地址: [国内地址,点我进入](https://atelier-anchor.com/typefaces/smiley-sans)

### 版权所有 (C) 2019-FUTURE 浩瀚银河，版权所有。

### 浩瀚银河字体 使用 浩瀚银河计算机软件产品源代码开放协议

**永久开源，二次传播使用请署名“浩瀚银河”**

* 使用开源协议：Haohanyh Computer Software Products Open Source LICENSE

### 版权所有 (C) 2019-FUTURE 浩瀚银河，版权所有。

### NLChat 使用 浩瀚银河计算机软件产品源代码开放协议

**永久开源，二次开发请署名“浩瀚银河”**

* 使用开源协议：Haohanyh Computer Software Products Open Source LICENSE

# 小小的须知（不知道也没关系）

🆓 此项目是开源的，可二次开发~

> 本项目，只需要遵守浩瀚银河开源协议，和部分作者协议，即可开源OK！

🆒 项目正努力往完美方向发展，需要时间的积累~

> 本项目目前还是正在更新中版本，但是就目前而言开源的代码已经很Cool了所以请麻烦给个⭐ 、🍴 、👁️三连支持吧
>
> （Star是对我们最棒的支持~，Fork是对你们最好的功能~，Watch可随时订阅我们的开源状况）

# 关于该项目已通过测试的设备

[![Xiaomi 13](https://img.shields.io/badge/Xiaomi%2013-FF6900?style=flat-square&logo=xiaomi&logoColor=FFFFFF&labelColor=FF6900)](https://www.mi.com/hk/product/xiaomi-13/) [![Xiaomi MIX 2S](https://img.shields.io/badge/Xiaomi%20MIX%202S-FF6900?style=flat-square&logo=xiaomi&logoColor=FFFFFF&labelColor=FF6900)](https://www.mi.com/hk/mix2s/) [![Xiaomi 9](https://img.shields.io/badge/Xiaomi%209-FF6900?style=flat-square&logo=xiaomi&logoColor=FFFFFF&labelColor=FF6900)](https://www.mi.com/hk/mi9/)

[![Oneplus 7T](https://img.shields.io/badge/Oneplus%207T-F5010D?style=flat-square&logo=oneplus&logoColor=FFFFFF&labelColor=F40010)](https://www.oneplus.com/hk/7t)

[![OPPO A97 PFTM10](https://img.shields.io/badge/OPPO%20A97%20PFTM10%20(%E5%8F%82%E4%B8%8E%E8%B0%83%E8%AF%95,%E6%84%9F%E8%B0%A2%E6%B7%A1%E5%A2%A8%E7%94%A8%E6%88%B7)-006B31?style=flat-square&logo=oppo&logoColor=FFFFFF&labelColor=006D37)](https://www.oppo.com/cn/smartphones/series-a/a97/specs/)

[![Huawei Mate 50](https://img.shields.io/badge/Huawei%20Mate%2050(HarmonyOS%204,%E5%8F%82%E4%B8%8E%E8%B0%83%E8%AF%95,%E6%84%9F%E8%B0%A2Destiny%E7%94%A8%E6%88%B7)-F5010D?style=flat-square&logo=huawei&logoColor=FFFFFF&labelColor=F40010)](https://consumer.huawei.com/hk/phones/mate50/)

[![Honor 70](https://img.shields.io/badge/Honor%2070-00CEFC?style=flat-square&logo=honor&logoColor=FFFFFF&labelColor=00CEFC)](https://www.honor.com/hk/phones/honor-70/) [![Honor Play 4T Pro](https://img.shields.io/badge/Honor%20Play%204T%20Pro%20(%E5%8F%82%E4%B8%8E%E8%B0%83%E8%AF%95,%E6%84%9F%E8%B0%A2Steven%E7%94%A8%E6%88%B7)-00CEFC?style=flat-square&logo=honor&logoColor=FFFFFF&labelColor=00CEFC)](https://baike.baidu.com/item/%E8%8D%A3%E8%80%80Play4T%20Pro/49793463)

[![Android ARM64](https://img.shields.io/badge/Android%20ARM64模拟器-00C000?style=flat-square&logo=android&logoColor=FFFFFF&labelColor=00C000)](https://github.com/Hny0305Lin/NLChat/actions/workflows/androidsign.yml)

### 期待您有更多的设备参与进来测试，以帮助该项目通过测试的设备更完善！

* 浩瀚银河为鼓励项目更多人参与进来，可以对以上设备以外(除Android ARM64等模拟器)参与测试的人员，给予不等的实物奖励。只需要您参与星闪的通讯软件功能使用，并反馈问题，即可获得。截止时间不限，奖励有限。（目前已有多位测试官们获得实物奖励）
* 设备表中以外的Android设备、华为鸿蒙操作系统设备、非星闪Android设备，优先参与测试，请自备一对小熊派星闪开发板按教程烧入相关代码后，即可参与测试。
* 请联系作者，获取参与测试的资格。如未*通过作者联系私自参与测试*，将视为违规，将取消参与资格。
* 请注意，参与测试的人员，如有二次开发商业化等需求，请参照开源协议。
* 该活动最终解释权归浩瀚银河负责人所有。

# 关于商业化该项目

请取得部分未使用开源协议的作者的同意，即可将该项目商业化。

# 关于该项目参与华为海思首批星闪开发者体验官活动

该项目，已在2024-07-01 15:57:56投稿至海思社区网站[点我前往](https://developers.hisilicon.com/postDetail?tid=02102154262223765013)

作为一份**容易上手**、**功能正在完善**、**永久开源且允许二次开发**等特色，且凸出了星闪网络**高可靠**、**低时延**等长项的网络聊天项目，一直以来得到了不少的关注和大家的期待。浩瀚银河将会保证如下:

- 新的1.4版本，将会支持更多功能，支持星闪BLE，支持更多好玩的特性。
- 后续如有跟进一对多的星闪网络底层Demo，将会推出更好的"群聊"、"区域网络"等功能，具体请以未来小熊派社区开源Demo的实现效果应用于此项目最终效果为准。
- ~~保证目前已知且公开的进度，在该活动期间完成。~~
- 保证目前已知且公开的进度，在**该活动期间**并提前完成。
- 保证项目在此期间**永久开源**，永久保护二次开发的作者著作权。
- 将会在中国大陆申请相关著作权保护软件版权，不受违法侵害和破坏开源精神等行为影响。
- 所有在此项目上获得的收益，将会**无条件用于回馈给予帮助此项目的开发者等等用户们**(比如最近开展的设备测试活动，已有大量用户参与进来测试并获得现金等实物奖励，后续将会继续开展相关活动)
- 浩瀚银河保证此项目**不侵害任何作者相关开源版权**，所有代码公开，并标注版权。如有疑问，可与我联系*并提供相关证据*，将立即停止我们对您的侵权行为，并给予相关赔偿。
- 浩瀚银河保证此项目**不用于违法行为**，诸如:不受控制的网络攻击、获取用户隐私制作面像等等。
- 浩瀚银河保证此项目遵守《深圳经济特区数据条例》、《中华人民共和国网络安全法》等当地相关法律法规，保证聊天记录、聊天信息、UUID等相关隐私*不外传、不联网、安全保存*。
- 浩瀚银河保证在参与活动期间，会与各行各业开发者们保持友好交流，提倡互帮互助，共同进步。如有不友好行为或项目侵权等等不利于活动开展行为。将会停止参与活动，并对此保留进一步追究权利。
- 以上内容最终解释权归浩瀚银河负责人所有。

2024-07-31 12:00 已得知参赛成功，开发工作继续，直至比赛结束后再确定后续安排。

[该项目位于列表第18号](https://developers.hisilicon.com/postDetail?tid=0238157777085907062)

2024-08-01 10:00 公布项目应用开发进度，并在此公布项目最终成果。

[该项目目前进展](https://github.com/Hny0305Lin/NLChat/blob/master/UPDATE.md)

- 1.4版本将会按上方内容实现更多功能。
- 按计划，会在今年推出星闪最小化Pico开发板样品，并开源相关原理图、PCB图等内容。（如果可以的话）
- 适配Hi2821和Hi3863等星闪多型号使用。

2024-08-09 已收到参加活动的润和WS63开发板套装，且在今天购买了小熊派Hi3863开发板，确定新版本适配这些套装开发板，让更多人参与星闪网络体验。
