<center><h1>Mirai to MCSM</h1></center>

每次管理服务器时都要登录网站,觉得麻烦?

想要在QQ群里遇到问题快速解决?

Mirai to MCSM它来了!

一个使你可以从QQ控制MCSM面板的插件

## 相关链接
> [Mirai Forum 介绍帖]

> [NLR Dev](www.nlrdev.top)
## 使用方法
### 部署
- 部署[Mirai Console Loader](https://github.com/iTXTech/mirai-console-loader);
- 安装前置插件 [Chat Command](https://github.com/project-mirai/chat-command);
- 在 Releases 中下载最新的插件, 放入 MCL 根目录下的 `plugins` 文件夹中
- 参阅[权限说明文档](https://docs.mirai.mamoe.net/console/Permissions.html), 赋予相对应的权限
- 配置APIKey
- - 登录MCSM面板
- - 点击右上角账户 进入个人资料
- - 点击API接口密钥处的生成密钥并复制
- - 填入插件配置中的相应位置
- 开始使用吧!
### 命令

|                                                      指令                                                       |                 描述                 |                  权限节点                   |
|:-------------------------------------------------------------------------------------------------------------:|:----------------------------------:|:---------------------------------------:|
|                                          `/queryStatus` <br>`/面板状态`                                           |               查看面板状态               |   `top.nlrdev.mirai2mcsm.queryStatus`   |
|                                   `/listRemotes`<br>`/listre`<br>`/守护进程列表`                                    |              列出守护进程列表              |   `top.nlrdev.mirai2mcsm.listRemotes`   |
|                 `/listInstances <uuid> [page]`<br> 缩写：`/listinst`<br>`/实例列表 <守护进程uuid> [页数]`                  |               列出实例列表               |  `top.nlrdev.mirai2mcsm.listInstances`  |
| `/operateInstance <operation> <remote_uuid> <uuid>` <br> 缩写：`/opinst` <br> `/实例操作 <操作类型> <守护进程UUID> <实例UUID>` | 操作一个实例，操作类型：open stop restart kill | `top.nlrdev.mirai2mcsm.operateInstance` |


## 配置文件
### MCSMConfig.yml

### PluginConfig.yml


## 更多
您可加入我们的 [QQ社群](https://join.nlrdev.top/) 了解更多
