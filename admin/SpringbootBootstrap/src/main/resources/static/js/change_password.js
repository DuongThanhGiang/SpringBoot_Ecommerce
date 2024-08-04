$(document).ready(function (){
    $('#btnUpdate').on("click",function (e){
        e.preventDefault();
        $.ajax({
            type : 'POST',
            url : '/khach_hang/validateChangePassword',
            processData : false,
            data: JSON.stringify({
                matKhau : $('#matKhau').val(),
                matKhauMoi : $('#matKhauMoi').val(),
            }),
            contentType: "application/json",
            dataType: "json",
            success:function (data){
                if(data.success){
                    $('#formUpdate').submit();
                    Swal.fire({
                        position: "center",
                        icon: "success",
                        title: "Update thành công",
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