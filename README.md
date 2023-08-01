<center><h1>Mirai to MCSM</h1></center>

每次管理服务器时都要登录网站，觉得有十分甚至九分的麻烦?

想要在QQ群里遇到问题快速解决？

Mirai to MCSM它来了！

一个使你可以从QQ控制MCSM面板的插件


## 相关链接

- [Mirai Forum 介绍帖](https://mirai.mamoe.net/topic/2400)

- [NLR DevTeam 官网](https://www.nlrdev.top)


## 使用方法

### 部署

- 部署 [Mirai Console Loader](https://github.com/iTXTech/mirai-console-loader)
- 安装前置插件 [Chat Command](https://github.com/project-mirai/chat-command)
- 在 Releases 中下载最新的插件, 放入 MCL 根目录下的 `plugins` 文件夹中
- 为插件配置 APIKey 密钥
  - 登录您的 MCSManager 面板
  - 点击右上角账户，进入「个人资料」页面
  - 找到「API 接口密钥」，点击「生成密钥」并复制
  - 填入 `/config/top.nlrdev.mirai2mcsm/MCSMConfig.yml` 中的相应位置
- 参阅 [权限说明文档](https://docs.mirai.mamoe.net/console/Permissions.html)，赋予相对应的权限
- 开始使用吧!

### 命令
- 当只有一个守护进程时，`<remote_uuid>` 项可以被省略
- 命令缩写用法与命令相同
- 使用名称代替 UUID 的功能正在开发中
- 以下为现有命令列表和使用方法：
  
  |指令|描述|权限节点|
  |:--|:--|:--|
  |- `/queryStatus`<br/>- `/面板状态`|**查看面板状态**|`top.nlrdev.mirai2mcsm:command.querystatus`|
  |- `/listRemotes`<br/>- `/listre`<br/>- `/守护进程列表`|**列出守护进程列表**|`top.nlrdev.mirai2mcsm:command.listremotes`|
  |- `/listInstances <uuid> [page]`<br/>- `/listInstances <uuid> [page]`<br/>- `/实例列表 <uuid> [page]`|**列出实例列表**|`top.nlrdev.mirai2mcsm:command.listinstances`|
  |- `/operateInstance <operation> <remote_uuid> <uuid>`<br/>- `/opinst <operation> <remote_uuid> <uuid>`<br/>- `/实例操作 <operation> <remote_uuid> <uuid>`|**操作一个实例**<br/>操作类型：`open` `stop` `restart`  `kill`|`top.nlrdev.mirai2mcsm:command.operateinstance`|


## 配置文件

### `MCSMConfig.yml`

- **`APIKey`**
  - **描述**：MCSM面板的APIKey，必填，连接至MCSM的必须密钥
  - **默认值**：`空`
- **`APIUrl`**
  - **描述**：MCSM面板的链接，形如`http://example.com:23333`，默认为本机面板
  - **默认值**：`http://127.0.0.1:23333`

### `PluginConfig.yml`

- **`UseForwardMessage`**
  - **描述**：是否在群聊中使用消息合并转发以避免消息过长
  - **默认值**：`true`
- **`IsDebugMode`**
  - **描述**：是否开启调试模式，在调试模式下插件会有更详细的输出以诊断错误
  - **默认值**：`false`


## 了解更多

- 此插件正在开发，未来会加入更多的功能，可以提出 Issue 以催更（不是）
- 您可加入我们的 [QQ社群](https://join.nlrdev.top/) 了解更多
