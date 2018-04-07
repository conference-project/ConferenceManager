(function() {
    "use strict";

    var module = angular.module("app");

    module.component("frontApp", {
        templateUrl: "/app/front-app.component.html",
        $routeConfig: [
            { path: "/list", component:"bookList", name: "List" },
            { path: "/create", component:"bookCreate", name: "Create" },
            { path: "/about", component: "about", name: "About" },
            { path: "/details/:id", component: "bookDetails", name:"Details" },
            { path: "/**", redirectTo: ["List", ""] }
        ]
    });

    module.component("about", {
       template: "About us ..."
    });

}());
