var myuser = new Vue({
    el: '#user',
    data: {

        form: {
            name: '',
        },
        userInfo: [],
        search: ''
    },

    created() {
        console.log("初始化...");
        this.getUserInfo();
    },
    methods: {
        handleEdit(index, row) {

            let url = 'http://localhost:8081/note/auth/updateUser';
            axios.post(url,
                row
            ).then ((res) => {

                console.log(res.data);
                this.$message({
                    message: '用户修改成功',
                    type: 'success'
                });
                this.getUserInfo();
            }).catch(function (error) {
                alert("error");
                console.log(error);
                this.$message.error('用户修改失败');
            });

            console.log(index, row);
        },
        handleDelete(index, row) {
            console.log(index, row);
        },
        getUserInfo() {
            axios.get('http://localhost:8081/note/auth/getUsers'
            ).then ((res) => {
                console.log(res.data.data);
                this.userInfo = res.data.data;
            }).catch(function (error) {

                console.log(error);
                this.$message.error('获取用户信息失败');
            });
        }
    }
});
new Vue({
    el: '#app',
    data: {
        form: {
            name: '',
            password: '',
        },
        //userInfo: [],
        dialogTableVisible: false,
        dialogFormVisible: false,
        formLabelWidth: '120px',
        rules: {
            name: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
            password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
        }
    },
    created() {
        console.log("初始化...");
    },
    methods: {
        onSubmit(formName) {
            let userName = this.form.name;
            let password = this.form.password;
            if(userName=="" || password==""){
                this.$refs[formName].validate();
                return false;
            }
            let url = 'http://localhost:8081/note/auth/addUser';
            console.log('[用户名: ' + userName + ', 密码: ' + password + '] submit!');
            axios.post(url,
                'userName='+ userName + '&password='+ password
            ).then ((res) => {
                console.log(res.data);
                this.$message({
                    message: '用户添加成功',
                    type: 'success'
                });
                this.dialogFormVisible = false,
                //window.location.reload();
                 this.getUserInfo();
            }).catch(function (error) {
                console.log(error);
                this.$message.error('用户添加失败');
            });
        },
        clear(){
            this.dialogFormVisible = true;
            this.form.name='';
            this.form.password='';
        },
         getUserInfo() {
            axios.get('http://localhost:8081/note/auth/getUsers'
            ).then ((res) => {
                console.log(res.data.data);
                myuser.userInfo = res.data.data;
            }).catch(function (error) {
                console.log(error);
                this.$message.error('获取用户信息失败');
            });
        }

    }
});