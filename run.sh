echo "Generating Security keys..."
chmod +x ./keygen.sh
./keygen.sh
echo "Keys generated"
echo "Building..."
chmod +x ./gradlew

./gradlew clean build -x test
./gradlew bootRun
echo "Running..."
