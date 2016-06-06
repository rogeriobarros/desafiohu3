(function() {
    'use strict';
    angular
        .module('desafiohu3App')
        .factory('TravelPackages', TravelPackages);

    TravelPackages.$inject = ['$resource'];

    function TravelPackages ($resource) {
        var resourceUrl =  'api/travel-packages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
