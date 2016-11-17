FROM ubuntu:16.04

RUN apt-get update -y && apt-get upgrade -y && \
    apt-get install -y openjdk-8-jre openjdk-8-jdk lib32stdc++6 lib32z1 wget git
RUN cd && wget https://dl.google.com/android/android-sdk_r24.4.1-linux.tgz && tar -zxvf android-sdk_r24.4.1-linux.tgz && \
    /root/android-sdk-linux/tools/android list sdk -a -e && \
    echo y | /root/android-sdk-linux/tools/android update sdk -a -u -t build-tools-24.0.2,android-24,extra-android-support,extra-android-m2repository,extra-google-m2repository,extra-google-google_play_services
ENV ANDROID_HOME /root/android-sdk-linux
ENV FABRIC_APIKEY_DEBUG
ENV GOOGLE_MAP_APIKEY_DEBUG
COPY app-build.sh /root/
COPY netrc /root/.netrc
CMD ["/bin/sh", "/root/app-build.sh"]
