date=$(shell date +%s)
apk: Dockerfile app-build.sh
	docker build -t app-build:0.1 .
	docker run --name="app-$(date)" app-build:0.1
	docker cp app-$(date):/root/pyconjp-android/app/build/outputs/apk/app-production-debug.apk .
clean:
	docker ps -a | grep "app" | awk '{print $$14}' | xargs docker rm
