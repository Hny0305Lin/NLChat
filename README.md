# Hi2821_NLChat

![IMG.SHIELD.IO](https://img.shields.io/github/v/release/Hny0305Lin/NLChat?include_prereleases&display_name=release&style=for-the-badge&logo=Android&logoSize=amd&label=Hi2821_NLChat&labelColor=007FFC&color=4DDDFC&cacheSeconds=60) ![IMG.SHIELD.IO](https://img.shields.io/github/stars/Hny0305Lin/NLChat?style=for-the-badge&label=NLChat%20Star&labelColor=007FFC&color=4DDDFC&cacheSeconds=60)

![Android自动化构建 Debug](https://img.shields.io/github/actions/workflow/status/Hny0305Lin/NLChat/android.yml?branch=master&style=for-the-badge&logo=android&logoSize=amd&label=Android%20CI&labelColor=00CEFC&color=00C08B&cacheSeconds=600) ![Android自动化构建 Release](https://img.shields.io/github/actions/workflow/status/Hny0305Lin/NLChat/androidsign.yml?branch=master&style=for-the-badge&logo=android&logoSize=amd&label=Android%20CI%20Release&labelColor=00CEFC&color=00C08B&cacheSeconds=600)

![Static Badge](https://img.shields.io/badge/Bearpi%20Hi2821%20Pico%20NLChat-00C08B?style=for-the-badge&logo=C%2B%2B&labelColor=00CC66&link=https%3A%2F%2Fgithub.com%2FHny0305Lin%2FBearpi_Hi2821_Pico_NLChat)

[![霓光计划-Harmony/NLChat星闪通讯软件](https://gitee.com/light-harmonyOS/NLChat/widgets/widget_card.svg?colors=393222,ebdfc1,fffae5,d8ca9f,393222,a28b40)](https://gitee.com/light-harmonyOS/NLChat)

[点我前往硬件侧端](https://github.com/Hny0305Lin/Bearpi_Hi2821_Pico_NLChat) [点我前往烧写编译教程](https://github.com/Hny0305Lin/NLChat/blob/master/SLE_Device_Configure_Tutorial.md)

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

# 🔝Haohanyh Computer Software Products Open Source LICENSE

* 下载地址1：https://github.com/Hny0305Lin/LICENSE/blob/main/LICENSE
* 下载地址2: 无

# 项目使用到的开源协议

### Copyright (c) @CompanyNameMagicTag 2023-2023. All rights reserved.

* 使用开源协议：未知
* 介绍：NLChat的成功，离不开小熊派开源社区的支持和公开的SLE_UART整套Demo代码支持。
* Description: SLE UART Sample Source.
* 作者: @CompanyNameTag
* 代码开源地址: [点我进入](https://gitee.com/bearpi/bearpi-h2821_pico)

### Copyright (c) Nanjing Xiaoxiongpai Intelligent Technology Co., Ltd.

* 使用开源协议：未知
* 介绍: 南京小熊派智能科技有限公司，致力于鸿蒙生态的硬件开发，提供硬件和软件的解决方案。
* 官网介绍：[点我进入](https://bearpi.cn/about/)
* 代码开源地址: [点我进入](https://gitee.com/bearpi/bearpi-h2821_pico)

### Copyright (c) 2002-2024 南京沁恒微电子股份有限公司

* 使用开源协议：未知
* 使用如下源码：ch34x Library反编译相关代码
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

# 更新情况

### 表格情况

| 版本号/功能 | CH340驱动 | 聊天功能 | 中文聊天 | SQLite管理 | 串口处理 | 剪贴板 | 背景运行 | 阅后即焚 | Github CI | 聊天增强 | 缓存内容 |
| --------- | --------- | -------- | -------- | -------- | -------- | -------- | -------- | ------- | :------: | -------- | -------- |
| [0.1.1.2024.0620](https://github.com/Hny0305Lin/NLChat/tree/58260c79e4987f945b3d090cd1ebf85317e081f6) | ✅ | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ |
| [1.0.12.2024.0625](https://github.com/Hny0305Lin/NLChat/tree/a7b3174baef120ecba70b48cd46ffd684e02ade2) | ✅ | ✅ | ❌ | ❌ | 💦 | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ |
| [1.1.26.2024.0625](https://github.com/Hny0305Lin/NLChat/tree/79cf346f4b454c6800ac0cd2ad3a25ccec1abfc1) | ✅ | ✅ | ❌ | 💦 | 💦 | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ |
| [1.2.1.2024.0701](https://github.com/Hny0305Lin/NLChat/tree/c171a44eddd3e021fd4f8466248aace08276c7e4) | ✅ | ✅ | ❌ | 💦 | 💦 | ❌ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9734249414/job/26862046534) | ❌ | ❌ |
| [1.2.16.2024.0701](https://github.com/Hny0305Lin/NLChat/tree/v1.2.16.2024.0701) | ✅ | ✅新增滚动显示 | ❌ | 💦可保存 | 💦 | ❌ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9736270620/job/26866668332) | ❌ | ❌ |
| [1.2.18.2024.0701](https://github.com/Hny0305Lin/NLChat/tree/v1.2.18.2024.0701) | ✅ | ✅ | ❌ | ✅修复bug | 💦 | ❌ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9736507708/job/26867235310) | ❌ | ❌ |
| [1.2.31.2024.0701](https://github.com/Hny0305Lin/NLChat/tree/v1.2.31.2024.0701) | ✅ | ✅ | ❌ | ✅ | 💦 | 💦仅数字(BETA) | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9739412445/job/26874577151) | ❌ | ❌ |
| [1.2.50.2024.0701](https://github.com/Hny0305Lin/NLChat/tree/v1.2.50.2024.0701) | ✅ | ✅ | ❌ | ✅修复空行 | 💦 | 💦 | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9744249513/job/26889473790) | ❌ | ❌ |
| [1.2.62.2024.0702](https://github.com/Hny0305Lin/NLChat/tree/v1.2.62.2024.0702) | ✅ | ✅修复消息滚动bug | ❌ | ✅ | 💦 | 💦 | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9759941990/job/26937682181) | ❌ | ❌ |
| [1.2.64.2024.0702](https://github.com/Hny0305Lin/NLChat/tree/v1.2.64.2024.0702) | ✅ | ✅ | ❌ | ✅ | 💦 | 💦 | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9759971546/job/26937771253) | ❌ | ❌ |
| [1.2.69.2024.0702](https://github.com/Hny0305Lin/NLChat/tree/v1.2.69.2024.0702) | ✅ | ✅ | ❌ | ✅ | ✅ | 💦修复bug | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9761003926/job/26941078614) | ❌ | ❌ |
| [1.2.71.2024.0702](https://github.com/Hny0305Lin/NLChat/tree/v1.2.71.2024.0702) | ✅ | ✅ | ❌ | ✅ | ✅ | 💦修复bug | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9764826462/job/26954067968) | ❌ | ❌ |
| [1.2.79.2024.0703](https://github.com/Hny0305Lin/NLChat/tree/v1.2.79.2024.0703) | ✅ | ✅ | ❌ | ✅ | ✅ | ✅验证码,链接,电子邮件等 | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9765127666/job/26955104588) | ❌ | ❌ |
| [1.2.93.2024.0703](https://github.com/Hny0305Lin/NLChat/tree/v1.2.93.2024.0703) ✅稳定版 | ✅ | ✅ | ❌ | ✅修复写入错误 | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9769518695/job/26969100870) | ❌ | ❌ |
| [1.3.1.2024.0703.alpha](https://github.com/Hny0305Lin/NLChat/tree/v1.3.1.2024.0703.alpha) | ✅ | ✅新UI | ❌ | ✅ | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9769881138/job/26970153616) | ❌ | ❌ |
| [1.3.20.2024.0704.alpha](https://github.com/Hny0305Lin/NLChat/tree/v1.3.20.2024.0704.alpha) | ✅ | 💦紧急修复多项Bug | ❌ | ✅ | ✅服务板OK | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9791846933/job/27036455298) | ❌ | ❌ |
| [1.3.26.2024.0704.alpha](https://github.com/Hny0305Lin/NLChat/tree/v1.3.26.2024.0704.alpha) | ✅ | 💦 | ❌ | ✅ | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9793018298/job/27040055090) | ❌ | ❌ |
| [1.3.48.2024.0705](https://github.com/Hny0305Lin/NLChat/tree/v1.3.48.2024.0705) | ✅ | ✅新UI重新发布 | ❌ | ✅ | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9801494346/job/27064881121) | ❌ | ❌ |
| [1.3.52.2024.0705](https://github.com/Hny0305Lin/NLChat/tree/v1.3.52.2024.0705) | ✅ | ✅微信气泡 | ❌ | ✅ | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9801743357/job/27065470639) | ❌ | ❌ |
| [1.3.56.2024.0706](https://github.com/Hny0305Lin/NLChat/tree/v1.3.56.2024.0706) | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9811064858/job/27092549245) | ❌ | ❌ |
| [1.3.60.2024.0706](https://github.com/Hny0305Lin/NLChat/tree/v1.3.60.2024.0706) | ✅ | ✅字体可定制 | ❌ | ✅ | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9812032215/job/27095312408) | ❌ | ❌ |
| [1.3.80.2024.0706](https://github.com/Hny0305Lin/NLChat/tree/v1.3.80.2024.0706) ✅1.3稳定版 | ✅ | ✅可设置新旧显示 | ❌ | ✅ | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9815410524/job/27104388068) | ❌ | ❌ |
| [1.3.100.2024.0706](https://github.com/Hny0305Lin/NLChat/tree/v1.3.100.2024.0707) | ✅ | ✅时间戳在气泡外 | ❌ | ✅ | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9820719366/job/27115612290) | ❌ | ❌ |
| [1.3.120.2024.0708](https://github.com/Hny0305Lin/NLChat/tree/v1.3.120.2024.0708) | ✅可设置高波特率 | ✅ | ❌ | ✅ | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9840106445/job/27164298901) | ❌ | ❌ |
| [1.3.139.2024.0708](https://github.com/Hny0305Lin/NLChat/tree/v1.3.139.2024.0708) | ✅ | ✅新消息置底 | ❌ | ✅ | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9843021843/job/27173409751) | ❌ | ❌ |
| [1.3.169.2024.0709](https://github.com/Hny0305Lin/NLChat/tree/v1.3.169.2024.0709) ✅1.3稳定版 | ✅ | ✅串口日志 | ❌ | ✅数据库可存储串口 | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9856399203/job/27213390083) | ❌ | ❌ |
| [1.3.179.2024.0709](https://github.com/Hny0305Lin/NLChat/tree/v1.3.179.2024.0709) | ✅ | ✅串口可设置 | ❌ | ✅ | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9859642134/job/27223991927) | ❌ | ❌ |
| [1.3.190.2024.0710](https://github.com/Hny0305Lin/NLChat/tree/v1.3.190.2024.0710) | ✅ | ✅ | 💦支持中文 | ✅ | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9868688684/job/27251146124) | ❌ | ❌ |
| [1.3.196.2024.0710](https://github.com/Hny0305Lin/NLChat/tree/v1.3.196.2024.0710) | ✅ | ✅串口内容UI交互 | 💦 | ✅ | ✅ | ✅ | ❌ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9878242396/job/27281839139) | ❌ | ❌ |
| [1.3.210.2024.0713](https://github.com/Hny0305Lin/NLChat/tree/v1.3.210.2024.0713) | ✅调整缓冲 | ✅ | 💦修复Bug | ✅ | ✅ | ✅ | 💦(BETA) | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9910986068/job/27382718568) | ❌ | ❌ |
| [1.3.236.2024.0713](https://github.com/Hny0305Lin/NLChat/tree/v1.3.236.2024.0713) | ✅重构线程 | ✅ | ✅再次修复 | ✅ | ✅ | ✅ | 💦 | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9920648366/job/27407768898) | ❌ | ❌ |
| [1.3.249.2024.0713](https://github.com/Hny0305Lin/NLChat/tree/v1.3.249.2024.0713) | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅优化 | ❌ | ❌ | ❌ | ❌ |
| [1.3.252.2024.0714.R](https://github.com/Hny0305Lin/NLChat/tree/v1.3.252.2024.0714.R) | ✅ | ✅ | ✅ | ✅ | ✅客户板OK | ✅ | ✅ | ❌ | [✅点我查看](https://github.com/Hny0305Lin/NLChat/actions/runs/9925572354/job/27418178798) | ❌ | ❌ |

