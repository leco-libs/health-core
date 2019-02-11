# SQL
export DATABASE_DRIVER="org.postgresql.Driver"
export DATABASE_URL="jdbc:postgresql://localhost:5432/health"
export DATABASE_USERNAME="sa"
export DATABASE_PASSWORD="sa"
# NOSQL
export NOSQL_DATABASE_HOSTNAME="localhost"
export NOSQL_DATABASE_NAME="health"
export NOSQL_DATABASE_USERNAME="sa"
export NOSQL_DATABASE_PASSWORD="sa"

export BINTRAY_KEY="c6890ca3d54aa153b255ef5f2e704ce482632e67"
export BINTRAY_USER="l3cobr"

./gradlew clean build -x test

./gradlew bintrayUpload