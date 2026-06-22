# 在线票务系统(Ticket System)

## 项目简介

一个基于 Java Web 的 MVC 架构在线票务系统，采用 Servlet + JSP + JDBC 技术栈，实现用户注册登录、票品浏览搜索、购物车管理、订单处理及后台管理等核心功能。

## 功能模块

### 用户端

| 功能 | 说明 |
|------|------|
| 用户注册 | 用户名、密码、邮箱、手机号注册，表单校验 |
| 用户登录 | 用户名 + 密码登录，Session 会话管理，30 分钟超时自动登出 |
| 个人中心 | 查看 / 修改个人信息 + 历史购买订单详情 |
| 票品浏览 | 首页按类型分组展示（演唱会 / 体育 / 电影 / 演出），卡片式展示 |
| 票品搜索 | 按关键词模糊搜索票品名称 |
| 票品详情 | 查看详细信息、价格、库存，支持加入购物车 |
| 购物车 | 添加 / 更新数量 / 删除票品，自动计算小计与总价 |
| 订单结算 | 确认订单商品与金额，提交订单并扣减库存 |
| 订单历史 | 查看 / 支付 / 取消订单 |

### 管理端

| 功能 | 说明 |
|------|------|
| 仪表盘 | 在售票品数、总订单数、今日订单、总销售额统计 |
| 票品管理 | 增删改查，支持图片上传、状态切换（在售 / 售罄 / 下架），票品名称去重校验 |
| 订单管理 | 查看所有订单及详情，标记已支付 / 取消 |

## 技术架构

| 层级 | 技术 |
|------|------|
| 前端 | JSP + JSTL + CSS3 + 响应式布局 |
| 后端 | Java Servlet 4.0 + 反射路由派发 |
| 数据库 | MySQL 8.0 + 原生 JDBC |
| 架构模式 | MVC（Model - View - Controller） |
| 会话管理 | HttpSession（购物车 / 登录态） |
| 过滤器 | 编码过滤器 + 登录验证过滤器 |

## 项目目录结构

```
TicketSystem/
├── init.sql                          数据库初始化脚本（建表 + 测试数据）
├── README.md                         项目说明文档
│
├── src/
│   ├── db.properties                 数据库连接配置
│   └── com/
│       ├── controller/               控制层（Servlet）
│       │   ├── BaseServlet.java      基类（路径反射派发）
│       │   ├── HomeController.java   首页控制器
│       │   ├── UserController.java   用户登录 / 注册 / 个人中心
│       │   ├── TicketController.java 票务列表 / 详情 / 搜索
│       │   ├── OrderController.java  购物车 / 结算 / 订单
│       │   └── AdminController.java  管理后台（仪表盘 / 票品管理 / 订单管理）
│       │
│       ├── model/
│       │   ├── entity/               实体层
│       │   │   ├── User.java         用户实体
│       │   │   ├── Ticket.java       票品实体
│       │   │   ├── Order.java        订单实体
│       │   │   └── OrderItem.java    订单项实体
│       │   ├── dao/                  数据访问层（JDBC）
│       │   │   ├── UserDao.java
│       │   │   ├── TicketDao.java
│       │   │   ├── OrderDao.java
│       │   │   └── OrderItemDao.java
│       │   └── service/              业务逻辑层
│       │       ├── UserService.java
│       │       ├── TicketService.java
│       │       ├── OrderService.java
│       │       └── impl/             接口实现
│       │           ├── UserServiceImpl.java
│       │           ├── TicketServiceImpl.java
│       │           └── OrderServiceImpl.java
│       │
│       ├── filter/                   过滤器
│       │   ├── EncodingFilter.java   UTF-8 编码过滤器
│       │   └── LoginFilter.java      登录验证过滤器
│       │
│       └── util/                     工具类
│           ├── DBUtil.java           数据库连接工具
│           ├── CartManager.java      购物车管理（Session 存储）
│           └── WebUtils.java         请求参数工具
│
└── web/
    ├── index.html                    入口重定向页
    ├── WEB-INF/
    │   └── web.xml                   部署描述符（Servlet 映射 / 过滤器 / 错误页）
    ├── static/
    │   ├── css/
    │   │   ├── style.css             主样式表（响应式）
    │   │   └── admin.css             管理后台样式
    │   └── images/                   票品图片资源
    └── views/
        ├── index.jsp                 首页（票品列表 + 分类筛选 + 搜索）
        ├── common/
        │   ├── header.jsp            顶部导航栏（登录状态 / 管理入口）
        │   ├── footer.jsp            页脚
        │   ├── sidebar.jsp           管理后台侧边栏
        │   └── styles.jsp            内联关键样式（防外部 CSS 未加载）
        ├── user/
        │   ├── login.jsp             登录页
        │   ├── register.jsp          注册页
        │   └── profile.jsp           个人中心
        ├── ticket/
        │   ├── list.jsp              票品列表页
        │   └── detail.jsp            票品详情页
        ├── order/
        │   ├── cart.jsp              购物车页
        │   ├── checkout.jsp          订单结算页
        │   └── history.jsp           订单历史页
        ├── admin/
        │   ├── dashboard.jsp         仪表盘（数据统计）
        │   ├── ticket-manage.jsp     票品管理（增删改查 + 模态框表单）
        │   └── order-manage.jsp      订单管理
        └── error/
            ├── 404.jsp               404 错误页
            └── 500.jsp               500 错误页
```

