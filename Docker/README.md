# build pyconjp-android on docker

1. Edit netrc

```
machine github.com
login <YOUR_ACCOUNT>
password <YOUR_PASSWORD>
```

1. Edit app-build.sh

```
cd ~ && 
git clone https://github.com/pyconjp/pyconjp-android.git && 
cd pyconjp-android && 
git checkout develop &&
GOOGLE_MAP_APIKEY_DEBUG='<YOUR_GOOGLE_MAP_APIKEY>' FABRIC_APIKEY='<YOUR_FABRIC_APIKEY>' ./gradlew clean assembleDebug
```

1. `make apk clean`
