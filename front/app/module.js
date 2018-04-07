(function() {
    "use strict";

    /*  var module = angular.module("psBooks", ["ngRoute"]);

       module.config(function($routeProvider) {

       $routeProvider
            .when("/list", { template: "<book-list></book-list>"})
            .otherwise({ redirectTo: "/list"});

    });
  */

    var module = angular.module("app", ["ngComponentRouter", "ngAnimate", "app.common", "app.book"]);
    module.value("$routerRootComponent", "frontApp");

}());
