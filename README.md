# 🚀 API Automation Tests (JUnit 5 + Rest Assured)

Автоматизированные **API-тесты** для сервиса  
👉 `https://testslotegrator.com`

Проект реализован на **Java 15**, **JUnit 5** и **Rest Assured**  
и покрывает полный **end-to-end (e2e) flow** работы с пользователями.

---

## 🧱 Технологический стек

- ☕ **Java 15**
- 🛠 **Gradle**
- 🧪 **JUnit 5**
- 🌐 **Rest Assured**
- 📄 **Jackson (JSON)**
- 🪵 **SLF4J + Logback** (логирование)

---

## 📁 Структура проекта

```text
src/test
 ├─ java
 │   ├─ config
 │   │   ├─ ApiConfig.java
 │   │   ├─ RequestSpecFactory.java
 │   │   ├─ ResponseSpecFactory.java
 │   │   ├─ TokenStorage.java
 │   │   └─ ApiErrorLoggingFilter.java
 │   │
 │   ├─ tests
 │   │   ├─ E2EFlowTest.java
 │   │   └─ CRUD tests
 │   │
 │   └─ utils
 │       ├─ JsonUtils.java
 │       ├─ TestDataGenerator.java
 │       └─ UserIdCollector.java
 │
 └─ resources
     ├─ data
     │   └─ test-data.json
     └─ token
         └─ access-token.json

🔄 End-to-End Flow
В проекте реализован полный e2e-сценарий:
🔑 Login — получение access token
➕ Create — создание 12 пользователей в цикле
🔍 Get One — получение пользователя по email
📋 Get All — получение списка всех пользователей
🗑 Delete One — удаление всех пользователей по id
✅ Get All — проверка, что список пуст