## 部署步骤

### 环境要求

| 软件 | 版本 |
|------|------|
| JDK | 1.8 或更高 |
| Tomcat | 9.0 或更高 |
| MySQL | 8.0 或更高 |
| IDE | IntelliJ IDEA（推荐） |

### 1. 数据库初始化

使用 Navicat 或 MySQL 命令行执行项目根目录下的 `init.sql`：

```sql
source /path/to/init.sql
```

该脚本会自动：
- 创建数据库 `ticketsystem`
- 创建 `user`、`ticket`、`order`、`order_item` 四张表
- 插入默认管理员账号（admin / admin123）
- 插入 8 条测试票品数据（演唱会、体育、电影、演出各两条）

### 2. 配置数据库连接

编辑 `src/db.properties`，修改 MySQL 用户名和密码：

```properties
driverClassName=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/ticketsystem?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=UTF-8&allowPublicKeyRetrieval=true
username=root                    # 改为你的 MySQL 用户名
password=your_password           # 改为你的 MySQL 密码
initialSize=5
maxActive=20
maxWait=3000
```

### 3. 导入 JAR 包

将以下 JAR 包复制到 `web/WEB-INF/lib/` 目录：

| JAR 包 | 用途 |
|--------|------|
| `javax.servlet-api-4.0.1.jar` | Servlet API |
| `jstl-1.2.jar` | JSTL 标签库 |
| `mysql-connector-java-8.0.19.jar` | MySQL JDBC 驱动 |
| `standard-1.1.2.jar` | JSTL 标准实现 |

### 4. IDEA 配置与启动

1. **File → Open** 打开项目根目录
2. **File → Project Structure → Modules**：确认 `src` 为 Sources，`web` 为 Web Resource Directory
3. **Run → Edit Configurations → + → Tomcat Server → Local**
4. **Deployment → + → Artifact**：选择 `TicketSystem:Web exploded`
5. **Application context** 填 `/`
6. 点击 **Run** 启动 Tomcat
7. 浏览器访问 `http://localhost:8080`

## 管理员账号

| 角色 | 用户名 | 密码 | 说明 |
|------|--------|------|------|
| 管理员 | `admin` | `admin123` | 由 init.sql 自动创建 |
| 普通用户 | 自行注册 | 自行设置 | 通过注册页面创建 |

## 主要页面路径

| 页面 | URL | 说明 |
|------|-----|------|
| 首页 | `/home/index` | 票品列表 + 分类筛选 + 搜索 |
| 用户登录 | `/views/user/login.jsp` | 登录页（公开） |
| 用户注册 | `/views/user/register.jsp` | 注册页（公开） |
| 全部票品 | `/ticket/list` | 按类型浏览 |
| 票品详情 | `/ticket/detail?id=1` | 查看详情 + 加入购物车 |
| 购物车 | `/order/cart` | 查看购物车 + 修改数量 |
| 结算 | `/order/checkout` | 确认订单 + 提交 |
| 订单历史 | `/order/history` | 查看 / 支付 / 取消订单 |
| 个人中心 | `/user/profile` | 查看 / 修改个人信息 + 订单历史 |
| 管理后台 | `/admin/dashboard` | 仪表盘（需管理员权限） |
| 票品管理 | `/admin/ticketManage` | 增删改查（需管理员权限） |
| 订单管理 | `/admin/orderManage` | 查看订单及详情 / 处理订单（需管理员权限） |

## 数据库表结构

### user（用户表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT, PK, AUTO_INCREMENT | 主键 |
| username | VARCHAR(50), UNIQUE | 用户名 |
| password | VARCHAR(100) | 密码 |
| email | VARCHAR(100) | 邮箱 |
| phone | VARCHAR(20) | 手机号 |
| role | VARCHAR(10), DEFAULT 'user' | 角色：user / admin |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### ticket（票品表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT, PK, AUTO_INCREMENT | 主键 |
| name | VARCHAR(200) | 票品名称 |
| type | VARCHAR(50) | 类型：concert / 体育 / 电影 / 演出 |
| description | TEXT | 详细描述 |
| price | DECIMAL(10,2) | 单价 |
| stock | INT, DEFAULT 0 | 库存数量 |
| image | VARCHAR(255) | 图片路径 |
| status | VARCHAR(10), DEFAULT 'on_sale' | 状态：on_sale / sold_out / off |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### order（订单表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT, PK, AUTO_INCREMENT | 主键 |
| order_no | VARCHAR(50), UNIQUE | 订单号 |
| user_id | INT, FK → user.id | 用户 ID |
| total_price | DECIMAL(12,2) | 总金额 |
| status | VARCHAR(20), DEFAULT 'pending' | 状态：pending / paid / cancelled |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### order_item（订单项表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT, PK, AUTO_INCREMENT | 主键 |
| order_id | INT, FK → order.id, CASCADE DELETE | 订单 ID |
| ticket_name | VARCHAR(200) | 票品名称 |
| quantity | INT, DEFAULT 1 | 购买数量 |
| price | DECIMAL(10,2) | 购买时的单价 |

