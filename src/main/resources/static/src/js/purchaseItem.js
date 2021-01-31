var currentUrl = window.location.pathname;
baseUrl = currentUrl.replace("/items","");
new Vue({
  el: '#purchase-items-list',
  data() {
    return {
      purchaseItems : null,
      currentId : -1,
      editMode  : false,
      transactions : null,
      cashList :null,
      purchase : null,
      total : null,
      totalPayed :null,
      totalPrice :null
    };
  },
  watch: {
      purchaseItems: {
      handler(val, oldVal) {
            console.log('book list changed');
                var sum = 0;
                if(this.purchaseItems == null) this.totalPrice = sum;
                this.purchaseItems.forEach(e => {
                     sum += e.price*e.quantity;
                });
                this.totalPrice = sum;
                this.total = this.totalPrice - this.totalPayed;
          },
          deep: true
      },

      transactions :{
          deep: true,
               // We have to move our method to a handler field
          handler(){
             var sum = 0;
             if(this.transactions == null) this.totalPayed = sum;
             this.transactions.forEach(e => {
               sum += e.amount;
             });
             this.totalPayed = sum;
             this.total = this.totalPrice - this.totalPayed;

         }
      },
  },
  mounted() {
    console.log(baseUrl);
    axios
        .get(baseUrl+"/getitems")
        .then(response => (
            this.purchaseItems = response.data
        ));
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

//        total = this.totalPrice - this.totalPayed;
  },
  methods: {
          savePurchaseItem: function (idx) {
              axios
                   .post(baseUrl+"/updateitem", this.purchaseItems[idx])
                   .then(response => {
                       this.purchaseItems[idx] = response.data;
                       this.total = this.totalPrice - this.totalPayed;
                       this.editMode = false;
//                          console.log(response.data);
                   })
                   .catch(exception => {
                       console.log(exception.response)
                   })

          },
          deletePurchaseItem: function (id,idx) {
              axios
                   .delete(baseUrl+"/deleteitem",  { params: { id: id } })
                   .then(response => {
                       this.purchases.splice(idx, 1);
                       this.editMode = false;
          //                          console.log(response.data);
                   })
                   .catch(exception => {
                       console.log(exception.response)
                   })
          },
          resetPurchaseItems: function(){
            axios
                .get(baseUrl+"/getitems")
                .then(response => {
                  this.purchaseItems = response.data;
                  this.editMode = false
                });
          },
          deleteTransaction: function (id,idx) {
                        axios
                             .delete(baseUrl+"/deletetransaction",  { params: { id: id } })
                             .then(response => {
                                 this.transactions.splice(idx, 1);
                                 axios
                                      .get("/cash")
                                      .then(response => (
                                          this.cashList = response.data
                                      ));
          //                       this.editMode = false;
                    //                          console.log(response.data);
                             })
                             .catch(exception => {
                                 console.log(exception.response)
                             })
                    },

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