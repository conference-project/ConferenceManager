(function () {
    "use strict";

    angular.module("app.common").factory("commonService", commonService);

    commonService.$inject = ["$http", "$location"];
    function commonService($http, $location) {

        /* API */
        return {
            get: doGet,
            post: doPost,
            put: doPut,
            delete: doDelete
        };

        /* Implementation */
        function doGet(url, successMsg, errorMsg) {
            return $http.get(url)
                .then(function (result) {
                    return handleSuccess(successMsg, result);
                }, function (message) {
                    handleException(errorMsg, message);
                });
        }

        function doPost(url, body, successMsg, errorMsg) {
            return $http.post(url, body)
                .then(function (result) {
                    return handleSuccess(successMsg, result);
                }, function (message) {
                    handleException(errorMsg, message);
                });
        }

        function doPut(url, body, successMsg, errorMsg) {
            return $http.put(url, body)
                .then(function (result) {
                    return handleSuccess(successMsg, result);
                }, function (message) {
                    handleException(errorMsg, message);
                });
        }

        function doDelete(url, successMsg, errorMsg) {
            return $http.delete(url)
                .then(function (result) {
                    return handleSuccess(successMsg, result);
                }, function (message) {
                    handleException(errorMsg, message);
                });
        }

        function handleSuccess(successMsg, result) {
            if (successMsg) {
                //logger.info(successMsg, result, $translate.instant("MESSAGE.SUCCESS"));
                console.log(successMsg);
            }
            console.log(result.data);
            return result.data;
        }

        function handleException(errorMsg, message) {
            if (errorMsg) {
                //if (message && message.data) {
                //    exception.getCatcher($translate.instant("MESSAGE.SERVER_ERROR"))(message.data.error);
                //}
                //exception.getCatcher(errorMsg)(message);
                // redirection
                console.log(errorMsg + " | " + message);
                // $location.url('/');
            }
        }
    }
})();
