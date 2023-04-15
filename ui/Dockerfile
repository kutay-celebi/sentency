FROM nginx:stable-alpine as webserver
COPY /data/nginx.conf /etc/nginx/conf.d/default.conf
COPY /dist /usr/share/nginx/html
EXPOSE 80
VOLUME /etc/nginx/conf.d/
CMD ["nginx", "-g", "daemon off;"]
