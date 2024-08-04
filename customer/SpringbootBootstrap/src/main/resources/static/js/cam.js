function domReady(fn) {
    document.addEventListener('DOMContentLoaded', function () {
        var tabs = document.querySelectorAll('button[data-bs-toggle="tab"]');
        tabs.forEach(function (tab) {
            tab.addEventListener('click', function () {
                let maHD = this.getAttribute('data-hd-ma');
                scanQR(maHD);
                sumbitQR(maHD,fn);

                seacrchModalVCAndKH(maHD);
                $('#submitQr'+maHD).on("click",function (e){
                    e.preventDefault();
                    let idhd = $('#idhd'+maHD).val();
                    let idspct = $('#idCTSP'+maHD).val();
                    let soLuong = $('#soLuong'+maHD).val();
                    let soLuongError = $('#soLuongError'+maHD);
                    let modalResultQr = document.getElementById("modalResultQr" + maHD);
                    $.ajax({
                        type:"POST",
                        url:"/validate",
                        processData: false,
                        data:JSON.stringify({
                            idhd : idhd,
                            idspct : idspct,
                            soLuong: soLuong
                        }),
                        contentType: "application/json",
                        dataType: "json",
                        success:function (data){
                            if(data.status==200){
                                $('#form'+maHD).submit();
                                modalResultQr.style.display = "none";
                                Swal.fire({
                                    position: "top-end",
                                    icon: "success",
                                    title: "Thêm thành công",
                                    showConfirmButton: false,
                                    timer: 1500
                                });
                            }else {
                                 soLuongError.text(data.errorSoLuong);
                            }
                        },
                        error:function (e){
                            console.log(e);
                        }
                    })
                })
                $('#btnTTHD'+maHD).on("click",function (e){
                    e.preventDefault();

                    Swal.fire({
                        title: "Are you sure?",
                        text: "Bạn có chắc muốn thanh toán không?",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#3085d6",
                        cancelButtonColor: "#d33",
                        confirmButtonText: "Yes"
                    }).then((result) => {
                        if (result.isConfirmed) {

                            Swal.fire({
                                title: "Thành công!",
                                text: "Bạn đã thanh toán thành công",
                                icon: "success"
                            });
                            window.location.href= $('#btnTTHD'+maHD).attr("href");
                        }
                    });
                })
                $(".formGioHang"+maHD).each(function (i,formGioHang){
                    let id = formGioHang.getAttribute("id");
                    $("#btn"+id).on("click",function (e){
                        e.preventDefault();
                        let soLuong = $("#soLuong"+id).val();
                        let ma = $("#ma"+id).text();
                        console.log(ma);
                        $.ajax({
                            type:"POST",
                            url:"/validate1",
                            processData: false,
                            data:JSON.stringify({
                                idHoaDonChiTiet : id,
                                ma : ma,
                                soLuong : soLuong
                            }),
                            contentType: "application/json",
                            dataType: "json",
                            success:function (data){
                                if(data.status==200){
                                    formGioHang.submit();
                                    Swal.fire({
                                        position: "top-end",
                                        icon: "success",
                                        title: "Sửa thành công",
                                        showConfirmButton: false,
                                        timer: 1500
                                    });
                                }else {
                                    Swal.fire({
                                        icon:"error",
                                        title:"Error",
                                        text:data.errorSoLuong,
                                    })
                                }
                            },
                            error:function (e){
                                console.log(e);
                            }
                        })
                    })
                })
            });
        });
        // Chọn tab đầu tiên
        var firstTab = tabs[0];
        firstTab.click();
    });

}
function scanQR(maHD) {
    let code = document.getElementById("idCTSP" + maHD);
    let modalResultQr = document.getElementById("modalResultQr" + maHD);
    let span = document.getElementsByClassName("close")[0];
    span.onclick = function() {
        modalResultQr.style.display = "none";
    }
    // If found you qr code
    function onScanSuccess(decodeText, decodeResult) {
        code.setAttribute("value", decodeText);
        modalResultQr.style.display = "block";
    }


    let idScanner = 'my-qr-reader' + maHD;
    let htmlscanner = new Html5QrcodeScanner(
        idScanner,
        {fps: 10, qrbos: 250}
    );

    htmlscanner.render(onScanSuccess);

}

function sumbitQR(maHD,fn) {
    if (
        document.readyState === "complete" ||
        document.readyState === "interactive"
    ) {
        setTimeout(fn, 1000);
    } else {
        document.addEventListener("DOMContentLoaded", fn);
    }

}

<!--    JS search for modal Voucher -->

function seacrchModalVCAndKH(maHD) {

    const searchInputKhachHang = document.getElementById("searchInputKhachHang"+ maHD);
    const khachHangs = Array.from(document.getElementsByClassName("card-khachHang"+ maHD));
    searchInputKhachHang.addEventListener("input", function () {
        const searchTerm = searchInputKhachHang.value.trim().toLowerCase();
        khachHangs.forEach(function (khachHang) {
            const khachHangCardId = khachHang.id;
            const maKhachHang = document.querySelector("#" + khachHangCardId + " .modal-maKhachHang").textContent.trim().toLowerCase();
            const sdtKhachHang = document.querySelector("#" + khachHangCardId + " .modal-sdtKhachHang").textContent.trim().toLowerCase();
            const tenKhachHang = document.querySelector("#" + khachHangCardId + " .modal-tenKhachHang").textContent.trim().toLowerCase();
            const isVisible = sdtKhachHang.includes(searchTerm) || tenKhachHang.includes(searchTerm) || maKhachHang.includes(searchTerm);

            if (isVisible) {
                khachHang.style.display = "block";
            } else {
                khachHang.style.display = "none";
            }
        });
    });
}

// xac nhan xoá
function xacNhanXoa(idhdct){
    Swal.fire({
        title: "Are you sure?",
        text: "Bạn muốn xoá giỏ hàng?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                title: "Deleted!",
                text: "Your file has been deleted.",
                icon: "success"
            });
            window.location.assign("http://localhost:8080/hoa_don_chi_tiet/delete/"+idhdct);
        }
    });
}

domReady();
