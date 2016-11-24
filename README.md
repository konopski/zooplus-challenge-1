# ZooPlus Challenge

[![Build Status](https://travis-ci.org/alexcibotari/zooplus-challenge.svg?branch=master)](https://travis-ci.org/alexcibotari/zooplus-challenge)

## 0. Pre-requirements
+ Java 8
+ NodeJS
+ MySQL with schema 'zooplus' for production environment

## 1. Setup
### Install Node Packages (see package.json)
- Install Global packages `npm install gulp-cli -g`
- Install Local packages `npm install`

## 2. Build 
### Build and Copy UI dependencies
- run `gulp`

### Build Java
- run `./gradlew assemble`

## 2. Run
### Spring Boot
Run `./gradlew bootRun`

## 3. Default Application credentials
admin / admin 