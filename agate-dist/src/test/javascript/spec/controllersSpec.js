'use strict';

describe('Controllers Tests ', function () {

  beforeEach(module('agate'));

  describe('LoginController', function () {
    var $scope;


    beforeEach(inject(function ($rootScope, $controller) {
      $scope = $rootScope.$new();
      $controller('LoginController', {$scope: $scope});
    }));

    it('should set remember Me', function () {
      expect($scope.rememberMe).toBeTruthy();
    });
  });

  describe('SettingsController', function () {
    var $scope, AccountService;

    beforeEach(inject(function ($rootScope, $controller, Account) {
      $scope = $rootScope.$new();

      AccountService = Account;
      $controller('SettingsController', {$scope: $scope, resolvedAccount: AccountService, Account: AccountService});
    }));

    it('should save account', function () {
      //GIVEN
      $scope.settingsAccount = {firstName: "John", lastName: "Doe"};

      //SET SPY
      spyOn(AccountService, 'save');

      //WHEN
      $scope.save();

      //THEN
      expect(AccountService.save).toHaveBeenCalled();
      expect(AccountService.save).toHaveBeenCalledWith({firstName: "John", lastName: "Doe"}, jasmine.any(Function), jasmine.any(Function));

      //SIMULATE SUCCESS CALLBACK CALL FROM SERVICE
      AccountService.save.calls.mostRecent().args[1]();
      expect($scope.error).toBeNull();
      expect($scope.success).toBe('OK');
    });
  });

  describe('SessionsController', function () {
    var $scope, SessionsService;

    beforeEach(inject(function ($rootScope, $controller, Sessions) {
      $scope = $rootScope.$new();

      SessionsService = Sessions;
      $controller('SessionsController', {$scope: $scope, resolvedSessions: SessionsService, Sessions: SessionsService});
    }));

    it('should invalidate session', function () {
      //GIVEN
      $scope.series = "123456789";

      //SET SPY
      spyOn(SessionsService, 'delete');

      //WHEN
      $scope.invalidate($scope.series);

      //THEN
      expect(SessionsService.delete).toHaveBeenCalled();
      expect(SessionsService.delete).toHaveBeenCalledWith({series: "123456789"}, jasmine.any(Function), jasmine.any(Function));

      //SIMULATE SUCCESS CALLBACK CALL FROM SERVICE
      SessionsService.delete.calls.mostRecent().args[1]();
      expect($scope.error).toBeNull();
      expect($scope.success).toBe('OK');
    });
  });
});
