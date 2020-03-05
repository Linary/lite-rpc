### 一个最精简版的RPC框架

1. common: 通用的工具模块
2. proto: 协议模块，定义client与server交互的数据格式
3. transport: 网络传输模块，可用Netty优化
4. codec: 序列化模块，可用jackson、kryo等优化
5. server: rpc服务模块，注册服务，提供服务调用
6. client: rpc客户端模块，调用服务模块提供的服务

