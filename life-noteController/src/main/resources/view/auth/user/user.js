new Vue({
    el: '#user',
    data: {
        form: {
            name: '',
            password: '',
        },
        userInfo: [],
    },
    created() {
        console.log("初始化...");
        this.getUserInfo();
    },
    methods: {
        onSubmit() {
            let userName = this.form.name;
            let password = this.form.password;
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
                this.getUserInfo();
            }).catch(function (error) {
                console.log(error);
                this.$message.error('用户添加失败');
            });
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