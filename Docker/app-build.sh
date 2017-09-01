set -ex
cd ~ && 
git clone https://github.com/pyconjp/pyconjp-android.git && 
cd pyconjp-android && 
git checkout $1 &&
./gradlew clean $2
