(function () {

    var module = angular.module("app.book");

    var controller = function() {
        var model = this;

        model.$routerOnActivate = function(next, previous) {
            model.id = next.params.id;
        };
    };

    module.component("bookDetails", {
        bindings: {
            "$router": "<"
        },
        templateUrl: "/app/book/book-details.component.html",
        controllerAs: "model",
        controller: [controller]
    });

} ());
