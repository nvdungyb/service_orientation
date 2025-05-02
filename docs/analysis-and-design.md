# 📊 Microservices System - Analysis and Design

This document outlines the **analysis** and **design** process for your microservices-based system assignment. Use it to explain your thinking and architecture decisions.

---

## 1. 🎯 Problem Statement

_Diễn đàn trực tuyến nhằm chia sẻ, trao đổi, và kết nối với mọi người._

- Người dùng hệ thống: end users, administrators, guest
- Mục tiêu: mọi người có thể tương tác với nhau trên nền tảng này
- Dữ liệu xử lý: thông tin người dùng, bài viết, bình luận, cảm xúc, log hệ thống (maybe) 

[//]: # (> Example: A course management system that allows students to register for courses and teachers to manage class rosters.)

---

## 2. 🧩 Identified Microservices

List the microservices in your system and their responsibilities.

| Service Name | Responsibility                                            | Tech Stack |
|--------------|-----------------------------------------------------------|------------|
| Auth service | Handles user authentication and authorization             |            |
| User service | Quản lý người dùng                                        |            |
| Post service | Quản lý bài viết                                          |            |
|Comment service| Quản lý bình luận                                         |            |
|Reaction service| Quản lý cảm xúc của người dùng trên bài viết và bình luận |            |
| Notification service| Quản lý và gửi thông báo                                  |            | 

---

## 3. 🔄 Service Communication

Describe how your services communicate (e.g., REST APIs, message queue, gRPC).

- Gateway ⇄ service-a (REST)
- Gateway ⇄ service-b (REST)
- Internal: service-a ⇄ service-b (optional)

---

## 4. 🗂️ Data Design

Describe how data is structured and stored in each service.

- service-a: User accounts, credentials
- service-b: Course catalog, registrations

Use diagrams if possible (DB schema, ERD, etc.)

---

## 5. 🔐 Security Considerations

- Use JWT for user sessions
- Validate input on each service
- Role-based access control for APIs

---


## 6. 📦 Deployment Plan

- Use `docker-compose` to manage local environment
- Each service has its own Dockerfile
- Environment config stored in `.env` file

---

## 7. 🎨 Architecture Diagram

> *(You can add an image or ASCII diagram below)*

```
+---------+        +--------------+
| Gateway | <----> | Service A    |
|         | <----> | Auth Service |
+---------+        +--------------+
       |                ^
       v                |
+--------------+   +------------------+
| Service B    |   | Database / Redis |
| Course Mgmt  |   +------------------+
+--------------+
```

---

## ✅ Summary

Summarize why this architecture is suitable for your use case, how it scales, and how it supports independent development and deployment.



## Author

This template was created by Hung Dang.
- Email: hungdn@ptit.edu.vn
- GitHub: hungdn1701


Good luck! 💪🚀
