# Bmob使用
前提：初始化BmobSDK，推送服务，以及其他要用的服务，具体初始化看代码

- Bmob后端增删改查（例子中是增查）
- Bmob消息推送(2种)
    - App端通过 `BmobPushManager`推送消息
    - 服务端手动推送消息![BmobPush](http://ww1.sinaimg.cn/mw1024/c05ae6b6gw1f5vvqq5sk8j20qs0gg75y.jpg)
    - 自定义`MyPushMessageReceiver`接收消息
