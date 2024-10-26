# Add Another Metrics - gin metrics

## Config gin Metrics as a Middleware

1. edit main.go in store-service

   ```golang
   m := ginmetrics.GetMonitor()

   // +optional set metric path, default /debug/metrics
   m.SetMetricPath("/metrics")
   // +optional set slow time, default 5s
   m.SetSlowTime(10)
   // +optional set request duration, default {0.1, 0.3, 1.2, 5, 10}
   // used to p95, p99
   m.SetDuration([]float64{0.1, 0.3, 1.2, 5, 10, 50, 100, 500})

   // set middleware for gin
   m.Use(route)
   ```

2. Build store-service

   ```cmd
   docker-compose build
   ```

3. Start store-service

   ```cmd
   docker-compose up -d store-service
   ```

4. Try to send request

   ```url
   http://localhost:8000/api/v1/product/3
   ```

5. Check gin-metrics url

   ```url
   http://localhost:8000/metrics
   ```

6. Stop store-service

   ```cmd
   docker-compose down
   ```

## Store gin-metric in Prometheus

1. Add job_name for store-service

   ```yaml
   global:
      scrape_interval: 15s # By default, scrape targets every 15 seconds.

      # Attach these labels to any time series or alerts when communicating with
      # external systems (federation, remote storage, Alertmanager).
      external_labels:
         monitor: "app-monitor"

   # A scrape configuration containing exactly one endpoint to scrape:
   # Here it's Prometheus itself.
   scrape_configs:
   - job_name: "user-service"

      metrics_path: "/actuator/prometheus"

      scrape_interval: 5s

      static_configs:
         - targets: ["user-service:8080"]

   - job_name: "store-service"

      metrics_path: "/metrics"

      scrape_interval: 5s

      static_configs:
         - targets: ["store-service:8000"]

   ```

2. Start application with prometheus

   ```cmd
   docker-compose up -d
   ```

3. Stop docker compose

   ```cmd
   docker-compose down
   ```
