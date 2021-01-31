var currentUrl = window.location.pathname;
baseUrl = currentUrl.replace("/items","");
new Vue({
  el: '#purchase-transactions',
  data() {
    return {
      currentId : -1,
      editMode  : false,
      transactions : null,
      cashList :null,
      purchase : null
    };
  },
  mounted() {
    axios
        .get(baseUrl+"/gettransactions")
        .then(response => (
            this.transactions = response.data
        ));

    axios
        .get("/cash")
        .then(response => (
            this.cashList = response.data
        ));
    axios
        .get(baseUrl)
        .then(response => (
            this.purchase = response.data
        ));
  },
  methods: {
//          savePurchaseItem: function (idx) {
//              axios
//                   .post(baseUrl+"/updateitem", this.purchaseItems[idx])
//                   .then(response => {
//                       this.purchaseItems[idx] = response.data;
//                       this.editMode = false;
////                          console.log(response.data);
//                   })
//                   .catch(exception => {
//                       console.log(exception.response)
//                   })
//
//          },
          deleteTransaction: function (id,idx) {
              axios
                   .delete(baseUrl+"/deletetransaction",  { params: { id: id } })
                   .then(response => {
                       this.transactions.splice(idx, 1);
//                       this.editMode = false;
          //                          console.log(response.data);
                   })
                   .catch(exception => {
                       console.log(exception.response)
                   })
          },
//          resetPurchaseItems: function(){
//            axios
//                .get(baseUrl+"/getitems")
//                .then(response => {
//                  this.purchaseItems = response.data;
//                  this.editMode = false
//                });
//          }

      },
});
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

/**
 * Next we will register the CSRF Token as a common header with Axios so that
 * all outgoing HTTP requests automatically have it attached. This is just
 * a simple convenience so we don't have to attach every token manually.
 */

if (token) {
    axios.defaults.headers.common['X-CSRF-TOKEN'] = token;
} else {
    console.error('CSRF token not found: https://laravel.com/docs/csrf#csrf-x-csrf-token');
}