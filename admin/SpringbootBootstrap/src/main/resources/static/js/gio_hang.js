$(document).ready(function () {
    $("#thanhToan").on("click",function (e){
        let listGioHang = [];
        e.preventDefault();
        $(".SLSanPham").each(function (i,sanPham){
            if(sanPham.checked){
                let id = sanPham.getAttribute("id");
                let soLuong = $("#"+id).val();
                listGioHang.push({idspct:id,soLuong:soLuong});
            }
        });
        $.ajax({
            type:"POST",
            url:"/validateGioHang",
            processData: false,
            data:JSON.stringify(listGioHang),
            contentType: "application/json",
            dataType: "json",
            success:function (data){
                if(data.status==200){
                    console.log("heloo")
                    window.location.href="/shop/thanh-toan";
                }else {
                    Swal.fire({
                        icon:"error",
                        title:"Error",
                        text:"Sản phẩm"+data.idspct+"có"+data.soLuong+" sản phẩm",
                    })
                }
            },
            error:function (e){
                console.log(e);
            }
        })
    })
})