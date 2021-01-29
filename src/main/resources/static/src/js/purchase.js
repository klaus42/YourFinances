new Vue({
  el: '#purchases-list',
  data() {
    return {
      purchases : null,
      currentId : -1,
      editMode  : false
    };
  },
  mounted() {
    axios
      .get('/user/purchases')
      .then(response => (
        this.purchases = response.data
      ));
  },
  methods: {
          savePurchase: function (e) {
              axios
                      .post('/user/purchase', this.purchases[e])
                      .then(response => {
                          this.purchases[e] = response.data;
                          this.editMode = false;
//                          console.log(response.data);
                      })
                      .catch(exception => {
                          console.log(exception.response)
                      })

          },
          deletePurchase: function (id,idx) {
                        axios
                                .delete('/user/purchase',  { params: { id: id } })
                                .then(response => {
                                    this.purchases.splice(idx, 1);
                                    this.editMode = false;
          //                          console.log(response.data);
                                })
                                .catch(exception => {
                                    console.log(exception.response)
                                })

                    }

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

//let token = document.head.querySelector('meta[name="csrf-token"]');

if (token) {
    axios.defaults.headers.common['X-CSRF-TOKEN'] = token;
} else {
    console.error('CSRF token not found: https://laravel.com/docs/csrf#csrf-x-csrf-token');
}