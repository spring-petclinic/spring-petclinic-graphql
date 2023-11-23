FROM nginx:stable-alpine
WORKDIR frontend
COPY dist /frontend
COPY docker/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 3090
CMD ["nginx", "-g", "daemon off;"]