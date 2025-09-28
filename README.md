# API Foundation - 后端API底座项目

## 项目简介

这是一个基于Spring Boot的后端API底座项目，提供了完整的基础功能和最佳实践，可以作为其他项目的起始模板。

## 技术栈

- **框架**: Spring Boot 2.7.18
- **数据库**: MySQL 8.0 + Druid连接池
- **ORM**: MyBatis Plus 3.5.5
- **文档**: Swagger 3.0.0
- **工具**: Lombok, Hutool, FastJSON
- **构建**: Maven 3.6+
- **JDK**: 1.8+

## 项目特性

### 🚀 核心功能
- ✅ 统一响应结果封装
- ✅ 全局异常处理
- ✅ 分页查询支持
- ✅ 通用CRUD操作
- ✅ 自动填充字段（创建时间、更新时间等）
- ✅ 逻辑删除支持
- ✅ 跨域配置
- ✅ API文档自动生成

### 🛠️ 工具类
- ✅ 日期时间工具类
- ✅ 字符串工具类
- ✅ 通用实体基类

### 📊 示例模块
- ✅ 用户管理（增删改查、状态管理、统计）
- ✅ 健康检查接口
- ✅ 系统信息接口

## 快速开始

### 1. 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+

### 2. 数据库配置
```sql
-- 创建数据库
CREATE DATABASE api_foundation DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

执行初始化脚本：
```bash
mysql -u root -p api_foundation < src/main/resources/sql/init.sql
```

### 3. 配置文件
修改 `src/main/resources/application-dev.yml` 中的数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/api_foundation?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 4. 启动项目
```bash
mvn clean install
mvn spring-boot:run
```

### 5. 访问地址
- 应用地址: http://localhost:8080/api
- Swagger文档: http://localhost:8080/api/swagger-ui/
- Druid监控: http://localhost:8080/api/druid/

## 项目结构

```
src/
├── main/
│   ├── java/com/backend/
│   │   ├── common/                 # 通用模块
│   │   │   ├── entity/            # 通用实体
│   │   │   ├── exception/         # 异常处理
│   │   │   ├── result/            # 响应结果封装
│   │   │   └── utils/             # 工具类
│   │   ├── config/                # 配置类
│   │   ├── controller/            # 控制器
│   │   ├── entity/                # 实体类
│   │   ├── mapper/                # 数据访问层
│   │   ├── service/               # 业务逻辑层
│   │   └── ApiFoundationApplication.java  # 启动类
│   └── resources/
│       ├── sql/                   # SQL脚本
│       ├── application.yml        # 主配置文件
│       ├── application-dev.yml    # 开发环境配置
│       └── application-prod.yml   # 生产环境配置
└── test/                          # 测试代码
```

## API接口

### 健康检查
- `GET /health` - 健康检查
- `GET /health/info` - 系统信息
- `GET /health/ping` - Ping接口

### 用户管理
- `GET /user/page` - 分页查询用户
- `GET /user/{id}` - 根据ID查询用户
- `GET /user/username/{username}` - 根据用户名查询用户
- `POST /user` - 创建用户
- `PUT /user` - 更新用户
- `DELETE /user/{id}` - 删除用户
- `DELETE /user/batch` - 批量删除用户
- `PUT /user/{id}/status` - 更新用户状态
- `PUT /user/{id}/password` - 重置用户密码
- `GET /user/exists/username/{username}` - 检查用户名是否存在
- `GET /user/exists/email/{email}` - 检查邮箱是否存在
- `GET /user/exists/phone/{phone}` - 检查手机号是否存在
- `GET /user/statistics` - 用户统计信息

## 开发指南

### 1. 添加新的实体类
继承 `BaseEntity` 类：
```java
@Entity
@Table(name = "your_table")
public class YourEntity extends BaseEntity {
    // 你的字段
}
```

### 2. 创建Mapper接口
继承 `BaseMapper<T>` 接口：
```java
@Mapper
public interface YourMapper extends BaseMapper<YourEntity> {
    // 自定义查询方法
}
```

### 3. 创建Service接口和实现
```java
public interface YourService extends IService<YourEntity> {
    // 自定义业务方法
}

@Service
public class YourServiceImpl extends ServiceImpl<YourMapper, YourEntity> implements YourService {
    // 实现业务逻辑
}
```

### 4. 创建Controller
```java
@RestController
@RequestMapping("/your-path")
@Api(tags = "你的模块")
public class YourController {
    // 实现API接口
}
```

## 配置说明

### 数据库配置
- 支持MySQL数据库
- 使用Druid连接池
- 自动配置MyBatis Plus

### 日志配置
- 开发环境：控制台输出
- 生产环境：文件输出
- 支持按日期滚动

### Swagger配置
- 自动扫描Controller
- 生成API文档
- 支持在线测试

## 部署说明

### 开发环境
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

### 生产环境
```bash
java -jar target/api-foundation-1.0.0.jar --spring.profiles.active=prod
```

## 扩展功能

项目已预留扩展接口，可以根据需要添加：
- 权限管理（角色、权限）
- 操作日志
- 系统配置
- 文件上传
- 消息通知
- 定时任务

## 贡献指南

1. Fork 项目
2. 创建特性分支
3. 提交更改
4. 推送到分支
5. 创建 Pull Request

## 许可证

MIT License

## 联系方式

如有问题，请提交 Issue 或联系项目维护者。
backend code
