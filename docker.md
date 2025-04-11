## 📦 Dockerized Spring Boot App – Dev vs Prod Builds

This project supports both **development** and **production** Docker builds for a Spring Boot microservice-based architecture.

---

### 🚀 Dev Build

In the development setup:

- Code changes are **hot-reloaded** using `spring-boot-devtools`.
- Maven is available inside the container and used via `mvn spring-boot:run`.
- Your local code is **mounted** inside the container.
- Great for fast iteration, but not optimized for deployment.

---

### 🏗️ Prod Build

In the production setup:

- Uses a **multi-stage Docker build** to package the app into a single JAR.
- No Maven or source code inside the container.
- Smaller image size and faster startup.
- Does **not** support live reload.

---

### 🔍 Comparison Table

| Feature                         | Dev Build 🧑‍💻                                   | Prod Build 🚀                                      |
|----------------------------------|-----------------------------------------------|---------------------------------------------------|
| **Hot Reload Support**           | ✅ Yes (via Spring DevTools)                   | ❌ No                                             |
| **Includes Maven & Source**      | ✅ Yes (mounted from local)                    | ❌ No (compiled into final JAR)                   |
| **Runs With JAR**                | ❌ No (runs with `mvn spring-boot:run`)        | ✅ Yes (runs with `java -jar`)                    |
| **Final Image Size**             | ❌ Huge                                        | ✅ Small                                          |
| **Live Reload on Code Change**   | ✅ Yes                                         | ❌ No                                             |
| **Mounted Volumes**              | ✅ Source & .m2                                | ❌ None                                           |
| **Use Case**                     | Local Development                               | Deployment/Production                             |
| **Build Time**                   | Faster (skips some steps)                       | Slower (due to full build)                        |
| **Dockerfile Location**          | `Dockerfile.dev`                                | `Dockerfile.prod`                                 |
| **Docker Compose File**          | `docker-compose.yml`                            | `docker-compose.prod.yml`                         |

---

### 📁 File Structure Overview

```bash
project-root/
│
├── discovery-service/
│   └── Dockerfile.prod
│   └── Dockerfile.dev
├── user-service/
│   └── Dockerfile.prod
│   └── Dockerfile.dev
├── docker-compose.yml             # Dev setup
├── docker-compose.prod.yml        # Prod setup
└── .env                           # env!
```
---
## 🐳 Running docker-compose.prod.yml
Use the following commands depending on your needs:

## 🔁 1. Run with No Cache
Forces Docker to ignore the build cache and rebuild all layers from scratch.
```
docker-compose -f docker-compose.prod.yml build --no-cache
docker-compose -f docker-compose.prod.yml up -d
```
✅ Use this when you want to ensure a completely clean build.

## 🚀 2. Run with Normal Build (Cache Enabled)
Builds using cached layers when possible, reducing build time.
```
docker-compose -f docker-compose.prod.yml build

```
🧠 Recommended for most use cases to save time unless you’ve made major dependency changes.

## 🏃 3. Just Run (Without Building)
```
docker-compose -f docker-compose.prod.yml up
```
⚠️ Only use this if the images have already been built earlier.

## 🧳 Optional: Run in Detached Mode
Add -d to any of the above commands to run in the background:
```
docker-compose -f docker-compose.prod.yml up --build -d
```

## 🧹 Optional: Recreate Containers
Force the containers to be recreated even if they already exist:
```
docker-compose -f docker-compose.prod.yml up --build --force-recreate
```