(function() {
    "use strict";

    var module = angular.module("app.book");

    function createNewBook(bookService, bookToCreate) {
        return bookService.createBook(bookToCreate);
    }

    function controller(bookService) {

        var model = this;
        model.newBook = {
            name: ""
        };

        model.$onInit = function() {
        };

        model.addBook = function() {
            createNewBook(bookService, model.newBook).then(function(result) {
                console.log(result);
                model.$router.navigate(["List"]);
            });
        };
    }

    module.component("bookCreate", {
        bindings: {
            "$router": "<"
        },
        templateUrl: "/app/book/book-create.component.html",
        controllerAs: "model",
        controller: ["bookService", controller]
    });

} ());
