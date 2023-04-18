FROM aemcloud/dispatcher:4.3.2
#COPY ./dispatcher.conf /etc/httpd/conf.d/
COPY ./httpd.conf /etc/httpd/conf/
ADD ./src/conf.d/htaccess /etc/httpd/conf.d/htaccess

#COPY ./dispatcher.any /etc/httpd/conf.d/