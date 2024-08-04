$(document).ready(function () {

    messegesXN();
    messegesCancel();

})
function messegesCancel() {
    $(document).on('click','#btnHuy',function (e) {


        if (!validateMoTaCancel() ) {

            e.preventDefault();
            return;
        }
        e.preventDefault();
        $("#formCancelTL").off("submit");

        Swal.fire({
            title: "Are you sure?",
            text: "Bạn có đồng ý muốn hủy đơn không?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        }).then((result) => {
            if (result.isConfirmed) {
                $("#formCancelTL").submit();
                Swal.fire({
                    position: "top-end",
                    icon: "success",
                    title: "Hủy đơn hàng thành công",
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });

    });

}
function messegesXN() {
    $(document).on('click','#btnXacNhan',function (e) {


        if (!validateMoTa()) {

            e.preventDefault();
            return;
        }
        e.preventDefault();
        $("#formXNTL").off("submit");
        Swal.fire({
            title: "Are you sure?",
            text: "Xác nhận đã nhận được hàng?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        }).then((result) => {
            if (result.isConfirmed) {
                $("#formXNTL").submit();
                Swal.fire({
                    position: "top-end",
                    icon: "success",
                    title: "Xác nhận thành công",
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });

    });


}
function validateMoTa() {
    var sl = document.querySelector("#ghiChu").value;
    if (sl.trim() === "" ) {
        Swal.fire({
            title: "Error!",
            text: "Mô tả không được để trống",
            icon: "warning",

        });
        return false;
    }


    return true;
}

function validateMoTaCancel() {
    var sl = document.querySelector("#ghiChuHuy").value;
    if (sl.trim() === "" ) {
        Swal.fire({
            title: "Error!",
            text: "Mô tả không được để trống",
            icon: "warning",

        });
        return false;
    }


    return true;
}