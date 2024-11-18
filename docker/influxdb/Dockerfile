FROM influxdb:1.8

ADD startup.sh startup.sh
RUN chmod +x /*.sh

ENTRYPOINT ["/startup.sh"]