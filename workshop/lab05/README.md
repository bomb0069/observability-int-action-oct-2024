# Add Another Metrics

## Run Prometheus

1. Create prometheus.yml config into prometheus folder

   ```yaml
   global:
   scrape_interval: 15s # By default, scrape targets every 15 seconds.

   # Attach these labels to any time series or alerts when communicating with
   # external systems (federation, remote storage, Alertmanager).
   external_labels:
       monitor: "spring-boot-monitor"

   # A scrape configuration containing exactly one endpoint to scrape:
   # Here it's Prometheus itself.
   scrape_configs:
   - job_name: "user-service"

       metrics_path: "/actuator/prometheus"

       scrape_interval: 5s

       static_configs:
       - targets: ["host.docker.internal:8080"]
   ```

2. Run Prometheus with Docker

   ```cmd
   docker run --name prometheus --rm --detach --volume $(pwd)/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml:ro --publish 9090:9090 prom/prometheus:latest --config.file=/etc/prometheus/prometheus.yml
   ```

3. Stop Prometheus

   ```cmd
   docker stop prometheus
   ```

## Run on Docker Compose

1. add service prometheus into docker compose file

   ```yaml
   prometheus:
     image: prom/prometheus:latest
     container_name: prometheus
     ports:
       - 9090:9090
     command:
       - --config.file=/etc/prometheus/prometheus.yml
     volumes:
       - ./prometheus/prometheus.yml:/etc/prometheus/prometheus.yml:ro
   ```

2. Update endpoint to user-service

   ```yaml
   global:
   scrape_interval: 15s # By default, scrape targets every 15 seconds.

   # Attach these labels to any time series or alerts when communicating with
   # external systems (federation, remote storage, Alertmanager).
   external_labels:
      monitor: "spring-boot-monitor"

   # A scrape configuration containing exactly one endpoint to scrape:
   # Here it's Prometheus itself.
   scrape_configs:
   - job_name: "user-service"

      metrics_path: "/actuator/prometheus"

      scrape_interval: 5s

      static_configs:
      - targets: ["user-service:8080"] # <- This Line
   ```

3. Start application with prometheus

   ```cmd
   docker-compose up -d
   ```

4. Stop docker compose

   ```cmd
   docker-compose down
   ```
