# Simple LGTM (Loki-Grafana-Tempo-Metrics)

## Build Java Docker

1. Build Docker Image

   ```cmd
   cd user
   docker build . -t user-service:1.0.0
   ```

2. Docker Run

   ```cmd
   docker run -p 8080:8080 user-service:1.0.0
   ```

3. Open URL for user api

   ```link
   http://localhost:8080/api/v1/users/1
   ```

4. Stop Application with `Ctrl+C`

## Add Java Agent for runtime

1. Copy agent into image via `user/Dockerfile`

   ```Dockerfile
   FROM eclipse-temurin:21-jre

   COPY agent/opentelemetry-javaagent.jar opentelemetry-javaagent.jar #<-- Add This Line

   COPY --from=build /home/app/target/*.jar app.jar

   ```

2. Change ENTRYPOINT to run Java with Agent

   ```Dockerfile

   ENTRYPOINT ["java", "-jar","app.jar"] # <- remove this

   ENTRYPOINT ["java","-javaagent:opentelemetry-javaagent.jar", "-jar","app.jar"] # <- add this

   ```

3. Re-Build Docker Image

   ```cmd
   docker build . -t user-service:1.0.0
   ```

4. Docker Run with Agent

   ```cmd
   docker run -p 8080:8080 user-service:1.0.0
   ```

5. See log

   ```log
    [otel.javaagent 2024-10-25 23:16:32:381 +0000] [OkHttp http://localhost:4318/...] ERROR io.opentelemetry.exporter.internal.http.HttpExporter - Failed to export logs. The request could not be executed. Full error message: Failed to connect to localhost/[0:0:0:0:0:0:0:1]:4318
    java.net.ConnectException: Failed to connect to localhost/[0:0:0:0:0:0:0:1]:4318
            at okhttp3.internal.connection.RealConnection.connectSocket(RealConnection.kt:297)
            at okhttp3.internal.connection.RealConnection.connect(RealConnection.kt:207)
            at okhttp3.internal.connection.ExchangeFinder.findConnection(ExchangeFinder.kt:226)
            at okhttp3.internal.connection.ExchangeFinder.findHealthyConnection(ExchangeFinder.kt:106)
            at okhttp3.internal.connection.ExchangeFinder.find(ExchangeFinder.kt:74)
            at okhttp3.internal.connection.RealCall.initExchange$okhttp(RealCall.kt:255)
   ```

6. Stop Application with `Ctrl+C`

## Start App and LGTM with docker-compose

1. exit from user folder

   ```cmd
   cd ..
   ```

2. Run Docker-Compose up

   ```cmd
   docker-compose up
   ```

3. Run Load Test in new Terminal

   ```cmd
   docker run --rm -i grafana/k6 run - <scripts/load.js
   ```

   ```log

            /\      Grafana   /‾‾/
      /\  /  \     |\  __   /  /
      /  \/    \    | |/ /  /   ‾‾\
   /          \   |   (  |  (‾)  |
   / __________ \  |_|\_\  \_____/

      execution: local
         script: -
         output: -

      scenarios: (100.00%) 1 scenario, 5 max VUs, 1m0s max duration (incl. graceful stop):
               * default: 5 looping VUs for 30s (gracefulStop: 30s)


   running (0m01.0s), 5/5 VUs, 0 complete and 0 interrupted iterations
   default   [   3% ] 5 VUs  01.0s/30s
      data_received..................: 38 kB  1.3 kB/s
      data_sent......................: 16 kB  519 B/s
      http_req_blocked...............: avg=884.67µs min=1.29µs med=10.95µs max=8.5ms    p(90)=3.51ms   p(95)=4.81ms
      http_req_connecting............: avg=789.15µs min=0s     med=0s      max=8.16ms   p(90)=3.29ms   p(95)=4.3ms
      http_req_duration..............: avg=7.87ms   min=2.17ms med=7.14ms  max=16.87ms  p(90)=11.67ms  p(95)=13.87ms
         { expected_response:true }...: avg=7.69ms   min=2.17ms med=7.12ms  max=16.87ms  p(90)=11.61ms  p(95)=13.37ms
      http_req_failed................: 22.66% 34 out of 150
      http_req_receiving.............: avg=165.24µs min=9.25µs med=95.91µs max=671.16µs p(90)=443.19µs p(95)=506.61µs
      http_req_sending...............: avg=54.23µs  min=2.79µs med=25.83µs max=745.79µs p(90)=116.38µs p(95)=142.68µs
      http_req_tls_handshaking.......: avg=0s       min=0s     med=0s      max=0s       p(90)=0s       p(95)=0s
      http_req_waiting...............: avg=7.65ms   min=2.14ms med=6.94ms  max=16.66ms  p(90)=11.45ms  p(95)=13.75ms
      http_reqs......................: 150    4.940216/s
      iteration_duration.............: avg=1.01s    min=1s     med=1.01s   max=1.02s    p(90)=1.01s    p(95)=1.01s
      iterations.....................: 150    4.940216/s
      vus............................: 5      min=5         max=5
      vus_max........................: 5      min=5         max=5


   running (0m30.4s), 0/5 VUs, 150 complete and 0 interrupted iterations
   default ✓ [ 100% ] 5 VUs  30s
   ```

4. Stop Application with `Ctrl+C`
