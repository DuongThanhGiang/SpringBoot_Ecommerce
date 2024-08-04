$(document).ready(function (){
    $('#btnRegis').on("click",function (e){
        e.preventDefault();
        $.ajax({
            type:'POST',
            url : '/khach_hang/validateRegistration',
            processData : false,
            data: JSON.stringify({
                ten : $('#ten').val(),
                email : $('#email').val(),
                ngaySinh:$('#ngaySinh').val(),
                matKhau : $('#matKhau').val(),
                sdt : $('#sdt').val()
            }),
            contentType: "application/json",
            dataType: "json",
            success:function (data){
                if(data.success){
                    $('#formRegis').submit();
                    Swal.fire({
                        position: "center",
                        icon: "success",
                        title: "Đăng ký thành công",
                        showConfirmButton: false,
                        timer: 1500
                    });
                }else {
                    Swal.fire({
                        icon:"error",
                        title:"Error",
                        text:data.message,
                    })
                }

            },
            error:function (e) {
                console.log(e);
            }
        })
    })
})