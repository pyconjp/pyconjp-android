cd ~ && 
git clone https://github.com/pyconjp/pyconjp-android.git && 
cd pyconjp-android && 
git checkout develop &&
GOOGLE_MAP_APIKEY_DEBUG='' FABRIC_APIKEY='' ./gradlew clean assembleDebug
