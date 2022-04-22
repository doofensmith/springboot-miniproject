# import nginx
FROM nginx:1.21.6-alpine

# copy coverage file
COPY ./target/site/jacoco /usr/share/nginx/html