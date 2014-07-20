angular.module('ot.directive.entity', [
    'ot.service.core'
])
    .directive('otEntityImage', function () {
        return {
            restrict: 'E',
            templateUrl: 'app/directive/directive.entity.tpl.html',
            transclude: true,
            scope: {
                entity: '=',
                link: '@'
            }
        };
    })
    .directive('otEntityDecorations', function ($http, ot) {
        return {
            restrict: 'E',
            templateUrl: 'app/directive/directive.entityDecorations.tpl.html',
            transclude: true,
            scope: {
                entity: '='
            },
            link: function (scope) {
                scope.$watch('entity', function () {
                    if (scope.entity) {
                        ot.call($http.get(scope.entity._decorations)).then(function (decorations) {
                            scope.decorations = decorations;
                            scope.decorationImagePath = function (decoration) {
                                return 'assets/extension/decoration/' + decoration.decorationType + '/' + decoration.id + '.png';
                            };
                            scope.decorationClassName = function (decoration) {
                                return (decoration.decorationType + '.' + decoration.id).replace(/\./g, '-');
                            };
                        });
                    }
                });
            }
        };
    })
    .directive('otEntityExtra', function ($http, ot) {
        return {
            restrict: 'E',
            templateUrl: 'app/directive/directive.entityExtra.tpl.html',
            scope: {
                entity: '='
            },
            link: function (scope) {
                scope.$watch('entity', function () {
                    if (scope.entity) {
                        scope.infosLoading = true;
                        ot.call($http.get(scope.entity._extra)).then(function (infos) {
                            scope.infosLoading = false;
                            scope.infos = infos;
                        });
                    }
                });
            }
        };
    })
    .directive('otBuildPromotionRuns', function () {
        return {
            restrict: 'E',
            templateUrl: 'app/directive/directive.buildPromotionRuns.tpl.html',
            scope: {
                promotionRuns: '='
            }
        };
    })
    .directive('otValidationRunStatus', function () {
        return {
            restrict: 'E',
            templateUrl: 'app/directive/directive.validationRunStatus.tpl.html',
            scope: {
                status: '='
            }
        };
    })
    .directive('otValidationStampRunView', function () {
        return {
            restrict: 'E',
            templateUrl: 'app/directive/directive.validationStampRunView.tpl.html',
            scope: {
                validationStampRunView: '='
            }
        };
    })
    .directive('otValidationRunStatusNone', function () {
        return {
            restrict: 'E',
            templateUrl: 'app/directive/directive.validationRunStatusNone.tpl.html'
        };
    })
    .directive('otSignature', function () {
        return {
            restrict: 'E',
            templateUrl: 'app/directive/directive.signature.tpl.html',
            scope: {
                value: '=',
                user: '=',
                time: '='
            }
        };
    })
;