
angular.module("myapp")
.controller('ModalDemoCtrl', function ($uibModal, $log, $scope) {
  var pc = this;
  pc.data = "/pdf/invoice/" + $scope.$parent.invoice_id; 

  pc.open = function (size) {
    var modalInstance = $uibModal.open({
      animation: true,
      ariaLabelledBy: 'modal-title',
      ariaDescribedBy: 'modal-body',
      templateUrl: 'template/invoice/invoiceModalContent.html',
      controller: 'ModalInstanceCtrl',
      controllerAs: 'pc',
      size: size,
      resolve: {
        data: function () {
          return pc.data;
        }
      }
    });

    modalInstance.result.then(function () {
      alert("now I'll close the modal");
    });
  };
});

angular.module("myapp")
.controller('ModalInstanceCtrl', function ($uibModalInstance, data) {
  var pc = this;
  pc.data = data;
  
  pc.ok = function () {
    //{...}
    $uibModalInstance.close();
  };

  pc.cancel = function () {
    //{...}
    $uibModalInstance.dismiss('cancel');
  };
});
