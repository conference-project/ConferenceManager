(function() {
    "use strict";

    var module = angular.module("app.book");

    function fetchBooks(bookService) {
        return bookService.getbooks();
    }

    function controller(bookService) {

        var model = this;
        model.books = [];

        model.$onInit = function() {
            fetchBooks(bookService).then(function(books) {
                model.books = books;
            });
        };

        model.goTo = function(id) {
            model.$router.navigate(["Details", {id:id}, "Overview"]);
        };
    }

    module.component("bookList", {
        bindings: {
            "$router": "<"
        },
        templateUrl: "/app/book/book-list.component.html",
        controllerAs: "model",
        controller: ["bookService", controller]
    });

} ());
