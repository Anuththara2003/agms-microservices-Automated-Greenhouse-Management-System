# Automated Greenhouse Management System (AGMS)

AGMS is a cloud-native, microservices-based platform designed for modern precision agriculture. The system automates climate control by fetching real-time telemetry from external IoT sensors and applying custom business rules to maintain ideal growing conditions.

## 🚀 System Architecture
The system is built using the Spring Cloud ecosystem and follows a distributed microservices architecture:

- **Infrastructure Services:** Service Discovery (Eureka), Centralized Configuration (Spring Cloud Config), and API Gateway.
- **Domain Microservices:** Auth Service, Zone Management Service, Sensor Telemetry Service, Automation Service, and Crop Inventory Service.

## 🛠️ Prerequisites
- **Java:** Version 21
- **Build Tool:** Maven
- **Database:** MySQL (Ensure MySQL is running and databases are created)
- **Centralized Config Repository:** (https://github.com/Anuththara2003/agms-microservices-Automated-Greenhouse-Management-System)]

## 🏁 Startup Instructions (Step-by-Step)
To ensure the system functions correctly, the services **must** be started in the following specific order:

### Step 1: Infrastructure Services (Start First)
1. **Service Registry (Eureka Server)**
   - **Folder:** `service-registry` | **Port:** `8761`
   - Wait for the dashboard at (http://localhost:8761).
2. **Config Server**
   - **Folder:** `config-server` | **Port:** `8888`
   - Fetches properties from the Git repository.
3. **API Gateway**
   - **Folder:** `api-gateway` | **Port:** `8080`
   - Handles routing and JWT security.

### Step 2: Domain Microservices (Start Second)
1. **Auth/Identity Service** (Port: `8085`) - User registration and JWT generation.
2. **Zone Management Service** (Port: `8081`) - Greenhouse zones and device registration.
3. **Automation Service** (Port: `8083`) - Rule engine that processes sensor data.
4. **Crop Inventory Service** (Port: `8084`) - Manages plant lifecycle stages.
5. **Sensor Telemetry Service** (Port: `8082`) - Acts as a data bridge for telemetry.

---

## ⚠️ Important Note on External IoT API
During the final testing phase, the **External IoT Data Provider API (104.211.95.241)** was found to be unreachable or expired. 

To ensure the system remains functional for evaluation, a **Fallback Mocking Mechanism** was implemented in the `Sensor-Telemetry-Service`. 
- The system attempts to connect to the real API.
- If the connection fails, it automatically switches to generating simulated telemetry data.
- This ensures the **End-to-End Workflow** (Sensor -> Automation -> Database Logging) can be fully demonstrated.

---

## 🔒 Security
- **Internal Security:** JWT (JSON Web Token) authorization is implemented at the Gateway level.
- **External Security:** The Sensor Service manages OAuth2/JWT authentication with the external IoT provider.

## 🧪 Testing with Postman
A complete Postman Collection is included in the project root:
- **File:** `AGMS_Postman_Collection.json`

**Steps:**
1. Import the collection into Postman.
2. Run **Auth Service -> Login** to obtain a Bearer Token.
3. Use this token for all protected routes (Zone, Automation Logs, Crop).

## 📊 Monitoring
All active microservices can be monitored via the Eureka Dashboard:
- **URL:** [http://localhost:8761](http://localhost:8761)
- Refer to `docs/eureka-dashboard.png` for a screenshot of the healthy system status.

---
**Developed by:** [Sandaru Perera]
**Batch:** GDSE [71]