## 核心设计说明

### Servlet 反射派发（BaseServlet）

`BaseServlet` 是所有 Controller 的基类，通过请求路径自动反射调用子类方法，无需为每个操作配置单独的 Servlet 映射。

**方法名确定规则（优先级从高到低）：**
1. 请求参数 `method`（如 `?method=login`）
2. 请求路径信息（如 `/user/register` 提取为 `register`）
3. 默认值 `index`

**示例：**
```
GET /user/login      → UserController.login()
POST /order/cart     → OrderController.cart()
GET /admin/dashboard → AdminController.dashboard()
```

### 购物车 Session 存储

购物车数据存储在 `HttpSession` 中，使用 `CartManager` 工具类管理。

**特点：**
- 用户无需登录即可浏览和添加购物车
- 提交订单时需要登录（LoginFilter 自动跳转登录页）
- 购物车数据在 Session 中以 `Map<Integer, CartItem>` 形式存储
- 支持添加、更新数量、删除、清空操作
- 自动计算小计与总价

### 事务处理

下单操作使用数据库事务确保数据一致性：

```
BEGIN TRANSACTION
  1. 插入订单记录（order 表）
  2. 插入订单项记录（order_item 表，含 ticket_name）
  3. 扣减库存（ticket 表 stock 字段）
COMMIT / ROLLBACK（任一失败则回滚）
```

### 登录验证过滤器（LoginFilter）

对所有请求进行拦截检查：

1. **公开路径直接放行**：登录页、注册页、静态资源、错误页
2. **检查 Session 登录态**：未登录则重定向到登录页
3. **保存原始请求路径**：登录后自动跳回原页面

### 编码过滤器（EncodingFilter）

对所有请求统一设置 UTF-8 编码，解决中文乱码问题。

## 前端设计

### 响应式布局

- 使用 CSS Grid 和 Flexbox 实现响应式布局
- 移动端（≤768px）自适应：侧边栏变为顶部导航，卡片网格缩减列数
- 小屏（≤480px）：单列卡片布局

### 页面组件

| 组件 | 说明 |
|------|------|
| 顶部导航栏 | Logo + 分类导航 + 用户信息，固定在页面顶部 |
| 侧边栏 | 管理后台菜单，固定在页面左侧 |
| 卡片网格 | 票品列表展示，支持悬停动画效果 |
| 模态框 | 管理后台新增 / 编辑票品表单 |
| 搜索框 | 圆角搜索栏，支持关键词搜索 |
| 分类标签 | 圆角标签切换票品分类 |

### 样式主题

- 主色调：`#1a73e8`（蓝色）
- 强调色：`#ffd54f`（金色）
- 背景色：`#f5f5f5`（浅灰）
- 渐变色：线性渐变用于导航栏、按钮、标签

## 常见问题

### Q: 启动后访问 404？

A: 检查以下几点：
1. `web.xml` 中 Servlet 映射是否正确
2. Tomcat 的 Application context 是否为 `/`
3. 项目是否正确部署到 Tomcat

### Q: 数据库连接失败？

A: 检查以下几点：
1. MySQL 服务是否启动
2. `db.properties` 中用户名密码是否正确
3. 数据库 `ticketsystem` 是否已创建（执行 init.sql）

### Q: 中文显示乱码？

A: 检查以下几点：
1. 数据库字符集是否为 `utf8mb4`
2. JSP 页面编码是否为 `UTF-8`
3. `EncodingFilter` 是否生效

### Q: 购物车数据丢失？

A: 购物车存储在 Session 中，以下情况会导致数据丢失：
1. Session 超时（默认 30 分钟）
2. 清除浏览器 Cookie
3. 重启 Tomcat 服务器

## 开发说明

### 代码规范

- 遵循 MVC 分层架构：Controller → Service → DAO → Entity
- Service 层定义接口，impl 包提供实现
- Controller 继承 BaseServlet，通过路径反射派发
- 工具类使用静态方法，便于复用

### 扩展建议

1. **密码加密**：当前密码明文存储，建议使用 BCrypt 加密
2. **分页查询**：票品列表和订单列表支持分页
3. **图片存储**：当前图片存储在本地，建议迁移到 OSS
4. **支付集成**：接入支付宝 / 微信支付
5. **日志系统**：集成 Log4j 记录操作日志

