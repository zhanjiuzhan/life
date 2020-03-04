new Vue({
    el: '#user',
    data: {
        form: {
            name: '',
            password: '',
        },
        userInfo: [],
        search: '',
        drawer: false,
        direction: 'ltr',
        size: '10%',
        isMenuOpen: true,
        isMenuClose: true,
        dialogTableVisible: false,
        addUserDialog: false,
        editUserDialog: false,
        formLabelWidth: '120px',
        rules: {
            name: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
            password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
        },
        editUser: {
            id: "",
            userName: "",
            password: ""
        }
    },
    created() {
        console.log("初始化...");
        this.getUserInfo();
    },
    methods: {
        handleEdit(index, row) {
            let url = 'http://localhost:8081/note/auth/updateUser';
            console.log("修改: [index:" + index + ', userName:' +row.userName);
            this.editUser.id = row.id;
            this.editUser.userName = row.userName;
            this.editUser.password = row.password;
            this.editUserDialog = true;
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
        },
        menuClose() {
            console.log('menuClose');
            this.isMenuClose=true;
            this.isMenuOpne=false;
        },
        menuOpen() {
            console.log('menuOpen');
            this.isMenuClose=false;
            this.isMenuOpne=true;
        },
        openAddDialog(){
            this.addUserDialog = true;
            this.form.name='';
            this.form.password='';
        },
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
                this.addUserDialog = false,
                this.getUserInfo();
            }).catch(function (error) {
                console.log(error);
                this.$message.error('用户添加失败');
            });
        },
        editSubmit() {
            console.log("editSubmit");
            // this.editUser  为编辑的参数
            /*axios.post(url,
               row
           ).then ((res) => {
               console.log(res.data);
               this.$message({
                   message: '用户修改成功',
                   type: 'success'
               });
               this.getUserInfo();
           }).catch(function (error) {
               console.log(error);
               this.$message.error('用户修改失败');
           });*/
        }
    }
});