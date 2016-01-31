FROM maven:3.2-jdk-7-onbuild

#ENV RABBIT_URL=url
#ENV RABBIT_USERNAME=admin
#ENV RABBIT_PASSWORD=password

RUN ["mvn", "compile"]

ENTRYPOINT ["mvn"]
CMD ["exec:java", "-Dexec.mainClass=co.johnrowley.distributed.prime.PrimeWorker"]