date=$(shell date +%s)
apk: Dockerfile app-build.sh
	docker build -t app-build:0.1 .
	docker run --name="app-$(date)" app-build:0.1 /bin/sh /root/app-build.sh $(BRANCH) $(VARIANTS)
	docker cp app-$(date):/root/pyconjp-android/app/build/outputs/apk .
clean:
	docker ps -a | awk '{print $NF}' | grep "app-" | xargs docker rm
