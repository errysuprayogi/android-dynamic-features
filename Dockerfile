FROM alpine
COPY build.sh /
CMD ["/build.sh"]