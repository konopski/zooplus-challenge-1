var gulp = require('gulp');
var webpack = require('webpack-stream');

var config = {
    "node": "./node_modules/",
    "libs": "./src/main/webapp/libs/",
    "src": "./src/main/jsx/",
    "app": "./src/main/webapp/app/"
};

gulp.task('default', ['copy:libs', 'webpack']);

gulp.task('copy:libs', function () {
    gulp.src(config.node + 'bootstrap/dist/**').pipe(gulp.dest(config.libs + 'bootstrap'));
    gulp.src(config.node + 'jquery/dist/**').pipe(gulp.dest(config.libs + 'jquery'));
    gulp.src(config.node + 'moment/min/**').pipe(gulp.dest(config.libs + 'moment'));
});

gulp.task('webpack', function () {
    return gulp.src(config.src + 'app.jsx')
        .pipe(webpack(require('./webpack.config.js')))
        .pipe(gulp.dest(config.app));
});

