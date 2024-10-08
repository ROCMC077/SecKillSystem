# 電商平台秒殺搶購系統

這個專案針對電商平台的秒殺搶購場景進行開發，涵蓋會員登入、瀏覽商品、秒殺搶購及訂單創建等流程。專案核心在於處理秒殺功能中的高併發情境，透過應用 Redis、RabbitMQ 等技術，提升系統回應速度並強化併發處理能力，確保能有效應對大量使用者的高併發需求。並透過 JMeter 工具進行壓力測試。

學習網址 https://www.bilibili.com/video/BV1Cp4y1b7Je?p=1&vd_source=d9506888b4f217083ac65b66611e7abb
# 系統環境
* Ubuntu 20.04

# 使用工具
* Eclipse
* MySQL Workbench 8.0 CE
* RedisDesktopManager
* GitHub
* Apache JMeter


# 使用技術
<table border="1">
  <tr>
    <th>技術</th>
    <th>版本</th>
    <th>技術</th>
    <th>版本</th>
  </tr>
  <tr>
    <td>OpenJDK</td>
    <td>17</td>
    <td>Redis</td>
    <td>2.7.9</td>
  </tr>
  <tr>
    <td>Spring Boot</td>
    <td>2.7.9</td>
    <td>MyBatis-Plus</td>
    <td>3.5.5</td>
  </tr>
  <tr>
    <td>MariaDB</td>
    <td>10.3.39</td>
    <td>Thymeleaf</td>
    <td>2.7.9</td>
  </tr>
  <tr>
    <td>RabbitMQ</td>
    <td>3.8.2</td>
    <td>MD5</td>
    <td>2.7.9</td>
  </tr>
</table>

# 系統架構
![系統架構圖](https://drive.google.com/uc?export=view&id=1nUDadf7ckw-Z6Ab39gmO3RofzKJCytwN)

